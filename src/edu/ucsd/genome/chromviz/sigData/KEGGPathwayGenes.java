package edu.ucsd.genome.chromviz.sigData;

import java.util.Comparator;

@SuppressWarnings("unchecked")
public class KEGGPathwayGenes implements Comparator {
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
	KEGGPathwayGenes p1 = (KEGGPathwayGenes) a1;
	KEGGPathwayGenes p2 = (KEGGPathwayGenes) a2;
   return p1.getPathName().compareToIgnoreCase(p2.getPathName());

}
}
