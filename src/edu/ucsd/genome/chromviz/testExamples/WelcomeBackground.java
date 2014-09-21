package edu.ucsd.genome.chromviz.testExamples;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * 
 * @author srujana
 * 
 */
public class WelcomeBackground extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//String inFile = "images/karyotype_Mus_musculus.png";
		String inFile = "images/karyotype_mouse new.png";
		Image bgImage = Toolkit.getDefaultToolkit().getImage(inFile);
		// Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		g.drawImage(bgImage, 0, 0, this);
		g.draw3DRect(8, 9, 21, 200, true);
		g.draw3DRect(42, 23, 21, 185, true);
		g.draw3DRect(77, 44, 21, 163, true);
		g.draw3DRect(111, 50, 21, 158, true);
		g.draw3DRect(147, 55, 21, 152, true);
		g.draw3DRect(183, 55, 21, 152, true);
		g.draw3DRect(220, 71, 20, 136, true);
		g.draw3DRect(255, 76, 20, 132, true);
		g.draw3DRect(291, 80, 20, 127, true);
		g.draw3DRect(327, 75, 20, 131, true);
		g.draw3DRect(363, 81, 20, 126, true);
		g.draw3DRect(400, 90, 21, 117, true);
		g.draw3DRect(440, 90, 21, 117, true);
		g.draw3DRect(480, 88, 21, 117, true);
		g.draw3DRect(520, 98, 21, 107, true);
		g.draw3DRect(560, 102, 21, 102, true);
		g.draw3DRect(600, 106, 20, 97, true);
		g.draw3DRect(640, 108, 20, 93, true);
		g.draw3DRect(680, 137, 20, 63, true);
		g.draw3DRect(720, 40, 20, 162, true);
		g.draw3DRect(760, 148, 20, 51, true);
		g.setColor(Color.blue);
		g.fillRect(8, 9, 21, 200);
		g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 24));
		// g.drawString("Signaling Network Visualization Chromosome",screenSize.width/2,screenSize.height/2);
		// g.setFont(new Font("Serif",Font.BOLD,14));
		// g.drawString("Authors: Ashok R. Dinsarapu, Srujana Dinasarapu",screenSize.width/2,screenSize.height/2+25);
		// g.drawString("System Design: Shankar Subramaniam",screenSize.width/2,screenSize.height/2+50);

	}

}
