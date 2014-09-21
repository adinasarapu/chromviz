package edu.ucsd.genome.chromviz.testExamples;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ProgressBarPanel extends JFrame {

	private static final long serialVersionUID = 1L;
	Thread loadThread;
	Object lock = new Object();
	boolean shouldStop = false;
	JTextField progressTextField;
	JProgressBar progressBar;

	public ProgressBarPanel() {
	}

	public void initProgressBar() {
		setLayout(new BorderLayout());
		progressTextField = new JTextField();
		add(progressTextField, BorderLayout.NORTH);
		JPanel bottomPanel = new JPanel();
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		bottomPanel.setLayout(new GridLayout(0, 1));
		bottomPanel.add(progressBar);
		bottomPanel.add(new JLabel("Load Status"));
		JPanel buttonPanel = new JPanel();
		JButton startButton = new JButton("Start");
		buttonPanel.add(startButton);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startLoading();
			}
		});
		JButton stopButton = new JButton("Stop");
		buttonPanel.add(stopButton);
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopLoading();
			}
		});
		bottomPanel.add(buttonPanel);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	public void startLoading() {
		if (loadThread == null) {
			loadThread = new LoadThread();
			shouldStop = false;
			loadThread.start();
		}
	}

	public void stopLoading() {
		synchronized (lock) {
			shouldStop = true;
			lock.notify();
		}
	}

	class LoadThread extends Thread {
		public void run() {
			int min = 0;
			int max = 100;
			progressBar.setValue(min);
			progressBar.setMinimum(min);
			progressBar.setMaximum(max);
			Runnable runner = new Runnable() {
				public void run() {
					int value = progressBar.getValue();
					value++;
					progressBar.setValue(value);
					progressTextField.setText("" + value);
				}
			};
			for (int i = min; i <= max; i++) {
				try {
					SwingUtilities.invokeAndWait(runner);
				} catch (InvocationTargetException e) {
					break;
				} catch (InterruptedException e) {
					// Ignore Exception
				}
				synchronized (lock) {
					if (shouldStop)
						break;
					try {
						lock.wait(100);
					} catch (InterruptedException e) {
						// Ignore Exception
					}
				}
			}
			loadThread = null;
		}
	}

	public static void main(String[] args) {
		ProgressBarPanel g = new ProgressBarPanel();
		g.initProgressBar();
		// Set the frame size as the user's screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		System.out.println("Loading, PLease Wait ....................");
		g.setSize(new Dimension(screenSize.width, screenSize.height - 25));
		g.setVisible(true);
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}