package client.gui.panel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.gui.ImageComponent;
import client.gui.batchstate.BatchState;
import client.gui.batchstate.BatchStateListener;

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

	public ImagePanel(BatchState batchState, String URL) {
		super();

		
		
		this.batchState = batchState;
		batchState.addListener(this);

		w_originX = batchState.getImageX();
		w_originY = batchState.getImageY();
		scale = .5;
		imageInverted = false;
		
		initDrag();

		this.setPreferredSize(new Dimension(700, 700));
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(1000, 1000));

		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addMouseWheelListener(mouseAdapter);
		
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

	private void drawBackground(Graphics2D g2) {
		g2.setColor(getBackground());
		g2.fillRect(0, 0, getWidth(), getHeight());
	}


	private MouseAdapter mouseAdapter = new MouseAdapter() {

		//try this
		//https://students.cs.byu.edu/~cs240ta/winter2015/rodham_files/23-image-navigator/code/DoubleSpongeBob_3_Scaling_Translation/src/DrawingComponent.java
		
		@Override
		public void mousePressed(MouseEvent e) {
			int d_X = e.getX();
			int d_Y = e.getY();
			
			System.out.print("d_X: " + d_X + "  d_Y: " + d_Y + "      ");
			
			AffineTransform transform = new AffineTransform();
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
				}
			}
			
			
			
			if (hitShape) {
				dragging = true;		
				w_dragStartX = w_X;
				w_dragStartY = w_Y;		
				w_dragStartOriginX = w_originX;
				w_dragStartOriginY = w_originY;
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {		
			if (dragging) {
				int d_X = e.getX();
				int d_Y = e.getY();
				
				AffineTransform transform = new AffineTransform();
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
				
				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			initDrag();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			
			if (e.getWheelRotation() < 0) {
				if (batchState.getZoomLevel() > 0.3)
					batchState.setZoomLevel(batchState.getZoomLevel() - 0.05);
			} else {
				if (batchState.getZoomLevel() < 3.0)
					batchState.setZoomLevel(batchState.getZoomLevel() + 0.05);
			}
		}
	};

	private int worldToDeviceX(int w_X) {
		double d_X = w_X;
		d_X -= w_originX;
		d_X *= scale;
		return (int) d_X;
	}

	private int worldToDeviceY(int w_Y) {
		double d_Y = w_Y;
		d_Y -= w_originY;
		d_Y *= scale;
		return (int) d_Y;
	}

	private int deviceToWorldX(int d_X) {
		double w_X = d_X;
		w_X *= 1.0 / scale;
		w_X += w_originX;
		return (int) w_X;
	}

	private int deviceToWorldY(int d_Y) {
		double w_Y = d_Y;
		w_Y *= 1.0 / scale;
		w_Y += w_originY;
		return (int) w_Y;
	}

	// //////////////
	// Drawing Shape
	// //////////////

//	interface DrawingShape {
//		boolean contains(Graphics2D g2, double x, double y);
//
//		void draw(Graphics2D g2);
//
//		Rectangle2D getBounds(Graphics2D g2);
//	}

//	class DrawingRect implements DrawingShape {
//
//		private Rectangle2D rect;
//		private Color color;
//
//		public DrawingRect(Rectangle2D rect, Color color) {
//			this.rect = rect;
//			this.color = color;
//		}
//
//		@Override
//		public boolean contains(Graphics2D g2, double x, double y) {
//			return rect.contains(x, y);
//		}
//
////		@Override
////		public void draw(Graphics2D g2) {
////			Rectangle2D transformedRect = new Rectangle2D.Double(
////					worldToDeviceX((int) rect.getX()),
////					worldToDeviceY((int) rect.getY()),
////					(int) (rect.getWidth() * scale),
////					(int) (rect.getHeight() * scale));
////			g2.setColor(color);
////			g2.fill(transformedRect);
////		}
//
//		@Override
//		public Rectangle2D getBounds(Graphics2D g2) {
//			return rect.getBounds2D();
//		}
//	}
//
//	class DrawingImage implements DrawingShape {
//
//		private Image image;
//		private Rectangle2D rect;
//
//		public DrawingImage(Image image, Rectangle2D rect) {
//			this.image = image;
//			this.rect = rect;
//		}
//
//		@Override
//		public boolean contains(Graphics2D g2, double x, double y) {
//			return rect.contains(x, y);
//		}
//
//		@Override
//		public void draw(Graphics2D g2) {
//			Rectangle2D transformedRect = new Rectangle2D.Double(
//					worldToDeviceX((int) rect.getX()),
//					worldToDeviceY((int) rect.getY()),
//					(int) (rect.getWidth() * scale),
//					(int) (rect.getHeight() * scale));
//
//			g2.drawImage(image, (int) transformedRect.getMinX(),
//					(int) transformedRect.getMinY(),
//					(int) transformedRect.getMaxX(),
//					(int) transformedRect.getMaxY(), 0, 0,
//					image.getWidth(null), image.getHeight(null), null);
//		}
//
//		@Override
//		public Rectangle2D getBounds(Graphics2D g2) {
//			return rect.getBounds2D();
//		}
//	}

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

			// TODO finish imagepanel stateChanged stuff
			// check for inverted

			if (downloadedImage == null) {
				w_originX = batchState.getImageX();
				w_originY = batchState.getImageY();
				imageInverted = false;
				String url = batchState.getImageURL();
				loadImage(url);
			} else {
				if (scale != batchState.getZoomLevel()) {
					scale = batchState.getZoomLevel();
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
