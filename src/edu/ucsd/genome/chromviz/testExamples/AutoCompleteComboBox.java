package edu.ucsd.genome.chromviz.testExamples;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class AutoCompleteComboBox extends JFrame{
	
	private static final long serialVersionUID = 1L;
	JPanel Panel = new JPanel();
	JComboBox comboBox = new JComboBox();
	Container contentPane;
	public AutoCompleteComboBox(){
		contentPane = getContentPane();
		comboBox = new JComboBox(new Object[] {"   ","Srujana","Ashok","Hansika","Siri"});
		//comboBox.setEditable(true);
		AutoCompleteDecorator.decorate(comboBox);
		Panel.add(comboBox);
		contentPane.add(Panel, BorderLayout.WEST);
	}
	public static void main(String[] args) {
		AutoCompleteComboBox g = new AutoCompleteComboBox();
		g.setSize(new Dimension(900, 700));
		g.setTitle("UCSD-Signaling Network Tool");
		g.setVisible(true);
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
