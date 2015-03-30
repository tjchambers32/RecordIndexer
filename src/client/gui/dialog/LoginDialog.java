/**
 * 
 */
package client.gui.dialog;

import javax.swing.JDialog;

/**
 * @author tchambs
 *
 */
public class LoginDialog extends JDialog{
	
	public LoginDialog() {
		
		//TODO why isn't this recognized as the JDialog constructor?
		
		//public JDialog(Frame owner, String title, boolean modal)
		
		super((Frame)null, "Login", true);
		
		this.setSize(400,200);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
