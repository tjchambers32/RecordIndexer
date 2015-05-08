package main.shared.model;

/**
 * @author tchambs
 * 
 */
public class Record {

	private int id;
	private int imageID;
	private int rowNumber;
	
	/**
	 * Constructor WITH recordID
	 * @param id the id of the record;
	 * @param imageID the id of the image the record belongs to
	 * @param rowNumber which row this record is associated with on the image (y-coord)
	 * @param fieldNumber which field this record is associated with on the image (x-coord)
	 */
	public Record(int id, int imageID, int rowNumber) {
		setId(id);
		setImageID(imageID);
		setRowNumber(rowNumber);
	}
	
	/**
	 * Constructor WITHOUT recordID
	 * @param imageID the id of the image the record belongs to
	 * @param rowNumber which row this record is associated with on the image (y-coord)
	 * @param fieldNumber which field this record is associated with on the image (x-coord)
	 */
	public Record(int imageID, int rowNumber) {
		setImageID(imageID);
		setRowNumber(rowNumber);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getImageID() {
		return imageID;
	}
	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
}
