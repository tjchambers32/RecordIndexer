/**
 * 
 */
package client.gui.batchstate;

import java.util.ArrayList;
import java.util.List;

import shared.communication.DownloadBatch_Result;
import shared.model.Field;
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
	private int numberOfRows;
	private int numberOfColumns;
	
	private String imageURL; //relative filepath to an image
	private int imageX;
	private int imageY;
	private int imageWidth;
	private int imageHeight;
	private int projectID;
	ArrayList<Field> fields;
	private boolean hasDownloadedBatch;
	private int firstYCoord;
	private int recordHeight;
	
	
	
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
			l.stateChanged();
		}
	}
	
	public String getValue(Cell cell) {
		return values[cell.record][cell.field];
	}
	
	public void setSelectedCell(Cell selCell) {
		
		selectedCell = selCell;
		
		for (BatchStateListener l : listeners) {
			l.stateChanged();
		}
	}
	
	public Cell getSelectedCell() {
		return selectedCell;
	}

	@Override
	public void stateChanged() {
		// TODO Auto-generated method stub
		
	}

	public void save() {
		//TODO IMPLEMENT save in batchState
	}
	
	public void submitBatch() {
		//TODO IMPLEMENT submitBatch in batchState
	}
	
	public void setDownloadedBatch(DownloadBatch_Result result) {
		this.setImageURL("http://" + hostname + ":" + port + "/" + result.getImage().getFilepath());
		this.setNumberOfRows(result.getProject().getRecordsPerImage());
		this.setFields(result.getFields());
		this.setHasDownloadedBatch(true);
		this.firstYCoord = result.getProject().getFirstYCoord();
		this.recordHeight = result.getProject().getRecordHeight();
		
		this.selectedCell = new Cell(1,1);
		
		for (BatchStateListener l : listeners) {
			l.stateChanged();
		}
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

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	
	public ArrayList<Field> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}

	public void setNumberOfRows(int recordsPerImage) {
		this.numberOfColumns = recordsPerImage;
	}

	public boolean getHasDownloadedBatch() {
		return hasDownloadedBatch;
	}

	public void setHasDownloadedBatch(boolean hasDownloadedBatch) {
		this.hasDownloadedBatch = hasDownloadedBatch;
	}
	
	public List<BatchStateListener> getListeners() {
		return listeners;
	}

	public void setListeners(List<BatchStateListener> listeners) {
		this.listeners = listeners;
	}

	public boolean isHighlightsVisible() {
		return highlightsVisible;
	}

	public void setHighlightsVisible(boolean highlightsVisible) {
		this.highlightsVisible = highlightsVisible;
	}

	public boolean isImageInverted() {
		return imageInverted;
	}

	public void setImageInverted(boolean imageInverted) {
		this.imageInverted = imageInverted;
	}

	public int getHorizontalDivider() {
		return horizontalDivider;
	}

	public void setHorizontalDivider(int horizontalDivider) {
		this.horizontalDivider = horizontalDivider;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}

	public int getFirstYCoord() {
		return firstYCoord;
	}

	public void setFirstYCoord(int firstYCoord) {
		this.firstYCoord = firstYCoord;
	}

	public int getRecordHeight() {
		return recordHeight;
	}

	public void setRecordHeight(int recordHeight) {
		this.recordHeight = recordHeight;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public double getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(double zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	public String[][] getValues() {
		return values;
	}

	public void setValues(String[][] values) {
		this.values = values;
	}

	public int getScrollPosition() {
		return scrollPosition;
	}

	public void setScrollPosition(int scrollPosition) {
		this.scrollPosition = scrollPosition;
	}

	public int getWindowPositionX() {
		return windowPositionX;
	}

	public void setWindowPositionX(int windowPositionX) {
		this.windowPositionX = windowPositionX;
	}

	public int getWindowPositionY() {
		return windowPositionY;
	}

	public void setWindowPositionY(int windowPositionY) {
		this.windowPositionY = windowPositionY;
	}

	public int getWindowSizeX() {
		return windowSizeX;
	}

	public void setWindowSizeX(int windowSizeX) {
		this.windowSizeX = windowSizeX;
	}

	public int getWindowSizeY() {
		return windowSizeY;
	}

	public void setWindowSizeY(int windowSizeY) {
		this.windowSizeY = windowSizeY;
	}

	public int getVerticalDivider() {
		return verticalDivider;
	}

	public void setVerticalDivider(int verticalDivider) {
		this.verticalDivider = verticalDivider;
	}

	public int getImageX() {
		return imageX;
	}

	public void setImageX(int imageX) {
		this.imageX = imageX;
	}

	public int getImageY() {
		return imageY;
	}

	public void setImageY(int imageY) {
		this.imageY = imageY;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

}