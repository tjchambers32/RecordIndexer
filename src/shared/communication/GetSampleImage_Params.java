/**
 * 
 */
package shared.communication;

import shared.model.User;

/**
 * @author tchambs
 *
 */
public class GetSampleImage_Params {

	private User user;
	private int projectID;

	/**
	 * 
	 * @param user the User requesting the sample image
	 * @param projectID the projectID the image will come from
	 * 
	 */
	GetSampleImage_Params(User user, int projectID) {
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
