package edu.ucsd.genome.chromviz.sigUtility;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SubImage extends JPanel {
	Image bgImage;
	JPanel panel;
	JScrollPane spane;
	BufferedImage bi;
	BufferedImage subImage;
	int x,y,w,h;
	//Rectangle rect;
	private static final long serialVersionUID = 1L;

	public SubImage(int x,int y,int w,int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
		public void paint(Graphics g) {
		File inFile = new File("images/karyotype_Mus_musculus.png");
		try {
			bgImage = ImageIO.read(inFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		bi = (BufferedImage) bgImage;
		//subImage = bi.getSubimage(5,5,15,145);
		subImage = bi.getSubimage(x,y,w,h);
	
		g.drawImage(subImage, 0, 0, null);
	}
	
	/**
	 * @param args
	 * @throws IOException
	 */
	/*public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame("Display");
		JPanel panel = new SubImage();
		frame.getContentPane().add(panel);
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 frame.setSize(new Dimension(screenSize.width, screenSize.height -
		25));
		frame.setTitle("SubImage Display!");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}*/
}
