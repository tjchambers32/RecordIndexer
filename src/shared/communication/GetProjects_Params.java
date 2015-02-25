/**
 * 
 */
package shared.communication;

import shared.model.User;

/**
 * @author tchambs
 *
 */
public class GetProjects_Params {

	private User params;

	/**
	 * 
	 * @param params a user object containing the username and password of the user
	 */
	GetProjects_Params(User params) {
		setParams(params);
	}
	
	public User getParams() {
		return params;
	}

	public void setParams(User params) {
		this.params = params;
	}
}
