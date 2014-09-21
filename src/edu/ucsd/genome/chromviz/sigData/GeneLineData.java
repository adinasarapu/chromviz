package edu.ucsd.genome.chromviz.sigData;

import java.awt.Rectangle;
import java.util.List;

public class GeneLineData {

	private Rectangle rectangle;
	private int txStartPos;
	private int txEndPos;
	private String name;
	private String geneName;
	public String geneSymbol;
	private String time;
	private String geneID;
	private List<String> arrayString1;
	private List<String> arrayString2;
	private List<String> arrayString3;
	
	public int ligandNumber;
		
	public String getGeneID() {
		return geneID;
	}

	public void setGeneEntrezID(String geneID) {
		this.geneID = geneID;
	}

	
	

	
	private List<List<String>> userGeneData;

	public List<List<String>> getUserGeneData() {
		return userGeneData;
	}

	public void setUserGeneData(List<List<String>> userGeneData) {
		this.userGeneData = userGeneData;
	}
	public List<String> getArrayString1() {
		return arrayString1;
	}

	public void setArrayString1(List<String> arrayString1) {
		this.arrayString1 = arrayString1;
	}

	public List<String> getArrayString2() {
		return arrayString2;
	}

	public void setArrayString2(List<String> arrayString2) {
		this.arrayString2 = arrayString2;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getGeneName() {
		return geneName;
	}

	public void setGeneName(String geneName) {
		this.geneName = geneName;
	}

	public String getGeneSymbol() {
		return geneSymbol;
	}

	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
	}

	public String getName() {
		return name;
	}

	public void setChromosomeName(String name) {
		this.name = name;
	}

	public int getTxStartPos() {
		return txStartPos;
	}

	public void setTxStartPos(int txStartPos) {
		this.txStartPos = txStartPos;
	}

	public int getTxEndPos() {
		return txEndPos;
	}

	public void setTxEndPos(int txEndPos) {
		this.txEndPos = txEndPos;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public void setArrayString3(List<String> arrayString3) {
		this.arrayString3 = arrayString3;

	}

	public List<String> getArrayString3() {
		return arrayString3;
	}

}
