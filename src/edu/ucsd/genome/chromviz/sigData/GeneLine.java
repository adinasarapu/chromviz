package edu.ucsd.genome.chromviz.sigData;


public class GeneLine {

	private double genePos;
	private int lineStart;
	private int lineEnd;
	private String geneSymbol;
	private String geneName;
	private String locusID;
	ChromosomeData chrData;

	public ChromosomeData getChrData() {
		return chrData;
	}

	public void setChrData(ChromosomeData chrData) {
		this.chrData = chrData;
	}

	public double getGenePos() {
		return genePos;
	}

	public void setGenePos(double genePos) {
		this.genePos = genePos;
	}

	public int getLineStart() {
		return lineStart;
	}

	public void setLineStart(int lineStart) {
		this.lineStart = lineStart;
	}

	public int getLineEnd() {
		return lineEnd;
	}

	public void setLineEnd(int lineEnd) {
		this.lineEnd = lineEnd;
	}

	public String getGeneSymbol() {
		return geneSymbol;
	}

	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
	}

	public String getLocusID() {
		return locusID;
	}

	public void setLocusID(String locusID) {
		this.locusID = locusID;
	}

	public String getGeneName() {
		return geneName;
	}

	public void setGeneName(String geneName) {
		this.geneName = geneName;
	}

}
