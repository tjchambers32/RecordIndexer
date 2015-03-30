/**
 * 
 */
package client.gui.dialog;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author tchambs
 * 
 */
public class LoginDialog extends JDialog {

	JButton loginButton;
	JButton exitButton;
	JTextField userField;
	JTextField passField;
	
	public LoginDialog() {
		super();

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
		userPanel.add(Box.createRigidArea(new Dimension(10,0)));
		userPanel.add(userField);

		passField = new JTextField(20);
		passField.setPreferredSize(new Dimension(150, 20));
		
		JPanel passPanel = new JPanel();
		passPanel.setLayout(new BoxLayout(passPanel, BoxLayout.X_AXIS));
		passPanel.add(passwordLabel);
		passPanel.add(Box.createRigidArea(new Dimension(10,0)));
		passPanel.add(passField);
		
		//TODO add button functionality
		loginButton = new JButton("Login");
		
		exitButton = new JButton("Exit");
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createRigidArea(new Dimension(45,0)));
		buttonPanel.add(loginButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(3,0)));
		buttonPanel.add(exitButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(50,0)));
		
		BoxLayout layout = new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS);
		this.setLayout(layout);

		this.add(userPanel);
		this.add(Box.createRigidArea(new Dimension(0, 25)));
		this.add(passPanel);
		this.add(Box.createRigidArea(new Dimension(0, 25)));
		this.add(buttonPanel);
		
		this.setSize(400, 150);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
