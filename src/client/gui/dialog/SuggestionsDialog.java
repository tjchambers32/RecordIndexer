package client.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import client.gui.batchstate.BatchState;

@SuppressWarnings("serial")
public class SuggestionsDialog extends JDialog{

	BatchState batchState;
	JButton cancelButton;
	JButton useSuggestionButton;
	
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
	
		DefaultListModel<String> suggestionsListModel = new DefaultListModel<String>();
	
		JPanel suggestionPanel = new JPanel();
		
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
