package client.gui.panel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import client.gui.batchstate.BatchState;
import client.gui.batchstate.BatchStateListener;

@SuppressWarnings("serial")
public class ImageNavigatorPanel extends JPanel implements BatchStateListener{

	private BatchState batchState;
	private Image downloadedImage;
	Rectangle2D highlightedCell;
	
	private int w_originX;
	private int w_originY;
	private int w_centerX;
	private int w_centerY;
	private double scale;

	private boolean dragging;
	private int w_dragStartX;
	private int w_dragStartY;
	private int w_dragStartOriginX;
	private int w_dragStartOriginY;
	
	private boolean imageInverted;
	
	private static Image NULL_IMAGE = new BufferedImage(10, 10,
			BufferedImage.TYPE_INT_ARGB);
	
	public ImageNavigatorPanel(BatchState batchState) {
		
		downloadedImage = null;
		this.batchState = batchState;
		batchState.addListener(this);
		imageInverted = false;
		
		w_originX = batchState.getNavImageX();
		w_originY = batchState.getNavImageY();
		
		highlightedCell = new Rectangle();
		
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		
		initDrag();
		
	}
	
	private void initDrag() {
		dragging = false;
		w_dragStartX = 0;
		w_dragStartY = 0;
		w_dragStartOriginX = batchState.getNavImageX();
		w_dragStartOriginY = batchState.getNavImageY();
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
			
			double height = (double)this.getHeight() / (double) downloadedImage.getHeight(null);
			double width = (double) this.getWidth() / (double) downloadedImage.getWidth(null);
			scale = Math.min(height, width);
			
			w_centerX = downloadedImage.getWidth(null)/2;
			w_centerY = downloadedImage.getHeight(null)/2;
			
			graphics.translate(getWidth()/2, getHeight()/2);
			graphics.scale(scale, scale);
			graphics.translate(-w_centerX, -w_centerY);
			
			graphics.drawImage(downloadedImage, 0, 0, null);
			
			if (imageInverted)
				graphics.setColor(Color.LIGHT_GRAY);
			else
				graphics.setColor(Color.DARK_GRAY);
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
			graphics.setComposite(ac);
			graphics.fill(highlightedCell);
		}
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
			transform.translate(-w_centerX, -w_centerY);
			
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
				if (highlightedCell.contains(w_X, w_Y)) {
					hitShape = true;
				}
			}
			
			if (hitShape) {
				dragging = true;
				
				w_dragStartX = w_X;
				w_dragStartY = w_Y;		

				w_originX = batchState.getNavImageX();
				w_originY = batchState.getNavImageY();

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
				transform.translate(getWidth()/2, getHeight()/2);
				transform.scale(scale, scale);
				transform.translate(-w_centerX, -w_centerY);
				
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
				
				w_originX = w_dragStartOriginX + w_deltaX;
				w_originY = w_dragStartOriginY + w_deltaY;
				
				batchState.setImageX(w_originX);
				batchState.setImageY(w_originY);
				
				repaint();
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			initDrag();
		}
		
	};
	
	@Override
	public void stateChanged() {
		if (batchState.getHasDownloadedBatch()) {
			int width = batchState.getNavImageWidth();
			int height = batchState.getNavImageHeight();
			int xCoord = batchState.getNavImageX();
			int yCoord = batchState.getNavImageY();
			highlightedCell = new Rectangle(xCoord, yCoord, width, height);

			if (downloadedImage == null) {
				imageInverted = false;
				String url = batchState.getImageURL();
				loadImage(url);	
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
