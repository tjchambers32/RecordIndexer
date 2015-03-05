/**
 * 
 */
package shared.communication;

import shared.model.User;

/**
 * @author tchambs
 *
 */
public class ValidateUser_Params {

	private User params;
	
	/**
	 * 
	 * @param params a user object that contains the user's username and password
	 */
	public ValidateUser_Params(User params) {
		setParams(params);
	}

	public User getParams() {
		return params;
	}

	public void setParams(User params) {
		this.params = params;
	}


}
