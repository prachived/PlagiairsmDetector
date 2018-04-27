/***
 * An object of the class Session represents a Session of the
 * plagiarism application. A Session would always contain a
 * Session ID.
 */

public class Session {
	
	private int sessionId;
	
	private Session(){
		
	}
	
	/***
	 * Returns the same Session, if one is already present,
	 * Otherwise, creates a new one.
	 */
	public Session getInstance() {
		return new Session();
	}

	/***
	 * Returns the sessionID of this Session
	 */
	public int getSessionId() {
		return sessionId;
	}


	/***
	 * Effect: Set the sessionID of this Session
	 */
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	
	
}
