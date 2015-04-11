package client.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.*;

import client.gui.batchstate.BatchState;

@SuppressWarnings("serial")
public class SuggestionsDialog extends JDialog{

	BatchState batchState;
	JButton cancelButton;
	JButton useSuggestionButton;
	JList<String> suggestionsList;
	
	public SuggestionsDialog(BatchState batchState) {
		super();
		
		this.setTitle("Suggestions");
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setResizable(false);
		this.batchState = batchState;
		
		createComponents();
	}
	
	private void createComponents() {
		
		JPanel rootPanel = new JPanel();
		rootPanel.setLayout(new BorderLayout());
					
		Set<String> results = batchState.makeSuggestions(batchState.getSelectedCell());

		DefaultListModel<String> suggestionsListModel = new DefaultListModel<String>();
		for (String s : results) {
			suggestionsListModel.addElement(s);
		}
		
		suggestionsList = new JList<String>(suggestionsListModel);
		suggestionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		if (suggestionsList.getModel().getSize() == 0) {
			useSuggestionButton.setEnabled(false);
		} else {
			suggestionsList.setSelectedIndex(0);
			useSuggestionButton.setEnabled(true);
		}
		
		JPanel suggestionPanel = new JPanel();
		JScrollPane suggestionScroll = new JScrollPane(suggestionsList);

		suggestionPanel.setLayout(new BorderLayout());
		suggestionPanel.setBorder(BorderFactory.createEmptyBorder(10,20,20,10));
		suggestionPanel.add(suggestionScroll, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(actionListener);
		
		useSuggestionButton = new JButton("Use Suggestion");
		useSuggestionButton.addActionListener(actionListener);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(3, 0)));
		buttonPanel.add(cancelButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(6, 0)));
		buttonPanel.add(useSuggestionButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(3, 0)));
		
		rootPanel.add(suggestionPanel, BorderLayout.CENTER);
		rootPanel.add(buttonPanel, BorderLayout.SOUTH);
		rootPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		this.add(rootPanel);
		
		this.setSize(new Dimension(255, 205));
	}
	
	private ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == cancelButton){
				SuggestionsDialog.this.dispose();
			} else if (e.getSource() == useSuggestionButton) {
				
			}
			
		}
		
	};

}
