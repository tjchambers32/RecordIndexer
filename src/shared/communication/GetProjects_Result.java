package shared.communication;

import java.util.List;

import shared.model.Project;

/**
 * @author tchambs
 *
 */
public class GetProjects_Result {

		private List<Project> projects;

		/**
		 * 
		 * @param returnProjects a list of each project object
		 */
		public GetProjects_Result(List<Project> returnProjects) {
			setProjects(returnProjects);
		}
		
		public List<Project> getProjects() {
			return projects;
		}

		public void setProjects(List<Project> projects) {
			this.projects = projects;
		}
		
}
