package shared.model;

/**
 * @author tchambs
 * 
 * 
 * 
 */
public class Record {

	private int id;
	private int imageID;
	private int rowNumber;
	private int fieldNumber;
	
	/**
	 * 
	 * @param id the id of the record;
	 * @param imageID the id of the image the record belongs to
	 * @param rowNumber which row this record is associated with on the image (y-coord)
	 * @param fieldNumber which field this record is associated with on the image (x-coord)
	 */
	public Record(int id, int imageID, int rowNumber, int fieldNumber) {
		setId(id);
		setImageID(imageID);
		setRowNumber(rowNumber);
		setFieldNumber(fieldNumber);
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

	public int getFieldNumber() {
		return fieldNumber;
	}
	public void setFieldNumber(int fieldNumber) {
		this.fieldNumber = fieldNumber;
	}
	
	
}
