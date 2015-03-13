package client.search;

import java.awt.EventQueue;

import client.communication.ClientCommunicator;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
		
		initializeComponents();
		
	}

	private void initializeComponents() {
		//TODO: just testing delete when done
		
		
		this.add(new JMenuBar());
		this.add(new JMenu());
		this.add(new JMenuItem());
		
		
		// end of testing
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				SearchGUI frame = new SearchGUI("SearchGUI");
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
}
