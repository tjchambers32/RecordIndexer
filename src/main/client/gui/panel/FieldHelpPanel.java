package main.client.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.client.gui.batchstate.*;

@SuppressWarnings("serial")
public class FieldHelpPanel extends JPanel implements BatchStateListener{

	BatchState batchState;
	JEditorPane helpPane;
	String helpURL;
	Cell selectedCell;
	
	public FieldHelpPanel(BatchState batchState) {
		super();
		
		this.batchState = batchState;
		batchState.addListener(this);
		selectedCell = new Cell(50,50); //random value that batchState.selectedCell won't have
		createComponents();
	}

	private void createComponents() {
		
		
		helpPane = new JEditorPane();
		helpPane.setEditable(false);
		helpPane.setOpaque(true);
		helpPane.setBackground(Color.white);
		helpPane.setContentType("text/html");
		
		JScrollPane helpScroll = new JScrollPane(helpPane);
		
		showHelpInfo();
		
		this.setLayout(new BorderLayout());
		this.add(helpScroll, BorderLayout.CENTER);
	}

	private void showHelpInfo() {
		try {
			helpPane.setPage(helpURL);
		} catch (IOException e) {
			return;
		}
	}
	
	private void hideHelpInfo() {
		helpPane.setText("");
	}
	
	@Override
	public void stateChanged() {
		
		if (batchState.getHasDownloadedBatch() == false) {
			hideHelpInfo();
			return;
		}
		
		int selectedField = batchState.getSelectedCell().getField();
		
		Cell tempCell = selectedCell;
		Cell batchCell = batchState.getSelectedCell();
		
		if (tempCell == null) {
			hideHelpInfo();
			tempCell = batchCell;
			selectedCell = batchCell;
		
		}
		
		if ((tempCell.getField() != batchCell.getField()) || (tempCell.getRecord() != batchCell.getRecord())) {
			selectedCell = batchCell;
			if (!batchState.getFields().isEmpty()) {
				String shortFieldURL = batchState.getFields().get(selectedField).getHelpHTML();
				String fullURL = buildFullURL(shortFieldURL);
				helpURL = fullURL;
				showHelpInfo();
			}
		}
		
	}

	private String buildFullURL(String shortURL) {
		StringBuilder fullURL = new StringBuilder();
		
		fullURL.append("http://");
		fullURL.append(batchState.getHostname());
		fullURL.append(":");
		fullURL.append(batchState.getPort());
		fullURL.append("/");
		fullURL.append(shortURL);
		
		return fullURL.toString();
	}
	
}
