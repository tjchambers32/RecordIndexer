package main.client.gui.panel;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.IOException;
import java.net.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.client.gui.batchstate.*;
import main.shared.model.Field;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel implements BatchStateListener {

	private Image downloadedImage;
	String imageURL;
	Rectangle2D highlightedCell;
	private boolean imageInverted;
	
	private static Image NULL_IMAGE = new BufferedImage(10, 10,
			BufferedImage.TYPE_INT_ARGB);

	private int w_originX;
	private int w_originY;
	private double scale;

	private boolean dragging;
	private int w_dragStartX;
	private int w_dragStartY;
	private int w_dragStartOriginX;
	private int w_dragStartOriginY;
	
	BatchState batchState;

	private boolean isCellSelected;
	private int selectedRow;
	private int selectedColumn;
	
	public ImagePanel(BatchState batchState, String URL) {

		this.batchState = batchState;
		batchState.addListener(this);

		w_originX = batchState.getImageX();
		w_originY = batchState.getImageY();
		scale = .5;
		imageInverted = false;
		
		isCellSelected = false;
		selectedRow = -1;
		selectedColumn = -1;

		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addMouseWheelListener(mouseAdapter);
		
		initDrag();

		
	}

	private void initDrag() {
		dragging = false;
		w_dragStartX = 0;
		w_dragStartY = 0;
		w_dragStartOriginX = 0;
		w_dragStartOriginY = 0;
	}

	private void loadImage(String imageFile) {
		
		try {
			
			downloadedImage = ImageIO.read(new URL(imageFile));
			
			batchState.setNavImageHeight(getWorldHeight());
			batchState.setNavImageWidth(getWorldWidth());
			batchState.setNavImageY(getWorldY());
			batchState.setNavImageX(getWorldX());
			
		} catch (IOException | NullPointerException e) {
			downloadedImage = NULL_IMAGE;
			System.out.println("ERROR DOWNLOADING IMAGE");
		}
		
	}

	//Override Paint Component
	@Override
	public void paintComponent(Graphics g) {
		
		if (downloadedImage != null) {
			Graphics2D graphics = (Graphics2D)g.create();
			graphics.setBackground(Color.DARK_GRAY);
			
			graphics.translate(getWidth()/2, getHeight()/2);
			
			graphics.scale(scale, scale);
			
			graphics.translate(-w_originX, -w_originY);
			
			graphics.drawImage(downloadedImage, 0, 0, null);
			
			if (batchState.isHighlightsVisible()) {
				graphics.setColor(Color.blue);
				AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
				graphics.setComposite(ac);
				graphics.fill(highlightedCell);
			}
		}
	}
	
	public void setScale(double newScale) {
		scale = newScale;
		this.repaint();
	}

	private int getWorldWidth() {
		AffineTransform transform = new AffineTransform();
		
		transform.scale(scale, scale);
		
		int deviceWidth = getWidth();
		int deviceHeight = getHeight();
		
		Point2D d_Pt = new Point2D.Double(deviceWidth, deviceHeight);
		Point2D w_Pt = new Point2D.Double();
		try {
			transform.inverseTransform(d_Pt, w_Pt);
		} catch (NoninvertibleTransformException e) {
			return -1;
		}
		int worldWidth = (int)w_Pt.getX();
		
		return worldWidth;
	}
	
	private int getWorldHeight() {
		
		AffineTransform transform = new AffineTransform();

		transform.scale(scale, scale);
		
		int deviceWidth = getWidth();
		int deviceHeight = getHeight();
		
		Point2D d_Pt = new Point2D.Double(deviceWidth, deviceHeight);
		Point2D w_Pt = new Point2D.Double();
		try
		{
			transform.inverseTransform(d_Pt, w_Pt);
		}
		catch (NoninvertibleTransformException ex) {
			return -1;
		}
		int worldHeight = (int)w_Pt.getY();
		
		return worldHeight;
		
	}
	
	private int getWorldX() {
		AffineTransform transform = new AffineTransform();
		transform.translate(getWidth()/2, getHeight()/2);
		transform.scale(scale, scale);
		transform.translate(-w_originX, -w_originY);
		
		int deviceX = getX();
		int deviceY = getY();
		
		Point2D d_Pt = new Point2D.Double(deviceX, deviceY);
		Point2D w_Pt = new Point2D.Double();
		try
		{
			transform.inverseTransform(d_Pt, w_Pt);
		}
		catch (NoninvertibleTransformException ex) {
			return -1;
		}
		int worldX = (int)w_Pt.getX();
		
		return worldX;
	
	}

	private int getWorldY() {
		AffineTransform transform = new AffineTransform();
		transform.translate(getWidth()/2, getHeight()/2);
		transform.scale(scale, scale);
		transform.translate(-w_originX, -w_originY);
		
		int deviceX = getX();
		int deviceY = getY();
		
		Point2D d_Pt = new Point2D.Double(deviceX, deviceY);
		Point2D w_Pt = new Point2D.Double();
		try
		{
			transform.inverseTransform(d_Pt, w_Pt);
		}
		catch (NoninvertibleTransformException ex) {
			return -1;
		}
		int worldY = (int)w_Pt.getY();
		
		return worldY;
		
	}

	private MouseAdapter mouseAdapter = new MouseAdapter() {
	
		@Override
		public void mousePressed(MouseEvent e) {
			int d_X = e.getX();
			int d_Y = e.getY();
			
			System.out.print("d_X: " + d_X + "  d_Y: " + d_Y + "      ");
			
			AffineTransform transform = new AffineTransform();
			transform.translate(getWidth()/2, getHeight()/2);
			transform.scale(scale, scale);
			transform.translate(-w_originX, -w_originY);
			
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex) {
				return;
			}
			int w_X = (int)w_Pt.getX();
			int w_Y = (int)w_Pt.getY();
			
			System.out.print("w_X: " + w_X + "  w_Y: " + w_Y + "\n");
			
			boolean hitShape = false;
			
			if (downloadedImage != null) {
				Rectangle2D rect = new Rectangle2D.Double(0, 0, downloadedImage.getWidth(null), downloadedImage.getHeight(null));
				if (rect.contains(w_X, w_Y)) {
					hitShape = true;
					
					//figure out which cell was clicked on.
					
					int y = batchState.getFirstYCoord() + batchState.getRecordHeight()*batchState.getNumberOfRows();
					//check to make sure click is between max row height and firstYCoord
					if (w_Y >= batchState.getFirstYCoord() && w_Y < y) {
						selectedRow = (w_Y - batchState.getFirstYCoord())/batchState.getRecordHeight();
						if (selectedRow >= batchState.getNumberOfRows()) {
							selectedRow = -1; 
						}
					}
					for (int i = 1; i < batchState.getFields().size(); i++) {
						Field clickedColumn = batchState.getFields().get(i);
						if (w_X >= clickedColumn.getxCoord() && w_X < (clickedColumn.getxCoord() + clickedColumn.getWidth())) {
							selectedColumn = i;
						}					
					}
					if (selectedRow != -1 && selectedColumn != -1) {
						isCellSelected = true;
					}
				}
			}
						
			if (hitShape) {
				dragging = true;		
				w_dragStartX = w_X;
				w_dragStartY = w_Y;		
				w_dragStartOriginX = w_originX;
				w_dragStartOriginY = w_originY;
				
				batchState.setNavImageWidth(getWorldWidth());
				batchState.setNavImageHeight(getWorldHeight());
				batchState.setNavImageX(getWorldX());
				batchState.setNavImageY(getWorldY());
				
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {		
			if (dragging) {
				int d_X = e.getX();
				int d_Y = e.getY();
				
				isCellSelected = false;
				selectedColumn = -1;
				selectedRow = -1;
				
				AffineTransform transform = new AffineTransform();
				transform.translate(getWidth()/2, getHeight()/2);
				transform.scale(scale, scale);
				transform.translate(-w_dragStartOriginX, -w_dragStartOriginY);
				
				Point2D d_Pt = new Point2D.Double(d_X, d_Y);
				Point2D w_Pt = new Point2D.Double();
				try
				{
					transform.inverseTransform(d_Pt, w_Pt);
				}
				catch (NoninvertibleTransformException ex) {
					return;
				}
				int w_X = (int)w_Pt.getX();
				int w_Y = (int)w_Pt.getY();
				
				int w_deltaX = w_X - w_dragStartX;
				int w_deltaY = w_Y - w_dragStartY;
				
				w_originX = w_dragStartOriginX - w_deltaX;
				w_originY = w_dragStartOriginY - w_deltaY;
				
				batchState.setImageX(w_originX);
				batchState.setImageY(w_originY);
				
				batchState.setNavImageHeight(getWorldHeight());
				batchState.setNavImageWidth(getWorldWidth());
				batchState.setNavImageY(getWorldY());
				batchState.setNavImageX(getWorldX());
				
				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			initDrag();
			//find the clicked cell
			if (isCellSelected) {
				Cell clickedCell = new Cell(selectedRow, selectedColumn);
				batchState.setSelectedCell(clickedCell);
				selectedColumn = -1;
				selectedRow = -1;
				isCellSelected = false;
			}
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			
			if (e.getWheelRotation() > 0) {
				if (batchState.getZoomLevel() > 0.3)
					batchState.setZoomLevel(batchState.getZoomLevel() - 0.05);
			} else {
				if (batchState.getZoomLevel() < 3.0)
					batchState.setZoomLevel(batchState.getZoomLevel() + 0.05);
			}
		}
	};

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	@Override
	public void stateChanged() {
		
		if (batchState.getHasDownloadedBatch()) {
			int width = batchState.getFields()
					.get(batchState.getSelectedCell().getField()).getWidth();
			int height = batchState.getRecordHeight();
			int xCoord = batchState.getFields()
					.get(batchState.getSelectedCell().getField()).getxCoord();
			int yCoord = batchState.getFirstYCoord()
					+ batchState.getRecordHeight()
					* batchState.getSelectedCell().getRecord();
			highlightedCell = new Rectangle(xCoord, yCoord, width, height);

			if (downloadedImage == null) {
				w_originX = batchState.getImageX();
				w_originY = batchState.getImageY();
				imageInverted = false;
				String url = batchState.getImageURL();
				loadImage(url);
			} else {
				if (scale != batchState.getZoomLevel()) {
					scale = batchState.getZoomLevel();
					
					batchState.setNavImageHeight(getWorldHeight());
					batchState.setNavImageWidth(getWorldWidth());
					batchState.setNavImageY(getWorldY());
					batchState.setNavImageX(getWorldX());
				} else {
					int w_deltaX = getWorldX() - batchState.getNavImageX();
					int w_deltaY = getWorldY() - batchState.getNavImageY();
					
						w_originX = w_originX - w_deltaX;
						w_originY = w_originY - w_deltaY;
						
						batchState.setImageX(w_originX);
						batchState.setImageY(w_originY);
				}
			}
			
			if (batchState.isImageInverted() != imageInverted) {
				imageInverted = !imageInverted; //toggle 
				RescaleOp op = new RescaleOp(-1.0f, 255f, null);
				BufferedImage negative = op.filter((BufferedImage) downloadedImage, null);
				downloadedImage = negative;
			}
			
		} else {
			downloadedImage = null;
			highlightedCell = null;
		}
		repaint();
	}
}
