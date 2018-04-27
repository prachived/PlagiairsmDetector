import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.plagiarism.detector.model.Outcome;
import com.plagiarism.detector.utilities.AmazonClient;
import com.plagiarism.detector.utilities.FileUtility;
import com.plagiarism.detector.utilities.SnippetGeneration;

public class TestSnippet {

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
	public void testDifferentContextFiles() throws IOException {

		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath + "//BinarySearch1.py";
		File file1 = new File(source1);
		String source2 = currpath + "//BinarySearch2.py";
		File file2 = new File(source2);
		FileUtility fu = new FileUtility();
		fu.writeFile(currpath + "/uploads/student1/BinarySearch1.py", fu.readFile(source1));
		fu.writeFile(currpath + "/uploads/student2/BinarySearch2.py", fu.readFile(source2));
		s3.uploadFileTos3bucketIndv("student1/BinarySearch1.py", file1);
		s3.uploadFileTos3bucketIndv("student2/BinarySearch2.py", file2);
		SnippetGeneration s = new SnippetGeneration();
		ArrayList<Integer> lineNumbers1 = new ArrayList<Integer>(Arrays.asList(1, 3, 5));
		ArrayList<Integer> lineNumbers2 = new ArrayList<Integer>(Arrays.asList(1, 3, 5));
		s.makeHTML("/student1/BinarySearch1.py", "/student2/BinarySearch2.py", lineNumbers1, lineNumbers2);
	}

	/**
	 * test variable summary
	 * 
	 * @throws IOException
	 */
	@Test
	public void testVariableReport() throws IOException {

		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath + "//BinarySearch1.py";
		File file1 = new File(source1);
		String source2 = currpath + "//BinarySearch2.py";
		File file2 = new File(source2);
		FileUtility fu = new FileUtility();
		fu.writeFile(currpath + "/uploads/student1/BinarySearch1.py", fu.readFile(source1));
		fu.writeFile(currpath + "/uploads/student2/BinarySearch2.py", fu.readFile(source2));
		s3.uploadFileTos3bucketIndv("student1/BinarySearch1.py", file1);
		s3.uploadFileTos3bucketIndv("student2/BinarySearch2.py", file2);
		SnippetGeneration s = new SnippetGeneration();
		s.makeHTMLForVariableCheck("/student1/BinarySearch1.py", "/student2/BinarySearch2.py", Outcome.PLAGIARIZED);

	}

	/**
	 * test summary
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSummaryReport() throws IOException {

		SnippetGeneration s = new SnippetGeneration();
		String file1 = "InsertionSort1.py";
		String file2 = "InsertionSort2.py";

		s.makeHTMLForSummary(file1, file2, "summary");
	}

	/**
	 * test variable summary
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCommentReport() throws IOException {

		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath + "//BinarySearch1.py";
		File file1 = new File(source1);
		String source2 = currpath + "//BinarySearch2.py";
		File file2 = new File(source2);
		FileUtility fu = new FileUtility();
		fu.writeFile(currpath + "/uploads/student1/BinarySearch1.py", fu.readFile(source1));
		fu.writeFile(currpath + "/uploads/student2/BinarySearch2.py", fu.readFile(source2));
		s3.uploadFileTos3bucketIndv("student1/BinarySearch1.py", file1);
		s3.uploadFileTos3bucketIndv("student2/BinarySearch2.py", file2);
		SnippetGeneration s = new SnippetGeneration();
		s.makeHTMLforCommentsCheck("/student1/BinarySearch1.py", "/student2/BinarySearch2.py", Outcome.PLAGIARIZED);

	}

}
