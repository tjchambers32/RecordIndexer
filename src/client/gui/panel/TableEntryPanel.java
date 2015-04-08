package client.gui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

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
		super();

		this.batchState = batchState;
		tableEntryModel = new TableEntryModel(batchState);
		batchState.addListener(this);
		
		createComponents();
	}

	private void createComponents() {
		entryTable = new JTable(tableEntryModel);
		entryTable.setRowHeight(20);
		entryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		entryTable.setCellSelectionEnabled(true);
		entryTable.getTableHeader().setReorderingAllowed(false);
		entryTable.addMouseListener(mouseAdapter);
	}

	@Override
	public void stateChanged() {
		// TODO Auto-generated method stub
		
	}

	private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mouseReleased(MouseEvent e) {
			row = entryTable.rowAtPoint(e.getPoint());
			column = entryTable.columnAtPoint(e.getPoint());
			batchState.setSelectedCell(new Cell(row, column));
			JPopupMenu popup = null;
			JMenuItem seeSuggest = null;
			if (e.getButton() == MouseEvent.BUTTON3) {
				if (batchState.checkMisspelled(new Cell(row, column))) {
					popup = new JPopupMenu();
					seeSuggest = new JMenuItem("See Suggestions.");
					seeSuggest.addActionListener(actionListener);
					popup.add(seeSuggest);
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
