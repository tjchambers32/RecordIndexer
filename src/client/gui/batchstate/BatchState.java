/**
 * 
 */
package client.gui.batchstate;

import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.DownloadBatch_Result;
import shared.communication.SubmitBatch_Params;
import shared.communication.SubmitBatch_Result;
import shared.model.Field;
import shared.model.Record;
import shared.model.User;
import shared.model.Value;
import client.ClientException;
import client.communication.ClientCommunicator;

/**
 * @author tchambs
 *
 */
public class BatchState implements BatchStateListener {

	// utility variables
	transient private List<BatchStateListener> listeners;
	private ClientCommunicator comm;
	private String hostname;
	private int port;
	private User user;

	// persistent user state variables
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

	private String imageURL; // relative filepath to an image
	private int imageX;
	private int imageY;
	private int imageWidth;
	private int imageHeight;
	private int projectID;
	ArrayList<Field> fields;
	private boolean hasDownloadedBatch;
	private int firstYCoord;
	private int recordHeight;

	private boolean loggingIn;
	private boolean downloadingBatch;
	
	private int navImageX;
	private int navImageY;
	private int navImageWidth;
	private int navImageHeight;

	//dictionary is a set of words (knownData) for EACH field
	private Map<String, Set<String> > dictionary;
	
	public BatchState(String hostname, int port) {

		this.hostname = hostname;
		this.port = port;

		comm = new ClientCommunicator(hostname, port);
		listeners = new ArrayList<BatchStateListener>();

		numberOfRows = 0;
		numberOfColumns = 0;
		values = new String[numberOfRows][numberOfColumns];
		selectedCell = new Cell(1, 1);
		imageURL = "";
		user = null;

		fields = new ArrayList<Field>();

		
		values = new String[numberOfRows][numberOfColumns];

		for (int i = 0; i < numberOfRows; i++) {
			for (int j = 0; j < numberOfColumns; j++) {
				values[i][j] = "";
			}
		}

		// Initialize RecordNumber Values
		for (int i = 0; i < numberOfRows; i++) {
			values[i][0] = "" + (i + 1);
		}
		
		hasDownloadedBatch = false;

		imageX = 0;
		imageY = 0;
		zoomLevel = .6;
		highlightsVisible = true;
		imageInverted = false;

		horizontalDivider = 400;
		verticalDivider = 400;

		navImageX = 0;
		navImageY = 0;
		navImageWidth = 0;
		navImageHeight = 0;
		
		loggingIn = false;
		downloadingBatch = false;
		
		dictionary = new HashMap<String, Set<String> >();
	}

	public void addListener(BatchStateListener l) {
		listeners.add(l);
	}

	public void setValue(Cell cell, String value) {

		values[cell.getRecord()][cell.getField()] = value;

		for (BatchStateListener l : listeners) {
			l.stateChanged();
		}
	}

	public String getValue(Cell cell) {
		
		if (cell.record < numberOfRows && cell.field < numberOfColumns)
			return values[cell.record][cell.field];
		else
			return "";
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
		for (BatchStateListener l : listeners) {
			l.stateChanged();
		}
	}

	public void save() {

		XStream xstream = new XStream(new DomDriver());

		File savedBatchState = new File("savedBatches/" + user.getUsername() + ".xml");
		try {
			xstream.toXML(this, new FileWriter(savedBatchState));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean submitBatch() {
		
		ArrayList<Record> records = new ArrayList<Record>();
		ArrayList<Value> valueList = new ArrayList<Value>();
		
		String valueString = "";
		for (int i = 0; i < numberOfRows; i++) {
			records.add(new Record(user.getImageID(), i+1));
			for (int j = 1; j < numberOfColumns; j++) {
				valueString = values[i][j];
				valueList.add(new Value(i+1, valueString, j+1));
			}
		}
		
		SubmitBatch_Params params = new SubmitBatch_Params(this.getUser(), records, valueList);
		SubmitBatch_Result result = null;
		
		try {
			result = comm.submitBatch(params);
		} catch (ClientException e) {
			JOptionPane.showMessageDialog(null, "Could not submit batch.",
					"Submit Batch Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		
		return result.getResult();
	}

	public void setDownloadedBatch(DownloadBatch_Result result) {

		this.setImageURL("http://" + hostname + ":" + port + "/" + result.getImage().getFilepath());
		this.setNumberOfRows(result.getProject().getRecordsPerImage());
		this.setFields(result.getFields());

		Field recordNumber = new Field();
		recordNumber.setTitle("Record Number");
		fields.add(0, recordNumber);

		this.setNumberOfColumns(result.getFields().size());
		this.values = new String[numberOfRows][numberOfColumns];

		for (int i = 0; i < numberOfRows; i++) {
			for (int j = 0; j < numberOfColumns; j++) {
				values[i][j] = "";
			}
		}

		// Initialize RecordNumber Values
		for (int i = 0; i < numberOfRows; i++) {
			values[i][0] = "" + (i + 1);
		}
		
		//read in knownData for quality checker
		for (int i = 1; i < fields.size(); i++) {
			Set<String> tempSet = new TreeSet<String>();
			String tempData = fields.get(i).getKnownData();
			if (tempData == null || tempData == "")
				return;
			
			URL url = null;
			Scanner scanner;
			try {
				
				url = new URL("http://" + hostname + ":" + port + "/" + tempData);
								
				
				scanner = new Scanner(url.openStream());
				scanner.useDelimiter(",");
				
				while (scanner.hasNext()) {
					tempSet.add(scanner.next().toLowerCase());
				}
				
			} catch (MalformedURLException e) {
				continue;
			} catch (IOException e) {
				continue;
			}
			
			scanner.close();
			
			dictionary.put(fields.get(i).getTitle(), tempSet);
		}

		this.setHasDownloadedBatch(true);
		this.firstYCoord = result.getProject().getFirstYCoord();
		this.recordHeight = result.getProject().getRecordHeight();

		this.selectedCell = new Cell(1, 1);

		downloadingBatch = true;
		for (BatchStateListener l : listeners) {
			l.stateChanged();
		}
		downloadingBatch = false;

	}

	//returns true if word IS misspelled. false if spelled correctly
	public boolean qualityCheck(Cell cell) {
		String inputWord = this.getValue(cell);
		
		inputWord = inputWord.trim();
		
		//empty cells should be WHITE
		if (inputWord.equals("")) {
			return true;
		}
		String word = inputWord.toLowerCase();
		
		//if there isn't a dictionary for the field, we can't mark it as incorrect
		if (dictionary.get(fields.get(cell.getField()).getTitle()) == null) {
			return true;
		} else {
			//if the word is in the dictionary, its correct
			Set<String> tempSet = dictionary.get(fields.get(cell.getField()).getTitle());
			if (tempSet.contains(word.toLowerCase())) {
				return true; //true = correct
			}
		}
		return false; //incorrect - mark cell RED
	}

	public Set<String> makeSuggestions(Cell cell) {
		
		Set<String> allSuggested = findAllSuggestions(cell);
		
		Set<String> knownSuggestions = refineSuggestions(allSuggested, cell);
		
		return knownSuggestions;
		
	}
	
	private Set<String> refineSuggestions(Set<String> allSuggested, Cell cell) {
		
		TreeSet<String> refined = new TreeSet<String>();
		
		Set<String> tempSet = dictionary.get(fields.get(cell.getField()).getTitle());
		for (String s : allSuggested) {
			if (tempSet.contains(s.toLowerCase())) {
				refined.add(s);
			}
		}
		
		return refined;
	}

	private Set<String> findAllSuggestions(Cell cell) {
		
		String word = this.getValue(cell);
		
		word = word.toLowerCase();
		Set<String> suggestions;
		Set<String> finalSuggestions = new TreeSet<String>();
		suggestions = findNearbyWords(word);
		
		for (String suggestion : suggestions) {
			finalSuggestions.addAll(findNearbyWords(suggestion));
		}
	
		return finalSuggestions;
	}
	
	private Set<String> findNearbyWords(String word) {
		
		TreeSet<String> suggestions = new TreeSet<String>();
		String editedWord = "";
		StringBuilder sb = new StringBuilder();
		
		//deletion distance
		for (int i = 0; i < word.length(); i++) {
			sb = new StringBuilder(word);
			editedWord = sb.deleteCharAt(i).toString();
			suggestions.add(editedWord);
		}
		
		//transposition distance
		char L = ' ';
		char R = ' ';
		for (int i = 0; i < word.length() - 1; i++) {
			sb = new StringBuilder(word);
			L = sb.charAt(i);
			R = sb.charAt(i + 1);
			sb.setCharAt(i, R);
			sb.setCharAt(i+1, L);
			suggestions.add(sb.toString());
		}
		
		//Alteration Distance
		for (int i = 0; i < word.length(); i++) {
			for (int j = 0; j < 26; j++) {
				sb = new StringBuilder(word);
				char inputChar = (char) (j + 'a'); //inputChar matching original char doesn't matter because that would mean our original word is in the Trie, which we already checked for
				sb.setCharAt(i, inputChar);
				suggestions.add(sb.toString());
			}
		}
		
		//Insertion Distance
		for (int i = 0; i <= word.length(); i++) {
			for (int j = 0; j < 26; j++) {
				sb = new StringBuilder(word);
				char inputChar = (char) (j + 'a');
				sb.insert(i, inputChar);
				suggestions.add(sb.toString());
			}
		}	
		
		return suggestions;
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
		this.loggingIn = true;

		for (BatchStateListener l : listeners) {
			l.stateChanged();
		}
		this.loggingIn = false;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;

		try {
			Image testImage = ImageIO.read(new URL(imageURL));
			imageX = testImage.getWidth(null) / 2;
			imageY = testImage.getHeight(null) / 2;
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		this.numberOfRows = recordsPerImage;
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

		for (BatchStateListener l : listeners) {
			l.stateChanged();
		}
	}

	public boolean isImageInverted() {
		return imageInverted;
	}

	public void setImageInverted(boolean imageInverted) {
		this.imageInverted = imageInverted;

		for (BatchStateListener l : listeners) {
			l.stateChanged();
		}
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

		for (BatchStateListener l : listeners) {
			l.stateChanged();
		}
	}

	public String[][] getValues() {
		return values;
	}

	public void setValues(String[][] values) {
		this.values = values;

		for (BatchStateListener l : listeners) {
			l.stateChanged();
		}
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
	
	public boolean isLoggingIn() {
		return loggingIn;
	}

	public void setLoggingIn(boolean loggingIn) {
		this.loggingIn = loggingIn;
	}

	public boolean isDownloadingBatch() {
		return downloadingBatch;
	}

	public void setDownloadingBatch(boolean downloadingBatch) {
		this.downloadingBatch = downloadingBatch;
	}

	/**
	 * @param savedState
	 */
	public void update(BatchState savedState) {
		this.comm = savedState.comm;
		this.hostname = savedState.hostname;
		this.port = savedState.port;

		this.values = savedState.values;
		this.selectedCell = savedState.selectedCell;

		this.scrollPosition = savedState.scrollPosition;
		this.highlightsVisible = savedState.highlightsVisible;
		this.imageInverted = savedState.imageInverted;
		this.windowPositionX = savedState.windowPositionX;
		this.windowPositionY = savedState.windowPositionY;
		this.windowSizeX = savedState.windowSizeX;
		this.windowSizeY = savedState.windowSizeY;
		this.horizontalDivider = savedState.horizontalDivider;
		this.verticalDivider = savedState.verticalDivider;
		this.numberOfRows = savedState.numberOfRows;
		this.numberOfColumns = savedState.numberOfColumns;

		values = new String[numberOfRows][numberOfColumns];

		for (int i = 0; i < numberOfRows; i++) {
			for (int j = 0; j < numberOfColumns; j++) {
				values[i][j] = "";
			}
		}

		// Initialize RecordNumber Values
		for (int i = 0; i < numberOfRows; i++) {
			values[i][0] = "" + (i + 1);
		}
		
		this.imageURL = savedState.imageURL;
		this.imageX = savedState.imageX;
		this.imageY = savedState.imageY;
		this.imageWidth = savedState.imageWidth;
		this.imageHeight = savedState.imageHeight;
		this.projectID = savedState.projectID;
		this.fields = savedState.fields;
		this.hasDownloadedBatch = savedState.hasDownloadedBatch;
		this.firstYCoord = savedState.firstYCoord;
		this.recordHeight = savedState.recordHeight;

		this.loggingIn = savedState.loggingIn;

		this.user = savedState.user;
		this.zoomLevel = savedState.zoomLevel;

		this.dictionary = savedState.dictionary;
		
		this.navImageX = savedState.navImageX;
		this.navImageY = savedState.navImageY;
		this.navImageWidth = savedState.navImageWidth;
		this.navImageHeight = savedState.navImageHeight;
	}

	public int getNavImageX() {
		return navImageX;
	}

	public void setNavImageX(int navImageX) {
		this.navImageX = navImageX;
	}

	public int getNavImageY() {
		return navImageY;
	}

	public void setNavImageY(int navImageY) {
		this.navImageY = navImageY;
	}

	public int getNavImageWidth() {
		return navImageWidth;
	}

	public void setNavImageWidth(int navImageWidth) {
		this.navImageWidth = navImageWidth;
	}

	public int getNavImageHeight() {
		return navImageHeight;
	}

	public void setNavImageHeight(int navImageHeight) {
		this.navImageHeight = navImageHeight;
	}
}