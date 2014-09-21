package edu.ucsd.genome.chromviz.sigKegg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.xml.rpc.ServiceException;

import org.jdom.Document;

import edu.ucsd.genome.chromviz.testExamples.PathwayDocWriteJDOM;
import edu.ucsd.genome.chromviz.testExamples.ProgressBar;
import edu.ucsd.genome.chromviz.testExamples.ProgressBarDemo;

import keggapi.*;

public class KeggPathwaysLocal {
	static ProgressBar pbar;
	static JLabel label;
	public static Map<String, String> getGenesFromKGML(String kegID)
			throws ServiceException, RemoteException {
		KEGGLocator locator = new KEGGLocator();
		KEGGPortType serv = locator.getKEGGPort();
		String[] results = serv.get_genes_by_pathway(kegID);
		Map<String, String> map = new HashMap<String, String>();
		System.out.println(kegID + "\t");
		label = new JLabel("downloading genes");
		pbar = new ProgressBar(results.length,label);
		for (int i = 0; i < results.length; i++) {
			String pe = results[i];
			//System.out.println("path gene string\t"+pe.substring(4)+"\t"+pe);
			/*String title = serv.btit(pe);
			String[] str = title.split(";");
			int n = str[0].indexOf(" ");
			String k = str[0].substring(n);
			String[] a = k.split(",");
			System.out.println("\t" + a[0] + "\t" + str[1]);*/
			 pbar.updateBar(i+1);
			 //map.put(a[0], str[1]);
			 map.put(pe.substring(4), "");
		}
		//System.out.println("Genes in Pathway " + map.size());
		return map;
	}

	public static StringBuffer getProteinSeqFromKEGG(String kegID)
			throws ServiceException, RemoteException {
		KEGGLocator locator = new KEGGLocator();
		KEGGPortType serv = locator.getKEGGPort();
		StringBuffer buffer1 = new StringBuffer();
		String[] results = serv.get_genes_by_pathway(kegID);
		label = new JLabel("downloading proteinSequences");
		pbar = new ProgressBar(results.length,label);
		for (int i = 0; i < results.length; i++) {
			String gene = results[i];
			// System.out.println("gene in getSeqFromKEGG"+gene);
			String seq = serv.bget("-f -n a " + gene);
			pbar.updateBar(i+1);
			buffer1.append(seq);
		}
		return buffer1;
	}

	/*public static void getDnaSeqFromKEGGForGeneList(Object[] values)
			throws ServiceException, RemoteException {
		KEGGLocator locator = new KEGGLocator();
		KEGGPortType serv = locator.getKEGGPort();
		for (int j = 0; j < values.length; j++) {
			String gid = serv.bconv("genbank:" + (String) values[j]);
			// System.out.println(gid);
		}
	}*/

	public static StringBuffer getDnaSeqFromKEGG(String kegID)
			throws ServiceException, RemoteException {
		StringBuffer buffer2 = new StringBuffer();
		KEGGLocator locator = new KEGGLocator();
		KEGGPortType serv = locator.getKEGGPort();
		String[] results = serv.get_genes_by_pathway(kegID);
		label = new JLabel("downloading dnaSequences");
		pbar = new ProgressBar(results.length,label);
		for (int i = 0; i < results.length; i++) {
			String gene = results[i];
			String dnaSeq = serv.bget("-f -n n " + gene);
			System.out.println("dnaSeq ==>" +dnaSeq);
			pbar.updateBar(i+1);
			buffer2.append(dnaSeq);
		}
		return buffer2;
	}

	public static Map<String, String> getVertexWithGeneID(String kegID)
			throws ServiceException, RemoteException {
		KEGGLocator locator = new KEGGLocator();
		KEGGPortType serv = locator.getKEGGPort();
		String[] results = serv.get_genes_by_pathway(kegID);
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < results.length; i++) {
			String pe = results[i];
			//System.out.println(kegID + "\t" + pe);
			map.put(Integer.toString(i + 1), pe);
		}
		return map;
	}

	public static Map<String, String> getVertexWithGeneSymbol(String kegID)
			throws ServiceException, RemoteException {
		KEGGLocator locator = new KEGGLocator();
		KEGGPortType serv = locator.getKEGGPort();
		String[] results = serv.get_genes_by_pathway(kegID);
		Map<String, String> map = new HashMap<String, String>();
		label = new JLabel("Graph is loading,please wait...");
		pbar = new ProgressBar(results.length,label);
		for (int i = 0; i < results.length; i++) {
			String pe = results[i];
			String title = serv.btit(pe);
			pbar.updateBar(i+1);
			String[] str = title.split(";");
			int n = str[0].indexOf(" ");
			String k = str[0].substring(n);
			String[] a = k.split(",");

			// String pathDes = serv.btit(kegID);
			// System.out.println(kegID + "\t" + a[0] + "\t" + str[1]);
			map.put(Integer.toString(i + 1), a[0]);
		}
		// System.out.println("map size in getgenesbypathway class " +
		// map.size());
		return map;
	}

	public static Map<String, String> getElementRelations(String kegID)
			throws ServiceException, IOException {
		KEGGLocator locator = new KEGGLocator();
		KEGGPortType serv = locator.getKEGGPort();
		PathwayElementRelation[] erelations = serv
				.get_element_relations_by_pathway(kegID);
		Map<String, String> nodes = getVertexWithGeneSymbol(kegID);
		Map<String, String> map = new HashMap<String, String>();
		ProgressBarDemo pbDemo = new ProgressBarDemo();
		// FileWriter fstream = new FileWriter("text/relations.txt");
		// BufferedWriter out = new BufferedWriter(fstream);
		for (int i = 0; i < erelations.length; i++) {
			int id1 = erelations[i].getElement_id1();
			int id2 = erelations[i].getElement_id2();
			System.out.println(nodes.get(Integer.toString(id1)) + "\t"
					+ nodes.get(Integer.toString(id2)));
			// out.write(nodes.get(Integer.toString(id1))+"\t"+
			// nodes.get(Integer.toString(id2))+"\n");
			pbDemo.updateBar(i, erelations.length);
			map.put(nodes.get(Integer.toString(id1)), nodes.get(Integer
					.toString(id2)));
		}
		// out.close();
		return map;

	}

	public static void exportPathwayDataAsXML(String pathName, String kegID,File file)
			throws ServiceException, IOException {
		KEGGLocator locator = new KEGGLocator();
		KEGGPortType serv = locator.getKEGGPort();
		PathwayElementRelation[] erelations = serv
				.get_element_relations_by_pathway(kegID);
		Map<String, String> nodes = getVertexWithGeneID(kegID);
		Map<String, String> geneNames = getVertexWithGeneSymbol(kegID);
		List<PathwayXMLData> list = new ArrayList<PathwayXMLData>();
		for (int i = 0; i < erelations.length; i++) {
			PathwayXMLData xmlData = new PathwayXMLData();
			int id1 = erelations[i].getElement_id1();
			int id2 = erelations[i].getElement_id2();
			String fromGeneName=geneNames.get(Integer.toString(id1));
			String toGeneName=geneNames.get(Integer.toString(id2));
			String geneid1 = nodes.get(Integer.toString(id1));
			String geneid2 = nodes.get(Integer.toString(id2));
			String dnaSeq1 = serv.bget("-f -n n " + geneid1);
			String dnaSeq2 = serv.bget("-f -n n " + geneid2);
			String proteinSeq1 = serv.bget("-f -n a " +geneid1);
			String proteinSeq2 = serv.bget("-f -n a " +geneid2);
			
			xmlData.setGeneFrom(fromGeneName);
			xmlData.setGeneTo(toGeneName);
			xmlData.setDnaFrom(dnaSeq1);
			xmlData.setDnaTo(dnaSeq2);
			xmlData.setProteinFrom(proteinSeq1);
			xmlData.setProteinTo(proteinSeq2);
			list.add(xmlData);
			System.out.println(geneid1+"\n"+geneid2+"\n"+dnaSeq1+"\n"+dnaSeq2);
		}
		PathwayDocWriteJDOM.xmlOutput(pathName, kegID, list,file);
	}
}
