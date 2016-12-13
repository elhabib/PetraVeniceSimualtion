package OnlineReading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import pedestrian.handeler.PedestrianArc;

public class GateOpenings {

	LinkedList<GateOpening> go = new LinkedList<GateOpening>();
	
	
	public GateOpenings(String file){

		try {
			
			

			File fXmlFile = new File("./input/dumpXML.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			for (int temp = 0; temp < 97; temp++) {
				
				//System.out.println("temps :" + temp);
				
				if (doc.getElementsByTagName("q"+temp+":AXOEMAPERTUREINFORESULTDET").getLength() == 0){
					continue;
				}
				
				Node nNode = doc.getElementsByTagName("q"+temp+":AXOEMAPERTUREINFORESULTDET").item(0);
				
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					
					String date =  eElement.getElementsByTagName("dataApertura").item(0).getTextContent();
					int validations = new Integer(eElement.getElementsByTagName("numAperture").item(0).getTextContent());
					int id = new Integer(eElement.getElementsByTagName("codPostazione").item(0).getTextContent());
					String s = eElement.getElementsByTagName("descrPostazione").item(0).getTextContent();
					/*System.out.println("dataApertura id : " + eElement.getElementsByTagName("dataApertura").item(0).getTextContent());
					System.out.println("numAperture : " + eElement.getElementsByTagName("numAperture").item(0).getTextContent());
					System.out.println("codPostazione : " + eElement.getElementsByTagName("codPostazione").item(0).getTextContent());
					System.out.println("descrPostazione : " + eElement.getElementsByTagName("descrPostazione").item(0).getTextContent());*/
					go.add(new GateOpening( s,  id,  validations));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public int getTravellersAtThisTime() {
		int ids[] = {1,2,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,
				23,24,25,28,29,30,31,34,35,36,37,38,200,201,202,203,204,101
				,102,103,120,121,122,123,140,141,142,26,27,32,33};
		int travelers = 0;
		for (GateOpening gop:go){
			for(int i= 0; i<ids.length;i++)
			if(gop.getId() == ids[i]){
				travelers+=gop.getValidations();
			}
		}
		return travelers;
	}
}