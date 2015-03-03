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
	private int column;
	private String text;
	
	/**
	 * 
	 * @param id the id of the value
	 * @param recordID the recordID the value belongs to
	 * @param text the actual text
	 */
	public Value(int id, int recordID, int column, String text) {
		setId(id);
		setRecordID(recordID);
		setColumn(column);
		setText(text);
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
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
}
