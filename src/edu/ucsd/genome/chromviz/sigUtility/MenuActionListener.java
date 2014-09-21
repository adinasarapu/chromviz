package edu.ucsd.genome.chromviz.sigUtility;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.rpc.ServiceException;

import keggapi.KEGGLocator;
import keggapi.KEGGPortType;
import edu.ucsd.genome.chromviz.sigData.ChromosomeData;
import edu.ucsd.genome.chromviz.sigData.GeneLineData;
import edu.ucsd.genome.chromviz.sigDrawChrom.ChromosomeIdeogram;
import edu.ucsd.genome.chromviz.sigDrawChrom.ItemListenerPathWayImpl;

public class MenuActionListener implements ActionListener {
	JMenuItem open;
	JPanel panel;
	JScrollPane scrollPane1;
	JButton GeneButton;
	JScrollPane scrollPane2;
	JButton DnaSeqButton;
	JButton ProteinSeqButton;
	JButton ExportImage;
	JButton graphButton;
	JButton geneRelations;
	StringBuffer buffer = null;
	Map<String, String> mapValues = null;

	public MenuActionListener(JMenuItem open, JScrollPane scrollPane1,
			JButton GeneButton, JButton DnaSeqButton, JButton ProteinSeqButton,
			JButton ExportImage,JButton graphButton, JButton geneRelations, JScrollPane scrollPane2) {
		this.open = open;
		this.scrollPane1 = scrollPane1;
		this.GeneButton = GeneButton;
		this.DnaSeqButton = DnaSeqButton;
		this.ProteinSeqButton = ProteinSeqButton;
		this.ExportImage = ExportImage;
		this.graphButton = graphButton;
		this.geneRelations = geneRelations;
		this.scrollPane2 = scrollPane2;
	}

	public void actionPerformed(ActionEvent ae) {
		if (open.isArmed()) {
			final List<String> da = new ArrayList<String>();
			JFileChooser fileopen = new JFileChooser();
			FileFilter filter = new FileNameExtensionFilter("text files", "txt");
			fileopen.addChoosableFileFilter(filter);
			int ret = fileopen.showDialog(null, "Select file");
			if (ret == JFileChooser.APPROVE_OPTION) {
				File file = fileopen.getSelectedFile();
				DnaSeqButton.setEnabled(true);
				ProteinSeqButton.setEnabled(true);
				GeneButton.setEnabled(true);
				ExportImage.setEnabled(true);
				graphButton.setEnabled(true);
				geneRelations.setEnabled(true);
				mapValues = new HashMap<String, String>();
				// READ FILE TEXT LINE BY LINE AND DISPLAY IT TO STANDARD OUTPUT
				try {
					BufferedReader in = new BufferedReader(new FileReader(file));
					String str;
					int i =0;
					GeneLineData gene = new GeneLineData();
					while ((str = in.readLine()) != null) {
						i++;
						//System.out.println(str);
						String[] str1 = str.split("\t");
						gene.setTime(str1[1]);
						mapValues.put(str1[0], str1[1]);
					}
					//System.out.println("ivalue and mapSize"+i +"\t"+ mapValues.size());
					//System.out.println("mapValues"+mapValues.keySet());
					Object[] dataValues = ItemListenerPathWayImpl.getDataValuesFromKeggPathway(mapValues);
					System.out.println("dataValues"+dataValues);
					//panel = GetDrawChromosome.getChromosomeIdeogram(dataValues, null);
					scrollPane1.setViewportView(panel);
					in.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private String getGeneSymbolWithGeneId(String str) {
		KEGGLocator locator = new KEGGLocator();
		KEGGPortType serv = null;
		String tit = null;
		try {
			serv = locator.getKEGGPort();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		try {
			tit = serv.btit(str);
		} catch (RemoteException e) {

			e.printStackTrace();
		}
		String[] s = tit.split(";");
		int n = s[0].indexOf(" ");
		String k = s[0].substring(n);
		String[] a = k.split(",");
		System.out.println(a[0]);
		return a[0];

	}
	

}
