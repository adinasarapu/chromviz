package edu.ucsd.genome.chromviz.testExamples.IO;

import java.util.StringTokenizer;

/***
 * Make FASTQ formatted file.
 * This is more like an adapter class for file IO.
 * @author Arang
 *
 */
public class FASTQmaker {
	private FileMaker fastqFileMaker;
	
	/***
	 * Make .fastq file with FileMaker.
	 * @param path	where to place the output file
	 * @param name	output file name
	 */
	public FASTQmaker(String path, String name) {
		fastqFileMaker = new FileMaker(path, name + ".fastq");
	}
	
	/***
	 * Add the reference line for sequences starting with ">".
	 * @param reference	reference name. could be accession number, etc.
	 */
	public void addSeqReference(byte[] reference) {
		fastqFileMaker.write("@");
		fastqFileMaker.writeLine(reference.toString());
	}
	
	public void addSeqReference(String reference) {
		fastqFileMaker.write("@");
		fastqFileMaker.writeLine(reference);
	}
	
	/***
	 * Add the base pair sequences.
	 * Recommended to use "A, T(U), G, C" for nucleotide(deoxynucleotide), "N" for unknown bases.
	 * @param sequence Base pair sequences.
	 */
	public void addSequence(byte[] sequence) {
		fastqFileMaker.writeLine(new String(sequence));
	}
	
	public void addSequence(String sequence) {
		fastqFileMaker.writeLine(sequence);
	}
	
	/***
	 *  Add the reference line for qualities starting with "+".
	 * @param reference	reference name. could be accession number, etc.
	 */
	public void addQualReference(byte[] reference) {
		fastqFileMaker.write("+");
		fastqFileMaker.writeLine(reference.toString());
	}
	
	public void addQualReference(String reference) {
		fastqFileMaker.write("+");
		fastqFileMaker.writeLine(reference);
	}
	
	/***
	 * Add quality score, one at a time.
	 * Assumed to getting phred-like quality score,
	 * so we make it in fastq character formed qualities
	 * by adding 64 and cascading it into char.
	 * @param quality	quality score
	 */
	public void addQuality(int quality) {
		fastqFileMaker.write((char)(quality + 64));
	}
	
	public void addQuality(String quality) {
		StringTokenizer st = new StringTokenizer(quality);
		while(st.hasMoreElements()) {
			int qual = Integer.parseInt(st.nextToken()) + 64;
			fastqFileMaker.write((char)(qual));
		}
		fastqFileMaker.writeLine("");
	}
	
	/***
	 * White character for giving line spacing.
	 */
	public void addLine() {
		fastqFileMaker.writeLine("");
	}
}
