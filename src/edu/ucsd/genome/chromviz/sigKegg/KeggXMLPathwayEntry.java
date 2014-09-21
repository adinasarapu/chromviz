package edu.ucsd.genome.chromviz.sigKegg;

import java.util.List;

public class KeggXMLPathwayEntry {
	private String entryID;	
	private String entryType;
	private List<String> geneIDs;
	private String nodeDisplayName;
	private List<KeggPathwayEntryComponent> components;
	
	public List<KeggPathwayEntryComponent> getComponents() {
		return components;
	}
	public void setComponents(List<KeggPathwayEntryComponent> components) {
		this.components = components;
	}
	public String getEntryID() {
		return entryID;
	}
	public void setEntryID(String entryID) {
		this.entryID = entryID;
	}
	public String getEntryType() {
		return entryType;
	}
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}
	public List<String> getGeneIDs() {
		return geneIDs;
	}
	public void setGeneIDs(List<String> geneIDs) {
		this.geneIDs = geneIDs;
	}
	public String getNodeDisplayName() {
		return nodeDisplayName;
	}
	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}
}
