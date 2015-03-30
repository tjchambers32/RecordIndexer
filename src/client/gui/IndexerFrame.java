/**
 * 
 */
package client.gui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;

import client.communication.*;
import client.gui.dialog.LoginDialog;

/**
 * @author tchambs
 * 
 */
@SuppressWarnings("serial")
public class IndexerFrame extends JFrame{

	private int port = 39460;
	private String hostname = "localhost";
	private ClientCommunicator comm;
	private LoginDialog loginDialog;
	
	IndexerFrame(String title, String[] args) {
		super(title);
		
		this.hostname = args[0];
		this.port = Integer.parseInt(args[1]);
		comm = new ClientCommunicator(hostname, port);
		
		loginDialog = new LoginDialog();
		
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
				
				frame.setVisible(true);
			}
		});
	}
}