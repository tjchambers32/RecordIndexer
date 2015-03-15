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
	ArrayList<String> searchValues;
	
	public ProjectPanel() {
		super();

		createComponents();
	}

	private void createComponents() {

		this.setLayout((LayoutManager) new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		
		// Projects
		JLabel projectsLabel = new JLabel("Projects:");

		JTextArea projectsArea = new JTextArea();
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

		DefaultListModel<String> listModel;
		listModel = new DefaultListModel<String>();

		JList<String> fieldsList = new JList<String>(listModel);
		fieldsList
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		fieldsList.setSelectedIndex(0);
		fieldsList.setVisibleRowCount(5);
		JScrollPane fieldsScroll = new JScrollPane(fieldsList);

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
		this.add(Box.createRigidArea(new Dimension(0,3)));
		this.add(searchScroll);
		this.add(Box.createVerticalGlue());
		
	}
	
	private ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == searchButton) {
				String searchText = searchArea.getText();
				
				String[] searchStrings = searchText.split("\\s*{,}\\s*");
				for (String s : searchStrings) {
					searchValues.add(s);
				}
				
			}
		}
	};
}
