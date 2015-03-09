/**
 * 
 */
package shared.model;

/**
 * @author tchambs
 *
 */
public class Value {

	private int id;
	private int recordID;
	private String text;
	private int fieldNumber;

	
	/**
	 * Constructor WITH id
	 * @param id the id of the value
	 * @param recordID the recordID the value belongs to
	 * @param text the actual text
	 * @param fieldNumber xCoord of the text
	 */
	public Value(int id, int recordID, String text, int fieldNumber) {
		setId(id);
		setRecordID(recordID);
		setText(text);
		setFieldNumber(fieldNumber);
	}
	
	/**
	 * Constructor WITHOUT id
	 * @param recordID the recordID the value belongs to
	 * @param text the actual text
	 * @param fieldNumber xCoord of the text
	 */
	public Value(int recordID, String text, int fieldNumber) {
		setRecordID(recordID);
		setText(text);
		setFieldNumber(fieldNumber);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRecordID() {
		return recordID;
	}
	public void setRecordID(int recordID) {
		this.recordID = recordID;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getFieldNumber() {
		return fieldNumber;
	}
	public void setFieldNumber(int fieldID) {
		this.fieldNumber = fieldID;
	}
}
