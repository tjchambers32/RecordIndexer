package client.gui.panel;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import client.gui.ImageComponent;
import client.gui.batchstate.BatchState;
import client.gui.batchstate.BatchStateListener;
import client.gui.batchstate.Cell;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel implements BatchStateListener{

	ImageComponent downloadedImage;
	String imageURL;
	Rectangle2D highlightedCell;
	
	BatchState batchState;
	
	public ImagePanel(BatchState batchState) {
		super();
		
		this.batchState = batchState;
		
		createComponents();
	}

	private void createComponents() {
		
//		downloadedImage = new ImageComponent(imageURL); 
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	@Override
	public void stateChanged() {
		if (batchState.getHasDownloadedBatch()) {
			int width = batchState.getFields().get(batchState.getSelectedCell().getField()).getWidth();
			int height = batchState.getRecordHeight();
			int xCoord = batchState.getFields().get(batchState.getSelectedCell().getField()).getxCoord();
			int yCoord = batchState.getFirstYCoord() + batchState.getRecordHeight() * batchState.getSelectedCell().getRecord();
			highlightedCell = new Rectangle(xCoord, yCoord, width, height);
			
			//TODO finish imagepanel stateChanged stuff
			//check for zooming
			//check for inverted
			
		}
		
	}
}
