import org.junit.Test;

import com.plagiarism.detector.strategies.PlagiarismMoss;

/**
 * Class to run Moss.
 * 
 * @author ashuk
 *
 */
public class TestMoss {

	/**
	 * test that runs runMossRequest to request
	 * moss server to run the plagiarism check on 
	 * and return Url of results.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMoss() throws Exception {
		PlagiarismMoss obj = new PlagiarismMoss();
		obj.runMossRequest();
	}
	
}
