package edu.ucsd.genome.chromviz.sigData;

import java.util.List;

public class FileMetaData {
	private int ligandsSelected = -1, timePointsSelected = -1;
	private List<String> ligandTitleList, timeTitleList;

	public int getLigandsSelected() {
		return ligandsSelected;
	}

	public void setLigandsSelected(int ligandsSelected) {
		this.ligandsSelected = ligandsSelected;
	}

	public int getTimePointsSelected() {
		return timePointsSelected;
	}

	public void setTimePointsSelected(int timePointsSelected) {
		this.timePointsSelected = timePointsSelected;
	}

	public List<String> getLigandTitleList() {
		return ligandTitleList;
	}

	public void setLigandTitleList(List<String> ligandTitleList) {
		this.ligandTitleList = ligandTitleList;
	}

	public List<String> getTimeTitleList() {
		return timeTitleList;
	}

	public void setTimeTitleList(List<String> timeTitleList) {
		this.timeTitleList = timeTitleList;
	}
}
