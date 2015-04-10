package client.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.gui.batchstate.*;

@SuppressWarnings("serial")
public class FieldHelpPanel extends JPanel implements BatchStateListener{

	BatchState batchState;
	JEditorPane helpPane;
	String helpURL;
	
	public FieldHelpPanel(BatchState batchState) {
		super();
		
		this.batchState = batchState;
		batchState.addListener(this);
		
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
	
	@Override
	public void stateChanged() {
		int selectedField = batchState.getSelectedCell().getField();
		if (!batchState.getFields().isEmpty()) {
			String shortFieldURL = batchState.getFields().get(selectedField).getHelpHTML();
			String fullURL = buildFullURL(shortFieldURL);
			helpURL = fullURL;
			showHelpInfo();
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
