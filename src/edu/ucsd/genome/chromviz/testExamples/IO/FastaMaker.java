package edu.ucsd.genome.chromviz.testExamples.IO;

/***
 * Make FASTA(.fna) and Phred quality score (.qual) formatted file.
 * This is more like an adapter class for file IO.
 * @author Arang
 *
 */
public class FastaMaker {
	private FileMaker fastaFileMaker;
	private FileMaker qualFileMaker;
	private boolean makeQual = true;
	
	/***
	 * Make .fna, .qual files with FileMaker.
	 * @param path	where to place the output files
	 * @param name	output file name
	 * @param makeQual	whether to make the phred scored .qual file or not.
	 *  Default is true.
	 */
	public FastaMaker(String path, String name, boolean makeQual) {
		fastaFileMaker = new FileMaker(path, name + ".fna");
		if (makeQual)	qualFileMaker = new FileMaker(path, name + ".qual");
		this.makeQual = makeQual;
	}
	
	public FastaMaker(String path, String name) {
		fastaFileMaker = new FileMaker(path, name + ".fna");
		qualFileMaker = new FileMaker(path, name + ".qual");
	}
	
	/***
	 * Add the reference line for each Fasta, Qual files starting with ">".
	 * @param reference	reference name. could be accession number, etc.
	 */
	public void addReferences(byte[] reference) {
		String n = new String(reference);
		fastaFileMaker.write(">");
		fastaFileMaker.writeLine(n);
		qualFileMaker.write(">");
		qualFileMaker.writeLine(n);
	}
	
	public void addReferences(String reference) {
		fastaFileMaker.write(">");
		fastaFileMaker.writeLine(reference);
		if (makeQual) qualFileMaker.write(">");
		if (makeQual) qualFileMaker.writeLine(reference);
	}
	
	/***
	 * Add the base pair sequences.
	 * Recommended to use "A, T(U), G, C" for nucleotide(deoxynucleotide), "N" for unknown bases.
	 * @param sequence Base pair sequences.
	 */
	public void addSequence(byte[] sequence) {
		fastaFileMaker.writeLine(new String(sequence));
	}
	
	public void addSequence(String sequence) {
		fastaFileMaker.writeLine(sequence);
	}

	/***
	 * Add quality score, one at a time
	 * @param quality	phred quality score
	 */
	public void addQuality(int quality) {
		if (makeQual)
			qualFileMaker.write(String.valueOf(quality) + " ");
		else
			System.out.println("Quality file is not to be made!!");
	}
	
	public void addQuality(String quality) {
		if (makeQual)
			qualFileMaker.writeLine(quality);
		else
			System.out.println("Quality file is not to be made!!");
	}
	
	/***
	 * White character for giving line spacing.
	 */
	public void addLine() {
		qualFileMaker.writeLine("");
	}
}
