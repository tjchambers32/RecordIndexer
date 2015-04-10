package client.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import client.gui.ImageComponent;

@SuppressWarnings("serial")
public class SampleImageDialog extends JDialog{

	ImageComponent sampleImage;
	String imageURL;
	JButton closeButton;
	
	public SampleImageDialog(String projectTitle, String imageURL) {
		super();
		
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setSize(new Dimension(600, 450));
		this.imageURL = imageURL;
		this.setTitle(projectTitle);
		this.setOpacity(1.0f);
		
		createComponents();
	}
	
	private void createComponents() {
		
		sampleImage = new ImageComponent(imageURL);
		sampleImage.repaint();
		
		closeButton = new JButton("Close");
		closeButton.addActionListener(actionListener);
		
		JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(closeButton);
        buttonPanel.add(Box.createHorizontalGlue());
        
        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout());
        rootPanel.add(sampleImage, BorderLayout.CENTER);
        rootPanel.add(buttonPanel, BorderLayout.SOUTH);
        rootPanel.setPreferredSize(new Dimension(600,450));
        
        this.add(rootPanel);
        this.setResizable(false);
	}
	
	private ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == closeButton) {
				SampleImageDialog.this.dispose();
			}
		}
	};
	
}
