package client.search;

import java.awt.EventQueue;
import client.communication.ClientCommunicator;
import javax.swing.JFrame;

/**
 * @author tchambs
 * 
 */
public class SearchGUI extends JFrame{

	ClientCommunicator comm;
	
	/**
	 * @param string
	 */
	public SearchGUI(String windowTitle) {
		super(windowTitle);
		comm = new ClientCommunicator();
		
	}

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				SearchGUI frame = new SearchGUI("SearchGUI");
				//frame.setSize(100,100);
				//frame.pack();
				frame.setVisible(true);
			}
		});
	}
}
