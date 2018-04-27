import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.apache.logging.log4j.LogManager;

/**
 * Functionality testing of logger. Should send email on Error encountered in
 * application
 * 
 * @author Prachi
 *
 */
public class TestLogger {

	private static Logger logger = LogManager.getLogger();

	
	
	/**
	 * Test logger function
	 */
	@Test
	public void testLogger() {
		logger.error("Testing error for SMTP appender");

	}

}
