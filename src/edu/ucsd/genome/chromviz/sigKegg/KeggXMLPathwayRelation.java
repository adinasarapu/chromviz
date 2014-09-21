package edu.ucsd.genome.chromviz.sigKegg;

import java.util.List;

public class KeggXMLPathwayRelation {
	private KeggXMLPathwayEntry nodeFrom;
	private KeggXMLPathwayEntry nodeTo;
	private String relationType;
	//private List<KeggPathwayRelationSubType> relationSubTypes;
	private List<String> relationSubTypes;
	public KeggXMLPathwayEntry getNodeFrom() {
		return nodeFrom;
	}
	public void setNodeFrom(KeggXMLPathwayEntry nodeFrom) {
		this.nodeFrom = nodeFrom;
	}
	public KeggXMLPathwayEntry getNodeTo() {
		return nodeTo;
	}
	public void setNodeTo(KeggXMLPathwayEntry nodeTo) {
		this.nodeTo = nodeTo;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	public List<String> getRelationSubTypes() {
		return relationSubTypes;
	}
	public void setRelationSubTypes(List<String> relationSubTypes) {
		this.relationSubTypes = relationSubTypes;
	}
	
}
