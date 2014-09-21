package edu.ucsd.genome.chromviz.testExamples;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
public class FileChooser {
	
	public static void main(String[] args) {

	JFileChooser fileopen = new JFileChooser();
	FileFilter filter = new FileNameExtensionFilter("text files", "txt");
	fileopen.addChoosableFileFilter(filter);
	int ret = fileopen.showDialog(null, "Select file");
	if (ret == JFileChooser.APPROVE_OPTION) {
		File file = fileopen.getSelectedFile();

		//READ FILE TEXT LINE BY LINE AND DISPLAY IT TO STANDARD OUTPUT
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			while ((str = in.readLine()) != null) {
				System.out.println(str);
			}
			in.close();
		} catch (IOException e) {
		}
	}
}
}
