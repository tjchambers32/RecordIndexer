package client.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import client.gui.batchstate.*;
import client.gui.dialog.*;
import client.gui.panel.*;

/**
 * @author tchambs
 * 
 */
@SuppressWarnings("serial")
public class IndexerFrame extends JFrame implements BatchStateListener {

	public int port = 39460;
	public String hostname = "localhost";

	BatchState batchState;

	private LoginDialog loginDialog;
	private DownloadBatchDialog downloadBatchDialog;

	JMenuItem exitMenuItem;
	JMenuItem downloadBatchMenuItem;
	JMenuItem logoutMenuItem;

	ButtonPanel buttonPanel;
	ImagePanel imagePanel;
	JTabbedPane BLentryPanel;
	JTabbedPane BRentryPanel;
	TableEntryPanel tableEntryPanel;
	FormEntryPanel formEntryPanel;
	FieldHelpPanel fieldHelpPanel;
	ImageNavigatorPanel imageNavigatorPanel;

	JPanel rootPanel;
	
	JSplitPane horizontalSplit;
	JSplitPane verticalSplit;

	IndexerFrame(String title, String[] args) {
		super(title);

		this.hostname = args[0];
		this.port = Integer.parseInt(args[1]);

		this.addWindowListener(windowAdapter);

		batchState = new BatchState(hostname, port);
		batchState.addListener(this);

		loginDialog = new LoginDialog(batchState, this);

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

		buttonPanel = new ButtonPanel(this);

		imagePanel = new ImagePanel(batchState, null);
		imagePanel.setPreferredSize(new Dimension(800, 500));
		imagePanel.setMinimumSize(new Dimension(300, 250));

		tableEntryPanel = new TableEntryPanel(batchState);
		formEntryPanel = new FormEntryPanel(batchState);
		fieldHelpPanel = new FieldHelpPanel(batchState);
		imageNavigatorPanel = new ImageNavigatorPanel(batchState);
		imageNavigatorPanel.setBackground(Color.DARK_GRAY);
		
		BLentryPanel = new JTabbedPane();
		BLentryPanel.setMinimumSize(new Dimension(300, 100));
		BLentryPanel.setPreferredSize(new Dimension(400, 200));
		JScrollPane tableScroll = new JScrollPane(tableEntryPanel);
		tableScroll.getVerticalScrollBar().setUnitIncrement(10);
		BLentryPanel.addTab("Table Entry", tableScroll);
		BLentryPanel.addTab("Form Entry", formEntryPanel);

		BRentryPanel = new JTabbedPane();
		BRentryPanel.setMinimumSize(new Dimension(200, 100));
		BRentryPanel.setPreferredSize(new Dimension(400, 200));
		BRentryPanel.addTab("Field Help", fieldHelpPanel);
		BRentryPanel.addTab("Image Navigator", imageNavigatorPanel);
		
		horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, BLentryPanel, BRentryPanel);
		horizontalSplit.setPreferredSize(new Dimension(100, 100));

		verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, imagePanel, horizontalSplit);

		rootPanel = new JPanel();
		rootPanel.setLayout(new BorderLayout());

		rootPanel.add(buttonPanel, BorderLayout.NORTH);
		rootPanel.add(verticalSplit, BorderLayout.CENTER);
		this.pack();

		this.add(rootPanel);

		// batchState.setZoomLevel(batchState.getZoomLevel());

	}
	
	public void clearEntryPanels() {
		
		BLentryPanel.removeAll();
		
		tableEntryPanel = new TableEntryPanel(batchState);
		JScrollPane tableScroll = new JScrollPane(tableEntryPanel);
		tableScroll.getVerticalScrollBar().setUnitIncrement(10);
		
		formEntryPanel = new FormEntryPanel(batchState);
		
		BLentryPanel.addTab("Table Entry", tableScroll);
		BLentryPanel.addTab("Form Entry", formEntryPanel);
		
		repaint();
		
	}

	private ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == logoutMenuItem) {
				logout();
			} else if (e.getSource() == exitMenuItem) {
				saveWindowLocation();
				batchState.save();
				System.exit(0);
			} else if (e.getSource() == downloadBatchMenuItem) {
				downloadBatch();
			}
		}

	};

	private WindowAdapter windowAdapter = new WindowAdapter() {

		public void windowClosing(WindowEvent e) {
			saveWindowLocation();
			batchState.save();
			System.exit(0);
		}
	};

	private void logout() {

		// save state
		saveWindowLocation();
		batchState.save();

		// hide main indexer window
		this.setVisible(false);

		// show login dialog
		loginDialog = new LoginDialog(batchState, this);
	}

	private void downloadBatch() {
		downloadBatchDialog = new DownloadBatchDialog(batchState);
	}

	private void saveWindowLocation() {

		batchState.setWindowPositionX(IndexerFrame.this.getX());
		batchState.setWindowPositionY(IndexerFrame.this.getY());
		batchState.setWindowSizeX(IndexerFrame.this.getWidth());
		batchState.setWindowSizeY(IndexerFrame.this.getHeight());

		batchState.setHorizontalDivider(horizontalSplit.getDividerLocation());
		batchState.setVerticalDivider(verticalSplit.getDividerLocation());
	}

	public static void main(final String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				IndexerFrame frame;
				if (args.length == 2) {
					frame = new IndexerFrame("Record Indexer", args);
				} else {
					String[] defaultArgs = { "localhost", Integer.toString(39640) };
					frame = new IndexerFrame("Record Indexer", defaultArgs);
				}

				// frame.pack();
				frame.setSize(800, 800);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * @return
	 */
	public BatchState getBatchState() {
		return batchState;
	}

	public void setBatchState(BatchState batchState) {
		this.batchState = batchState;
	}

	@Override
	public void stateChanged() {

		if (this.batchState.getHasDownloadedBatch() && downloadBatchMenuItem != null) {
			this.downloadBatchMenuItem.setEnabled(false);
		} else if (downloadBatchMenuItem != null)
			this.downloadBatchMenuItem.setEnabled(true);
		
		repaint();

	}
}
