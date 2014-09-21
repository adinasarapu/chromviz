package edu.ucsd.genome.chromviz.testExamples;

	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.JProgressBar;
	import javax.swing.SwingUtilities;

	public class ProgressBarDemo  extends JPanel {

	private static final long serialVersionUID = 1L;

	JProgressBar pbar;

	  //static int min = 0;

	   //int max ;//= 100;

	  public  ProgressBarDemo() {
		 //this.max = maxi; 
	    pbar = new JProgressBar();
	    //pbar.setMinimum(min);
	    //pbar.setMaximum(max);
	    add(pbar);
	  
	    JFrame frame = new JFrame("Progress Bar Example");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setContentPane(this);
	    frame.pack();
	    frame.setVisible(true);
	  }
	  int percent;
	    public void updateBar(int i,int max) {
	    //for (int i = min; i <= max; i++) {
	    	pbar.setMinimum(0);
		    pbar.setMaximum(max);
	      percent= i;
	      try {
	        SwingUtilities.invokeLater(new Runnable() {
	          public void run() {
	        	  pbar.setValue(percent);
	      	    pbar.setStringPainted(true);
	          }
	        });
	        Thread.sleep(100);
	      } //try
	      catch (InterruptedException e) {
	      }//catch
	    }//for
	 }
	    
	  //constructor

	  //public void updateBar(int newValue) {
	    //pbar.setValue(newValue);
	    //pbar.setStringPainted(true);
	  //}

	  //public static void main(String args[]) {
	    //new ProgressBarDemo();
	  //}
	//class
