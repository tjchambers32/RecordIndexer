/**
 * 
 */
package client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import client.communication.*;
import client.gui.dialog.LoginDialog;

/**
 * @author tchambs
 * 
 */
@SuppressWarnings("serial")
public class IndexerFrame extends JFrame{

	
	public int port = 39460;
	public String hostname = "localhost";
	private LoginDialog loginDialog;
	JMenuItem exitMenuItem;
	JMenuItem downloadBatchMenuItem;
	JMenuItem logoutMenuItem;
	
	ButtonPanel buttonPanel;
	ImagePanel imagePanel;
	EntryPanel BLentryPanel;
	EntryPanel BRentryPanel;
	TableEntryPanel tableEntryPanel;
	FormEntryPanel formEntryPanel;
	FieldHelpPanel fieldHelpPanel;
	ImageNavigatorPanel imageNavigatorPanel;
	
	JSplitPane horizontalSplit;
	JSplitPane verticalSplit;
	
	IndexerFrame(String title, String[] args) {
		super(title);
		
		this.hostname = args[0];
		this.port = Integer.parseInt(args[1]);
	
		this.addWindowListener(windowAdapter);
		
		loginDialog = new LoginDialog(hostname, port, this);
		
		createComponents();
	}
	
	private void createComponents() {
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("File");
		menu.setMnemonic('c');
		menuBar.add(menu);
		
		downloadBatchMenuItem = new JMenuItem("Download Batch");
		downloadBatchMenuItem.addActionListener(actionListener);
		menu.add(downloadBatchMenuItem);
		
		logoutMenuItem = new JMenuItem("Logout");
		logoutMenuItem.addActionListener(actionListener);
		menu.add(logoutMenuItem);
		
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(actionListener);
		menu.add(exitMenuItem);
		
		buttonPanel = new ButtonPanel();
		imagePanel = new ImagePanel();
		BLentryPanel = new EntryPanel();
		tableEntryPanel = new TableEntryPanel();
		formEntryPanel = new FormEntryPanel();
		
		BLentryPanel.addTab("Table Entry", tableEntryPanel);
		BLentryPanel.addTab("Form Entry", formEntryPanel);
		
		fieldHelpPanel = new FieldHelpPanel();
		imageNavigatorPanel = new ImageNavigatorPanel();
		
		BRentryPanel = new EntryPanel();
		BRentryPanel.addTab("Field Help", fieldHelpPanel);
		BRentryPanel.addTab("Image Navigator", imageNavigatorPanel);
		
		horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, BLentryPanel, BRentryPanel);
		
		verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, imagePanel, horizontalSplit);
		
		
		JPanel rootPanel = new JPanel();
		rootPanel.setLayout(new BorderLayout());
		
		rootPanel.add(buttonPanel, BorderLayout.NORTH);
		rootPanel.add(verticalSplit, BorderLayout.CENTER);
		
		this.add(rootPanel);
	}
	
	private ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == logoutMenuItem) {
				logout();
			} else if (e.getSource() == exitMenuItem) {
				System.exit(0);
			} else if (e.getSource() == downloadBatchMenuItem) {
				downloadBatch();
			}
		}

	};
	
	private WindowAdapter windowAdapter = new WindowAdapter() {

		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	};
	
	private void logout() {
		//TODO implement
	}
	
	private void downloadBatch() {
		//TODO implement
	}
	
	public static void main(final String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				IndexerFrame frame;
				if (args.length == 2) {
					frame = new IndexerFrame("Record Indexer", args);
				} else {
					String[] defaultArgs = {"localhost", Integer.toString(39640)};
					frame = new IndexerFrame("Record Indexer", defaultArgs);
				}
				
				//frame.pack();
				frame.setSize(500,500);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}
