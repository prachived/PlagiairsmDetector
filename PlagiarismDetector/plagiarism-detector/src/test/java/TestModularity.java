import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.plagiarism.detector.model.Outcome;
import com.plagiarism.detector.model.Result;
import com.plagiarism.detector.strategies.FunctionalityFactory;
import com.plagiarism.detector.strategies.ModularityCheck;
import com.plagiarism.detector.strategies.PlagiarismModularity;
import com.plagiarism.detector.utilities.AmazonClient;
import com.plagiarism.detector.utilities.FileUtility;

/**
 * To test the codes for plagiarism through modularity.
 * 
 * @author ashuk
 *
 */
public class TestModularity {

	/**
	 * Checks plagiarism on Insertion Sort for plagiarized files.
	 * 
	 * @throws IOException
	 * 
	 */

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

	@Test
	public void testPlagiarizedFiles() throws IOException {
		FileUtility fileUtility = new FileUtility();
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = fileUtility.readFile(currpath + "//InsertionSort1.py");
		String source2 = fileUtility.readFile(currpath + "//InsertionSort2.py");

		PlagiarismModularity object = new PlagiarismModularity();
		Result outcome = object.getPlagiarismSimilarity("","",source1, source2);

		assertEquals(Outcome.PLAGIARIZED, outcome.getOutcome());
	}

	/**
	 * test plagiarism on other set of plagiarized files.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testOtherPlagiarizedFiles() throws IOException {
		FileUtility fileUtility = new FileUtility();
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = fileUtility.readFile(currpath + "//sum.py");
		String source2 = fileUtility.readFile(currpath + "//sum1.py");
		PlagiarismModularity object = new PlagiarismModularity();
		Result outcome = object.getPlagiarismSimilarity("","",source1, source2);
		assertEquals(Outcome.NOTPLAGIARIZED, outcome.getOutcome());
	}

	/**
	 * test files that are not plagiarized.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testDifferentContextFiles() throws IOException {

		FileUtility fileUtility = new FileUtility();
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = fileUtility.readFile(currpath + "//factorial1.py");
		String source2 = fileUtility.readFile(currpath + "//sum1.py");
		PlagiarismModularity object = new PlagiarismModularity();
		Result outcome = object.getPlagiarismSimilarity("","",source1, source2);
		assertEquals(Outcome.NOTPLAGIARIZED, outcome.getOutcome());
	}

	/**
	 * test other set of files that are not plagiarized.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testUnPlagiarizedFiles() throws IOException {

		FileUtility fileUtility = new FileUtility();
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = fileUtility.readFile(currpath + "//InsertionSort1.py");
		String source2 = fileUtility.readFile(currpath + "//sum1.py");

		PlagiarismModularity object = new PlagiarismModularity();
		Result outcome = object.getPlagiarismSimilarity("","",source1, source2);
		assertEquals(Outcome.NOTPLAGIARIZED, outcome.getOutcome());
	}

	/**
	 * Test plagiarized files
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPlagiarismFiles() throws IOException {

		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath + "//BinarySearch1.py";
		File file1 = new File(source1);
		String source2 = currpath + "//BinarySearch2.py";
		File file2 = new File(source2);
		s3.uploadFileTos3bucketIndv("student1/BinarySearch1.py", file1);
		s3.uploadFileTos3bucketIndv("student2/BinarySearch2.py", file2);
		FunctionalityFactory factory = FunctionalityFactory.instance();
		ModularityCheck modularityCheck = factory.makeModularityCheckInstance();
		List<Result> results = new ArrayList<Result>();
		results = modularityCheck.getSimilarity();
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
	 * Test similar files
	 */
	@Test
	public void testSimilarFiles() {
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath + "//InsertionSort1.py";
		File file1 = new File(source1);
		String source2 = currpath + "//InsertionSort2.py";
		File file2 = new File(source2);
		s3.uploadFileTos3bucketIndv("student1/InsertionSort1.py", file1);
		s3.uploadFileTos3bucketIndv("student2/InsertionSort2.py", file2);
		FunctionalityFactory factory = FunctionalityFactory.instance();
		ModularityCheck modularityCheck = factory.makeModularityCheckInstance();
		List<Result> results = new ArrayList<Result>();
		results = modularityCheck.getSimilarity();
		for (Result result : results) {

			// test similar files.
			if (result.getFile1().equals("/student1/InsertionSort1.py")) {
				if (result.getFile2().equals("/student2/InsertionSort2.py")) {
					assertEquals(Outcome.PLAGIARIZED, result.getOutcome());
				}
			}
		}
	}

	/**
	 * Test non plagiarized files
	 */
	@Test
	public void testNonPlagiarizedFiles() {
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath + "//InsertionSort1.py";
		File file1 = new File(source1);
		String source2 = currpath + "//BinarySearch2.py";
		File file2 = new File(source2);
		s3.uploadFileTos3bucketIndv("student1/InsertionSort1.py", file1);
		s3.uploadFileTos3bucketIndv("student2/BinarySearch2.py", file2);
		FunctionalityFactory factory = FunctionalityFactory.instance();
		ModularityCheck modularityCheck = factory.makeModularityCheckInstance();
		List<Result> results = new ArrayList<Result>();
		results = modularityCheck.getSimilarity();
		for (Result result : results) {

			// test non plagiarized files.
			if (result.getFile1().equals("/student1/InsertionSort1.py")) {
				if (result.getFile2().equals("/student2/BinarySearch2.py")) {
					assertEquals(Outcome.NOTPLAGIARIZED, result.getOutcome());
				}
			}
		}
	}

}
