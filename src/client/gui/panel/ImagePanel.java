package client.gui.panel;

import javax.swing.JPanel;

import client.gui.ImageComponent;
import client.gui.batchstate.BatchStateListener;
import client.gui.batchstate.Cell;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel implements BatchStateListener{

	ImageComponent downloadedImage;
	String imageURL;
	
	public ImagePanel() {
		super();
		
		createComponents();
	}

	private void createComponents() {
		
//		downloadedImage = new ImageComponent(imageURL); 
	}

	/* (non-Javadoc)
	 * @see client.gui.batchstate.BatchStateListener#valueChanged(client.gui.batchstate.Cell, java.lang.String)
	 */
	@Override
	public void valueChanged(Cell cell, String newValue) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.gui.batchstate.BatchStateListener#selectedCellChanged(client.gui.batchstate.Cell)
	 */
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

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
}
