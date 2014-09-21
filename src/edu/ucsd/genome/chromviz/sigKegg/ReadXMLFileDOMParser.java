package edu.ucsd.genome.chromviz.sigKegg;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadXMLFileDOMParser {

	
	public static Map<String, List<String>> getKEGGPathwayWithCompleteGeneListFromLocalXMLFiles(String keggBase) {
		Map<String, List<String>> xmlMap = new HashMap<String, List<String>>();
		//List<String> ret = new ArrayList<String>();
		File folder = new File(keggBase);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			String str = listOfFiles[i].getName();
			if (str.indexOf(".xml") > -1) {
				int n = str.indexOf(".xml");
				String pathwayID = str.substring(0, n);
				try {
					String pathwayName = getPathwayName(pathwayID);
					List<String> lst = getGenesFromXML(pathwayID);
					xmlMap.put(pathwayName.concat(";" + pathwayID), lst);
					//ret.add(pathwayName.concat(";" + pathwayID));
				} catch (ParserConfigurationException e1) {
					e1.printStackTrace();
				} catch (SAXException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return xmlMap;
	}

	
	public static String getPathwayName(String pathwayID)
			throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File("kegg_xml/" + pathwayID + ".xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		Node pathwayNode = doc.getDocumentElement();
		NamedNodeMap pathwayElmAttributes = pathwayNode.getAttributes();
		return pathwayElmAttributes.getNamedItem("title").getTextContent();

	}

	public static List<String> getGenesFromXML(String pathwayID)
			throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File("kegg_xml/" + pathwayID + ".xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		NodeList entryElmList = doc.getElementsByTagName("entry");
		//Map<String, String> map = new HashMap<String, String>();
		List<String> ret = new ArrayList<String>();
		for (int temp = 0; temp < entryElmList.getLength(); temp++) {
			Node entryElmNode = entryElmList.item(temp);
			if (entryElmNode.getNodeType() == Node.ELEMENT_NODE) {
				Element entryElement = (Element) entryElmNode;
				Node grapElmNode = entryElement
						.getElementsByTagName("graphics").item(0);
				NamedNodeMap g = grapElmNode.getAttributes();
				NamedNodeMap n = entryElmNode.getAttributes();
				String entryType = n.getNamedItem("type").getTextContent();
				boolean gene = entryType.matches("gene");
				if (gene) {
					// System.out.println("geneType: " + geneType);
					String mmuGeneIDsString = n.getNamedItem("name")
							.getTextContent();
					//String geneNameString = g.getNamedItem("name").getTextContent();
					String[] mmuGeneIDs = mmuGeneIDsString.split(" ");
					for (int i = 0; i < mmuGeneIDs.length; i++) {
						String mmuGeneID = mmuGeneIDs[i]; // mmu:11739
						int idx = mmuGeneID.indexOf(":");
						String ncbiGeneID = mmuGeneID.substring(idx + 1);// 11739
						if(!ret.contains(ncbiGeneID)){
							ret.add(ncbiGeneID);
						}
						//map.put(ncbiGeneID, "");
					}
				}
			}
		}
		return ret;
	}

	public static List<KeggXMLPathwayRelation> getKeggXMLPathwayRelations(
			String pathID) {
		List<KeggXMLPathwayRelation> ret = new ArrayList<KeggXMLPathwayRelation>();
		String str[] = pathID.split(":");
		String ID = str[1];
		File fXmlFile = new File("kegg_xml/" + ID + ".xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		try {
			Document doc = dBuilder.parse(fXmlFile);
			Map<String, KeggXMLPathwayEntry> nodeMap = getKeggPathNodeDetails(doc); // Map<String,KeggXMLPathwayEntry>
			NodeList nList = doc.getElementsByTagName("relation");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				KeggXMLPathwayRelation relation = new KeggXMLPathwayRelation();
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					NamedNodeMap n = nNode.getAttributes();
					Element e = (Element) nNode;
					NodeList nodeList = e.getElementsByTagName("subtype");
					System.out.println("Number of subtypes: "
							+ nodeList.getLength());
					List<String> relationSubTypes = new ArrayList<String>();
					for (int i = 0; i < nodeList.getLength(); i++) {
						Node subtypeNode = nodeList.item(i);
						if (subtypeNode.getNodeType() == Node.ELEMENT_NODE) {
							NamedNodeMap subtypeAttrs = subtypeNode
									.getAttributes();
							String subtypeName = subtypeAttrs.getNamedItem(
									"name").getTextContent();
							relationSubTypes.add(subtypeName);
						}
					}
					/*
					 * NodeList e = nNode.getChildNodes(); Node subtype =
					 * e.item(0); NamedNodeMap subtypeAttributes =
					 * subtype.getAttributes(); String subtypeName =
					 * subtypeAttributes.getNamedItem("name").getTextContent();
					 * System.out.println("subtypeName"+subtypeName);
					 */
					String nodeFrom = n.getNamedItem("entry1").getTextContent();
					String nodeTo = n.getNamedItem("entry2").getTextContent();
					String relationType = n.getNamedItem("type")
							.getTextContent();
					KeggXMLPathwayEntry from = nodeMap.get(nodeFrom); // KeggXMLPathwayEntry
					KeggXMLPathwayEntry to = nodeMap.get(nodeTo); // KeggXMLPathwayEntry
					if (from != null && to != null) {
						relation.setNodeFrom(from);
						relation.setNodeTo(to);
						// add relation type here
						relation.setRelationType(relationType); 
						// add relation sub types, some times  > 1  sub types  are there
						relation.setRelationSubTypes(relationSubTypes); 
						ret.add(relation);
					}
				}
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private static Map<String, KeggXMLPathwayEntry> getKeggPathNodeDetails(
			Document doc) {
		Map<String, KeggXMLPathwayEntry> map = new HashMap<String, KeggXMLPathwayEntry>();
		NodeList nList = doc.getElementsByTagName("entry");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node entryNode = nList.item(temp);
			if (entryNode.getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap entryAtr = entryNode.getAttributes();
				Element entryElement = (Element) entryNode;
				Node grapElmNode = entryElement
						.getElementsByTagName("graphics").item(0);
				NamedNodeMap graphAtr = grapElmNode.getAttributes();
				String geneType = entryAtr.getNamedItem("type")
						.getTextContent();
				boolean b = geneType.matches("gene");
				if (b) {
					KeggXMLPathwayEntry entry = new KeggXMLPathwayEntry();
					String nodeName = entryAtr.getNamedItem("name")
							.getTextContent();
					String nodeDisplayName = graphAtr.getNamedItem("name")
							.getTextContent();
					String nodeID = entryAtr.getNamedItem("id")
							.getTextContent();
					// System.out.println(nodeID+"\t"+nodeName);
					entry.setNodeDisplayName(nodeDisplayName); // add here
																// graphics name
					// entry.setGeneIDs(""); // split entry name in to gene id
					// list and add here
					// entry.setEntryType("");
					// entry.setComponents(components) // some cases > 1
					// components are there
					entry.setEntryID(nodeID);
					map.put(nodeID, entry);
				}
			}
		}
		return map;
	}
	
	public static List<String> getKEGGPathwayListFromLocalXMLFiles(String keggBase) {
		List<String> ret = new ArrayList<String>();
		File folder = new File(keggBase);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			String str = listOfFiles[i].getName();
			if (str.indexOf(".xml") > -1) {
				int n = str.indexOf(".xml");
				String pathwayID = str.substring(0, n);
				try {
					String pathwayName = ReadXMLFileDOMParser
							.getPathwayName(pathwayID);
					ret.add(pathwayName.concat(";" + pathwayID));
				} catch (ParserConfigurationException e1) {
					e1.printStackTrace();
				} catch (SAXException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return ret;
	}
}
