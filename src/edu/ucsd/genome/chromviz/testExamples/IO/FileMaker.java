package edu.ucsd.genome.chromviz.testExamples.IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class FileMaker {

	BufferedWriter bw;

	/***
	 * Make a file with the directory name and file name
	 * @param directory
	 * @param filename
	 */
	public FileMaker(String directory, String filename){
		try{
			String dir = directory;
			File newfile = new File(dir);
			newfile.mkdirs();
			newfile = new File(dir+"/"+filename);
			bw = new BufferedWriter(new FileWriter(newfile));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/***
	 * Write a line in the FileMaker object
	 * @param text
	 */
	public void writeLine(String text){
		try{
			bw.write(text);
			bw.write("\r\n");
			bw.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void write(String text){
		try{
			bw.write(text);
			bw.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void write(Character character) {
		try {
			bw.append(character);
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
