package edu.ucsd.genome.chromviz.testExamples;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.ucsd.genome.chromviz.sigData.ChromosomeData;
import edu.ucsd.genome.chromviz.sigData.KnownGene;
import edu.ucsd.genome.chromviz.sigDrawChrom.DrawChromosome;
import edu.ucsd.genome.chromviz.sigSql.KnownGeneListDAO;

/**
 * 
 * @author srujana
 * 
 */
public class KnownGeneCgiTable extends JFrame {
	static JScrollPane _scrollPane1;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static JTable getKnownGeneCgiTable() throws Exception {
		List<KnownGene> kgList = KnownGeneListDAO.getAllKnownGenes();

		int total = kgList.size();

		Object data[][] = new Object[total][9];
		String headers[] = { "Known Gene Name", "Chromosome", "Strand",
				"Protein ID", "TransStart", "TransEnd", "CDS Start", "CDS End",
				"exon Count" };

		if (kgList != null && kgList.size() > 0) {
			int i = 0;
			Iterator<KnownGene> it = kgList.iterator();
			while (it.hasNext()) {
				KnownGene kg = (KnownGene) it.next();
				data[i][0] = kg.getKnownGeneID();
				data[i][1] = kg.getChromosome();
				// data[i][2] = kg.getStrand();
				data[i][3] = kg.getSpID();
				data[i][4] = kg.getTxStartPosition();
				data[i][5] = kg.getTxEndPosition();
				data[i][6] = kg.getCdsStartPosition();
				data[i][7] = kg.getCdsEndPosition();
				data[i][8] = kg.getExonCount();
				i++;
			}
		}
		return new JTable(new DefaultTableModel(data, headers));
	}

	public static JTable getKnownGeneCgiTable(String chr1) throws Exception {
		// List<KnownGene> kgList = KnownGeneList.getAllKnownGenes();

		// String chrom = chr1;
		List<KnownGene> kgList = KnownGeneListDAO.getKnownChromGene(chr1);

		int total = kgList.size();

		Object data[][] = new Object[total][10];
		String headers[] = { "Known Gene Name", "Chromosome", "Strand",
				"Protein ID", "TransStart", "TransEnd", "CDS Start", "CDS End",
				"exon Count", "Symbol" };

		if (kgList != null && kgList.size() > 0) {
			int i = 0;
			Iterator<KnownGene> it = kgList.iterator();
			while (it.hasNext()) {
				KnownGene kg = (KnownGene) it.next();
				data[i][0] = kg.getKnownGeneID();
				data[i][1] = kg.getChromosome();
				data[i][2] = kg.getStrand();
				data[i][3] = kg.getSpID();
				data[i][4] = kg.getTxStartPosition();
				data[i][5] = kg.getTxEndPosition();
				data[i][6] = kg.getCdsStartPosition();
				data[i][7] = kg.getCdsEndPosition();
				data[i][8] = kg.getExonCount();
				data[i][9] = kg.getGeneSymbol();
				i++;
			}
		}
		return new JTable(new DefaultTableModel(data, headers));
	}

	public static JTable getKnownGeneTable(String kgname) throws Exception {

		// List<KnownGene> kgList = KnownGeneList.getAllKnownGenes();

		// String chrom = chr1;
		List<KnownGene> kgList = KnownGeneListDAO.getKnownGeneSequence(kgname);

		int total = kgList.size();

		Object data[][] = new Object[total][9];
		String headers[] = { "Known Gene Name", "Chromosome", "Strand",
				"Protein ID", "TransStart", "TransEnd", "CDS Start", "CDS End",
				"exon Count" };

		if (kgList != null && kgList.size() > 0) {
			int i = 0;
			Iterator<KnownGene> it = kgList.iterator();
			while (it.hasNext()) {
				KnownGene kg = (KnownGene) it.next();
				data[i][0] = kg.getKnownGeneID();
				data[i][1] = kg.getChromosome();
				data[i][2] = kg.getStrand();
				data[i][3] = kg.getSpID();
				data[i][4] = kg.getTxStartPosition();
				data[i][5] = kg.getTxEndPosition();
				data[i][6] = kg.getCdsStartPosition();
				data[i][7] = kg.getCdsEndPosition();
				data[i][8] = kg.getExonCount();
				i++;
			}
		}
		return new JTable(new DefaultTableModel(data, headers));
	}

	/**
	 * 
	 * @param kgname
	 * @return
	 */
	

	
	public static JTable getKnownGeneCgiTable(String chrom, String col1,
			String col2, String col3, String col4, String col5, String col6,
			String col7, String col8, String col9) throws Exception {
		List<KnownGene> kgList = KnownGeneListDAO.getKnownChromGene(chrom);
		int total = kgList.size();
		Object data[][] = new Object[total][9];
		String headers[] = { "Known Gene Name", "Chromosome", "Protein ID",
				"TransStart", "TransEnd", "CDS Start", "CDS End", "exon Count",
				"Symbol" };

		if (kgList != null && kgList.size() > 0) {
			int i = 0;
			Iterator<KnownGene> it = kgList.iterator();
			while (it.hasNext()) {
				KnownGene kg = (KnownGene) it.next();
				if (col1 != null) {
					data[i][0] = kg.getKnownGeneID();
				}
				if (col2 != null) {
					data[i][1] = kg.getChromosome();
				}
				if (col3 != null) {
					data[i][2] = kg.getSpID();
				}
				if (col4 != null) {
					data[i][3] = kg.getTxStartPosition();
				}
				if (col5 != null) {
					data[i][4] = kg.getTxEndPosition();
				}
				if (col6 != null) {
					data[i][5] = kg.getCdsStartPosition();
				}
				if (col7 != null) {
					data[i][6] = kg.getCdsEndPosition();
				}
				if (col8 != null) {
					data[i][7] = kg.getExonCount();
				}
				if (col9 != null) {
					data[i][8] = kg.getGeneSymbol();
				}
				i++;
			}
		}
		return new JTable(new DefaultTableModel(data, headers));
	}

	public static ChromosomeData getChromosome1Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(11, 12, 23, 232);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome2Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(51, 28, 23, 216);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome3Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(92, 52, 23, 193);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome4Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(133, 59, 23, 186);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome5Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(174, 63, 26, 182);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome6Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(216, 64, 26, 182);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome7Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(258, 84, 26, 162);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome8Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(300, 89, 26, 157);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome9Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(344, 93, 25, 153);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome10Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(386, 88, 26, 158);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome11Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(428, 96, 25, 150);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome12Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(10, 330, 26, 139);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome13Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(50, 329, 26, 140);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome14Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(90, 328, 26, 141);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome15Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(130, 343, 26, 126);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome16Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(174, 345, 26, 124);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome17Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(216, 355, 26, 114);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome18Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(257, 358, 26, 111);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosome19Data() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(300, 393, 26, 76);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosomeXData() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(344, 273, 26, 196);
		data.setRectangle(rect);
		return data;
	}

	public static ChromosomeData getChromosomeYData() {
		ChromosomeData data = new ChromosomeData();
		final Rectangle rect = new Rectangle(384, 410, 26, 59);
		data.setRectangle(rect);
		return data;
	}

	public static JPanel getChromosomeIdeogram(Object[] dataValues) {
		List<ChromosomeData> chrDataList = new ArrayList<ChromosomeData>();
		int i = 10;
		List<Double> chr1GenePos = new ArrayList<Double>();
		List<Double> chr2GenePos = new ArrayList<Double>();
		List<Double> chr3GenePos = new ArrayList<Double>();
		List<Double> chr4GenePos = new ArrayList<Double>();
		List<Double> chr5GenePos = new ArrayList<Double>();
		List<Double> chr6GenePos = new ArrayList<Double>();
		List<Double> chr7GenePos = new ArrayList<Double>();
		List<Double> chr8GenePos = new ArrayList<Double>();
		List<Double> chr9GenePos = new ArrayList<Double>();
		List<Double> chr10GenePos = new ArrayList<Double>();
		List<Double> chr11GenePos = new ArrayList<Double>();
		List<Double> chr12GenePos = new ArrayList<Double>();
		List<Double> chr13GenePos = new ArrayList<Double>();
		List<Double> chr14GenePos = new ArrayList<Double>();
		List<Double> chr15GenePos = new ArrayList<Double>();
		List<Double> chr16GenePos = new ArrayList<Double>();
		List<Double> chr17GenePos = new ArrayList<Double>();
		List<Double> chr18GenePos = new ArrayList<Double>();
		List<Double> chr19GenePos = new ArrayList<Double>();
		List<Double> chrXGenePos = new ArrayList<Double>();
		List<Double> chrYGenePos = new ArrayList<Double>();

		List<Double> startPos1 = new ArrayList<Double>();
		List<Double> endPos1 = new ArrayList<Double>();
		List<Double> startPos2 = new ArrayList<Double>();
		List<Double> endPos2 = new ArrayList<Double>();
		List<Double> startPos3 = new ArrayList<Double>();
		List<Double> endPos3 = new ArrayList<Double>();
		List<Double> startPos4 = new ArrayList<Double>();
		List<Double> endPos4 = new ArrayList<Double>();
		List<Double> startPos5 = new ArrayList<Double>();
		List<Double> endPos5 = new ArrayList<Double>();
		List<Double> startPos6 = new ArrayList<Double>();
		List<Double> endPos6 = new ArrayList<Double>();
		List<Double> startPos7 = new ArrayList<Double>();
		List<Double> endPos7 = new ArrayList<Double>();
		List<Double> startPos8 = new ArrayList<Double>();
		List<Double> endPos8 = new ArrayList<Double>();
		List<Double> startPos9 = new ArrayList<Double>();
		List<Double> endPos9 = new ArrayList<Double>();
		List<Double> startPos10 = new ArrayList<Double>();
		List<Double> endPos10 = new ArrayList<Double>();
		List<Double> startPos11 = new ArrayList<Double>();
		List<Double> endPos11 = new ArrayList<Double>();
		List<Double> startPos12 = new ArrayList<Double>();
		List<Double> endPos12 = new ArrayList<Double>();
		List<Double> startPos13 = new ArrayList<Double>();
		List<Double> endPos13 = new ArrayList<Double>();
		List<Double> startPos14 = new ArrayList<Double>();
		List<Double> endPos14 = new ArrayList<Double>();
		List<Double> startPos15 = new ArrayList<Double>();
		List<Double> endPos15 = new ArrayList<Double>();
		List<Double> startPos16 = new ArrayList<Double>();
		List<Double> endPos16 = new ArrayList<Double>();
		List<Double> startPos17 = new ArrayList<Double>();
		List<Double> endPos17 = new ArrayList<Double>();
		List<Double> startPos18 = new ArrayList<Double>();
		List<Double> endPos18 = new ArrayList<Double>();
		List<Double> startPos19 = new ArrayList<Double>();
		List<Double> endPos19 = new ArrayList<Double>();
		List<Double> startPosX = new ArrayList<Double>();
		List<Double> endPosX = new ArrayList<Double>();
		List<Double> startPosY = new ArrayList<Double>();
		List<Double> endPosY = new ArrayList<Double>();

		List<String> geneNames1 = new ArrayList<String>();
		List<String> geneNames2 = new ArrayList<String>();
		List<String> geneNames3 = new ArrayList<String>();
		List<String> geneNames4 = new ArrayList<String>();
		List<String> geneNames5 = new ArrayList<String>();
		List<String> geneNames6 = new ArrayList<String>();
		List<String> geneNames7 = new ArrayList<String>();
		List<String> geneNames8 = new ArrayList<String>();
		List<String> geneNames9 = new ArrayList<String>();
		List<String> geneNames10 = new ArrayList<String>();
		List<String> geneNames11 = new ArrayList<String>();
		List<String> geneNames12 = new ArrayList<String>();
		List<String> geneNames13 = new ArrayList<String>();
		List<String> geneNames14 = new ArrayList<String>();
		List<String> geneNames15 = new ArrayList<String>();
		List<String> geneNames16 = new ArrayList<String>();
		List<String> geneNames17 = new ArrayList<String>();
		List<String> geneNames18 = new ArrayList<String>();
		List<String> geneNames19 = new ArrayList<String>();
		List<String> geneNamesX = new ArrayList<String>();
		List<String> geneNamesY = new ArrayList<String>();

		List<String> geneSymbols1 = new ArrayList<String>();
		List<String> geneSymbols2 = new ArrayList<String>();
		List<String> geneSymbols3 = new ArrayList<String>();
		List<String> geneSymbols4 = new ArrayList<String>();
		List<String> geneSymbols5 = new ArrayList<String>();
		List<String> geneSymbols6 = new ArrayList<String>();
		List<String> geneSymbols7 = new ArrayList<String>();
		List<String> geneSymbols8 = new ArrayList<String>();
		List<String> geneSymbols9 = new ArrayList<String>();
		List<String> geneSymbols10 = new ArrayList<String>();
		List<String> geneSymbols11 = new ArrayList<String>();
		List<String> geneSymbols12 = new ArrayList<String>();
		List<String> geneSymbols13 = new ArrayList<String>();
		List<String> geneSymbols14 = new ArrayList<String>();
		List<String> geneSymbols15 = new ArrayList<String>();
		List<String> geneSymbols16 = new ArrayList<String>();
		List<String> geneSymbols17 = new ArrayList<String>();
		List<String> geneSymbols18 = new ArrayList<String>();
		List<String> geneSymbols19 = new ArrayList<String>();
		List<String> geneSymbolsX = new ArrayList<String>();
		List<String> geneSymbolsY = new ArrayList<String>();

		Set<String> s = new HashSet<String>();
		DrawChromosome dc = new DrawChromosome();
		for (int j = 0; j < dataValues.length; j++) {
			String kgname = (String) dataValues[j];
			try {
				List<KnownGene> kgList = KnownGeneListDAO
						.getKnownGeneSequence(kgname);
				System.out.println("kgList size for "+kgname +" ==>"+kgList.size());
				KnownGene kg = (KnownGene) kgList.get(0);
				String geneName = kg.getKnownGeneID();
				String chr = kg.getChromosome();
				double txStart = kg.getTxStartPosition();
				double txEnd = kg.getTxEndPosition();
				String geneSymbol = kg.getGeneSymbol();
System.out.println("Finally geneName==>"+geneName+" txStart == >"+txStart);
				double lengthChr1 = 197195432;
				double lengthChr2 = 181748087;
				double lengthChr3 = 159599783;
				double lengthChr4 = 155630120;
				double lengthChr5 = 152537259;
				double lengthChr6 = 149517037;
				double lengthChr7 = 152524553;
				double lengthChr8 = 131738871;
				double lengthChr9 = 124076172;
				double lengthChr10 = 129993255;
				double lengthChr11 = 121843856;
				double lengthChr12 = 121257530;
				double lengthChr13 = 120284312;
				double lengthChr14 = 125194864;
				double lengthChr15 = 103494974;
				double lengthChr16 = 98319150;
				double lengthChr17 = 95272651;
				double lengthChr18 = 90772031;
				double lengthChr19 = 61342430;
				double lengthChrX = 166650296;
				double lengthChrY = 15902555;

				if (chr.equals("chr1")) {
					startPos1.add(txStart);
					endPos1.add(txEnd);
					geneNames1.add(geneName);
					geneSymbols1.add(geneSymbol);
					double linePos = ((232 * txStart) / lengthChr1);
					chr1GenePos.add(linePos);
					ChromosomeData chrData1 = getChromosome1Data();
					chrData1.setTxStart(startPos1);
					chrData1.setTxEnd(endPos1);
					chrData1.setLinePos(chr1GenePos);
					chrData1.setGeneName(geneNames1);
					chrData1.setGeneSymbolList(geneSymbols1);
					if (s.add(chr)) {
						chrData1.setChromosomeName(chr);
						chrData1.setChromosomeXPos(i);
						chrData1.setChromosomeYPos(12);
						chrDataList.add(chrData1);
						i += 35;
					}
				}
				if (chr.equals("chr2")) {
					startPos2.add(txStart);
					endPos2.add(txEnd);
					double linePos2 = ((216 * txStart) / lengthChr2);
					chr2GenePos.add(linePos2);
					ChromosomeData chrData2 = getChromosome2Data();
					chrData2.setTxStart(startPos2);
					chrData2.setTxEnd(endPos2);
					chrData2.setLinePos(chr2GenePos);
					geneNames2.add(geneName);
					geneSymbols2.add(geneSymbol);
					chrData2.setGeneName(geneNames2);
					chrData2.setGeneSymbolList(geneSymbols2);
					if (s.add(chr)) {
						chrData2.setChromosomeName(chr);
						chrData2.setChromosomeXPos(i);
						chrData2.setChromosomeYPos(28);
						chrDataList.add(chrData2);
						i += 35;
					}
				}
				if (chr.equals("chr3")) {
					double linePos3 = ((193 * txStart) / lengthChr3);
					chr3GenePos.add(linePos3);
					ChromosomeData chrData3 = getChromosome3Data();
					startPos3.add(txStart);
					endPos3.add(txEnd);
					chrData3.setTxStart(startPos3);
					chrData3.setTxEnd(endPos3);
					chrData3.setLinePos(chr3GenePos);
					geneNames3.add(geneName);
					chrData3.setGeneName(geneNames3);
					geneSymbols3.add(geneSymbol);
					chrData3.setGeneSymbolList(geneSymbols3);
					if (s.add(chr)) {
						chrData3.setChromosomeName(chr);
						chrData3.setChromosomeXPos(i);
						chrData3.setChromosomeYPos(52);
						chrDataList.add(chrData3);
						i += 35;
					}
				}

				if (chr.equals("chr4")) {
					double linePos4 = ((186 * txStart) / lengthChr4);
					chr4GenePos.add(linePos4);
					ChromosomeData chrData4 = getChromosome4Data();
					chrData4.setLinePos(chr4GenePos);
					startPos4.add(txStart);
					endPos4.add(txEnd);
					chrData4.setTxStart(startPos4);
					chrData4.setTxEnd(endPos4);
					geneNames4.add(geneName);
					chrData4.setGeneName(geneNames4);
					geneSymbols4.add(geneSymbol);
					chrData4.setGeneSymbolList(geneSymbols4);
					if (s.add(chr)) {
						chrData4.setChromosomeName(chr);
						chrData4.setChromosomeXPos(i);
						chrData4.setChromosomeYPos(59);
						chrDataList.add(chrData4);
						i += 35;
					}
				}
				if (chr.equals("chr5")) {
					double linePos5 = ((182 * txStart) / lengthChr5);
					chr5GenePos.add(linePos5);
					ChromosomeData chrData5 = getChromosome5Data();
					chrData5.setLinePos(chr5GenePos);
					startPos5.add(txStart);
					endPos5.add(txEnd);
					chrData5.setTxStart(startPos5);
					chrData5.setTxEnd(endPos5);
					geneNames5.add(geneName);
					chrData5.setGeneName(geneNames5);
					geneSymbols5.add(geneSymbol);
					chrData5.setGeneSymbolList(geneSymbols5);
					if (s.add(chr)) {
						chrData5.setChromosomeName(chr);
						chrData5.setChromosomeXPos(i);
						chrData5.setChromosomeYPos(63);
						chrDataList.add(chrData5);
						i += 35;
					}
				}
				if (chr.equals("chr6")) {
					double linePos6 = ((182 * txStart) / lengthChr6);
					chr6GenePos.add(linePos6);
					ChromosomeData chrData6 = getChromosome6Data();
					chrData6.setLinePos(chr6GenePos);
					startPos6.add(txStart);
					endPos6.add(txEnd);
					chrData6.setTxStart(startPos6);
					chrData6.setTxEnd(endPos6);
					geneNames6.add(geneName);
					chrData6.setGeneName(geneNames6);
					geneSymbols6.add(geneSymbol);
					chrData6.setGeneSymbolList(geneSymbols6);
					if (s.add(chr)) {
						chrData6.setChromosomeName(chr);
						chrData6.setChromosomeXPos(i);
						chrData6.setChromosomeYPos(64);
						chrDataList.add(chrData6);
						i += 35;
					}
				}
				if (chr.equals("chr7")) {
					double linePos7 = ((162 * txStart) / lengthChr7);
					chr7GenePos.add(linePos7);
					ChromosomeData chrData7 = getChromosome7Data();
					chrData7.setLinePos(chr7GenePos);
					startPos7.add(txStart);
					endPos7.add(txEnd);
					chrData7.setTxStart(startPos7);
					chrData7.setTxEnd(endPos7);
					geneNames7.add(geneName);
					chrData7.setGeneName(geneNames7);
					geneSymbols7.add(geneSymbol);
					chrData7.setGeneSymbolList(geneSymbols7);
					if (s.add(chr)) {
						chrData7.setChromosomeName(chr);
						chrData7.setChromosomeXPos(i);
						chrData7.setChromosomeYPos(84);
						chrDataList.add(chrData7);
						i += 35;
					}
				}
				if (chr.equals("chr8")) {
					double linePos8 = ((157 * txStart) / lengthChr8);
					chr8GenePos.add(linePos8);
					ChromosomeData chrData8 = getChromosome8Data();
					chrData8.setLinePos(chr8GenePos);
					startPos8.add(txStart);
					endPos8.add(txEnd);
					chrData8.setTxStart(startPos8);
					chrData8.setTxEnd(endPos8);
					geneNames8.add(geneName);
					chrData8.setGeneName(geneNames8);
					geneSymbols8.add(geneSymbol);
					chrData8.setGeneSymbolList(geneSymbols8);
					if (s.add(chr)) {
						chrData8.setChromosomeName(chr);
						chrData8.setChromosomeXPos(i);
						chrData8.setChromosomeYPos(89);
						chrDataList.add(chrData8);
						i += 35;
					}
				}
				if (chr.equals("chr9")) {
					double linePos9 = ((153 * txStart) / lengthChr9);
					chr9GenePos.add(linePos9);
					ChromosomeData chrData9 = getChromosome9Data();
					chrData9.setLinePos(chr9GenePos);
					startPos9.add(txStart);
					endPos9.add(txEnd);
					chrData9.setTxStart(startPos9);
					chrData9.setTxEnd(endPos9);
					geneNames9.add(geneName);
					chrData9.setGeneName(geneNames9);
					geneSymbols9.add(geneSymbol);
					chrData9.setGeneSymbolList(geneSymbols9);
					if (s.add(chr)) {
						chrData9.setChromosomeName(chr);
						chrData9.setChromosomeXPos(i);
						chrData9.setChromosomeYPos(93);
						chrDataList.add(chrData9);
						i += 35;
					}
				}
				if (chr.equals("chr10")) {
					double linePos10 = ((158 * txStart) / lengthChr10);
					chr10GenePos.add(linePos10);
					ChromosomeData chrData10 = getChromosome10Data();
					chrData10.setLinePos(chr10GenePos);
					startPos10.add(txStart);
					endPos10.add(txEnd);
					chrData10.setTxStart(startPos10);
					chrData10.setTxEnd(endPos10);
					geneNames10.add(geneName);
					chrData10.setGeneName(geneNames10);
					geneSymbols10.add(geneSymbol);
					chrData10.setGeneSymbolList(geneSymbols10);
					if (s.add(chr)) {
						chrData10.setChromosomeName(chr);
						chrData10.setChromosomeXPos(i);
						chrData10.setChromosomeYPos(88);
						chrDataList.add(chrData10);
						i += 35;
					}
				}
				if (chr.equals("chr11")) {
					double linePos11 = ((150 * txStart) / lengthChr11);
					chr11GenePos.add(linePos11);
					ChromosomeData chrData11 = getChromosome11Data();
					chrData11.setLinePos(chr11GenePos);
					startPos11.add(txStart);
					endPos11.add(txEnd);
					chrData11.setTxStart(startPos11);
					chrData11.setTxEnd(endPos11);
					geneNames11.add(geneName);
					chrData11.setGeneName(geneNames11);
					geneSymbols11.add(geneSymbol);
					chrData11.setGeneSymbolList(geneSymbols11);
					if (s.add(chr)) {
						chrData11.setChromosomeName(chr);
						chrData11.setChromosomeXPos(i);
						chrData11.setChromosomeYPos(96);
						chrDataList.add(chrData11);
						i += 35;
					}
				}
				if (chr.equals("chr12")) {
					double linePos12 = ((139 * txStart) / lengthChr12);
					chr12GenePos.add(linePos12);
					ChromosomeData chrData12 = getChromosome12Data();
					chrData12.setLinePos(chr12GenePos);
					startPos12.add(txStart);
					endPos12.add(txEnd);
					chrData12.setTxStart(startPos12);
					chrData12.setTxEnd(endPos12);
					geneNames12.add(geneName);
					chrData12.setGeneName(geneNames12);
					geneSymbols12.add(geneSymbol);
					chrData12.setGeneSymbolList(geneSymbols12);
					if (s.add(chr)) {
						chrData12.setChromosomeName(chr);
						chrData12.setChromosomeXPos(i);
						chrData12.setChromosomeYPos(105);
						chrDataList.add(chrData12);
						i += 35;
					}
				}
				if (chr.equals("chr13")) {
					double linePos13 = ((140 * txStart) / lengthChr13);
					chr13GenePos.add(linePos13);
					ChromosomeData chrData13 = getChromosome13Data();
					chrData13.setLinePos(chr13GenePos);
					startPos13.add(txStart);
					endPos13.add(txEnd);
					chrData13.setTxStart(startPos13);
					chrData13.setTxEnd(endPos13);
					geneNames13.add(geneName);
					chrData13.setGeneName(geneNames13);
					geneSymbols13.add(geneSymbol);
					chrData13.setGeneSymbolList(geneSymbols13);
					if (s.add(chr)) {
						chrData13.setChromosomeName(chr);
						chrData13.setChromosomeXPos(i);
						chrData13.setChromosomeYPos(104);
						chrDataList.add(chrData13);
						i += 35;
					}
				}
				if (chr.equals("chr14")) {
					double linePos14 = ((141 * txStart) / lengthChr14);
					chr14GenePos.add(linePos14);
					ChromosomeData chrData14 = getChromosome14Data();
					chrData14.setLinePos(chr14GenePos);
					startPos14.add(txStart);
					endPos14.add(txEnd);
					chrData14.setTxStart(startPos14);
					chrData14.setTxEnd(endPos14);
					geneNames14.add(geneName);
					chrData14.setGeneName(geneNames14);
					geneSymbols14.add(geneSymbol);
					chrData14.setGeneSymbolList(geneSymbols14);
					if (s.add(chr)) {
						chrData14.setChromosomeName(chr);
						chrData14.setChromosomeXPos(i);
						chrData14.setChromosomeYPos(103);
						chrDataList.add(chrData14);
						i += 35;
					}
				}
				if (chr.equals("chr15")) {
					double linePos15 = ((126 * txStart) / lengthChr15);
					chr15GenePos.add(linePos15);
					ChromosomeData chrData15 = getChromosome15Data();
					chrData15.setLinePos(chr15GenePos);
					startPos15.add(txStart);
					endPos15.add(txEnd);
					chrData15.setTxStart(startPos15);
					chrData15.setTxEnd(endPos15);
					geneNames15.add(geneName);
					chrData15.setGeneName(geneNames15);
					geneSymbols15.add(geneSymbol);
					chrData15.setGeneSymbolList(geneSymbols15);
					if (s.add(chr)) {
						chrData15.setChromosomeName(chr);
						chrData15.setChromosomeXPos(i);
						chrData15.setChromosomeYPos(118);
						chrDataList.add(chrData15);
						i += 35;
					}
				}
				if (chr.equals("chr16")) {
					double linePos16 = ((124 * txStart) / lengthChr16);
					chr16GenePos.add(linePos16);
					ChromosomeData chrData16 = getChromosome16Data();
					chrData16.setLinePos(chr16GenePos);
					startPos16.add(txStart);
					endPos16.add(txEnd);
					chrData16.setTxStart(startPos16);
					chrData16.setTxEnd(endPos16);
					geneNames16.add(geneName);
					chrData16.setGeneName(geneNames16);
					geneSymbols16.add(geneSymbol);
					chrData16.setGeneSymbolList(geneSymbols16);
					if (s.add(chr)) {
						chrData16.setChromosomeName(chr);
						chrData16.setChromosomeXPos(i);
						chrData16.setChromosomeYPos(120);
						chrDataList.add(chrData16);
						i += 35;
					}
				}
				if (chr.equals("chr17")) {
					double linePos17 = ((114 * txStart) / lengthChr17);
					chr17GenePos.add(linePos17);
					ChromosomeData chrData17 = getChromosome17Data();
					chrData17.setLinePos(chr17GenePos);
					startPos17.add(txStart);
					endPos17.add(txEnd);
					chrData17.setTxStart(startPos17);
					chrData17.setTxEnd(endPos17);
					geneNames17.add(geneName);
					chrData17.setGeneName(geneNames17);
					geneSymbols17.add(geneSymbol);
					chrData17.setGeneSymbolList(geneSymbols17);
					if (s.add(chr)) {
						chrData17.setChromosomeName(chr);
						chrData17.setChromosomeXPos(i);
						chrData17.setChromosomeYPos(130);
						chrDataList.add(chrData17);
						i += 35;
					}
				}
				if (chr.equals("chr18")) {
					double linePos18 = ((111 * txStart) / lengthChr18);
					chr18GenePos.add(linePos18);
					ChromosomeData chrData18 = getChromosome18Data();
					chrData18.setLinePos(chr18GenePos);
					startPos18.add(txStart);
					endPos18.add(txEnd);
					chrData18.setTxStart(startPos18);
					chrData18.setTxEnd(endPos18);
					geneNames18.add(geneName);
					chrData18.setGeneName(geneNames18);
					geneSymbols18.add(geneSymbol);
					chrData18.setGeneSymbolList(geneSymbols18);
					if (s.add(chr)) {
						chrData18.setChromosomeName(chr);
						chrData18.setChromosomeXPos(i);
						chrData18.setChromosomeYPos(133);
						chrDataList.add(chrData18);
						i += 35;
					}
				}
				if (chr.equals("chr19")) {
					double linePos19 = ((76 * txStart) / lengthChr19);
					chr19GenePos.add(linePos19);
					ChromosomeData chrData19 = getChromosome19Data();
					chrData19.setLinePos(chr19GenePos);
					startPos19.add(txStart);
					endPos19.add(txEnd);
					chrData19.setTxStart(startPos19);
					chrData19.setTxEnd(endPos19);
					geneNames19.add(geneName);
					chrData19.setGeneName(geneNames19);
					geneSymbols19.add(geneSymbol);
					chrData19.setGeneSymbolList(geneSymbols19);
					if (s.add(chr)) {
						chrData19.setChromosomeName(chr);
						chrData19.setChromosomeXPos(i);
						chrData19.setChromosomeYPos(168);
						chrDataList.add(chrData19);
						i += 35;
					}
				}
				if (chr.equals("chrX")) {
					double linePosX = ((196 * txStart) / lengthChrX);
					chrXGenePos.add(linePosX);
					ChromosomeData chrDataX = getChromosomeXData();
					chrDataX.setLinePos(chrXGenePos);
					startPosX.add(txStart);
					endPosX.add(txEnd);
					chrDataX.setTxStart(startPosX);
					chrDataX.setTxEnd(endPosX);
					geneNamesX.add(geneName);
					chrDataX.setGeneName(geneNamesX);
					geneSymbolsX.add(geneSymbol);
					chrDataX.setGeneSymbolList(geneSymbolsX);
					if (s.add(chr)) {
						chrDataX.setChromosomeName(chr);
						chrDataX.setChromosomeXPos(i);
						chrDataX.setChromosomeYPos(48);
						chrDataList.add(chrDataX);
						i += 35;
					}
				}
				if (chr.equals("chrY")) {
					double linePosY = ((59 * txStart) / lengthChrY);
					chrYGenePos.add(linePosY);
					ChromosomeData chrDataY = getChromosomeYData();
					chrDataY.setLinePos(chrYGenePos);
					startPosY.add(txStart);
					endPosY.add(txEnd);
					chrDataY.setTxStart(startPosY);
					chrDataY.setTxEnd(endPosY);
					geneNamesY.add(geneName);
					chrDataY.setGeneName(geneNamesY);
					geneSymbolsY.add(geneSymbol);
					chrDataY.setGeneSymbolList(geneSymbolsY);
					if (s.add(chr)) {
						chrDataY.setChromosomeName(chr);
						chrDataY.setChromosomeXPos(i);
						chrDataY.setChromosomeYPos(185);
						chrDataList.add(chrDataY);
						i += 35;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	//dc.drawChromosome(chrDataList);
		return (JPanel) dc;
	}
}
