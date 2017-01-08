package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/** @author Kayler Renslow<br>
 * <br> */
public class ImagePreview extends JPanel{
	private BufferedImage image;

	public ImagePreview(int width, int height) {
		setSize(width, height);
		setPreferredSize(new Dimension(width,height));
	}

	public void paintImage(BufferedImage image) {
		this.image = image;
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image, 0, 0, this);
		
	}

}
