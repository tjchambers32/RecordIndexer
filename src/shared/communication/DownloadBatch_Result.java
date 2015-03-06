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
	public DownloadBatch_Result(Project project, ArrayList<Field> fields, Image image) {
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

	/**
	 * @param string
	 * @return
	 */
	public String toString(String urlPrefix) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(image.getId() + "\n");
		sb.append(project.getId() + "\n");
		sb.append(urlPrefix + image.getFilepath() + "\n");
		sb.append(project.getFirstYCoord() + "\n");
		sb.append(project.getRecordHeight() + "\n");
		sb.append(project.getRecordsPerImage() + "\n");
		sb.append(fields.size() + "\n");
		int i = 0;
		for (Field f : fields) {
			i++;
			sb.append(f.getId() + "\n");
			sb.append(i + "\n");
			sb.append(f.getTitle() + "\n");
			sb.append(urlPrefix + f.getHelpHTML() + "\n");
			sb.append(f.getxCoord() + "\n");
			sb.append(f.getWidth() + "\n");
			if (f.getKnownData() != null && !f.getKnownData().equals(""))
				sb.append(urlPrefix + f.getKnownData() + "\n");
		}
		
		return sb.toString();
	}
	
	
}
