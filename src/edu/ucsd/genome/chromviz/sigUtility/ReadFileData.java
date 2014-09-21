package edu.ucsd.genome.chromviz.sigUtility;

import java.util.List;
import java.util.Map;

import edu.ucsd.genome.chromviz.sigData.FileMetaData;

public class ReadFileData {

	private int ligandsSelected = -1;
	private int timePointsSelected = -1;
	//private List<String> ligandTitleList, timeTitleList
	private FileMetaData fileMetaData;
	private Map<String, List<List<String>>>  fileArrayData;
	
	public int getLigandNumber() {
		return ligandsSelected;
	}
	public void setLigandNumber(int ligandNumber) {
		this.ligandsSelected = ligandNumber;
	}
	public Map<String, List<List<String>>> getArrayData() {
		return fileArrayData;
	}
	public void setArrayData(Map<String, List<List<String>>>  arrayData) {
		this.fileArrayData = arrayData;
	}
	public int getTimePointsSelected() {
		return timePointsSelected;
	}
	public void setTimePointsSelected(int timePointNumber) {
		this.timePointsSelected = timePointNumber;
	}
	public FileMetaData getFileMetaData() {
		return fileMetaData;
	}
	public void setFileMetaData(FileMetaData fileMetaData) {
		this.fileMetaData = fileMetaData;
	}	

}
