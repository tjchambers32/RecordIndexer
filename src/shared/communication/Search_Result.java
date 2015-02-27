/**
 * 
 */
package shared.communication;

/**
 * @author tchambs
 *
 */
public class Search_Result {

	private int imageID;
	private String imageURL;
	private int rowNumber;
	private int fieldID;
	
	/**
	 * 
	 * @param imageID the ID of the image 
	 * @param imageURL the URL of the image
	 * @param rowNumber the row number (y-coord) of the result
	 * @param fieldID the fieldID (x-coord) of the result
	 */
	public Search_Result(int imageID, String imageURL, int rowNumber, int fieldID) {
		setImageID(imageID);
		setImageURL(imageURL);
		setRowNumber(rowNumber);
		setFieldID(fieldID);
	}
	
	public int getImageID() {
		return imageID;
	}
	public void setImageID(int imageID) {
		this.imageID = imageID;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getFieldID() {
		return fieldID;
	}
	public void setFieldID(int fieldID) {
		this.fieldID = fieldID;
	}
}
