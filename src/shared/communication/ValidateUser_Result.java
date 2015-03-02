/**
 * 
 */
package shared.communication;

import shared.model.User;

/**
 * @author tchambs
 *
 */
public class ValidateUser_Result {

	private User result;
	
	/**
	 * 
	 * @param result a user object containing the user's information
	 */
	public ValidateUser_Result(User result) {
		setResult(result);
	}

	public User getResult() {
		return result;
	}

	public void setResult(User result) {
		this.result = result;
	}
}
