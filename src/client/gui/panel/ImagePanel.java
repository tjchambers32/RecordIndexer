package client.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
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

	private BufferedImage downloadedImage;
	String imageURL;
	Rectangle2D highlightedCell;
	
	private static Image NULL_IMAGE = new BufferedImage(10, 10,
			BufferedImage.TYPE_INT_ARGB);

	private int w_originX;
	private int w_originY;
	private double scale;

	private boolean dragging;
	private int d_dragStartX;
	private int d_dragStartY;
	private int w_dragStartOriginX;
	private int w_dragStartOriginY;
	
	BatchState batchState;

	public ImagePanel(BatchState batchState, String URL) {
		super();

		this.batchState = batchState;
		batchState.addListener(this);

		w_originX = 0;
		w_originY = 0;
		scale = 1.0;

		initDrag();

		this.setBackground(new Color(178, 223, 210));
		this.setPreferredSize(new Dimension(700, 700));
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(1000, 1000));

		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		
		

	}

	private void initDrag() {
		dragging = false;
		d_dragStartX = 0;
		d_dragStartY = 0;
		w_dragStartOriginX = 0;
		w_dragStartOriginY = 0;
	}

	private Image loadImage(String imageFile) {
		try {
			return ImageIO.read(new File(imageFile));
		} catch (IOException | NullPointerException e) {
			return NULL_IMAGE;
		}
	}

	public void setScale(double newScale) {
		scale = newScale;
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		drawBackground(g2);
		
	}

	private void drawBackground(Graphics2D g2) {
		g2.setColor(getBackground());
		g2.fillRect(0, 0, getWidth(), getHeight());
	}


	private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mousePressed(MouseEvent e) {
			int d_X = e.getX();
			int d_Y = e.getY();
			int w_X = deviceToWorldX(d_X);
			int w_Y = deviceToWorldY(d_Y);

			boolean hitShape = false;

			Graphics2D g2 = (Graphics2D) getGraphics();
			if (downloadedImage != null) {
				Rectangle2D tempRect = new Rectangle2D.Double(0, 0, downloadedImage.getWidth(null), downloadedImage.getHeight(null));
				if (tempRect.contains(w_X, w_Y)) {
					hitShape = true;
					
					//TODO figure out how to highlight cells when user clicks image
				}
			}

			if (hitShape) {
				dragging = true;
				d_dragStartX = e.getX();
				d_dragStartY = e.getY();
				w_dragStartOriginX = w_originX;
				w_dragStartOriginY = w_originY;
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (dragging) {
				int d_deltaX = (e.getX() - d_dragStartX);
				int d_deltaY = (e.getY() - d_dragStartY);

				int w_deltaX = (int) (d_deltaX / scale);
				int w_deltaY = (int) (d_deltaY / scale);

				w_originX = w_dragStartOriginX - w_deltaX;
				w_originY = w_dragStartOriginY - w_deltaY;

				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			initDrag();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			return;
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

	interface DrawingShape {
		boolean contains(Graphics2D g2, double x, double y);

		void draw(Graphics2D g2);

		Rectangle2D getBounds(Graphics2D g2);
	}

	class DrawingRect implements DrawingShape {

		private Rectangle2D rect;
		private Color color;

		public DrawingRect(Rectangle2D rect, Color color) {
			this.rect = rect;
			this.color = color;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			Rectangle2D transformedRect = new Rectangle2D.Double(
					worldToDeviceX((int) rect.getX()),
					worldToDeviceY((int) rect.getY()),
					(int) (rect.getWidth() * scale),
					(int) (rect.getHeight() * scale));
			g2.setColor(color);
			g2.fill(transformedRect);
		}

		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}
	}

	class DrawingImage implements DrawingShape {

		private Image image;
		private Rectangle2D rect;

		public DrawingImage(Image image, Rectangle2D rect) {
			this.image = image;
			this.rect = rect;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			Rectangle2D transformedRect = new Rectangle2D.Double(
					worldToDeviceX((int) rect.getX()),
					worldToDeviceY((int) rect.getY()),
					(int) (rect.getWidth() * scale),
					(int) (rect.getHeight() * scale));

			g2.drawImage(image, (int) transformedRect.getMinX(),
					(int) transformedRect.getMinY(),
					(int) transformedRect.getMaxX(),
					(int) transformedRect.getMaxY(), 0, 0,
					image.getWidth(null), image.getHeight(null), null);
		}

		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}
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
				loadImage(batchState.getImageURL());
			}
			
			repaint();
			// TODO finish imagepanel stateChanged stuff
			// check for zooming
			// check for inverted

		}

	}
}
