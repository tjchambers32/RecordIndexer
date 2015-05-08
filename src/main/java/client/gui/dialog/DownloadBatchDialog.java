/**
 * 
 */
package main.java.client.gui.dialog;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import main.java.client.ClientException;
import main.java.client.gui.batchstate.BatchState;
import main.java.shared.communication.*;
import main.java.shared.model.Project;
import main.java.shared.model.User;

/**
 * @author tchambs
 *
 */
@SuppressWarnings("serial")
public class DownloadBatchDialog extends JDialog {

	BatchState batchState;

	Project selectedProject;
	int projectID;
	String projectTitle;

	JButton viewSampleButton;
	JButton cancelButton;
	JButton downloadButton;
	JComboBox<String> projectsComboBox;
	SampleImageDialog sampleImageDialog;

	String[] projectList;

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

		// TODO: GET ACTUAL PROJECTS LIST
		GetProjects_Params params = new GetProjects_Params(batchState.getUser());
		GetProjects_Result result = null;
		try {
			result = batchState.getComm().getProjects(params);
		} catch (ClientException e) {
			JOptionPane.showMessageDialog(null, "Check main.java.server",
					"Connection Error", JOptionPane.ERROR_MESSAGE);
		}
		
		projectList = new String[result.getProjects().size()];
		for (int i = 0; i < result.getProjects().size(); i++) {
			projectList[i] = result.getProjects().get(i).getTitle();
		}
		
		projectsComboBox = new JComboBox<String>(projectList);

		JPanel selectionPanel = new JPanel();
		selectionPanel
				.setLayout(new BoxLayout(selectionPanel, BoxLayout.X_AXIS));

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
	}

	private ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			projectID = projectsComboBox.getSelectedIndex() + 1;
			projectTitle = projectsComboBox.getSelectedItem().toString();

			User user = batchState.getUser();

			if (e.getSource() == viewSampleButton) {

				GetSampleImage_Params params = new GetSampleImage_Params(user,
						projectID);
				GetSampleImage_Result result = null;
				try {
					result = batchState.getComm().getSampleImage(params);
				} catch (ClientException e1) {
					JOptionPane.showMessageDialog(null,
							"Could not get sample image.", "Connection Error",
							JOptionPane.ERROR_MESSAGE);
				}

				String imageURL = "http://" + batchState.getHostname() + ":"
						+ batchState.getPort() + "/"
						+ result.getImageURL();
				
				sampleImageDialog = new SampleImageDialog(projectTitle,
						imageURL);
				
				sampleImageDialog.setVisible(true);
				sampleImageDialog.repaint();

			} else if (e.getSource() == cancelButton) {
				DownloadBatchDialog.this.dispose();
			} else if (e.getSource() == downloadButton) {

				DownloadBatch_Params params = new DownloadBatch_Params(user,
						projectID);
				DownloadBatch_Result result = null;
				try {
					result = batchState.getComm().downloadBatch(params);
				} catch (ClientException e1) {
					JOptionPane.showMessageDialog(null,
						    "You must submit the current batch before you can download another.",
						    "Unable to Download Batch",
						    JOptionPane.ERROR_MESSAGE);
				}

				if (result == null)
					return; // TODO implement joptionpanel that explains no
							// batch available

				batchState.setDownloadedBatch(result);
				DownloadBatchDialog.this.dispose();

			}
		}
	};
}
