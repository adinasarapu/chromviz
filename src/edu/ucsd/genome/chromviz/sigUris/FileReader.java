/**
 * 
 */
package edu.ucsd.genome.chromviz.sigUris;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Reads specified file
 * @author Arang
 *
 */
public class FileReader {

	BufferedReader br;
	private static final int LINE_OFFSET = 70;
	
	/***
	 * Reads specified file.
	 * File path directory can be written with "/" or "\\". 
	 * @param path
	 */
	public FileReader(String path) {
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FileReader(String path, String fileName) {
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path + "/" + fileName)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * Reads file specified with FileReader object.
	 * @return the line cascaded from the last line,
	 * or null if the line has reached to end of the file.
	 * 
	 */
	public StringBuffer readLine(){
		StringBuffer str = new StringBuffer();
		try {
			str.append(br.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public boolean hasMoreLines() {
		try {
			return br.ready();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


	/***
	 * reads numLines from the n-th line of the file 
	 * @param from the line where to start, 1 is the first line
	 * @param numLines	the number of lines to read
	 * @return the StringBuffer containing characters
	 * 
	 */
	public StringBuffer readLine(int from, int numLines) {
		StringBuffer str = new StringBuffer();
		try {
			// skip the lines until we reach 'from'
			while ((--from) != 0) {
				br.readLine();
			}
			// read the number of lines specified in 'numLines'
			while((numLines--) != 0) {
				str.append(br.readLine());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/***
	 * Reads sequence with length of length from the given fromIndex
	 * @param fromIndex		the index where start to read
	 * @param length		the length of sequence
	 * @return	the sequence
	 */
	public StringBuffer readSequence(int fromIndex, int length) {
		StringBuffer str = new StringBuffer();
		int lines = fromIndex / LINE_OFFSET;
		int residue = fromIndex % LINE_OFFSET;

		try {
			// skip the lines until we reach 'fromIndex'
			while ((--lines) > 0) {
				br.readLine();
			}

			String line = br.readLine();
			// Skeep if the line contains < ...
//			if (line.contains("<")) {
//				System.out.println(line);
//				line = br.readLine();
//			}

			// read the chars with length of 'length'
			if (length < (line.length() - line.substring(0, residue).length())) {
				str.append(line.substring(residue, residue + length));
			} 
			else {
				str.append(line.substring(residue));
				length -= str.length();

				lines = length / LINE_OFFSET;
				residue = length % LINE_OFFSET;

				while((--lines) >= 0) {
					str.append(br.readLine());
				}
				line = br.readLine();
				str.append(line.substring(0, residue));
			}
		} catch (Exception e) {
			// do nothing
		}
		return str;
	}

	public void close() {
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
