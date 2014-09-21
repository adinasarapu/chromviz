package edu.ucsd.genome.chromviz.testExamples;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class PathwayDocValidateJDOM {
	public static void main(String args[]){
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(true);

		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

		try {
			factory.setSchema(schemaFactory.newSchema(new Source[] {new StreamSource("text/pathway.xsd")}));
		
			DocumentBuilder builder;
		
			builder = factory.newDocumentBuilder();
		
			builder.setErrorHandler(new SimpleErrorHandler());
		
			Document document =  builder.parse(new InputSource("text/chemokine.xml"));
			System.out.println( "Successfully parsed and validated" );
			} catch (ParserConfigurationException e1) {
					e1.printStackTrace();
			}
			catch (SAXException e) {
					e.printStackTrace();
			} catch (IOException e) {
					e.printStackTrace();
			}
		}
}
