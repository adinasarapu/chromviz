package edu.ucsd.genome.chromviz.testExamples;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Test extends JPanel implements ItemListener{
	JComboBox jc = new JComboBox(); 
	JButton button = new JButton("submit");
	public Test() {
	      jc.addItem("France"); 
	      jc.addItem("Germany"); 
	      jc.addItem("Italy"); 
	      jc.addItem("Japan"); 
	      jc.addItemListener(this); 
	      add(jc); 
	      add(button);
	  }
	
		public void itemStateChanged(ItemEvent ievent) {
				if (ievent.getStateChange() == ItemEvent.SELECTED) {
					button.setEnabled(true);
		String str = (String) jc.getSelectedItem();
		System.out.println("ComboBox "+str);
				
		button.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("submit")) {
			System.out.println("Sequences after pressing button");
		}
		}
		});//end button action listener
		}
		}//end Combobox itemlistener
		public static void main(String[] args) {
		    JFrame frame = new JFrame();
		    frame.getContentPane().add(new Test());

		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setSize(200, 200);
		    frame.setVisible(true);
		  }
	
}
