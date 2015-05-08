package main.java.shared.communication;

import java.util.List;

import main.java.shared.model.Project;

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
		
		public String toString() {
			
			StringBuilder sb = new StringBuilder();
			List<Project> projects = this.getProjects();
			for (Project p : projects) {
				sb.append(p.getId() + "\n");
				sb.append(p.getTitle() + "\n");
			}
			
			return sb.toString();
		}
}
