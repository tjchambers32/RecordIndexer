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
import java.net.URL;

public class ImageComponent extends JComponent {

	private Image image;
	private boolean isFitToPanel;
	double scaleFactor;
	
	public ImageComponent(String imageURL) {

		isFitToPanel = true;
		scaleFactor = 1.0;
		
		drawImage(imageURL);
	}

	public void drawImage(String imageURL) {
		
		if (imageURL.equals("")) //no image requested yet
			return;
		
		try {
			image = ImageIO.read(new URL(imageURL));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "ERROR.", "Image Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	//Override Paint Component
	@Override
	public void paintComponent(Graphics g) {
		
		if (image != null) {
			Graphics2D graphics = (Graphics2D)g.create();
			if (isFitToPanel) {
				//Scales to Panel by default
				float ylimit = (float) this.getHeight() / (float) (image.getHeight(null));
				float xlimit = (float) this.getWidth() / (float) (image.getWidth(null));
				
				scaleFactor = Math.min(ylimit, xlimit);					
				graphics.scale(scaleFactor, scaleFactor);
			}
			else {
				//graphics.translate(w_centerX, w_centerY);
				int w = (int) ((getWidth() - scaleFactor*image.getWidth(null)) / 2);
				int h = (int) ((getHeight() - scaleFactor*image.getHeight(null)) / 2);
				graphics.translate(w, h);
				graphics.scale(scaleFactor, scaleFactor);
			}
			graphics.drawImage(image, 0, 0, null);

		}
	}
}
