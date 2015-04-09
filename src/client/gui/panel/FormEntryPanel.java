package client.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import shared.model.Field;
import client.gui.batchstate.BatchState;
import client.gui.batchstate.BatchStateListener;
import client.gui.batchstate.Cell;

@SuppressWarnings("serial")
public class FormEntryPanel extends JPanel implements BatchStateListener{

	private BatchState batchState;
	private int row;
	private int column;
	private JList<Integer> recordList;
	private List<JTextField> textFields;
	
	public FormEntryPanel(BatchState batchState) {
		super();
		
		this.batchState = batchState;
		
		batchState.addListener(this);
		recordList = null;

//		createComponents();
	}

	private void createComponents() {
		
		DefaultListModel<Integer> listItems = new DefaultListModel<Integer>();
		
		for (int i = 0; i < batchState.getNumberOfRows(); i++) {
			listItems.addElement(i + 1);
		}
		
		recordList = new JList<Integer>(listItems);
		recordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		recordList.setPreferredSize(new Dimension(50, 150));
		recordList.addListSelectionListener(lsListener);
		
		recordList.setSelectedIndex(0);
		
		JScrollPane recordScroll = new JScrollPane(recordList);
		recordScroll.getVerticalScrollBar().setUnitIncrement(10);
		
		JPanel dataEntryPanel = new JPanel();
		dataEntryPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		List<Field> fieldList = batchState.getFields();
		textFields = new ArrayList<JTextField>();
		for (int i = 1; i < batchState.getNumberOfColumns(); i++) {
			JLabel fieldLabel = new JLabel(fieldList.get(i).getTitle());
			JTextField fieldText = new JTextField(20);
			fieldText.setName(fieldList.get(i).getTitle());
			textFields.add(fieldText);
			
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 1;
			gbc.gridy = i;
			gbc.weightx = 0.0;
			gbc.insets = new Insets(5, 10, 5, 10);
			dataEntryPanel.add(fieldLabel, gbc);
			
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 3;
			gbc.gridy = i;
			gbc.weightx = 0.0;
			gbc.insets = new Insets(5, 10, 5, 10);
			dataEntryPanel.add(fieldText, gbc);
			
			fieldText.addFocusListener(focusListener);
			fieldText.addMouseListener(mouseAdapter);
		}
		
		JScrollPane fieldScroll = new JScrollPane(dataEntryPanel);
		fieldScroll.getVerticalScrollBar().setUnitIncrement(10);
		
		JPanel rootPanel = new JPanel(new BorderLayout());
		rootPanel.add(recordScroll, BorderLayout.WEST);
		rootPanel.add(fieldScroll, BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(rootPanel, BorderLayout.CENTER);
	}
	

	private FocusListener focusListener = new FocusListener() {

		@Override
		public void focusGained(FocusEvent e) {
			for (int i = 0; i < batchState.getFields().size(); i++) {
				if (batchState.getFields().get(i).getTitle().equals(((JTextField)e.getSource()).getName())) {
					column = i;
					break;
				}
			}
			row = recordList.getSelectedIndex();
			
			batchState.setSelectedCell(new Cell(row, column));
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			for (int i = 0; i < textFields.size(); i++) {
				JTextField jText = textFields.get(i);
				if (e.getSource() == jText) {
					batchState.setValue(new Cell(recordList.getSelectedIndex(), i+1), jText.getText());
				}
			}
			//TODO add logic for quality checker
		}
		
	};
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {
		
		public void mouseRelease(MouseEvent e) {
			//TODO add logic for right click to show JPopupMenu
		}
	};
	
	@Override
	public void stateChanged() {
		
		column = batchState.getSelectedCell().getField();
		row = batchState.getSelectedCell().getRecord();
		
		if (recordList == null){
			createComponents();
		} else if (batchState.isLoggingIn() && recordList != null) {
			
		} else if (recordList.getSelectedIndex() != row) {
			recordList.setSelectedIndex(row);
		}

		if (textFields.size() != 0) {
			textFields.get(column - 1).setText(batchState.getValue(batchState.getSelectedCell()));
			//TODO add logic to check for mispelled and show red/white
		}
	}
	private ListSelectionListener lsListener = new ListSelectionListener() {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			
			if (arg0.getSource() != recordList)
				return;
			if (textFields == null)
				return;
			
			row = recordList.getSelectedIndex();
//			row = recordList.getSelectedValue();
			if (row < 0)
				return;
			
			//Initialize the proper data
			for (int i = 0; i < textFields.size(); i++) {
				if (batchState.checkMisspelled(new Cell(row, i+1))) {
					textFields.get(i).setBackground(Color.red);
				}
				else {
					textFields.get(i).setBackground(Color.white);
				}
				
				textFields.get(i).setText(batchState.getValue(new Cell(row, i+1)));
			}
			batchState.setSelectedCell(new Cell(row, column));
		}
		
	};
}
