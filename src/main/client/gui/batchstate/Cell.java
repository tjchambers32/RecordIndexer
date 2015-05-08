/**
 * 
 */
package main.client.gui.batchstate;

/**
 * @author tchambs
 *
 */
public class Cell {
	
	int record;
	int field;
	
	public Cell(int record, int field) {
		this.record = record;
		this.field = field;
	}

	public int getRecord() {
		return record;
	}

	public void setRecord(int record) {
		this.record = record;
	}

	public int getField() {
		return field;
	}

	public void setField(int field) {
		this.field = field;
	}
}
