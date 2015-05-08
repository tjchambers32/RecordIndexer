/**
 * 
 */
package main.shared.communication;

import main.shared.model.User;

/**
 * @author tchambs
 *
 */
public class DownloadBatch_Params {
	
	private User user;
	private int projectID;
	
	/**
	 * 
	 * @param user a user object containing username and password
	 * @param projectID the ID of the project to download
	 */
	public DownloadBatch_Params(User user, int projectID) {
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
