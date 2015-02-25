/**
 * 
 */
package shared.communication;

import java.util.ArrayList;

import shared.model.Project;

/**
 * @author tchambs
 *
 */
public class GetProjects_Result {

		private ArrayList<Project> projects;

		/**
		 * 
		 * @param projects a list of each project object
		 */
		GetProjects_Result(ArrayList<Project> projects) {
			setProjects(projects);
		}
		
		public ArrayList<Project> getProjects() {
			return projects;
		}

		public void setProjects(ArrayList<Project> projects) {
			this.projects = projects;
		}
		
}
