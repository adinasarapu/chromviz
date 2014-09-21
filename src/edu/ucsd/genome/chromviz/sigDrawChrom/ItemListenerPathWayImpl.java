package edu.ucsd.genome.chromviz.sigDrawChrom;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.ucsd.genome.chromviz.sigData.KnownGene;
import edu.ucsd.genome.chromviz.sigGateWay.VisualInterface;
import edu.ucsd.genome.chromviz.sigSql.KnownGeneListDAO;
import edu.ucsd.genome.chromviz.sigUtility.ReadFileData;

public class ItemListenerPathWayImpl implements ItemListener {

	ReadFileData fileData;
	JComboBox pathSelectComboBox;
	Map<String, List<String>> xml_data_map;
	JScrollPane scrollSouthPane1;
	JScrollPane scrollSouthPane2;
	JButton dnaSeqButton;
	JButton proteinSeqButton;
	JButton proteinListButton;
	JButton exportImageButton;
	JButton networkButton;
	JButton xmlButton;
	JButton refreshButton;

	static String ID;

	public ItemListenerPathWayImpl(ReadFileData fileData,
			JComboBox pathSelectComboBox, Map<String, List<String>> xml_data_map,
			JScrollPane scrollSouthPane1, JScrollPane scrollSouthPane2,
			JButton dnaSeqButton, JButton proteinSeqButton,
			JButton proteinListButton, JButton exportImageButton, JButton networkButton, JButton xmlButton, JButton refreshButton) {
		this.fileData = fileData;
		this.pathSelectComboBox = pathSelectComboBox;
		this.xml_data_map = xml_data_map;
		this.scrollSouthPane1 = scrollSouthPane1;
		this.scrollSouthPane2 = scrollSouthPane2;
		this.dnaSeqButton = dnaSeqButton;
		this.proteinSeqButton = proteinSeqButton;
		this.proteinListButton = proteinListButton;
		this.exportImageButton = exportImageButton;
		this.networkButton = networkButton;
		this.xmlButton = xmlButton;	
		this.refreshButton = refreshButton;
	}

	public void itemStateChanged(ItemEvent ievent) {
		if (ievent.getStateChange() == ItemEvent.SELECTED) {
			dnaSeqButton.setEnabled(true);
			proteinSeqButton.setEnabled(true);
			proteinListButton.setEnabled(true);
			exportImageButton.setEnabled(true);
			networkButton.setEnabled(true);
			xmlButton.setEnabled(true);
			String pathNameID = (String) pathSelectComboBox.getSelectedItem();
			String str = "path:";
			String string[] = pathNameID.split(";");
			String pathwayID = string[1];
			ID = str.concat(pathwayID);
			List<String> gene_list = xml_data_map.get(pathNameID);
			//System.out.println("PathID: " + pathNameID);
			//System.out.println("Path Genes: " + gene_list);
			List<KnownGene> selectedKnownGeneDetails = null;
			try {
				selectedKnownGeneDetails = KnownGeneListDAO.getDataValuesForKeggPathway(pathwayID, gene_list);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			Iterator<KnownGene> it = selectedKnownGeneDetails.iterator();
//			while(it.hasNext()){
//				KnownGene g = it.next();
//				System.out.println(g.getKnownGeneID()+"\t"+g.getLocusID()+"\t"+g.getGeneSymbol());
//			}
			ChromosomeIdeogram chromosomeIdeogram = new ChromosomeIdeogram();
			JPanel panel = chromosomeIdeogram.getChromosomeIdeogram(selectedKnownGeneDetails, fileData);
			panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			// System.out.println("Layout Manager " + panel.getLayout());
			// refresh bottom pane every time new pathway selected
			VisualInterface.addTextPane(scrollSouthPane2); 
			scrollSouthPane1.setViewportView(panel);
			refreshButton.setEnabled(true);
		}
	}

	public static String getPathID() {
		return ID;
	}

	public static String getPathName() {
		return "";
	}

	public static Object[] getDataValuesFromKeggPathway(Map<String, String> map) {
		List<String> values = new ArrayList<String>();
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		Set<String> kgIDs = null;
		for (int i = 0; i < map.size(); i++) {
			String locusID = it.next();
			try {
				kgIDs = KnownGeneListDAO.getKnownGeneIDWithEntrezID(locusID);
				values.addAll(kgIDs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return values.toArray();
	}

	/*public static Object[] getDataValues(Map<String, String> map) {
		List<KnownGene> kglist = null;
		List<String> da = null;
		Object[] values = null;
		try {
			kglist = KnownGeneListDAO.getAllKnownGenes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Set<String> keys = map.keySet();
		Iterator<KnownGene> kgit = kglist.iterator();
		da = new ArrayList<String>();
		// int i = 0;
		List<String> list = new ArrayList<String>();
		while (kgit.hasNext()) {
			KnownGene kg = kgit.next();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String key = it.next();
				if (key.trim().equals(kg.getGeneSymbol().trim())) {
					if (!list.contains(key)) {
						list.add(key);
						if (kg.getKgID() != null && !"".equals(kg.getKgID())) {
							da.add(kg.getKgID().trim());
						}// if
							// i++;
					}// if
				}// if
			}// while
		}// while
		values = da.toArray();
		return values;
	}*/
}
