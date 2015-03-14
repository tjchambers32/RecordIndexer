package client.search;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import client.communication.ClientCommunicator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JLabel;

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
		
		initialize();
		
	}

	private void initialize() {
		
		JLabel hostLabel = new JLabel("Host Name: ");
		JTextField hostField = new JTextField(20);
		hostField.setOpaque(true);
		hostField.setBackground(Color.white);
		hostField.setPreferredSize(new Dimension(100,20));
		
		JDialog dialog = new JDialog(this, "Login", true);
		
		dialog.add(hostLabel);	
		dialog.add(hostField); // hostField covers up hostLabel
		//whichever comes second just covers up the first component
		
		
		dialog.setSize(500,500);
		dialog.setLocation(500,200);
//		dialog.pack();
		dialog.setVisible(true);
		
//		JMenuBar jmenubar = new JMenuBar();
//		setJMenuBar(jmenubar);
//		
//		JMenu jmenu = new JMenu();
//		jmenu.setMnemonic('c');
//		jmenubar.add(jmenu);
//		
//		JMenuItem jmenuitem = new JMenuItem("Exit", KeyEvent.VK_X);
//		jmenu.add(jmenuitem);
		
				
		
		
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				SearchGUI frame = new SearchGUI("SearchGUI");
				
				//sizing for testing
				frame.setLocation(500,200);
				frame.setSize(500,500);
				
				//frame.pack();
				frame.setVisible(true);
			}
		});
	}
}
