package edu.ucsd.genome.chromviz.testExamples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadSeq {
	public ReadSeq() {
	}

	public void getSequence() {
		String line = null;
		String geneId = null;
		int i = 0;
		try {
			BufferedReader input = new BufferedReader(new FileReader(
					"C:/Users/ashok/Desktop/sgmpseq.txt"));
			while ((line = input.readLine()) != null) {
				StringBuffer buffer = new StringBuffer();
				if (line.substring(0, 1).equals(">")) {
					i = 0;
					String[] str = line.split(":");
					geneId = str[1].substring(0, 7);
					System.out.println("geneID ===>" + geneId);
				}
				if (!(line.substring(0, 1).equals(">"))) {
					i++;
					buffer.append(line);
					System.out.println("Sequence ==> " + buffer);
				}
			}// while
			System.out.println("i ==>" + i);
			input.close();
		}// try
		catch (IOException ie) {
			ie.printStackTrace();
		}
		// System.out.println("Sequence ==> "+buffer);
	}// getSequence

	public static void main(String[] args) {
		ReadSeq seq = new ReadSeq();
		seq.getSequence();
	}
}
