package edu.ucsd.genome.chromviz.testExamples;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import edu.ucsd.genome.chromviz.sigKegg.PathwayXMLData;

public class PathwayDocWriteJDOM {
	// JComboBox comboBox;
	public static void xmlOutput(String pathName, String pathID,
			List<PathwayXMLData> transData, File file) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Document doc = new Document();
		Element pathwayElm = new Element("pathway");
		// FileReader fr = new FileReader("text/relations.txt");
		// BufferedReader br = new BufferedReader(fr);
		// String str = null;
		// String node[] = null;
		Iterator<PathwayXMLData> it = transData.iterator();
		while (it.hasNext()) {
			PathwayXMLData data = it.next();
			addTransition(pathwayElm, data);
		}
		// String string = (String) comboBox.getSelectedItem();
		pathwayElm.setAttribute("pathwayName", pathName);
		pathwayElm.setAttribute("id", pathID);
		doc.setRootElement(pathwayElm);
		XMLOutputter outputPutter = new XMLOutputter();
		outputPutter.setFormat(Format.getPrettyFormat());
		try {
			outputPutter.output(doc, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void addTransition(Element pathway, PathwayXMLData data) {
		Element transitionElm = new Element("transition");
		transitionElm.setAttribute("id", data.getGeneFrom().concat("/" + data.getGeneTo()));
		pathway.addContent(transitionElm);
		//addTextNode(transitionElm, "fromId", data.getGeneFrom());
		addFromGeneDetails(transitionElm,data);
		//addTextNode(transitionElm, "toId", data.getGeneTo());
		addToGeneDetails(transitionElm,data);
		 
	}
	private static void addFromGeneDetails(Element transitionElm, PathwayXMLData data) {
		Element fromElm = new Element("from");
		fromElm.setAttribute("geneName",data.getGeneFrom());
		transitionElm.addContent(fromElm);
		addTextNode(fromElm, "dnaSeq", data.getDnaFrom());
		addTextNode(fromElm,"proteinSeq",data.getProteinFrom());
		
	}

	private static void addToGeneDetails(Element transitionElm,PathwayXMLData data) {
		Element toElm = new Element("to");
		toElm.setAttribute("geneName",data.getGeneTo());
		transitionElm.addContent(toElm);
		addTextNode(toElm,"dnaSeq",data.getDnaTo());
		addTextNode(toElm,"proteinSeq",data.getProteinTo());
		addTranscriptionDetails(toElm,data);
				
	}
	private static void addTranscriptionDetails(Element to, PathwayXMLData data) {
		Element transcriptionElm = new Element("transcription");
		addTextNode(transcriptionElm,"start",data.getFromStartPos());
		addTextNode(transcriptionElm,"end",data.getFromEndPos());
	}

	private static void addTextNode(Element parentElm, String string,
			String string2) {
		Element childElm = new Element(string);
		childElm.setText(string2);
		parentElm.addContent(childElm);

	}
}