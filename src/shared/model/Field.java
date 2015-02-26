package shared.model;

/**
 * 
 * @author tchambs
 * 
 */
public class Field {

	private int id;
	private int fieldNumber;
	private String title;
	private int xCoord;
	private int width;
	private String helpHTML;
	private String knownData;
	private int projectID;
	
	/**
	 * 
	 * @param id the field ID
	 * @param fieldNumber indicates field's position in image
	 * @param title the field title
	 * @param xCoord specifies starting x position of the field in pixels
	 * @param width specifies the width of the field in pixels
	 * @param helpHTML relative path to the fieldhelp subdirectory
	 * @param knownData relative path to the knownData subdirectory
	 * @param projectID the id of the project this field belongs to
	 */
	public Field(int id, int fieldNumber, String title, int xCoord,
			int width, String helpHTML, String knownData, int projectID) {
		
		setId(id);
		setFieldNumber(fieldNumber);
		setTitle(title);
		setxCoord(xCoord);
		setWidth(width);
		setHelpHTML(helpHTML);
		setKnownData(knownData);
		setProjectID(projectID);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int fieldId) {
		this.id = fieldId;
	}
	public int getFieldNumber() {
		return fieldNumber;
	}
	public void setFieldNumber(int fieldNumber) {
		this.fieldNumber = fieldNumber;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getxCoord() {
		return xCoord;
	}
	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getHelpHTML() {
		return helpHTML;
	}
	public void setHelpHTML(String helpHTML) {
		this.helpHTML = helpHTML;
	}
	public String getKnownData() {
		return knownData;
	}
	public void setKnownData(String knownData) {
		this.knownData = knownData;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

		
	
	
	
}
