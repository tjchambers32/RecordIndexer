/**
 * 
 */
package client.gui.dialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import shared.communication.DownloadBatch_Params;
import shared.communication.DownloadBatch_Result;
import shared.model.Project;
import shared.model.User;
import client.ClientException;
import client.gui.batchstate.BatchState;
import client.search.SearchGUI;

/**
 * @author tchambs
 *
 */
@SuppressWarnings("serial")
public class DownloadBatchDialog extends JDialog{

	BatchState batchState;
	
	Project selectedProject;
	int projectID;
	
	JButton viewSampleButton;
	JButton cancelButton;
	JButton downloadButton;
	JComboBox<String> projectsComboBox;
	
	String[] projectList = {"1890 Census", "1900 Census", "Draft Records"};
	
	
	public DownloadBatchDialog(BatchState batchState) {
		super();
		
		this.batchState = batchState;

		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setTitle("Download Batch");
		this.setResizable(false);
		
		createComponents();
	}
	
	private void createComponents() {
		
		JLabel projectLabel = new JLabel("Project:");
		viewSampleButton = new JButton("View Sample");
		viewSampleButton.addActionListener(actionListener);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(actionListener);
		downloadButton = new JButton("Download");
		downloadButton.addActionListener(actionListener);
		
		//TODO: GET ACTUAL PROJECTS LIST
		projectsComboBox = new JComboBox<String>(projectList);
		
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
	
	private ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == viewSampleButton) {
				
			} else if (e.getSource() == cancelButton) {
				
			} else if (e.getSource() == downloadButton) {
				projectID = projectsComboBox.getSelectedIndex() + 1;
				User user = batchState.getUser();
				DownloadBatch_Params params = new DownloadBatch_Params(user, projectID);
				
				try {
					DownloadBatch_Result result = batchState.getComm().downloadBatch(params);
				} catch (ClientException e1) {
					e1.printStackTrace();
				}
				
			}
			
			System.out.println(e.getSource() + "\n");
			
			System.out.println(e.toString() + "\n");
		}
	};
}
