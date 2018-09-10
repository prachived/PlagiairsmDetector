package com.plagiarism.detector.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.plagiarism.detector.model.Result;
import com.plagiarism.detector.model.User;
import com.plagiarism.detector.repo.UserRepository;
import com.plagiarism.detector.strategies.ComparisonStrategy;
import com.plagiarism.detector.strategies.FunctionalityFactory;
import com.plagiarism.detector.strategies.WeightedResults;
import com.plagiarism.detector.utilities.AmazonClient;
import com.plagiarism.detector.utilities.FileUtility;
import com.plagiarism.detector.utilities.SnippetGeneration;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * @author Prachi Controller for spring application
 *
 */
@Controller
// @RequestMapping("/storage/")
public class MainController {

	/**
	 * Constants for showing messages on screen
	 */
	private static final String USER_SUCCESS = "User Added Successfully";
	private static final String SUCCESS = "success";
	private static final String RESULT = "result";
	private static final String RUN_PAGE = "runPlagiarismPage";
	private static final String REDIRECTUPLOADFILE = "redirect:/uploadfiles";
	private static final String RESULT1 = "result1";
	/**
	 * user repository
	 */
	@Autowired
	public UserRepository userRepository;

	/**
	 * Mapping for login page
	 * 
	 * @returnmap to login page
	 */
	@RequestMapping("/login")
	public ModelAndView index() {
		return new ModelAndView("login", "user", new User());

	}

	/**
	 * When username or password is incoreect on login, the page is again
	 * redirected to the login page
	 * 
	 * @param username
	 * @param password
	 * @param model
	 * @param redirectAttributes
	 *            ::used to redirect
	 * @return post mapping for login
	 */
	@PostMapping("/login")
	public ModelAndView submit(@Valid @ModelAttribute("user") User user, BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("login", "user", user);

		List<User> users = userRepository.findByNameAndPassword(user.getName(), user.getPassword());
		if (!users.isEmpty()) {

			if (user.getName().equals("admin")) {
				user = new User();
				ModelAndView modelAndView1 = new ModelAndView("register", "user", user);
				return modelAndView1;
			} else {
				ModelAndView mv = new ModelAndView("/uploadfiles");
				mv.getModelMap().addAttribute("name", user.getName());
				return mv;
			}
		} else {

			String error = "User name/password incorrect";
			model.addAttribute("error", error);
			modelAndView.getModelMap().addAllAttributes(model);
			return modelAndView;
		}
	}

	/**
	 * Go on page to register a user
	 * 
	 * @return register mapping
	 */
	@RequestMapping("/register")
	public ModelAndView registerPage() {
		return new ModelAndView("register", "user", new User());

	}

	/**
	 * Register landing page
	 * 
	 * @return if successful redirect to register page
	 */
	@PostMapping("/register")
	public ModelAndView register(@Valid @ModelAttribute("user") User user, BindingResult result, ModelMap model) {
		ModelAndView modelAndView = new ModelAndView("register", "user", user);
		if (user.getName().equalsIgnoreCase("")) {
			String regFail = "User cannot be created. Please enter a valid name";
			model.addAttribute("regFail", regFail);
			modelAndView.getModelMap().addAllAttributes(model);
			return modelAndView;
		}

		if (user.getFname().equalsIgnoreCase("")) {
			String regFail = "User cannot be created. Please enter a valid first name";
			model.addAttribute("regFail", regFail);
			modelAndView.getModelMap().addAllAttributes(model);
			return modelAndView;
		}

		if (user.getLname().equalsIgnoreCase("")) {
			String regFail = "User cannot be created. Please enter a valid last name";
			model.addAttribute("regFail", regFail);
			modelAndView.getModelMap().addAllAttributes(model);
			return modelAndView;
		}

		if (user.getEmail().equalsIgnoreCase("") || !emailValid(user.getEmail())) {
			String regFail = "User cannot be created. Please enter a valid email";
			model.addAttribute("regFail", regFail);
			modelAndView.getModelMap().addAllAttributes(model);
			return modelAndView;
		}
		if (user.getPassword().equalsIgnoreCase("")) {
			String regFail = "User cannot be created. Please enter a valid password";
			model.addAttribute("regFail", regFail);
			modelAndView.getModelMap().addAllAttributes(model);
			return modelAndView;
		}
		try {
			userRepository.save(user);
			user = new User();
			ModelAndView modelAndView1 = new ModelAndView("register", "user", user);
			modelAndView1.getModelMap().addAttribute(SUCCESS, USER_SUCCESS);
			return modelAndView1;

		} catch (Exception e) {
			String regFail = "User cannot be created. Please enter some other username";
			model.addAttribute("regFail", regFail);
		}

		return modelAndView;
	}

	/**
	 * Function to vaidate email address
	 * 
	 * @param email
	 * @return true if email address matches regexp, false otherwise
	 */
	private boolean emailValid(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();

	}

	/**
	 * Mapping for delete user page
	 * 
	 * @return if delete successful redirect to register page
	 */
	@PostMapping("/delete")
	public ModelAndView delete(@Valid @ModelAttribute("user") User user, BindingResult result, ModelMap model) {

		ModelAndView modelAndView = new ModelAndView("register", "user", new User());
		List<User> users = userRepository.findByName(user.getName());
		if (!users.isEmpty()) {
			userRepository.delete(users);
			String deleteSuccess = "User deleted Successfully";
			modelAndView.getModelMap().addAttribute("deleteSuccess", deleteSuccess);
			return modelAndView;
		} else {
			String deleteUnSuccess = "No such user found";
			modelAndView.getModelMap().addAttribute("deleteUnSuccess", deleteUnSuccess);

		}

		return modelAndView;
	}

	/**
	 * Mapping for variable Check plagiarism page
	 * 
	 * @throws IOException
	 * 
	 * @returnmap run variable check
	 */
	@RequestMapping("/VariableCheck")
	public ModelAndView variableCheck(ModelMap model) throws IOException {
		FileUtility fileUtility = new FileUtility();
		fileUtility.deleteEC2File();
		ModelAndView mv = new ModelAndView(RUN_PAGE);
		FunctionalityFactory factory = FunctionalityFactory.instance();
		ComparisonStrategy comparisonStrategy = factory.makeVariableCheckInstance();
		List<Result> resultList = comparisonStrategy.getSimilarity();
		Result[] result = new Result[resultList.size()];
		for (int i = 0; i < resultList.size(); i++) {
			SnippetGeneration snippet = new SnippetGeneration();
			Result res = resultList.get(i);
			snippet.makeHTMLForVariableCheck(res.getFile1(), res.getFile2(), res.getOutcome());
			result[i] = resultList.get(i);

		}
		mv.addObject(RESULT1, new Result());
		mv.addObject(RESULT, result);
		mv.getModelMap().addAttribute("displayResult", "displayResult");
		return mv;
	}

	/**
	 * Mapping for variable Check plagiarism page
	 * 
	 * @throws IOException
	 * 
	 * @returnmap run variable check
	 */
	@RequestMapping("/CommentsCheck")
	public ModelAndView commentsCheck(ModelMap model) throws IOException {
		FileUtility fileUtility = new FileUtility();
		fileUtility.deleteEC2File();
		ModelAndView mv = new ModelAndView(RUN_PAGE);
		FunctionalityFactory factory = FunctionalityFactory.instance();
		ComparisonStrategy commentsCheck = factory.makeCommentsCheckInstance();
		List<Result> resultList = commentsCheck.getSimilarity();
		Result[] result = new Result[resultList.size()];
		for (int i = 0; i < resultList.size(); i++) {
			SnippetGeneration snippet = new SnippetGeneration();
			Result res = resultList.get(i);
			snippet.makeHTMLforCommentsCheck(res.getFile1(), res.getFile2(), res.getOutcome());
			result[i] = resultList.get(i);

		}
		mv.addObject(RESULT1, new Result());
		mv.addObject(RESULT, result);
		mv.getModelMap().addAttribute("displayResult", "displayResult");
		return mv;
	}

	/**
	 * Mapping for code move over check
	 * 
	 * @throws IOException
	 * 
	 * @returnmap run code move over
	 */
	@RequestMapping("/CodeMoveOver")
	public ModelAndView codeMoveOver(ModelMap model) throws IOException {
		FileUtility fileUtility = new FileUtility();
		fileUtility.deleteEC2File();
		ModelAndView mv = new ModelAndView(RUN_PAGE);
		FunctionalityFactory factory = FunctionalityFactory.instance();
		ComparisonStrategy codeMoveOverCheck = factory.makeCodeMoveOverCheckInstance();
		List<Result> resultList = codeMoveOverCheck.getSimilarity();
		Result[] result = new Result[resultList.size()];
		for (int i = 0; i < resultList.size(); i++) {

			SnippetGeneration snippet = new SnippetGeneration();
			Result res = resultList.get(i);
			snippet.makeHTML(res.getFile1(), res.getFile2(), res.getFile1lineNum(), res.getFile2lineNum());
			result[i] = resultList.get(i);

		}
		mv.addObject(RESULT1, new Result());
		mv.addObject(RESULT, result);
		mv.getModelMap().addAttribute("displayResult", "displayResult");
		return mv;

	}

	/**
	 * Mapping for modularity check page
	 * 
	 * @throws IOException
	 * 
	 * @returnmap run modularity check
	 */
	@RequestMapping("/ModularityCheck")
	public ModelAndView modularityCheck(ModelMap model) throws IOException {
		FileUtility fileUtility = new FileUtility();
		fileUtility.deleteEC2File();
		ModelAndView mv = new ModelAndView(RUN_PAGE);
		FunctionalityFactory factory = FunctionalityFactory.instance();
		ComparisonStrategy modularityCheck = factory.makeModularityCheckInstance();
		List<Result> resultList = modularityCheck.getSimilarity();
		Result[] result = new Result[resultList.size()];
		for (int i = 0; i < resultList.size(); i++) {
			SnippetGeneration snippet = new SnippetGeneration();
			Result res = resultList.get(i);
			snippet.makeHTMLForSummary(res.getFile1(), res.getFile2(), res.getSummary());
			result[i] = resultList.get(i);

		}
		mv.addObject(RESULT1, new Result());
		mv.addObject(RESULT, result);
		mv.getModelMap().addAttribute("displayResult", "displayResult");
		return mv;

	}

	/**
	 * Mapping for weighted average checking page
	 * 
	 * @param model
	 * @return run weighted avg
	 * @throws IOException
	 */
	@RequestMapping("/WeightedAvg")
	public ModelAndView weightedAvg(ModelMap model) throws IOException {
		FileUtility fileUtility = new FileUtility();
		fileUtility.deleteEC2File();
		ModelAndView mv = new ModelAndView(RUN_PAGE);
		WeightedResults weightedResults = new WeightedResults();
		List<Result> resultList = weightedResults.getWeightedSimilarity();
		Result[] result = new Result[resultList.size()];
		for (int i = 0; i < resultList.size(); i++) {
			SnippetGeneration snippet = new SnippetGeneration();
			Result res = resultList.get(i);
			snippet.makeHTMLForSummary(res.getFile1(), res.getFile2(), res.getSummary());
			result[i] = resultList.get(i);
		}
		mv.addObject(RESULT1, new Result());
		mv.addObject(RESULT, result);
		mv.getModelMap().addAttribute("displayResult", "displayResult");
		return mv;
	}

	/**
	 * Object of AmazonClient to enable S3 files access
	 */
	@Autowired
	private AmazonClient amazonClient;

	/**
	 * @param amazonClient
	 */
	MainController(AmazonClient amazonClient) {
		this.amazonClient = amazonClient;
	}

	/**
	 * constructor
	 */
	public MainController() {

	}
	
	/**
		 * Go to upload files page on clicking upload files
		 * 
		 * @param username
		 * 
		 * @return if successful redirect to upload files page
		 */
		@RequestMapping("/uploadfiles")
		public ModelAndView uploadfiles(@ModelAttribute("name") String name) {
			return new ModelAndView("uploadfiles", "user", new User(name));
		}

	/**
	 * Mapping for actions to perform on upload file page
	 * 
	 * @param user
	 * @param uploadfiles1
	 * @param uploadfiles2
	 * @param model
	 * @param redirectAttributes
	 * @return redirect to upload files
	 * @throws IOException
	 */
	@PostMapping("/uploadfiles")
	public String uploadedFiles(@Valid @ModelAttribute("user") User user,
			@RequestParam("filesStudent1") MultipartFile[] uploadfiles1,
			@RequestParam("filesStudent2") MultipartFile[] uploadfiles2, Model model,
			RedirectAttributes redirectAttributes) throws IOException {
		amazonClient.deleteObject();

		if (uploadfiles1.length == 1 && uploadfiles1[0].getOriginalFilename().equals("")
				|| uploadfiles2.length == 1 && uploadfiles2[0].getOriginalFilename().equals("")) {
			String error = "Please upload a folder";
			redirectAttributes.addFlashAttribute("error", error);
			return REDIRECTUPLOADFILE;
		}

		for (MultipartFile file : uploadfiles1) {
			this.amazonClient.uploadFileIndiv(file);
		}
		for (MultipartFile file : uploadfiles2) {
			this.amazonClient.uploadFileIndiv(file);
		}

		redirectAttributes.addFlashAttribute("name", user.getName());
		String uploadSuccessful = "Upload Successful";
		redirectAttributes.addFlashAttribute("uploadSuccessful", uploadSuccessful);
		return REDIRECTUPLOADFILE;
	}

	/**
	 * Mapping of page to upload multiple files at once
	 * 
	 * @param user
	 * @param uploadfiles
	 * @param model
	 * @param redirectAttributes
	 * @return redirect to upload files
	 * @throws IOException
	 */
	@PostMapping("/uploadfilesInBatch")
	public String uploadFilesInBatch(@Valid @ModelAttribute("user") User user,
			@RequestParam("filesStudent") MultipartFile[] uploadfiles, Model model,
			RedirectAttributes redirectAttributes) throws IOException {

		amazonClient.deleteObject();

		if (uploadfiles.length == 1 && uploadfiles[0].getOriginalFilename().equals("")) {
			String error = "Please upload a folder";
			redirectAttributes.addFlashAttribute("error1", error);
			return REDIRECTUPLOADFILE;
		}

		for (MultipartFile file : uploadfiles) {
			this.amazonClient.uploadFile(file);
		}
		redirectAttributes.addFlashAttribute("name", user.getName());
		String uploadSuccessful = "Upload Successful";
		redirectAttributes.addFlashAttribute("uploadSuccessfulBatch", uploadSuccessful);
		return REDIRECTUPLOADFILE;

	}

	/**
	 * Run Plagiarism check button mapping
	 * 
	 * @param user
	 * @param redirectAttributes
	 * @return run plagiarism page redirect
	 */
	@PostMapping("/runPlg")
	public String runPlagiarism(@Valid @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		final Logger logger = LogManager.getLogger();

		List<User> users = userRepository.findByName(user.getName());
		if (!users.isEmpty()) {
			User existingUser = userRepository.findById(users.get(0).getId());
			if (existingUser != null) {
				existingUser.setRunCount(existingUser.getRunCount() + 1);
				userRepository.save(existingUser);
				logger.info(String.format("Plagiarism check is run %s times by %s", existingUser.getRunCount(),
						existingUser.getName()));
				redirectAttributes.addFlashAttribute("name", user.getName());
			}

		}
		return RUN_PAGE;
	}

	/**
	 * Run Plagiarism check button mapping
	 * 
	 * @param user
	 * @param redirectAttributes
	 * @return run plagiarism page redirect
	 * @throws IOException
	 */

	@Autowired
	ServletContext context;

	/**
	 * Download's report for a particular student
	 * 
	 * @param res
	 * @param result
	 * @param response
	 * @throws IOException
	 */
	@PostMapping("/download")
	public void download(@Valid @ModelAttribute("result1") Result res, BindingResult result,
			HttpServletResponse response) throws IOException {

		final Logger logger = LogManager.getLogger();
		String file1 = res.getFile1().replace("/", "").replace("\\", "");
		String file2 = res.getFile2().replace("/", "").replace("\\", "");
		String currpath = Paths.get("").toAbsolutePath().toString();
		currpath = currpath + "/results/" + file1 + file2 + ".html";
		Path file = Paths.get(currpath);
		// Check if file exists
		if (file.toFile().exists()) {
			// set content type
			response.setContentType("application/pdf");
			// add response header
			response.addHeader("Content-Disposition", "attachment; filename=" + file1 + file2 + ".html");
			try {
				// copies all bytes from a file to an output stream
				Files.copy(file, response.getOutputStream());
				// flushes output stream
				response.getOutputStream().flush();
			} catch (IOException e) {
				logger.info("Error :- " + e.getMessage());
			}
		} else {
			logger.info("Sorry File not found!!!!");
		}

	}

}
