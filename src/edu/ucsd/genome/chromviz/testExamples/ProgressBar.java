package edu.ucsd.genome.chromviz.testExamples;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
public class ProgressBar extends JPanel {
	private static final long serialVersionUID = 1L;
	JPanel panel;
	JLabel label;
	JProgressBar pbar;
	static int min = 0;
	int max;
	JFrame frame = new JFrame("Downloading");
	public ProgressBar(int max,JLabel label) {
		this.max =max;
		this.label = label;
		panel = new JPanel();
		pbar = new JProgressBar();
		pbar.setMinimum(min);
		pbar.setMaximum(max);
		panel.add(pbar);
		panel.add(label);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(panel);
		frame.setSize(new Dimension(400,100));
		frame.setVisible(true);
	}
	public void updateBar(int newValue) {
		pbar.setValue(newValue);
		pbar.setStringPainted(true);
		pbar.updateUI();
		frame.paint(frame.getGraphics());
		pbar.paint(pbar.getGraphics());
		
	}
}


