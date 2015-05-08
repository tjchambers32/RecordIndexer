package main.client.gui.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.client.gui.batchstate.*;
import shared.model.User;
import main.client.gui.IndexerFrame;

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel implements BatchStateListener {

	BatchState batchState;
	
	IndexerFrame frame;
	
	JButton zoomInButton;
	JButton zoomOutButton;
	JButton invertImageButton;
	JButton toggleHighlightsButton;
	JButton saveButton;
	JButton submitButton;

	public ButtonPanel(IndexerFrame frame) {
		super();

		batchState = frame.getBatchState();
		batchState.addListener(this);

		this.frame = frame;		
		
		createComponents();

	}

	private void createComponents() {

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		zoomInButton = new JButton("Zoom In");
		zoomInButton.addActionListener(actionListener);

		zoomOutButton = new JButton("Zoom Out");
		zoomOutButton.addActionListener(actionListener);

		invertImageButton = new JButton("Invert Image");
		invertImageButton.addActionListener(actionListener);

		toggleHighlightsButton = new JButton("Toggle Highlights");
		toggleHighlightsButton.addActionListener(actionListener);

		saveButton = new JButton("Save");
		saveButton.addActionListener(actionListener);

		submitButton = new JButton("Submit");
		submitButton.addActionListener(actionListener);

		if (batchState.getHasDownloadedBatch() == true) {
			zoomInButton.setEnabled(true);
			zoomOutButton.setEnabled(true);
			invertImageButton.setEnabled(true);
			toggleHighlightsButton.setEnabled(true);
			saveButton.setEnabled(true);
			submitButton.setEnabled(true);
		} else {
			zoomInButton.setEnabled(false);
			zoomOutButton.setEnabled(false);
			invertImageButton.setEnabled(false);
			toggleHighlightsButton.setEnabled(false);
			saveButton.setEnabled(false);
			submitButton.setEnabled(false);
		}

		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.add(zoomInButton);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(zoomOutButton);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(invertImageButton);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(toggleHighlightsButton);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(saveButton);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(submitButton);
		this.add(Box.createHorizontalGlue());
	}

	private ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == zoomInButton) {
				if (batchState.getZoomLevel() < 2.5)
					batchState.setZoomLevel(batchState.getZoomLevel() + .15);
			} else if (e.getSource() == zoomOutButton) {
				if (batchState.getZoomLevel() > .3)
					batchState.setZoomLevel(batchState.getZoomLevel() - .15);
			} else if (e.getSource() == invertImageButton) {
				batchState.setImageInverted(!batchState.isImageInverted());
			} else if (e.getSource() == toggleHighlightsButton) {
				batchState.setHighlightsVisible(!batchState.isHighlightsVisible());
			} else if (e.getSource() == saveButton) {
				batchState.save();
			} else if (e.getSource() == submitButton) {
				if (batchState.submitBatch()) {
					User tempUser = batchState.getUser();
					BatchState emptyState = new BatchState(batchState.getHostname(), batchState.getPort());
					emptyState.setUser(tempUser);
					batchState.update(emptyState);
					frame.clearEntryPanels();
					batchState.stateChanged();
				}		
			}
		}
	};

	@Override
	public void stateChanged() {

		if (batchState.getHasDownloadedBatch() == true) {
			invertImageButton.setEnabled(true);
			toggleHighlightsButton.setEnabled(true);
			saveButton.setEnabled(true);
			submitButton.setEnabled(true);
			if (batchState.getZoomLevel() <= .3) {
				zoomOutButton.setEnabled(false);
			} else {
				zoomOutButton.setEnabled(true);
			}
			
			if (batchState.getZoomLevel() >= 2.5) {
				zoomInButton.setEnabled(false);
			} else {
				zoomInButton.setEnabled(true);
			}
		} else {
			zoomInButton.setEnabled(false);
			zoomOutButton.setEnabled(false);
			invertImageButton.setEnabled(false);
			toggleHighlightsButton.setEnabled(false);
			saveButton.setEnabled(false);
			submitButton.setEnabled(false);
		}
	}
}
