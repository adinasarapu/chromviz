package edu.ucsd.genome.chromviz.sigData;

/**
 * This container holds the rows from the knownGene table.
 */

public class KnownGene {

	// data
	private String knownGeneID;
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
	private String locusID;
	private String mapID;
	private String refSeqID;
	private String spDisplayID;
	private String spID;
	private String geneSymbol;

	public String getKnownGeneID() {
		return knownGeneID;
	}

	public void setKnownGeneID(String kgID) {
		this.knownGeneID = kgID;
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

	public String getLocusID() {
		return locusID;
	}

	public void setLocusID(String locusID) {
		this.locusID = locusID;
	}

	public String getMapID() {
		return mapID;
	}

	public void setMapID(String mapID) {
		this.mapID = mapID;
	}

	public String getRefSeqID() {
		return refSeqID;
	}

	public void setRefSeqID(String refSeqID) {
		this.refSeqID = refSeqID;
	}

	public String getSpDisplayID() {
		return spDisplayID;
	}

	public void setSpDisplayID(String spDisplayID) {
		this.spDisplayID = spDisplayID;
	}

	public String getSpID() {
		return spID;
	}

	public void setSpID(String spID) {
		this.spID = spID;
	}

	public String getGeneSymbol() {
		return geneSymbol;
	}

	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
	}
}
