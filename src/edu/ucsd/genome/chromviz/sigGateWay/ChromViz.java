package edu.ucsd.genome.chromviz.sigGateWay;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class ChromViz {

	/**
	 * the size of the frame, width and height are 75% and 60% respectively, to
	 * the user's screen size.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		VisualInterface mainFrame = new VisualInterface();
		// Set the frame size according to the user's screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth() * (0.75);
		double height = screenSize.getHeight() * (0.60);
		int x = (int) Math.round(width);
		int y = (int) Math.round(height);
		System.out.println(screenSize.getWidth() + "(" + x + ")" + "\t"	+ screenSize.getHeight() + "(" + y + ")");
		System.out.println("Loading, PLease Wait ....................");
		mainFrame.setSize(new Dimension(1195, 720));
		mainFrame.setTitle("ChromViz 1.0 - Visualization of High-throughput Data on Chromosomes");
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
