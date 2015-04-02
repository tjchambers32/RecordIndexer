package client.gui;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.font.*;
import java.awt.event.*;

import javax.imageio.*;
import javax.swing.*;

import java.util.*;
import java.io.*;

@SuppressWarnings("serial")
public class ImageComponent extends JComponent {

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

	private ArrayList<DrawingShape> shapes;

	public ImageComponent(String URL) {
		w_originX = 0;
		w_originY = 0;
		scale = 1.0;

		initDrag();

		shapes = new ArrayList<DrawingShape>();

		this.setBackground(new Color(178, 223, 210));
		this.setPreferredSize(new Dimension(700, 700));
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(1000, 1000));

		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);

		Image downloadedImage = loadImage(URL);
		shapes.add(new DrawingImage(downloadedImage, new Rectangle2D.Double(50, 250,
				downloadedImage.getWidth(null) * 2, downloadedImage.getHeight(null) * 2)));

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
		} catch (IOException e) {
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
		drawShapes(g2);
	}

	private void drawBackground(Graphics2D g2) {
		g2.setColor(getBackground());
		g2.fillRect(0, 0, getWidth(), getHeight());
	}

	private void drawShapes(Graphics2D g2) {
		for (DrawingShape shape : shapes) {
			shape.draw(g2);
		}
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
			for (DrawingShape shape : shapes) {
				if (shape.contains(g2, w_X, w_Y)) {
					hitShape = true;
					break;
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
}
