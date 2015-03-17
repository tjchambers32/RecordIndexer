package client.search;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

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
	
	public ProjectPanel() {
		super();

		createComponents();
	}

	private void createComponents() {

		this.setLayout((LayoutManager) new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

		// Projects
		JLabel projectsLabel = new JLabel("Projects:");

		projectsArea = new JTextArea();
		JScrollPane projectScroll = new JScrollPane(projectsArea);
		projectsArea.setOpaque(true);
		projectsArea.setBackground(Color.WHITE);
		projectsArea.setEditable(false);
		projectsArea.setPreferredSize(new Dimension(100, 60));

		this.add(Box.createVerticalGlue());
		this.add(projectsLabel);
		this.add(Box.createRigidArea(new Dimension(0, 3)));
		this.add(projectScroll);
		this.add(Box.createVerticalGlue());

		// Fields
		JLabel fieldsLabel = new JLabel("Fields to Search:");

		DefaultListModel<String> fieldModel;
		fieldModel = new DefaultListModel<String>();

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

		// Images
		JLabel imageLabel = new JLabel("Images: ");

		DefaultListModel<String> imageModel;
		imageModel = new DefaultListModel<String>();

		images = new JList<String>(imageModel);

		images.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		images.setSelectedIndex(0);
		images.setVisibleRowCount(5);
		JScrollPane imageScroll = new JScrollPane(images);
		
		this.add(imageLabel);
		this.add(Box.createRigidArea(new Dimension(0, 3)));
		this.add(imageScroll);
		this.add(Box.createVerticalGlue());
		
	}

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
				
				//TODO: Implement SEARCH
				
			}
		}
	};
	
	public void setProjects(ArrayList<String> projects) {
		StringBuilder sb = new StringBuilder();
		for (String s : projects) {
			sb.append(s + "\n");
		}
		projectsArea.setText(sb.toString());
	}
	
	public void setFields(ArrayList<String> inputFields) {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		for (String s : inputFields) {
			listModel.addElement(s);
		}
		fields.setModel(listModel);
	}

}
