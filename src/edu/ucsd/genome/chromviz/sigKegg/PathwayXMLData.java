package edu.ucsd.genome.chromviz.sigKegg;

public class PathwayXMLData {
String geneFrom;
String geneTo;
String dnaFrom;
String dnaTo;
String proteinFrom;
String proteinTo;
String fromStartPos;
String fromEndPos;
public String getGeneFrom() {
	return geneFrom;
}
public void setGeneFrom(String geneFrom) {
	this.geneFrom = geneFrom;
}
public String getGeneTo() {
	return geneTo;
}
public void setGeneTo(String geneTo) {
	this.geneTo = geneTo;
}
public String getDnaFrom() {
	return dnaFrom;
}
public void setDnaFrom(String dnaFrom) {
	this.dnaFrom = dnaFrom;
}
public String getDnaTo() {
	return dnaTo;
}
public void setDnaTo(String dnaTo) {
	this.dnaTo = dnaTo;
}
public void setProteinFrom(String proteinFrom) {
	this.proteinFrom = proteinFrom;
}
public String getProteinFrom() {
	return proteinFrom;
}

public void setProteinTo(String proteinTo) {
	this.proteinTo= proteinTo;
}
public String getProteinTo() {
	return proteinTo;
}
public void setFromStartPos(String fromStartPos) {
	this.fromStartPos= fromStartPos;
}
public String getFromStartPos() {
	return fromStartPos;
}
public void setFromEndPos(String fromEndPos) {
	this.fromEndPos= fromEndPos;
}
public String getFromEndPos() {
	return fromEndPos;
}

}
