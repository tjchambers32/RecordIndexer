/**
 * 
 */
package client.gui.dialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import shared.model.*;
import client.gui.IndexerFrame;
import client.gui.batchstate.BatchState;
import client.gui.batchstate.BatchStateListener;

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
	JPasswordField passField;
	String username;
	String password;
	
	BatchState batchState;

	public LoginDialog(BatchState batchState, IndexerFrame inputFrame) {
		super();

		this.frame = inputFrame;
		this.batchState = batchState;
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
		userPanel.add(Box.createRigidArea(new Dimension(5,0)));
		userPanel.add(usernameLabel);
		userPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		userPanel.add(userField);
		userPanel.add(Box.createRigidArea(new Dimension(5,0)));
		
		passField = new JPasswordField(20);
		passField.setPreferredSize(new Dimension(150, 20));

		JPanel passPanel = new JPanel();
		passPanel.setLayout(new BoxLayout(passPanel, BoxLayout.X_AXIS));
		
		passPanel.add(Box.createRigidArea(new Dimension(5,0)));
		passPanel.add(passwordLabel);
		passPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		passPanel.add(passField);
		passPanel.add(Box.createRigidArea(new Dimension(5,0)));
		
		loginButton = new JButton("Login");
		loginButton.addActionListener(loginListener);

		exitButton = new JButton("Exit");
		exitButton.addActionListener(loginListener);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createRigidArea(new Dimension(45, 0)));
		buttonPanel.add(loginButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonPanel.add(exitButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));

		BoxLayout layout = new BoxLayout(this.getContentPane(),
				BoxLayout.Y_AXIS);
		this.setLayout(layout);

		this.add(Box.createRigidArea(new Dimension(0, 5)));
		this.add(userPanel);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(passPanel);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(buttonPanel);
		this.add(Box.createRigidArea(new Dimension(0, 5)));

		this.addWindowListener(windowAdapter);

		this.setSize(375, 125);
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
					char[] passwordChars = passField.getPassword();
					password = new String(passwordChars);
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

		User user = new User(username, password);
		ValidateUser_Params params = new ValidateUser_Params(user);
		ValidateUser_Result result = null;

		try {
			result = batchState.getComm().ValidateUser(params);
		} catch (Exception e) {
			if (e.getCause().toString().contains("Connect")) {
				JOptionPane.showMessageDialog(null, "Check server",
						"Connection Error", JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				JOptionPane.showMessageDialog(null, "Invalid User Credentials",
						"Input Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		if (result == null || result.getResult() == null) {
			JOptionPane.showMessageDialog(null, "Invalid User Credentials",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(null, "Welcome, "
				+ result.getResult().getFirstName() + " "
				+ result.getResult().getLastName() + ".\n" + "You have indexed "
				+ result.getResult().getRecordsIndexed() + " records",
				"Welcome", JOptionPane.INFORMATION_MESSAGE);

		batchState.setUser(result.getResult());
		
		//read in saved batchstate if possible
		XStream xstream = new XStream(new DomDriver());
		
		File savedBatch = new File("savedBatches/" + result.getResult().getUsername() + ".xml");
		
		if (savedBatch.exists()) {
			BatchState savedState = (BatchState) xstream.fromXML(savedBatch);
			savedState.setListeners(new ArrayList<BatchStateListener>());
			savedState.setUser(result.getResult());
			batchState.update(savedState);
		} else {
			BatchState emptyState = new BatchState(batchState.getHostname(), batchState.getPort());
			emptyState.setUser(result.getResult());
			batchState.update(emptyState);
		}
		
		batchState.stateChanged();
		
		this.setVisible(false);
		frame.setVisible(true);

	}
}
