import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.plagiarism.detector.controller.MainController;
import com.plagiarism.detector.utilities.AmazonClient;
import com.plagiarism.detector.utilities.FileUtility;

/**
 * Tests for the Controller class
 * 
 * @author ashuk
 *
 */
public class TestController {

	/**
	 * amazon client to test
	 */
	AmazonClient s3 = new AmazonClient();

	/**
	 * setup before
	 */
	@Before
	public void setUp() {
		s3.initializeAmazon();
	}

	/**
	 * delete resources
	 * 
	 * @throws IOException
	 */
	@After
	public void tearDown() throws IOException {

		s3.deleteObject();
	}

	/**
	 * test for login feature.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testLogin() throws IOException {
		MainController helloCon = new MainController();
		ModelAndView modelAndView = helloCon.index();
		assertEquals("login", modelAndView.getViewName());
	}

	/**
	 * test for register feature
	 * 
	 * @throws IOException
	 */
	@Test
	public void testRegister() throws IOException {
		MainController helloCon = new MainController();
		ModelAndView modelAndView = helloCon.registerPage();
		assertEquals("register", modelAndView.getViewName());
	}

	/**
	 * test for accessibility of modularity check.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testmodularity() throws IOException {
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath + "//BinarySearch1.py";
		File file1 = new File(source1);
		String source2 = currpath + "//BinarySearch2.py";
		File file2 = new File(source2);
		s3.uploadFileTos3bucketIndv("student1/BinarySearch1.py", file1);
		s3.uploadFileTos3bucketIndv("student2/BinarySearch2.py", file2);
		MainController helloCon = new MainController();
		ModelAndView modelAndView = helloCon.modularityCheck(null);
		assertEquals("runPlagiarismPage", modelAndView.getViewName());
	}

	/**
	 * test for accessibility of weighted plagiarism check.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testWeighted() throws IOException {
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath + "//BinarySearch1.py";
		File file1 = new File(source1);
		String source2 = currpath + "//BinarySearch2.py";
		File file2 = new File(source2);
		s3.uploadFileTos3bucketIndv("student1/BinarySearch1.py", file1);
		s3.uploadFileTos3bucketIndv("student2/BinarySearch2.py", file2);
		MainController helloCon = new MainController();
		ModelAndView modelAndView = helloCon.weightedAvg(null);
		assertEquals("runPlagiarismPage", modelAndView.getViewName());
	}

	/**
	 * test for accessibility of variable name change check.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testVariable() throws IOException {
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath + "//BinarySearch1.py";
		File file1 = new File(source1);
		String source2 = currpath + "//BinarySearch2.py";
		File file2 = new File(source2);
		FileUtility fu=new FileUtility();
		fu.writeFile(currpath+"/uploads/student1/BinarySearch1.py", fu.readFile(source1));
		fu.writeFile(currpath+"/uploads/student2/BinarySearch2.py", fu.readFile(source2));
		s3.uploadFileTos3bucketIndv("student1/BinarySearch1.py", file1);
		s3.uploadFileTos3bucketIndv("student2/BinarySearch2.py", file2);
		MainController helloCon = new MainController();
		ModelAndView modelAndView = helloCon.variableCheck(null);
		assertEquals("runPlagiarismPage", modelAndView.getViewName());
	}

	/**
	 * test for accessibility of code move over check.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testcodeMoveOver() throws IOException {
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath + "//BinarySearch1.py";
		File file1 = new File(source1);
		String source2 = currpath + "//BinarySearch2.py";
		File file2 = new File(source2);
		FileUtility fu=new FileUtility();
		fu.writeFile(currpath+"/uploads/student1/BinarySearch1.py", fu.readFile(source1));
		fu.writeFile(currpath+"/uploads/student2/BinarySearch2.py", fu.readFile(source2));
		s3.uploadFileTos3bucketIndv("student1/BinarySearch1.py", file1);
		s3.uploadFileTos3bucketIndv("student2/BinarySearch2.py", file2);
		MainController helloCon = new MainController();
		ModelAndView modelAndView = helloCon.codeMoveOver(null);
		assertEquals("runPlagiarismPage", modelAndView.getViewName());
	}

}
