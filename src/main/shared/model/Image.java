package main.shared.model;

/**
 * 
 * @author tchambs
 * 
 */
public class Image {

	private int id;
	private int projectID;
	private String filepath;
	private int availability; //not a boolean, because we have 3 different statuses

	
	/**
	 * Constructor WITH imageID
	 * @param id the image id
	 * @param projectID the projectID the image belongs to
	 * @param filepath the relative filepath to the image
	 * @param availability an integer representing whether the image is checked out, available, or submitted
	 */
	public Image(int id, int projectID, String filepath, int availability) {
		setId(id);
		setProjectID(projectID);
		setFilepath(filepath);
		setAvailability(availability);
	}
	
	/**
	 * Constructor WITHOUT imageID
	 * @param id the image id
	 * @param projectID the projectID the image belongs to
	 * @param filepath the relative filepath to the image
	 * @param availability an integer representing whether the image is checked out, available, or submitted
	 */
	public Image(int projectID, String filepath, int availability) {
		setProjectID(projectID);
		setFilepath(filepath);
		setAvailability(availability);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int imageID) {
		this.id = imageID;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public int getAvailability() {
		return availability;
	}

	// -1 = available
	//  0 = unavailable
	//  1 = submitted
	public void setAvailability(int availability) {
		this.availability = availability;
	}

}
