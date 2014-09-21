package edu.ucsd.genome.chromviz.sigData;

/**
 * This container holds the rows from the knownGene table.
 */

public class KnownGeneNew {

	// data
	private String kgID;
	private String chromosome;
	private String strand;
	private int txStartPosition;
	private int txEndPosition;
	private int cdsStartPosition;
	private int cdsEndPosition;
	private int exonCount;
	private String exonStartsPosition;
	private String exonEndsPosition;
	private String alignID;
	private String proteinID;

	public String getKgID() {
		return kgID;
	}

	public void setKgID(String kgID) {
		this.kgID = kgID;
	}

	public String getChromosome() {
		return chromosome;
	}

	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
	}

	public String getStrand() {
		return strand;
	}

	public void setStrand(String strand) {
		this.strand = strand;
	}

	public int getTxStartPosition() {
		return txStartPosition;
	}

	public void setTxStartPosition(int txStartPosition) {
		this.txStartPosition = txStartPosition;
	}

	public int getTxEndPosition() {
		return txEndPosition;
	}

	public void setTxEndPosition(int txEndPosition) {
		this.txEndPosition = txEndPosition;
	}

	public int getCdsStartPosition() {
		return cdsStartPosition;
	}

	public void setCdsStartPosition(int cdsStartPosition) {
		this.cdsStartPosition = cdsStartPosition;
	}

	public int getCdsEndPosition() {
		return cdsEndPosition;
	}

	public void setCdsEndPosition(int cdsEndPosition) {
		this.cdsEndPosition = cdsEndPosition;
	}

	public int getExonCount() {
		return exonCount;
	}

	public void setExonCount(int exonCount) {
		this.exonCount = exonCount;
	}

	public String getExonStartsPosition() {
		return exonStartsPosition;
	}

	public void setExonStartsPosition(String exonStartsPosition) {
		this.exonStartsPosition = exonStartsPosition;
	}

	public String getExonEndsPosition() {
		return exonEndsPosition;
	}

	public void setExonEndsPosition(String exonEndsPosition) {
		this.exonEndsPosition = exonEndsPosition;
	}

	public String getAlignID() {
		return alignID;
	}

	public void setAlignID(String alignID) {
		this.alignID = alignID;
	}
	
	public String getProteinID() {
		return proteinID;
	}

	public void setProteinID(String proteinID) {
		this.proteinID = proteinID;
	}
}
