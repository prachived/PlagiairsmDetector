import static org.junit.Assert.assertEquals;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.plagiarism.detector.model.Outcome;
import com.plagiarism.detector.model.Result;
import com.plagiarism.detector.strategies.FunctionalityFactory;
import com.plagiarism.detector.strategies.VariableCheck;
import com.plagiarism.detector.utilities.AmazonClient;

/**
 * 
 * Class to test the correctness of determining plagiarism for variable check.
 * 
 * @author ashuk
 *
 */
public class TestVariable {

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
	 * test to check for plagiarized files.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPlagiarizedFiles() throws IOException {
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath + "//BinarySearch1.py";
		File file1 = new File(source1);
		String source2 = currpath + "//BinarySearch2.py";
		File file2 = new File(source2);
		s3.uploadFileTos3bucketIndv("student1/BinarySearch1.py", file1);
		s3.uploadFileTos3bucketIndv("student2/BinarySearch2.py", file2);
		FunctionalityFactory factory = FunctionalityFactory.instance();
		VariableCheck variableCheck = factory.makeVariableCheckInstance();
		List<Result> results = new ArrayList<Result>();
		results = variableCheck.getSimilarity();
		for (Result result : results) {

			// test plagiarized files.
			if (result.getFile1().equals("/student1/BinarySearch1.py")) {
				if (result.getFile2().equals("/student2/BinarySearch2.py")) {
					assertEquals(Outcome.PLAGIARIZED, result.getOutcome());
				}
			}
		}
	}

	/**
	 * test to check for similar files.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testLikelyFiles() throws IOException {
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath + "//InsertionSort1.py";
		File file1 = new File(source1);
		String source2 = currpath + "//InsertionSort2.py";
		File file2 = new File(source2);
		s3.uploadFileTos3bucketIndv("student1/InsertionSort1.py", file1);
		s3.uploadFileTos3bucketIndv("student2/InsertionSort2.py", file2);
		FunctionalityFactory factory = FunctionalityFactory.instance();
		VariableCheck variableCheck = factory.makeVariableCheckInstance();
		List<Result> results = new ArrayList<Result>();
		results = variableCheck.getSimilarity();
		for (Result result : results) {

			// test similar files.
			if (result.getFile1().equals("/student1/InsertionSort1.py")) {
				if (result.getFile2().equals("/student2/InsertionSort2.py")) {
					assertEquals(Outcome.NOTPLAGIARIZED, result.getOutcome());
				}
			}
		}
	}

	/**
	 * test to check for non-plagiarized files.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testNonPlagiarizedFiles() throws IOException {
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath + "//InsertionSort1.py";
		File file1 = new File(source1);
		String source2 = currpath + "//BinarySearch2.py";
		File file2 = new File(source2);
		s3.uploadFileTos3bucketIndv("student1/InsertionSort1.py", file1);
		s3.uploadFileTos3bucketIndv("student2/BinarySearch2.py", file2);
		FunctionalityFactory factory = FunctionalityFactory.instance();
		VariableCheck variableCheck = factory.makeVariableCheckInstance();
		List<Result> results = new ArrayList<Result>();
		results = variableCheck.getSimilarity();
		for (Result result : results) {
			// test non-plagiarized files.
			if (result.getFile1().equals("/student1/InsertionSort1.py")) {
				if (result.getFile2().equals("/student2/BinarySearch2.py")) {
					assertEquals(Outcome.NOTPLAGIARIZED, result.getOutcome());
				}
			}
		}
	}
	
	
	/**
	 * test to check for same files.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSameFiles() throws IOException {
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath + "//InsertionSort1.py";
		File file1 = new File(source1);
		String source2 = currpath + "//InsertionSort1.py";
		File file2 = new File(source2);
		s3.uploadFileTos3bucketIndv("student1/InsertionSort1.py", file1);
		s3.uploadFileTos3bucketIndv("student1/InsertionSort1.py", file2);
		FunctionalityFactory factory = FunctionalityFactory.instance();
		VariableCheck variableCheck = factory.makeVariableCheckInstance();
		List<Result> results = new ArrayList<Result>();
		results = variableCheck.getSimilarity();
		for (Result result : results) {

			// test similar files.
			if (result.getFile1().equals("/student1/InsertionSort1.py")) {
				if (result.getFile2().equals("/student1/InsertionSort1.py")) {
					assertEquals(Outcome.PLAGIARIZED, result.getOutcome());
				}
			}
		}
	}


}
