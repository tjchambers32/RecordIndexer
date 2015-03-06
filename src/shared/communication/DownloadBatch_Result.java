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
		for (Field f : fields) {
			sb.append(f.getId());
			sb.append(f.getTitle());
			sb.append(urlPrefix + f.getHelpHTML());
			sb.append(f.getxCoord());
			sb.append(f.getWidth());
			if (f.getKnownData() != null)
				sb.append(f.getKnownData());
		}
		
		return sb.toString();
	}
	
	
}
