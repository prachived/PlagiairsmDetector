/***
 * Factory to create different types of users.
 */

public class UserFactory{
	
	/***
	 * Create an object of Admin class.
	 */
	public Admin makeAdmin()
	{
		return new Admin("","");
		
	}
	
	
	/***
	 * Create an object of Instructor class.
	 */
	public Instructor makeInstructor()
	{
		return new Instructor("","");
		
	}
}