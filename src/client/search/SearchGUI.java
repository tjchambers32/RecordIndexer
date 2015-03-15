package client.search;

import java.awt.BorderLayout;
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

import client.communication.ClientCommunicator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

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

		JLabel portLabel = new JLabel("Port Number: ");
		
		portField = new JTextField(50);
		portField.setOpaque(true);
		portField.setBackground(Color.white);
		portField.setPreferredSize(new Dimension(50, 20));
		
		JLabel userLabel = new JLabel("Username: ");
		
		userField = new JTextField(50);
		userField.setOpaque(true);
		userField.setBackground(Color.white);
		userField.setPreferredSize(new Dimension(50, 20));

		JLabel passwordLabel = new JLabel("Password: ");
		
		passwordField = new JTextField(50);
		passwordField.setOpaque(true);
		passwordField.setBackground(Color.white);
		passwordField.setPreferredSize(new Dimension(50, 20));

		
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
		
		//TODO: Implement image panel
		JPanel imagePanel = new JPanel();
		imagePanel.setLayout(new GridBagLayout());
		imagePanel.add(new JLabel("implement image panel"), gbc);
		imagePanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		ProjectPanel projectPanel = new ProjectPanel();
		projectPanel.setLayout(new GridBagLayout());
		projectPanel.setMinimumSize(new Dimension(1000, 250));
		projectPanel.setMaximumSize(new Dimension(1000, 400));
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
				host = hostField.getText();
				port = Integer.parseInt(portField.getText());
				username = userField.getText();
				password = passwordField.getText();
			}
	        else if (e.getSource() == exitMenuItem) {
	            System.exit(0);
	        }
		}
	};

	private WindowAdapter windowAdapter = new WindowAdapter() {

		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	};

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				SearchGUI frame = new SearchGUI("SearchGUI");

				//frame.pack();
				frame.setSize(1000,700);
				frame.setVisible(true);
			}
		});
	}
}
