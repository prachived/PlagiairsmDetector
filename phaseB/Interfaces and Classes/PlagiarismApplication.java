/***
 * An object of the PlagiarismApplication class represents the
 * application.The application can have many users.
 */

public class PlagiarismApplication implements DatabaseService {
	
	public User  users[];
	
	/***
	 * Given: two strings that are the username and password of
	 * a user,
	 * Returns:  a string value, representing, the type of user,
	 * i.e. admin or instructor.
	 */
	public String validateCredentials(String uName, String pWord) {
	
		return "hello";
	}
	

}
