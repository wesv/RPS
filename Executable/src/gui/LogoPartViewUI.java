package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import config.CfgStyles;

/** @author Kayler Renslow<br />
 * <br />
 * 
 *         JPanel that holds the game view. */
@SuppressWarnings("serial")
public class LogoPartViewUI extends JPanel{
	// private ImageIcon img = new ImageIcon(getClass().getResource("/image.jpg"));
	private ImageIcon bg = new ImageIcon(getClass().getResource("/rps.png"));

	public LogoPartViewUI() {
		setBackground(CfgStyles.COLOR_ALMOST_BLACK);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2d.drawImage(bg.getImage(), 0, 0, bg.getIconWidth(), bg.getIconHeight(), this);
		g2d.dispose();
		g.dispose();

		// g.drawImage(img.getImage(), 0, 0, img.getIconWidth(), img.getIconHeight(), this);
	}
}
