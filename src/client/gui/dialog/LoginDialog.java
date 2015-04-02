/**
 * 
 */
package client.gui.dialog;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import shared.communication.*;
import shared.model.*;
import client.communication.*;
import client.gui.IndexerFrame;

/**
 * @author tchambs
 * 
 */
@SuppressWarnings("serial")
public class LoginDialog extends JDialog {

	IndexerFrame frame;
	JButton loginButton;
	JButton exitButton;
	JTextField userField;
	JTextField passField;
	private String host;
	private int port;
	String username;
	String password;

	public LoginDialog(String hostname, int port, IndexerFrame inputFrame) {
		super();

		this.frame = inputFrame;
		this.host = hostname;
		this.port = port;
		createComponents();
	}

	private void createComponents() {
		this.setModal(true);
		this.setTitle("Login");
		this.setResizable(false);

		JLabel usernameLabel = new JLabel("Username:");
		JLabel passwordLabel = new JLabel("Password:");

		userField = new JTextField();
		userField.setPreferredSize(new Dimension(150, 20));

		JPanel userPanel = new JPanel();
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
		userPanel.add(usernameLabel);
		userPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		userPanel.add(userField);

		passField = new JTextField(20);
		passField.setPreferredSize(new Dimension(150, 20));

		JPanel passPanel = new JPanel();
		passPanel.setLayout(new BoxLayout(passPanel, BoxLayout.X_AXIS));
		passPanel.add(passwordLabel);
		passPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		passPanel.add(passField);

		// TODO add button functionality
		loginButton = new JButton("Login");
		loginButton.addActionListener(loginListener);

		exitButton = new JButton("Exit");
		exitButton.addActionListener(loginListener);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createRigidArea(new Dimension(45, 0)));
		buttonPanel.add(loginButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(3, 0)));
		buttonPanel.add(exitButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));

		BoxLayout layout = new BoxLayout(this.getContentPane(),
				BoxLayout.Y_AXIS);
		this.setLayout(layout);

		this.add(userPanel);
		this.add(Box.createRigidArea(new Dimension(0, 25)));
		this.add(passPanel);
		this.add(Box.createRigidArea(new Dimension(0, 25)));
		this.add(buttonPanel);

		this.addWindowListener(windowAdapter);

		this.setSize(400, 150);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private WindowAdapter windowAdapter = new WindowAdapter() {

		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	};

	private ActionListener loginListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == loginButton) {
				try {
					username = userField.getText();
					password = passField.getText();
				} catch (Exception exc) {
					JOptionPane
							.showMessageDialog(
									null,
									"Malformed input. Try again.\n"
											+ "USAGE: Hostname [String] Port [Integer] Username [String] Password [String]",
									"Input Error", JOptionPane.ERROR_MESSAGE);
				}

				login();

			} else if (e.getSource() == exitButton) {
				System.exit(0);
			}
		}
	};

	private void login() {

		ClientCommunicator comm = new ClientCommunicator(host, port);

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

		JOptionPane.showMessageDialog(null, "Welcome, "
				+ result.getResult().getFirstName() + " "
				+ result.getResult().getLastName() + ".\n" + "You have indexed "
				+ result.getResult().getRecordsIndexed() + " records",
				"Welcome", JOptionPane.INFORMATION_MESSAGE);

		this.setVisible(false);
		frame.setVisible(true);
	}
}
