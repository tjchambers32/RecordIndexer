package client.gui.panel;

import javax.swing.table.AbstractTableModel;

import client.gui.batchstate.BatchState;
import client.gui.batchstate.Cell;

@SuppressWarnings("serial")
public class TableEntryModel extends AbstractTableModel {

	private int rows;
	private int columns;
	private BatchState batchState;
	
	public TableEntryModel(BatchState batchState) {
		this.batchState = batchState;
		this.columns = batchState.getNumberOfColumns();
		this.rows = batchState.getNumberOfRows();
	}

	@Override
	public int getColumnCount() {
		return columns;
	}

	@Override
	public int getRowCount() {
		return rows;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0)
			return rowIndex + 1;
		
		return batchState.getFields().get(columnIndex-1).getTitle();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex < 1)
			return false;
		else
			return true;
	}
	
	@Override
	public void setValueAt(Object inputValue, int inputRow, int inputColumn) {
		Cell selectedCell = new Cell(inputRow, inputColumn);
		batchState.setValue(selectedCell,  (String) inputValue);
	}

}
