/***
 * An object of the class User represents a User of the application.
 * An user would have a first name, a last name, and a Session. A
 * user may also invoke various system functionalities.
 */

public abstract class User {

	private String firstName;
	private String lastName;
	public Session session;
	FunctionalityFactory factory=new FunctionalityFactory();
	
	/***
	 * Effect: login to the application
	 */
	public void login(User uName) {
		
	}
	
	/***
	 * Effect: logout of the application
	 */
	public void logout(User uName) {
		
	}
	
	/***
	 * Return: the last name of the user.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/***
	 * Effect: Set the last name of the user.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/***
	 * Returns: the first name of the user.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/***
	 * Effect: set the first name of the user.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
}