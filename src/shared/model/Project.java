package shared.model;
/**
 * 
 * @author tchambs
 * 
 */
public class Project {

	private int id;
	private String title;
	private int recordsPerImage;
	private int firstYCoord;
	private int recordHeight;
	
	/**
	 * 
	 * @param id the project id
	 * @param title the title of the project
	 * @param recordsPerImage the number of records in each image in this project
	 * @param firstYCoord the top of the first record of the project's images, in pixels
	 * @param recordHeight the height of each record, in pixels
	 */
	public Project(int id, String title, int recordsPerImage, int firstYCoord, int recordHeight) {
		setId(id);
		setTitle(title);
		setRecordsPerImage(recordsPerImage);
		setFirstYCoord(firstYCoord);
		setRecordHeight(recordHeight);
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getRecordsPerImage() {
		return recordsPerImage;
	}
	public void setRecordsPerImage(int recordsPerImage) {
		this.recordsPerImage = recordsPerImage;
	}
	public int getFirstYCoord() {
		return firstYCoord;
	}
	public void setFirstYCoord(int firstYCoord) {
		this.firstYCoord = firstYCoord;
	}
	public int getRecordHeight() {
		return recordHeight;
	}
	public void setRecordHeight(int recordHeight) {
		this.recordHeight = recordHeight;
	}
	
	
	

}
