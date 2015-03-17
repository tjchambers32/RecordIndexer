package client.search;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import client.ClientException;
import client.communication.ClientCommunicator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

import shared.communication.*;
import shared.model.*;

/**
 * @author tchambs
 * 
 */
@SuppressWarnings("serial")
public class SearchGUI extends JFrame {

	ClientCommunicator comm;
	JTextField hostField;
	JTextField portField;
	JTextField userField;
	JTextField passwordField;
	String host;
	int port;
	String username;
	String password;
	private JButton loginButton;
	private JMenuItem exitMenuItem;
	ProjectPanel projectPanel;
	static JPanel imagePanel;
	static JLabel imagePanelLabel;
	/**
	 * @param string
	 */
	public SearchGUI(String windowTitle) {
		super(windowTitle);
		comm = new ClientCommunicator();

		createComponents();

	}

	private void createComponents() {

		addWindowListener(windowAdapter);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("File");
		menu.setMnemonic('c');
		menuBar.add(menu);

		exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		exitMenuItem.addActionListener(actionListener);
		menu.add(exitMenuItem);

		JLabel hostLabel = new JLabel("Host Name: ");

		hostField = new JTextField(50);
		hostField.setOpaque(true);
		hostField.setBackground(Color.white);
		hostField.setPreferredSize(new Dimension(50, 20));
		hostField.setText("localhost"); // default value to speed up testing

		JLabel portLabel = new JLabel("Port Number: ");

		portField = new JTextField(50);
		portField.setOpaque(true);
		portField.setBackground(Color.white);
		portField.setPreferredSize(new Dimension(50, 20));
		portField.setText("39640"); // default value to speed up testing

		JLabel userLabel = new JLabel("Username: ");

		userField = new JTextField(50);
		userField.setOpaque(true);
		userField.setBackground(Color.white);
		userField.setPreferredSize(new Dimension(50, 20));
		userField.setText("sheila"); // default value to speed up testing

		JLabel passwordLabel = new JLabel("Password: ");

		passwordField = new JTextField(50);
		passwordField.setOpaque(true);
		passwordField.setBackground(Color.white);
		passwordField.setPreferredSize(new Dimension(50, 20));
		passwordField.setText("parker"); // default value to speed up testing
		
		loginButton = new JButton("Login");
		loginButton.addActionListener(actionListener);

		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.X_AXIS));
		loginPanel.setMaximumSize(new Dimension(1000, 1000));
		loginPanel.add(hostLabel);
		loginPanel.add(hostField);
		loginPanel.add(portLabel);
		loginPanel.add(portField);
		loginPanel.add(userLabel);
		loginPanel.add(userField);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordField);
		loginPanel.add(loginButton);

		GridBagConstraints gbc = new GridBagConstraints();

		// TODO: Implement image panel
		imagePanel = new JPanel();
		imagePanel.setLayout(new GridBagLayout());
		imagePanelLabel = new JLabel("No Image Selected");
		imagePanel.add(imagePanelLabel, gbc);
		imagePanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		imagePanel.setPreferredSize(new Dimension(1000, 500));

		projectPanel = new ProjectPanel();
		projectPanel.setLayout(new GridBagLayout());
		projectPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

		JPanel rootPanel = new JPanel();
		rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
		rootPanel.add(loginPanel);
		rootPanel.add(projectPanel);
		rootPanel.add(imagePanel);

		this.add(rootPanel);
	}

	private ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == loginButton) {
				try {
					host = hostField.getText();
					port = Integer.parseInt(portField.getText());
					username = userField.getText();
					password = passwordField.getText();
				} catch (Exception exc) {
					JOptionPane
							.showMessageDialog(
									null,
									"Malformed input. Try again.\n"
											+ "USAGE: Hostname [String] Port [Integer] Username [String] Password [String]",
									"Input Error", JOptionPane.ERROR_MESSAGE);
				}

				login();

			} else if (e.getSource() == exitMenuItem) {
				System.exit(0);
			}
		}

	};

	private WindowAdapter windowAdapter = new WindowAdapter() {

		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	};

	public void login() {
		ClientCommunicator comm = new ClientCommunicator(host, port);
		projectPanel.setComm(comm);
		
		User user = new User(username, password);
		ValidateUser_Params params = new ValidateUser_Params(user);
		ValidateUser_Result result = null;

		try {
			result = comm.ValidateUser(params);
		} catch (Exception e) {
			if (e.getCause().toString().contains("Connect")) {
				JOptionPane.showMessageDialog(null, "Check server",
						"Connection Error", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Invalid User Credentials",
						"Input Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if (result.getResult() == null) {
			JOptionPane.showMessageDialog(null, "Invalid User Credentials",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			
			return;
		}
		
		projectPanel.setUser(user);
		
		GetProjects_Params projParams = new GetProjects_Params(user);
		GetProjects_Result projResult = null;
		
		try {
			projResult = comm.getProjects(projParams);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Could not get project list",
					"Input Error", JOptionPane.ERROR_MESSAGE);
		}
		
		ArrayList<String> projects = new ArrayList<String>();
		for (Project p : projResult.getProjects()) {
			projects.add(p.getTitle());
		}
		projectPanel.setProjects(projects);
		
		GetFields_Params fieldParams = new GetFields_Params(user, -1); //-1 gets all fields
		GetFields_Result fieldResult = null;
		
		try {
			fieldResult = comm.getFields(fieldParams);
		} catch (ClientException e) {
			JOptionPane.showMessageDialog(null, "Could not get fields list",
					"Input Error", JOptionPane.ERROR_MESSAGE);
		}
		
		ArrayList<String> fields = new ArrayList<String>();
		for (Field f : fieldResult.getFields()) {
			fields.add(f.getTitle());
		}
		projectPanel.setFields(fields);
	}

	public static void redrawImage(ImageIcon newImage) {
		imagePanelLabel.setText(null);
		imagePanelLabel.setIcon(newImage);
		imagePanel.revalidate();
		imagePanel.repaint();
	}
	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				SearchGUI frame = new SearchGUI("SearchGUI");

				// frame.pack();
				frame.setSize(1500, 800);
				frame.setVisible(true);
			}
		});
	}
}
