/**
 * 
 */
package main.shared.communication;

import main.shared.model.User;

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
	
	public String toString() {

		StringBuilder result = new StringBuilder();
		User user = this.result;
		
		result.append("TRUE\n");
		result.append(user.getFirstName() + "\n");
		result.append(user.getLastName() + "\n");
		result.append(user.getRecordsIndexed() + "\n");
		
		return result.toString();
	}
}
