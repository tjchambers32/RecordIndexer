package client.gui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel {

	public ButtonPanel() {
		super();

		createComponents();
	}

	private void createComponents() {

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		JButton zoomInButton = new JButton("Zoom In");
		
		JButton zoomOutButton = new JButton("Zoom Out");
		
		JButton invertImageButton = new JButton("Invert Image");
		
		JButton toggleHighlightsButton = new JButton("Toggle Highlights");
		
		JButton saveButton = new JButton("Save");

		JButton submitButton = new JButton("Submit");
		
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
}
