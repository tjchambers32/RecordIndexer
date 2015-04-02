/**
 * 
 */
package client.gui.dialog;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author tchambs
 *
 */
@SuppressWarnings("serial")
public class DownloadBatchDialog extends JDialog{

	String hostname;
	int port;
	
	public DownloadBatchDialog(String hostname, int port) {
		super();
		
		this.hostname = hostname;
		this.port = port;
		
		this.setModal(true);
		this.setTitle("Download Batch");
		this.setResizable(false);
		
		createComponents();
	}
	
	private void createComponents() {
		
		JLabel projectLabel = new JLabel("Project:");
		JButton viewSampleButton = new JButton("View Sample");
		JButton cancelButton = new JButton("Cancel");
		JButton downloadButton = new JButton("Download");
		
		String[] projectList = {"1890 Census", "1900 Census", "Draft Records"};
		JComboBox projectsComboBox = new JComboBox(projectList);
		
		JPanel selectionPanel = new JPanel();
		selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.X_AXIS));
		
		selectionPanel.add(Box.createRigidArea(new Dimension(5, 25)));
		selectionPanel.add(projectLabel);
		selectionPanel.add(Box.createRigidArea(new Dimension(5, 25)));
		selectionPanel.add(projectsComboBox);
		selectionPanel.add(Box.createRigidArea(new Dimension(5, 25)));
		selectionPanel.add(viewSampleButton);
		selectionPanel.add(Box.createRigidArea(new Dimension(5, 25)));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 25)));
		buttonPanel.add(cancelButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 25)));
		buttonPanel.add(downloadButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 25)));
		
		JPanel rootPanel = new JPanel();
		rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
		
		rootPanel.add(Box.createRigidArea(new Dimension(300, 10)));
		rootPanel.add(selectionPanel);
		rootPanel.add(Box.createRigidArea(new Dimension(300, 10)));
		rootPanel.add(buttonPanel);
		rootPanel.add(Box.createRigidArea(new Dimension(300, 10)));
		
		this.add(rootPanel);
		
		this.setSize(330, 100);
		this.setVisible(true);
	}
}
