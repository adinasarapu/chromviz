package edu.ucsd.genome.chromviz.sigData;

import java.util.List;

public class ArrayData {
	
	private String identifier;
	private List<ValueArrayList> valueArrayList;
	public List<ValueArrayList> getValueArrayList() {
		return valueArrayList;
	}
	public void setValueArrayList(List<ValueArrayList> valueArrayList) {
		this.valueArrayList = valueArrayList;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
		
}
