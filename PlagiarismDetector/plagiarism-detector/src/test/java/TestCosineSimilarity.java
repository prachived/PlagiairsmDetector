import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;
import org.junit.Test;

import com.plagiarism.detector.model.Outcome;
import com.plagiarism.detector.model.Result;
import com.plagiarism.detector.strategies.PlagiarismModularity;
import com.plagiarism.detector.techniques.CosineSimilarity;
import com.plagiarism.detector.utilities.FileUtility;

/**
 * Class to test code plagiarism based on Cosine Similarity strategy
 * 
 * @author Prachi
 *
 */
public class TestCosineSimilarity {

	/**
	 * Function to test plagiarism between two bubble sort files
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPlagiarismUsingCosine() throws IOException {
		FileUtility fileUtility = new FileUtility();
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = fileUtility.readFile(currpath + "//BubbleSort2.py");
		String currpath2 = Paths.get("").toAbsolutePath().toString();
		String source2 = fileUtility.readFile(currpath2 + "//BubbleSort1.py");
		PlagiarismModularity plagiarismModularity = new PlagiarismModularity();
		Result outcome = plagiarismModularity.getPlagiarismSimilarity("", "", source1, source2);

		assertEquals(Outcome.PLAGIARIZED, outcome.getOutcome());
	}

	/**
	 * Function to test plagiarism between two empty files
	 * 
	 * @throws IOException
	 */

	@Test
	public void testEmptyFile() throws IOException {
		FileUtility fileUtility = new FileUtility();
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = fileUtility.readFile(currpath + "//empty.py");
		String source2 = fileUtility.readFile(currpath + "//empty.py");
		PlagiarismModularity plagiarismModularity = new PlagiarismModularity();
		Result outcome = plagiarismModularity.getPlagiarismSimilarity("", "", source1, source2);
		assertEquals(Outcome.PLAGIARIZED, outcome.getOutcome());
	}

	/**
	 * Function to test exception
	 * 
	 * @throws IOException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNull() throws IOException {
		CosineSimilarity cs = new CosineSimilarity();
		cs.getCosineSimilarity(null, null);
	}

	/**
	 * Function to test plagiarism between two very distinct files
	 * 
	 * @throws IOException
	 */
	@Test
	public void testUnplagiarizedFiles() throws IOException {
		FileUtility fileUtility = new FileUtility();
		String currpath = Paths.get("").toAbsolutePath().toString();
		String source1 = fileUtility.readFile(currpath + "//sum1.py");
		String currpath2 = Paths.get("").toAbsolutePath().toString();
		String source2 = fileUtility.readFile(currpath2 + "//factorial1.py");
		PlagiarismModularity plagiarismModularity = new PlagiarismModularity();
		Result outcome = plagiarismModularity.getPlagiarismSimilarity("", "", source1, source2);

		assertEquals(Outcome.NOTPLAGIARIZED, outcome.getOutcome());
	}

}
