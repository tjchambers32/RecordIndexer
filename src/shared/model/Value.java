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
	private String value;
	
	/**
	 * 
	 * @param id the id of the value
	 * @param recordID the recordID the value belongs to
	 * @param value the actual text
	 */
	public Value(int id, int recordID, String value) {
		setId(id);
		setRecordID(recordID);
		setValue(value);
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
