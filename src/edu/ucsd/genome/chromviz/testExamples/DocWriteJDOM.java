package edu.ucsd.genome.chromviz.testExamples;


import java.io.FileWriter;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class DocWriteJDOM {
	public static void main(String args[]){
		  
		/*SAXBuilder builder = new SAXBuilder();
          builder.setFeature(
              "http://apache.org/xml/features/validation/schema", true );
          builder.setProperty(
                  "http://apache.org/xml/properties/schema"
                  + "/external-noNamespaceSchemaLocation",
                 xsdFile.toURL().toString() );
                Document doc = builder.build( xmlFile );
                System.out.println( "Successfully parsed and validated" );*/
		
		try {
			FileWriter writer = new FileWriter("files/userInfo.xml");
			Document doc = new Document();
			Element root = new Element("rows");			
			Element child = new Element("row");
			Element subChild1 = new Element("firstname");
			Element subChild2 = new Element("lastname");
			Element subChild3 = new Element("address");
			child.addContent(subChild1.setText("Srujana"));
			child.addContent(subChild2.setText("Dinasarapu"));
			child.addContent(subChild3.setText("Decoro Street"));			
			root.addContent(child);
			doc.setRootElement(root);			
			XMLOutputter outputPutter = new XMLOutputter();
			outputPutter.setFormat(Format.getPrettyFormat());
			outputPutter.output(doc, writer);			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}


