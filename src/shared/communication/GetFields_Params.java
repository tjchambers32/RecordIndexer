/**
 * 
 */
package shared.communication;

import shared.model.User;

/**
 * @author tchambs
 *
 */
public class GetFields_Params {

	private User user;
	private int projectID;
	
	/**
	 * 
	 * @param user a user object containing the user's information
	 * @param projectID the ID of the project the fields belong to
	 */
	GetFields_Params(User user, int projectID) {
		setUser(user);
		setProjectID(projectID);
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	
}
