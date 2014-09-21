package edu.ucsd.genome.chromviz.sigData;

import java.util.List;

public class LocusKnownGenes {
private String locusID;
private List<String> knownGenes;

public String getLocusID() {
	return locusID;
}
public void setLocusID(String locusID) {
	this.locusID = locusID;
}
public List<String> getKnownGenes() {
	return knownGenes;
}
public void setKnownGenes(List<String> knownGenes) {
	this.knownGenes = knownGenes;
}
}
