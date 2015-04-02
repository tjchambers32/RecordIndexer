/**
 * 
 */
package client.gui.batchstate;

import java.util.ArrayList;
import java.util.List;

import shared.model.User;
import client.communication.ClientCommunicator;

/**
 * @author tchambs
 *
 */

public class BatchState implements BatchStateListener{

	//utility variables
	private List<BatchStateListener> listeners;
	private ClientCommunicator comm;
	private String hostname;
	private int port;
	private User user;

	//persistent user state variables
	private String[][] values;
	private Cell selectedCell;
	private double zoomLevel;
	private int scrollPosition;
	private boolean highlightsVisible;
	private boolean imageInverted;
	private int windowPositionX;
	private int windowPositionY;
	private int windowSizeX;
	private int windowSizeY;
	private int horizontalDivider;
	private int verticalDivider;
	
	private String imageURL;
	
	
	public BatchState(String hostname, int port) {
		
		this.hostname = hostname;
		this.port = port;
		
		comm = new ClientCommunicator(hostname, port);
		listeners = new ArrayList<BatchStateListener>();
	}
	
	public void addListener(BatchStateListener l) {
		listeners.add(l);
	}
	
	public void setValue(Cell cell, String value) {
		
		values[cell.record][cell.field] = value;
		
		for (BatchStateListener l : listeners) {
			l.valueChanged(cell, value);
		}
	}
	
	public String getValue(Cell cell) {
		return values[cell.record][cell.field];
	}
	
	public void setSelectedCell(Cell selCell) {
		
		selectedCell = selCell;
		
		for (BatchStateListener l : listeners) {
			l.selectedCellChanged(selCell);
		}
	}
	
	public Cell getSelectedCell() {
		return selectedCell;
	}

	@Override
	public void valueChanged(Cell cell, String newValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectedCellChanged(Cell newSelectedCell) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.gui.batchstate.BatchStateListener#invertImageChanged()
	 */
	@Override
	public void invertImageChanged() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.gui.batchstate.BatchStateListener#highlightsVisibleChanged()
	 */
	@Override
	public void highlightsVisibleChanged() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.gui.batchstate.BatchStateListener#zoomChanged()
	 */
	@Override
	public void zoomChanged() {
		// TODO Auto-generated method stub
		
	}

	public ClientCommunicator getComm() {
		return comm;
	}

	public void setComm(ClientCommunicator comm) {
		this.comm = comm;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}