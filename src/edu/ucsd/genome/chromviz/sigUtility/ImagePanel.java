package edu.ucsd.genome.chromviz.sigUtility;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private BufferedImage image;

	public ImagePanel() {
		/*try {
			image = ImageIO.read(new File("images/chrom/humanChrFinal.png"));
		} catch (IOException ex) {
		}*/
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//g.drawImage(image, 5, 0, 1158,594, null);
		//g.drawImage(image, 5, 0, 1158,294, null);
		//Font font = new Font("Verdana", Font.BOLD, 9);
		//g.setFont(font);
		//g.drawString("Human Chromosomes", 1050, 290);
		Graphics2D dg = (Graphics2D)g;
		Font font2 = new Font("Verdana", Font.PLAIN, 150);
		dg.setFont(font2);
		dg.setColor(Color.LIGHT_GRAY);
		//dg.setColor(Color.YELLOW);
		dg.fill3DRect(5, 5, 1151, 289, false);
		RoundRectangle2D rrect1;
		for(int i = 0; i < 3; i++){
			rrect1 = new RoundRectangle2D.Float(160+(i*10), 50+(i*10), 810-(2*(i*10)), 180-(2*(i*10)), 30, 20);
			dg.draw(rrect1);
		}
		/*RoundRectangle2D rrect1 = new RoundRectangle2D.Float(100, 50, 930, 180, 30, 20);
		dg.draw(rrect1);
		RoundRectangle2D rrect2 = new RoundRectangle2D.Float(110, 60, 910, 160, 30, 20);
		dg.draw(rrect2);*/
		dg.drawString("ChromViz", 200, 195);
		dg.dispose();
		g.dispose();
		// see javadoc for more info on the parameters
	}
}
