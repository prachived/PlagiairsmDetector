import static org.junit.Assert.*;

import org.junit.Test;

import com.plagiarism.detector.model.User;

/**
 * @author Prachi
 * 
 *         This class tests the User data type defined in main package
 */
public class TestUser {

	/**
	 * test the SetName and getName functions of User
	 */
	@Test
	public void testUserSetName() {
		User user = new User();
		user.setName("Joe");
		user.setPassword("joepassword");
		user.setEmail("joe@gmail.com");
		assertEquals(user.getName(), "Joe");
	}

	/**
	 * test the setPassword and getPassword functions of User
	 */
	@Test
	public void testUserSetPassword() {
		User user = new User();
		user.setName("Joe");
		user.setPassword("joepassword");
		user.setEmail("joe@gmail.com");
		assertEquals(user.getPassword(), "joepassword");
	}

	/**
	 * test the setEmail and getEmail functions of User
	 */
	@Test
	public void testUserSetEmail() {
		User user = new User();
		user.setName("Joe");
		user.setPassword("joepassword");
		user.setEmail("joe@gmail.com");
		assertEquals(user.getEmail(), "joe@gmail.com");
	}

	/**
	 * test the setId and getId functions of User
	 */
	@Test
	public void testUserSetId() {
		User user = new User();
		user.setId(5);
		user.setName("Joe");
		user.setPassword("joepassword");
		user.setEmail("joe@gmail.com");
		assertEquals(user.getId(), 5);
	}

}