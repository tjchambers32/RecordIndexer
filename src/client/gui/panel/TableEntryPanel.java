package client.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import client.gui.batchstate.BatchState;
import client.gui.batchstate.BatchStateListener;
import client.gui.batchstate.Cell;
import client.gui.dialog.SuggestionsDialog;

@SuppressWarnings("serial")
public class TableEntryPanel extends JPanel implements BatchStateListener{

	private JTable entryTable;
	private TableEntryModel tableEntryModel;
	private BatchState batchState;
	int row;
	int column;
	
	public TableEntryPanel(BatchState batchState) {

		this.batchState = batchState;
		tableEntryModel = null;
		entryTable = null;

		batchState.addListener(this);
		
//		createComponents();
	}

	private void createComponents() {
		
		
		tableEntryModel = new TableEntryModel(batchState);
		entryTable = new JTable(tableEntryModel);
		entryTable.setRowHeight(20);
		entryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		entryTable.setCellSelectionEnabled(true);
		entryTable.getTableHeader().setReorderingAllowed(false);
		entryTable.addMouseListener(mouseAdapter);
		
		TableColumnModel columnModel = entryTable.getColumnModel();
		for (int i = 0; i < tableEntryModel.getColumnCount(); ++i) {
			TableColumn column = columnModel.getColumn(i);
			if (i > 0)
				column.setCellRenderer(new EntryCellRenderer(batchState));
			column.setPreferredWidth(100);
		}
		
		JPanel rootPanel = new JPanel(new BorderLayout());
		rootPanel.add(entryTable.getTableHeader(), BorderLayout.NORTH);
		rootPanel.add(entryTable, BorderLayout.CENTER);
		
		this.add(rootPanel);
		this.setVisible(true);
		
	}
	
	@Override
	public void stateChanged() {
		
		if (entryTable == null || batchState.isDownloadingBatch()) {
			
			//create table
			createComponents();
		} 
		
		if (batchState.isLoggingIn() && entryTable != null) {
			
			//just update table structure
			TableColumnModel columnModel = entryTable.getColumnModel();
			for (int i = 0; i < tableEntryModel.getColumnCount(); ++i) {
				TableColumn column = columnModel.getColumn(i);
				if (i > 0)
					column.setCellRenderer(new EntryCellRenderer(batchState));
				column.setPreferredWidth(100);
			}
		} else if (!batchState.isLoggingIn() && entryTable != null) {
			if (batchState.getHasDownloadedBatch()){
			
				entryTable.changeSelection(batchState.getSelectedCell().getRecord(), batchState.getSelectedCell().getField(), false, false);
			
//				if (batchState.getValue(new Cell(row, column)) != tableEntryModel.getValueAt(row, column)) {
//					tableEntryModel.fireTableDataChanged();
//				}
			}
		}
	}

	private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mouseReleased(MouseEvent e) {
			row = entryTable.rowAtPoint(e.getPoint());
			column = entryTable.columnAtPoint(e.getPoint());
			batchState.setSelectedCell(new Cell(row, column));
			JPopupMenu popup = null;
			JMenuItem seeSuggestions = null;
			if (e.getButton() == MouseEvent.BUTTON3) {
				if (batchState.qualityCheck(new Cell(row, column)) == false) {
					popup = new JPopupMenu();
					seeSuggestions = new JMenuItem("See Suggestions.");
					seeSuggestions.addActionListener(actionListener);
					popup.add(seeSuggestions);
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}
	};
	
	private ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			SuggestionsDialog suggestions = new SuggestionsDialog(batchState);
			suggestions.setVisible(true);
		}
	};
}

@SuppressWarnings("serial")
class EntryCellRenderer extends JLabel implements TableCellRenderer {

	private Border unselectedBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
	private Border selectedBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
	private BatchState batchState;
	
	public EntryCellRenderer(BatchState batchState) {
		this.batchState = batchState;
		setOpaque(true);
		setFont(getFont().deriveFont(16.0f));
	}

	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {
		
		if (isSelected) {
			this.setBorder(selectedBorder);
			Cell selectedCell = new Cell(row, column);
			batchState.setSelectedCell(selectedCell);
		}
		else {
			this.setBorder(unselectedBorder);
		}
		
		//TODO add logic for quality checker here
		if (batchState.qualityCheck(new Cell(row, column))) {
			this.setBackground(Color.white);
		} else {
			this.setBackground(Color.red);
		}
		
		this.setText((String)value);
		return this;
	}

}
