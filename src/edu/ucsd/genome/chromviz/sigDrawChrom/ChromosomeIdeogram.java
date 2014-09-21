package edu.ucsd.genome.chromviz.sigDrawChrom;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.ucsd.genome.chromviz.sigData.ChromosomeData;
import edu.ucsd.genome.chromviz.sigData.KnownGene;
import edu.ucsd.genome.chromviz.sigUtility.ReadFileData;

/**
 * 
 * @author srujana
 * 
 */
public class ChromosomeIdeogram extends JFrame {
	// static JScrollPane _scrollPane1;
	private static List<ChromosomeData> chrDataList = null;
	private static final long serialVersionUID = 1L;

	/*
	 * These data are obtained from UCSC Genome Browser
	 */
	
	static String chromName[] = {"chr1","chr2",	"chr3",	"chr4",	"chr5",	"chr6",	"chr7",	"chr8",	"chr9",	"chr10","chr11","chr12",
		"chr13","chr14","chr15","chr16","chr17","chr18","chr19","chrX",	"chrY"};
	
	static String chromLength[] = {
			// lengthChr1 = 197195432;
			"197195432",
			// lengthChr2 = 181748087;
			"181748087",
			// lengthChr3 = 159599783;
			"159599783",
			// lengthChr4 = 155630120;
			"155630120",
			// lengthChr5 = 152537259;
			"152537259",
			// lengthChr6 = 149517037;
			"149517037",
			// lengthChr7 = 152524553;
			"152524553",
			// lengthChr8 = 131738871;
			"131738871",
			// lengthChr9 = 124076172;
			"124076172",
			// lengthChr10 = 129993255;
			"129993255",
			// lengthChr11 = 121843856;
			"121843856",
			// lengthChr12 = 121257530;
			"121257530",
			// lengthChr13 = 120284312;
			"120284312",
			// lengthChr14 = 125194864;
			"125194864",
			// lengthChr15 = 103494974;
			"103494974",
			// lengthChr16 = 98319150;
			"98319150",
			// lengthChr17 = 95272651;
			"95272651",
			// lengthChr18 = 90772031;
			"90772031",
			// lengthChr19 = 61342430;
			"61342430",
			// lengthChrX (20) = 166650296;
			"166650296",
			// lengthChrY (21) = 15902555;
			"15902555" };

	/*
	 * Parameters: x - the specified X coordinate; y - the specified Y
	 * coordinate; width - the width of the Rectangle; height - the height of
	 * the Rectangle;
	 */

	static String coordinates[][] = {
			// 1 Chromosome ("11", "14", "23", "230" )
			{ "50", "12", "22", "230" },
			// 2 Chromosome("52", "29", "23", "215")
			{ "100", "27", "22", "215" },
			// 3 Chromosome("93", "54", "22", "190")
			{ "150", "52", "22", "190" },
			// 4 Chromosome("133", "61", "23", "183")
			{ "200", "59", "22", "183" },
			// 5 Chromosome("176", "66", "22", "178")
			{ "250", "64", "22", "178" },
			// 6 Chromosome("218", "66", "23", "178")
			{ "300", "64", "22", "178" },
			// 7 Chromosome("261", "86", "22", "159")
			{ "350", "83", "22", "159" },
			// 8 Chromosome("304", "91", "22", "153")
			{ "400", "89", "22", "153" },
			// 9 Chromosome("346", "96", "22", "148")
			{ "450", "93", "22", "148" },
			// 10 Chromosome("388", "89", "22", "155")
			{ "500", "87", "22", "155" },
			// 11 Chromosome("431", "98", "22", "147")
			{ "550", "95", "22", "147" },
			// 12 Chromosome("11", "332", "23", "137")
			{ "600", "105", "22", "137" },
			// 13 Chromosome("52", "330", "23", "139")
			{ "650", "103", "22", "139" },
			// 14 Chromosome("93", "328", "22", "141")
			{ "700", "101", "22", "141" },
			// 15 Chromosome("134", "344", "22", "125")
			{ "750", "117", "22", "125" },
			// 16 Chromosome("176", "349", "22", "120")
			{ "800", "122", "22", "120" },
			// 17 Chromosome("218", "356", "23", "113")
			{ "850", "129", "22", "113" },
			// 18 Chromosome("261", "360", "22", "109")
			{ "900", "133", "22", "109" },
			// 19 Chromosome("303", "395", "23", "74")
			{ "950", "168", "22", "74" },
			// 20 "X" Chromosome("346", "277", "22", "192")
			{ "1000", "50", "22", "192" },
			// 21 "Y" Chromosome("384", "410", "26", "59")
			{ "1050", "183", "22", "59" } };

	//static String chromYaxisPos[] = { "12", "28","52","59","63","64","84","89","93","88","96","105","104","103","118","120","130","133","168","48","185"};
	//static String chromYaxisPos[] = { "12", "27","52", "59", "64", "64", "83", "89", "93", "87", "95","105","103","101","117","122","129","133","168", "50","183"};
	// chr_height = {"230","215","190","183","178","178","159","153","148","155","147","137","139","141","125","120","113","109", "74","192", "59"};
	// 242,  242,  242,  242,  242,  242,  242,  242,  242,  242,, 242,  242,  242,  242,  242,  242,  242,  242,  242,  242,  242}
	public ChromosomeIdeogram() {
		chrDataList = new ArrayList<ChromosomeData>();
	}

	public ChromosomeData getChromosomeImageCoordinates(ChromosomeData data,
			int chrNum) {

		int v1 = Integer.parseInt(coordinates[chrNum][0]); // x position
		int v2 = Integer.parseInt(coordinates[chrNum][1]); // y-position
		int v3 = Integer.parseInt(coordinates[chrNum][2]); // width
		int v4 = Integer.parseInt(coordinates[chrNum][3]); // height

		final Rectangle rect = new Rectangle(v1, v2, v3, v4);
		data.setRectangle(rect);
		data.setChromosomeXPos(v1);
		data.setChromosomeYPos(v2);
		return data;
	}

	public JPanel getChromosomeIdeogram(List<KnownGene> selectedKnownGeneDetails, ReadFileData fileData) {

		//int xPosition = 50; // 10
		
		List<Double> chr1GenePos = new ArrayList<Double>(), chr2GenePos = new ArrayList<Double>(), chr3GenePos = new ArrayList<Double>(), chr4GenePos = new ArrayList<Double>(), chr5GenePos = new ArrayList<Double>(), chr6GenePos = new ArrayList<Double>(), chr7GenePos = new ArrayList<Double>(), chr8GenePos = new ArrayList<Double>(), chr9GenePos = new ArrayList<Double>(), chr10GenePos = new ArrayList<Double>(), chr11GenePos = new ArrayList<Double>(), chr12GenePos = new ArrayList<Double>(), chr13GenePos = new ArrayList<Double>(), chr14GenePos = new ArrayList<Double>(), chr15GenePos = new ArrayList<Double>(), chr16GenePos = new ArrayList<Double>(), chr17GenePos = new ArrayList<Double>(), chr18GenePos = new ArrayList<Double>(), chr19GenePos = new ArrayList<Double>(), chrXGenePos = new ArrayList<Double>(), chrYGenePos = new ArrayList<Double>();
		List<Double> chr1StartPos = new ArrayList<Double>(), chr2StartPos = new ArrayList<Double>(),chr3StartPos = new ArrayList<Double>(),chr4StartPos = new ArrayList<Double>(), chr5StartPos = new ArrayList<Double>(), chr6StartPos = new ArrayList<Double>(), chr7StartPos = new ArrayList<Double>(), chr8StartPos = new ArrayList<Double>(), chr9StartPos = new ArrayList<Double>(), chr10StartPos = new ArrayList<Double>(),chr11StartPos = new ArrayList<Double>(),chr12StartPos = new ArrayList<Double>(), chr13StartPos = new ArrayList<Double>(),chr14StartPos = new ArrayList<Double>(),chr15StartPos = new ArrayList<Double>(),chr16StartPos = new ArrayList<Double>(),chr17StartPos = new ArrayList<Double>(),chr18StartPos = new ArrayList<Double>(),chr19StartPos = new ArrayList<Double>(),chrXStartPos = new ArrayList<Double>(),chrYStartPos = new ArrayList<Double>();
		List<Double> chr1EndPos = new ArrayList<Double>(),  chr2EndPos = new ArrayList<Double>(),  chr3EndPos = new ArrayList<Double>(),  chr4EndPos = new ArrayList<Double>(),  chr5EndPos = new ArrayList<Double>(),  chr6EndPos = new ArrayList<Double>(),  chr7EndPos = new ArrayList<Double>(),  chr8EndPos = new ArrayList<Double>(),  chr9EndPos = new ArrayList<Double>(),  chr10EndPos = new ArrayList<Double>(),  chr11EndPos = new ArrayList<Double>(),  chr12EndPos = new ArrayList<Double>(),  chr13EndPos = new ArrayList<Double>(),  chr14EndPos = new ArrayList<Double>(),  chr15EndPos = new ArrayList<Double>(),  chr16EndPos = new ArrayList<Double>(),  chr17EndPos = new ArrayList<Double>(),  chr18EndPos = new ArrayList<Double>(),  chr19EndPos = new ArrayList<Double>(),  chrXEndPos = new ArrayList<Double>(),  chrYEndPos = new ArrayList<Double>();
		List<String> chr1KGDetails = new ArrayList<String>(), chr2KGDetails = new ArrayList<String>(), chr3KGDetails = new ArrayList<String>(), chr4KGDetails = new ArrayList<String>(), chr5KGDetails = new ArrayList<String>(), chr6KGDetails = new ArrayList<String>(), chr7KGDetails = new ArrayList<String>(), chr8KGDetails = new ArrayList<String>(), chr9KGDetails = new ArrayList<String>(), chr10KGDetails = new ArrayList<String>(), chr11KGDetails = new ArrayList<String>(), chr12KGDetails = new ArrayList<String>(), chr13KGDetails = new ArrayList<String>(), chr14KGDetails = new ArrayList<String>(), chr15KGDetails = new ArrayList<String>(), chr16KGDetails = new ArrayList<String>(), chr17KGDetails = new ArrayList<String>(), chr18KGDetails = new ArrayList<String>(), chr19KGDetails = new ArrayList<String>(), chrXKGDetails = new ArrayList<String>(), chrYKGDetails = new ArrayList<String>();
		List<String> chr1geneSymbols = new ArrayList<String>(), chr2geneSymbols = new ArrayList<String>(), chr3geneSymbols = new ArrayList<String>(), chr4geneSymbols = new ArrayList<String>(), chr5geneSymbols = new ArrayList<String>(), chr6geneSymbols = new ArrayList<String>(), chr7geneSymbols = new ArrayList<String>(), chr8geneSymbols = new ArrayList<String>(), chr9geneSymbols = new ArrayList<String>(), chr10geneSymbols = new ArrayList<String>(), chr11geneSymbols = new ArrayList<String>(), chr12geneSymbols = new ArrayList<String>(), chr13geneSymbols = new ArrayList<String>(), chr14geneSymbols = new ArrayList<String>(), chr15geneSymbols = new ArrayList<String>(), chr16geneSymbols = new ArrayList<String>(), chr17geneSymbols = new ArrayList<String>(), chr18geneSymbols = new ArrayList<String>(), chr19geneSymbols = new ArrayList<String>(), chrXgeneSymbols = new ArrayList<String>(), chrYgeneSymbols = new ArrayList<String>();
		List<String> chr1arrayStringLocusID = new ArrayList<String>(), chr2arrayStringLocusID = new ArrayList<String>(), chr3arrayStringLocusID = new ArrayList<String>(), chr4arrayStringLocusID = new ArrayList<String>(), chr5arrayStringLocusID = new ArrayList<String>(), chr6arrayStringLocusID = new ArrayList<String>(), chr7arrayStringLocusID = new ArrayList<String>(), chr8arrayStringLocusID = new ArrayList<String>(), chr9arrayStringLocusID = new ArrayList<String>(), chr10arrayStringLocusID = new ArrayList<String>(), chr11arrayStringLocusID = new ArrayList<String>(), chr12arrayStringLocusID = new ArrayList<String>(), chr13arrayStringLocusID = new ArrayList<String>(), chr14arrayStringLocusID = new ArrayList<String>(), chr15arrayStringLocusID = new ArrayList<String>(), chr16arrayStringLocusID = new ArrayList<String>(), chr17arrayStringLocusID = new ArrayList<String>(), chr18arrayStringLocusID = new ArrayList<String>(), chr19arrayStringLocusID = new ArrayList<String>(), chrXarrayStringLocusID = new ArrayList<String>(), chrYarrayStringLocusID = new ArrayList<String>();
		
		Set<String> noDuplicate = new HashSet<String>();

		try {
			Iterator<KnownGene> kgListIt = selectedKnownGeneDetails.iterator();
			while (kgListIt.hasNext()) {
				KnownGene knownGene = kgListIt.next();
				double txStart = knownGene.getTxStartPosition();
				String chr = knownGene.getChromosome();				
				ChromosomeData chrData = new ChromosomeData();
				
		
				if (chr.equals(chromName[0])) {					
					getChromosomeImageCoordinates(chrData, 0);
					/*230*/
					int chrLowPos = Integer.parseInt(coordinates[0][3]);
					double geneLinePosChr = ((chrLowPos*txStart) / Double.parseDouble(chromLength[0]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[0]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chr1StartPos, 
							chr1EndPos, chr1KGDetails, chr1geneSymbols, chr1GenePos, chr1arrayStringLocusID);

				}				
				
				if (chr.equals(chromName[1])) {
					getChromosomeImageCoordinates(chrData, 1);
					int chrLowPos = Integer.parseInt(coordinates[1][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[1]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[1]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chr2StartPos, 
							chr2EndPos, chr2KGDetails,chr2geneSymbols, chr2GenePos, chr2arrayStringLocusID);

				}
				if (chr.equals(chromName[2])) {
					getChromosomeImageCoordinates(chrData, 2);
					int chrLowPos = Integer.parseInt(coordinates[2][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[2]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[2]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chr3StartPos, 
							chr3EndPos, chr3KGDetails, chr3geneSymbols, chr3GenePos, chr3arrayStringLocusID);

				}

				if (chr.equals(chromName[3])) {
					getChromosomeImageCoordinates(chrData, 3);
					int chrLowPos = Integer.parseInt(coordinates[3][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[3]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[3]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chr4StartPos, 
							chr4EndPos, chr4KGDetails, chr4geneSymbols,	chr4GenePos, chr4arrayStringLocusID);

				}
				if (chr.equals(chromName[4])) {
					getChromosomeImageCoordinates(chrData, 4);
					int chrLowPos = Integer.parseInt(coordinates[4][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[4]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[4]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chr5StartPos, 
							chr5EndPos, chr5KGDetails, chr5geneSymbols, chr5GenePos, chr5arrayStringLocusID);

				}
				if (chr.equals(chromName[5])) {
					getChromosomeImageCoordinates(chrData, 5);
					int chrLowPos = Integer.parseInt(coordinates[5][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[5]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[5]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chr6StartPos, 
							chr6EndPos, chr6KGDetails, chr6geneSymbols,	chr6GenePos, chr6arrayStringLocusID);

				}
				if (chr.equals(chromName[6])) {
					getChromosomeImageCoordinates(chrData, 6);
					int chrLowPos = Integer.parseInt(coordinates[6][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[6]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[6]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chr7StartPos, 
							chr7EndPos, chr7KGDetails, chr7geneSymbols, chr7GenePos, chr7arrayStringLocusID);

				}
				if (chr.equals(chromName[7])) {
					getChromosomeImageCoordinates(chrData, 7);
					int chrLowPos = Integer.parseInt(coordinates[7][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[7]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[7]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chr8StartPos, 
							chr8EndPos, chr8KGDetails, chr8geneSymbols, chr8GenePos, chr8arrayStringLocusID);

				}
				if (chr.equals(chromName[8])) {
					getChromosomeImageCoordinates(chrData, 8);
					int chrLowPos = Integer.parseInt(coordinates[8][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[8]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[8]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chr9StartPos, 
							chr9EndPos, chr9KGDetails, chr9geneSymbols, chr9GenePos, chr9arrayStringLocusID);

				}
				if (chr.equals(chromName[9])) {
					getChromosomeImageCoordinates(chrData, 9);
					int chrLowPos = Integer.parseInt(coordinates[9][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[9]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[9]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chr10StartPos, 
							chr10EndPos, chr10KGDetails, chr10geneSymbols, chr10GenePos, chr10arrayStringLocusID);

				}
				if (chr.equals(chromName[10])) {
					getChromosomeImageCoordinates(chrData, 10);
					int chrLowPos = Integer.parseInt(coordinates[10][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[10]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[10]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chr11StartPos, 
							chr11EndPos, chr11KGDetails, chr11geneSymbols, chr11GenePos, chr11arrayStringLocusID);

				}
				if (chr.equals(chromName[11])) {
					getChromosomeImageCoordinates(chrData, 11);
					int chrLowPos = Integer.parseInt(coordinates[11][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[11]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[11]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData,geneLinePosChr, chr12StartPos, 
							chr12EndPos, chr12KGDetails,	chr12geneSymbols, chr12GenePos, chr12arrayStringLocusID);

				}
				if (chr.equals(chromName[12])) {
					getChromosomeImageCoordinates(chrData, 12);
					int chrLowPos = Integer.parseInt(coordinates[12][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[12]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[12]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chr13StartPos, 
							chr13EndPos, chr13KGDetails, chr13geneSymbols, chr13GenePos, chr13arrayStringLocusID);

				}
				if (chr.equals(chromName[13])) {
					getChromosomeImageCoordinates(chrData, 13);
					int chrLowPos = Integer.parseInt(coordinates[13][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[13]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[13]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData,  geneLinePosChr, chr14StartPos, 
							chr14EndPos, chr14KGDetails,chr14geneSymbols, chr14GenePos, chr14arrayStringLocusID);

				}
				if (chr.equals(chromName[14])) {
					getChromosomeImageCoordinates(chrData, 14);
					int chrLowPos = Integer.parseInt(coordinates[14][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[14]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[14]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chr15StartPos, 
							chr15EndPos, chr15KGDetails,chr15geneSymbols, chr15GenePos, chr15arrayStringLocusID);
				}

				if (chr.equals(chromName[15])) {
					getChromosomeImageCoordinates(chrData, 15);
					int chrLowPos = Integer.parseInt(coordinates[15][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[15]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[15]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chr16StartPos, 
							chr16EndPos, chr16KGDetails,chr16geneSymbols, chr16GenePos, chr16arrayStringLocusID);

				}
				if (chr.equals(chromName[16])) {
					getChromosomeImageCoordinates(chrData, 16);
					int chrLowPos = Integer.parseInt(coordinates[16][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[16]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[16]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chr17StartPos, 
							chr17EndPos, chr17KGDetails,chr17geneSymbols, chr17GenePos, chr17arrayStringLocusID);

				}
				if (chr.equals(chromName[17])) {
					getChromosomeImageCoordinates(chrData, 17);
					int chrLowPos = Integer.parseInt(coordinates[17][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[17]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[17]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chr18StartPos,
							chr18EndPos, chr18KGDetails,chr18geneSymbols, chr18GenePos, chr18arrayStringLocusID);

				}
				if (chr.equals(chromName[18])) {
					getChromosomeImageCoordinates(chrData, 18);
					int chrLowPos = Integer.parseInt(coordinates[18][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[18]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[18]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chr19StartPos, 
							chr19EndPos, chr19KGDetails,chr19geneSymbols, chr19GenePos, chr19arrayStringLocusID);

				}
				if (chr.equals(chromName[19])) {
					getChromosomeImageCoordinates(chrData, 19);
					int chrLowPos = Integer.parseInt(coordinates[19][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[19]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[19]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr, chrXStartPos, 
							chrXEndPos, chrXKGDetails, chrXgeneSymbols, chrXGenePos, chrXarrayStringLocusID);

				}				
				if (chr.equals(chromName[20])) {
					getChromosomeImageCoordinates(chrData, 20);
					int chrLowPos = Integer.parseInt(coordinates[20][3]);
					double geneLinePosChr = ((chrLowPos * txStart) / Double.parseDouble(chromLength[20]));
					//int chromYPos = Integer.parseInt(chromYaxisPos[20]);
					addKnownGeneAndUserDataAsChromosomeData(knownGene, fileData, chrData, geneLinePosChr,chrYStartPos,
							chrYEndPos, chrYKGDetails, chrYgeneSymbols, chrYGenePos, chrYarrayStringLocusID);
				}
				
				if (noDuplicate.add(chr)) {
					//chrData.setChromosomeXPos(xPosition);
					//xPosition += 50;
					chrDataList.add(chrData);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		DrawChromosome draw = new DrawChromosome();
		draw.drawChromosome(chrDataList, fileData);
		//System.out.println("Size " + chrDataList.size());
		return (JPanel) draw;
	}

	private void addKnownGeneAndUserDataAsChromosomeData(KnownGene kGene,
			ReadFileData fileData, ChromosomeData chrData, double linePos,
			List<Double> startPosList, List<Double> endPosList,
			List<String> kgDetailList, List<String> geneSymbolList,
			List<Double> chrGenePosList, List<String> arrayStringLocusIDList) {		
		
		//+ ";" + kGene.getGeneSymbol()+ ";" + kGene.getRefSeqID() + ";" + kGene.getLocusID() + ";"+ kGene.getSpID();
		String knownGeneID = kGene.getKnownGeneID();
		String knownGeneChr = kGene.getChromosome();
		String knownGeneRefSeqID = kGene.getRefSeqID();
		String knownGeneLocusID = kGene.getLocusID();
		String knownGeneSymbol = kGene.getGeneSymbol();
		String knownGeneSpID = kGene.getSpID();
		
		double knownGeneTxStart = kGene.getTxStartPosition();
		double knownGeneTxEnd = kGene.getTxEndPosition();

		kgDetailList.add(knownGeneID);
		kgDetailList.add(knownGeneSymbol);
		kgDetailList.add(knownGeneRefSeqID);
		kgDetailList.add(knownGeneLocusID);
		kgDetailList.add(knownGeneSpID);
		
		geneSymbolList.add(knownGeneSymbol);
		startPosList.add(knownGeneTxStart);
		endPosList.add(knownGeneTxEnd);
		chrGenePosList.add(linePos);
		arrayStringLocusIDList.add(knownGeneLocusID);

		chrData.setGeneName(kgDetailList);
		chrData.setGeneSymbolList(geneSymbolList);
		chrData.setTxStart(startPosList);
		chrData.setTxEnd(endPosList);
		chrData.setLinePos(chrGenePosList);
		chrData.setLocusID(arrayStringLocusIDList);
		
		chrData.setChromosomeName(knownGeneChr);
		//chrData.setChromosomeYPos(yChromPosition);
		//chrData.setUserData(fileData);
	}

	public static List<ChromosomeData> getProteinList() {
		return chrDataList;
	}
}
