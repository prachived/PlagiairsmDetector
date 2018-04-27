import static org.junit.Assert.assertEquals;

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
import com.plagiarism.detector.strategies.CommentsCheck;
import com.plagiarism.detector.strategies.FunctionalityFactory;
import com.plagiarism.detector.utilities.AmazonClient;

/**
 * Class to test code plagiarism based on Code Move Over strategy
 * 
 * @author Prachi
 *
 *
 */
public class TestComments {


	/**
	 * amazon client to test
	 */
	AmazonClient s3=new AmazonClient();
	
	/**
	 * setup before
	 */
	@Before
	public void setUp()
	{
		s3.initializeAmazon();
	}
	
	/**
	 * delete resources
	 * @throws IOException 
	 */
	@After
	public void tearDown() throws IOException
	{
		
		s3.deleteObject();
	}
	
	/**
	 * test to check for non plagiarized files.
	 * 
	 * @throws IOException
	 */
	
	@Test
	public void testUnplagiarizedFiles() throws IOException {
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath+"//InsertionSort1.py";
		File file1=new File(source1);
		String source2 = currpath+"//InsertionSort2.py";
		File file2=new File(source2);
		s3.uploadFileTos3bucketIndv("student1/InsertionSort1.py", file1);
		s3.uploadFileTos3bucketIndv("student2/InsertionSort2.py", file2);
		FunctionalityFactory factory = FunctionalityFactory.instance();
		List<Result> results = new ArrayList<>();
		CommentsCheck object = factory.makeCommentsCheckInstance();
		results = object.getSimilarity();
		for (Result result : results) {

			// test plagiarized files.
			if (result.getFile1().equals("/student1/InsertionSort1.py")) {
				if (result.getFile2().equals("/student2/InsertionSort2.py")) {
					assertEquals(Outcome.NOTPLAGIARIZED, result.getOutcome());
				}
			}
		}
	}
	
	
	/**
	 * test to check for plagiarized files.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPlagiarizedFiles1() throws IOException {
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath+"//BinarySearch1.py";
		File file1=new File(source1);
		String source2 = currpath+"//BinarySearch2.py";
		File file2=new File(source2);
		s3.uploadFileTos3bucketIndv("student1/BinarySearch1.py", file1);
		s3.uploadFileTos3bucketIndv("student2/BinarySearch2.py", file2);
		FunctionalityFactory factory = FunctionalityFactory.instance();
		List<Result> results = new ArrayList<>();
		CommentsCheck object = factory.makeCommentsCheckInstance();
		results = object.getSimilarity();
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
	 * test to check for non plagiarized files.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testUnplagiarizedFiles2() throws IOException {
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath+"//factorial1.py";
		File file1=new File(source1);
		String source2 = currpath+"//factorial2.py";
		File file2=new File(source2);
		s3.uploadFileTos3bucketIndv("student1/factorial1.py", file1);
		s3.uploadFileTos3bucketIndv("student2/factorial2.py", file2);
		FunctionalityFactory factory = FunctionalityFactory.instance();
		List<Result> results = new ArrayList<>();
		CommentsCheck object = factory.makeCommentsCheckInstance();
		results = object.getSimilarity();
		for (Result result : results) {

			// test plagiarized files.
			if (result.getFile1().equals("/student1/factorial1.py")) {
				if (result.getFile2().equals("/student2/factorial2.py")) {
					assertEquals(Outcome.NOTPLAGIARIZED, result.getOutcome());
				}
			}
		}
	}
	
	
	/**
	 * test to check for non plagiarized files.
	 * 
	 * @throws IOException
	 */
	
	@Test
	public void testUnplagiarizedFiles3() throws IOException {
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = currpath+"//InsertionSort1.py";
		File file1=new File(source1);
		String source2 = currpath+"//quickSort.py";
		File file2=new File(source2);
		s3.uploadFileTos3bucketIndv("student1/InsertionSort1.py", file1);
		s3.uploadFileTos3bucketIndv("student2/quickSort.py", file2);
		FunctionalityFactory factory = FunctionalityFactory.instance();
		List<Result> results = new ArrayList<>();
		CommentsCheck object = factory.makeCommentsCheckInstance();
		results = object.getSimilarity();
		for (Result result : results) {

			// test plagiarized files.
			if (result.getFile1().equals("/student1/InsertionSort1.py")) {
				if (result.getFile2().equals("/student2/quickSort.py")) {
					assertEquals(Outcome.NOTPLAGIARIZED, result.getOutcome());
				}
			}
		}
	}
}
