package edu.ucsd.genome.chromviz.sigData;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.genome.chromviz.sigUtility.ReadFileData;

public class ChromosomeData implements Comparable<ChromosomeData> {

	Rectangle rectangle;
	private ReadFileData userData;
	String nameChromosome;
	int xPos;
	int yPos;
	List<Double> linePos;
	List<Double> txStart;
	List<Double> txEnd;
	List<String> geneNames;
	List<String> geneSymbols;
	public List<String> listOfNames;
	public List<String> locusIDList;

	
	public ReadFileData getUserData() {
		return userData;
	}

	public void setUserData(ReadFileData userData) {
		this.userData = userData;
	}

	public List<String> getListOfNames() {
		return listOfNames;
	}

	public void setListOfNames(List<String> listOfNames) {
		this.listOfNames = listOfNames;
	}

	public String getLocusID(int i) {
		return locusIDList.get(i);
	}

	public void setLocusID(List<String> locusID) {
		locusIDList = locusID;
	}

	public void setName(String name) {
		this.nameChromosome = name;
	}

	public List<String> getGeneName() {
		return geneNames;
	}

	public void setGeneName(List<String> gene) {
		geneNames = gene;
	}

	public List<String> getGeneSymbol() {
		return geneSymbols;
	}

	public void setGeneSymbolList(List<String> symbol) {
		geneSymbols = symbol;
	}

	public List<Double> getLinePosList() {
		return linePos;
	}

	public void setLinePos(List<Double> pos) {
		linePos = pos;
	}

	public List<Double> getTxStart() {
		return txStart;
	}

	public void setTxStart(List<Double> txStart) {
		this.txStart = txStart;
	}

	public List<Double> getTxEnd() {
		return txEnd;
	}

	public void setTxEnd(List<Double> txEnd) {
		this.txEnd = txEnd;
	}

	public int getYPos() {
		return yPos;
	}

	public void setChromosomeYPos(int pos) {
		yPos = pos;
	}

	public int getXPos() {
		return xPos;
	}

	public void setChromosomeXPos(int pos) {
		xPos = pos;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public String getChromosomeName() {
		return nameChromosome;
	}

	public void setChromosomeName(String name) {
		this.nameChromosome = name;
	}

	public int compareTo(Object o1, Object o2) {
		ChromosomeData chr1 = (ChromosomeData) o1;
		ChromosomeData chr2 = (ChromosomeData) o2;
		// int c1 = Integer.parseInt(chr1.getName().substring(3));
		// int c2 = Integer.parseInt(chr2.getName().substring(3));
		// return chr1.getName().compareToIgnoreCase(chr2.getName());
		// return (int) (chr1.getRectangle().getHeight() - chr2.getRectangle().getHeight());  
		int i = chr1.getChromosomeName().compareTo(chr2.getChromosomeName());
		if (i != 0) return i;
	    return (int) (chr1.getRectangle().getHeight() - chr2.getRectangle().getHeight());
	}

	@Override
	public int compareTo(ChromosomeData arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
