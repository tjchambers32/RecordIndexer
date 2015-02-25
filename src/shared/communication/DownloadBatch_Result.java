/**
 * 
 */
package shared.communication;

import java.util.ArrayList;

import shared.model.Field;
import shared.model.Image;
import shared.model.Project;

/**
 * @author tchambs
 *
 */
public class DownloadBatch_Result {

	private Project project;
	private ArrayList<Field> fields;
	private Image image;
	
	/**
	 * 
	 * @param project an object containing all the information about the project
	 * @param fields an object containing a list of field
	 * @param image an object containing the image
	 */
	DownloadBatch_Result(Project project, ArrayList<Field> fields, Image image) {
		setProject(project);
		setFields(fields);
		setImage(image);
	}
	
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public ArrayList<Field> getFields() {
		return fields;
	}
	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	
	
}
