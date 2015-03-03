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
	private int fieldID;

	
	/**
	 * 
	 * @param id the id of the value
	 * @param recordID the recordID the value belongs to
	 * @param text the actual text
	 */
	public Value(int id, int recordID, String text, int fieldID) {
		setId(id);
		setRecordID(recordID);
		setText(text);
		setFieldID(fieldID);
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
	public int getFieldID() {
		return fieldID;
	}
	public void setFieldID(int fieldID) {
		this.fieldID = fieldID;
	}
}
