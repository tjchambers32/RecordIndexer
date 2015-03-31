/**
 * 
 */
package client.gui;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tchambs
 *
 */

class Cell {
	int record;
	int field;
}

class BatchState implements BatchStateListener{
	
	private String[][] values;
	private Cell selectedCell;
	private List<BatchStateListener> listeners;
	
	public BatchState(int records, int fields) {
		values = new String[records][fields];
		selectedCell = null;
		listeners = new ArrayList<BatchStateListener>();
	}
	
	public void addListener(BatchStateListener l) {
		listeners.add(l);
	}
	
	public void setValue(Cell cell, String value) {
		
		values[cell.record][cell.field] = value;
		
		for (BatchStateListener l : listeners) {
			l.valueChanged(cell, value);
		}
	}
	
	public String getValue(Cell cell) {
		return values[cell.record][cell.field];
	}
	
	public void setSelectedCell(Cell selCell) {
		
		selectedCell = selCell;
		
		for (BatchStateListener l : listeners) {
			l.selectedCellChanged(selCell);
		}
	}
	
	public Cell getSelectedCell() {
		return selectedCell;
	}


	@Override
	public void valueChanged(Cell cell, String newValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectedCellChanged(Cell newSelectedCell) {
		// TODO Auto-generated method stub
		
	}
}