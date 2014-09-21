package edu.ucsd.genome.chromviz.sigData;

import java.util.Comparator;

public class KEGGPathways implements Comparator<Object> {
	private String pathID;
	private String pathName;

	public String getPathID() {
		return pathID;
	}

	public void setPathID(String pathID) {
		this.pathID = pathID;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	@Override
	public int compare(Object a1, Object a2) {
		KEGGPathways p1 = (KEGGPathways) a1;
		KEGGPathways p2 = (KEGGPathways) a2;
		return p1.getPathName().compareToIgnoreCase(p2.getPathName());

	}
}
