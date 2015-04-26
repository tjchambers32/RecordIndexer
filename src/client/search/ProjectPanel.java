package client.search;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.communication.*;
import shared.communication.*;
import shared.model.*;

/**
 * @author tchambs
 * 
 */
@SuppressWarnings("serial")
public class ProjectPanel extends JPanel {

	JButton searchButton;
	JTextArea searchArea;
	JTextArea imageArea;
	ArrayList<String> searchValues;
	JList<String> fields;
	JList<String> images;
	JList<String> projects;
	JTextArea projectsArea;
	User user = null;
	ClientCommunicator comm = null;

	public ProjectPanel() {
		super();

		createComponents();
	}

	private void createComponents() {

		this.setLayout((LayoutManager) new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

		// Projects
		JLabel projectsLabel = new JLabel("Projects:");

		DefaultListModel<String> projectsModel = new DefaultListModel<String>();

		projects = new JList<String>(projectsModel);
		projects.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		projects.setSelectedIndex(0);
		projects.setVisibleRowCount(3);
		projects.addListSelectionListener(projectsListener);
		JScrollPane projectScroll = new JScrollPane(projects);

		this.add(Box.createVerticalGlue());
		this.add(projectsLabel);
		this.add(Box.createRigidArea(new Dimension(0, 3)));
		this.add(projectScroll);
		this.add(Box.createVerticalGlue());

		// Fields
		JLabel fieldsLabel = new JLabel("Fields to Search:");

		DefaultListModel<String> fieldModel = new DefaultListModel<String>();

		fields = new JList<String>(fieldModel);
		fields.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		fields.setSelectedIndex(0);
		fields.setVisibleRowCount(5);
		JScrollPane fieldsScroll = new JScrollPane(fields);

		this.add(fieldsLabel);
		this.add(Box.createRigidArea(new Dimension(0, 3)));
		this.add(fieldsScroll);
		this.add(Box.createVerticalGlue());

		// Search
		JLabel searchLabel = new JLabel("Search Values: ");

		searchArea = new JTextArea();
		JScrollPane searchScroll = new JScrollPane(searchArea);
		searchArea.setOpaque(true);
		searchArea.setBackground(Color.WHITE);
		searchArea.setEditable(true);
		searchArea.setPreferredSize(new Dimension(150, 60));

		searchButton = new JButton("Search");
		searchButton.addActionListener(actionListener);

		this.add(Box.createVerticalGlue());
		this.add(searchLabel);
		this.add(Box.createRigidArea(new Dimension(0, 3)));
		this.add(searchScroll);
		this.add(Box.createVerticalGlue());
		this.add(searchButton);
		this.add(Box.createVerticalGlue());
		this.add(Box.createRigidArea(new Dimension(0, 3)));

		// Images
		JLabel imageLabel = new JLabel("Image Results: ");

		DefaultListModel<String> imageModel = new DefaultListModel<String>();
		images = new JList<String>(imageModel);
		images.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		images.setSelectedIndex(0);
		images.setVisibleRowCount(5);
		images.addListSelectionListener(imageListener);
		JScrollPane imageScroll = new JScrollPane(images);

		this.add(imageLabel);
		this.add(Box.createRigidArea(new Dimension(0, 3)));
		this.add(imageScroll);
		this.add(Box.createVerticalGlue());

	}

	private ListSelectionListener imageListener = new ListSelectionListener() {

		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting()) {
				String imageURL = images.getSelectedValue();
				BufferedImage image = null;
				try {
					image = ImageIO.read(new URL(imageURL));
					ImageIcon icon = new ImageIcon(image);
					SearchGUI.redrawImage(icon);
				} catch (MalformedURLException e1) {
					JOptionPane.showMessageDialog(null, "Invalid URL.",
							"Image Display Error", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null,
							"IOException Occurred.", "Image Display Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	};

	private ListSelectionListener projectsListener = new ListSelectionListener() {

		public void valueChanged(ListSelectionEvent e) {

//			String selectedProject = projects.getSelectedValue();
		}

	};

	private ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == searchButton) {
				searchValues = new ArrayList<String>();
				String searchText = searchArea.getText();

				String[] searchStrings = searchText.split("\\s*[,]\\s*");
				for (String s : searchStrings) {
					searchValues.add(s);
				}

				ArrayList<Integer> searchFields = new ArrayList<Integer>();
				for (int i : fields.getSelectedIndices()) {
					searchFields.add(i + 1);
				}

				search(searchFields, searchStrings);

			}
		}
	};

	public void search(ArrayList<Integer> searchFields, String[] searchStrings) {

		if (user == null) {
			JOptionPane.showMessageDialog(null, "Login required.",
					"Search Error", JOptionPane.ERROR_MESSAGE);

			return;
		}

		Search_Params params = new Search_Params(user, searchFields,
				searchValues);
		ArrayList<Search_Result> result = new ArrayList<Search_Result>();

		try {
			result = comm.search(params);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Search failed. Exception caught.", "Search Error",
					JOptionPane.ERROR_MESSAGE);
		}

		ArrayList<String> imageResults = new ArrayList<String>();
		for (int i = 0; i < result.size(); i++) {
			imageResults.add(result.get(i).getImageURL());
		}
		this.setImages(imageResults);
	}

	public void setProjects(ArrayList<String> inputProjects) {
		DefaultListModel<String> projectModel = new DefaultListModel<String>();

		for (String s : inputProjects) {
			projectModel.addElement(s);
		}
		projects.setModel(projectModel);
	}

	public void setImages(ArrayList<String> inputImages) {
		DefaultListModel<String> imageModel = new DefaultListModel<String>();

		for (String s : inputImages) {
			imageModel.addElement(comm.getURL_PREFIX() + File.separator + s);
		}
		images.setModel(imageModel);
	}

	public void setFields(ArrayList<String> inputFields) {
		DefaultListModel<String> fieldModel = new DefaultListModel<String>();

		for (String s : inputFields) {
			fieldModel.addElement(s);
		}
		fields.setModel(fieldModel);
	}

	public void setUser(User inputUser) {
		this.user = inputUser;
	}

	public void setComm(ClientCommunicator comm) {
		this.comm = comm;
	}
}
