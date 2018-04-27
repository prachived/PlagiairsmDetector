/***
 * @author Preyank Jain
 * An object of DatabaseService type would be used to validate user
 * credentials, and to provide access to the plagiarism application
 */


public interface DatabaseService {
	
	/***
	 * @param uName - user name
	 * @param pWord - password
	 * @return : the type of user('instructor'/'admin'), after validating the
	 * username and password with in the database. Returns 'INV' if the 
	 * credentials are invalid.
	 */
	
	public String validateCredentials(String uName, String pWord);
	
}