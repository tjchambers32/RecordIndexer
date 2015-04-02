package client.gui.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import client.gui.IndexerFrame;
import client.gui.batchstate.*;

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel implements BatchStateListener {

	BatchState batchState;
	
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
		
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.add(zoomInButton);
		this.add(Box.createRigidArea(new Dimension(10,0)));
		this.add(zoomOutButton);
		this.add(Box.createRigidArea(new Dimension(10,0)));
		this.add(invertImageButton);
		this.add(Box.createRigidArea(new Dimension(10,0)));
		this.add(toggleHighlightsButton);
		this.add(Box.createRigidArea(new Dimension(10,0)));
		this.add(saveButton);
		this.add(Box.createRigidArea(new Dimension(10,0)));
		this.add(submitButton);
		this.add(Box.createHorizontalGlue());
	}

	private ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == zoomInButton) {
				
			} else if (e.getSource() == zoomOutButton) {
				
			} else if (e.getSource() == invertImageButton) {
				
			} else if (e.getSource() == toggleHighlightsButton) {
				
			} else if (e.getSource() == saveButton) {
				
			} else if (e.getSource() == submitButton) {
				
			}
		}

	};
	
	/* (non-Javadoc)
	 * @see client.gui.batchstate.BatchStateListener#valueChanged(client.gui.batchstate.Cell, java.lang.String)
	 */
	@Override
	public void valueChanged(Cell cell, String newValue) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.gui.batchstate.BatchStateListener#selectedCellChanged(client.gui.batchstate.Cell)
	 */
	@Override
	public void selectedCellChanged(Cell newSelectedCell) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.gui.batchstate.BatchStateListener#invertImageChanged()
	 */
	@Override
	public void invertImageChanged() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.gui.batchstate.BatchStateListener#highlightsVisibleChanged()
	 */
	@Override
	public void highlightsVisibleChanged() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.gui.batchstate.BatchStateListener#zoomChanged()
	 */
	@Override
	public void zoomChanged() {
		// TODO Auto-generated method stub
		
	}

}
