package FinalExec;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import math.tools.UniformDistribution;
import paths.PathCalculator;
import pedestrian.handeler.PedestrianNetwork;
import poi.handeler.BussNetwork;
import poi.handeler.BussStop;
import poi.handeler.Visits;
import simulation.Scenario;

public class ExecComputePaths {
	
	static String flickrDataFile;
	static String tripPlanerDataFile;
	static String patternsFile;
	static String pedestrianVertexesFile;
	static String pedestrianArcsFile;
	
	public static void main(String[] args) throws Exception {

		/* Scenario */
		System.out.println("** Loading Scenario **");
		Scenario s = new Scenario("./input/scenario.xml");
		System.out.println("** Scenario Loaded **");


		/* Input Data*/
		System.out.println("** Loading Pedestrian Network **");
		loadXml("./input/inputdata.xml");
		double[] xbs = {12.3018 , 12.3692};
		double[] ybs = {45.4227 , 45.4495};
		PedestrianNetwork net = new PedestrianNetwork(pedestrianVertexesFile, pedestrianArcsFile, patternsFile,xbs,ybs);
		System.out.println("** Pedestrian Network loaded **");

		/* Compute Paths*/
		System.out.println("** Loading Paths **");
		PathCalculator paths = new PathCalculator(net);
		double startTime = System.currentTimeMillis();
		paths.computePaths();
		paths.savePath("./input/next.csv");
		double endTime = System.currentTimeMillis();
		double execTime = (endTime - startTime);
		System.out.println("Paths loaded on:" + execTime*1.67*0.00001 + " to finish.");		
	}
	
	
	static void loadXml(String fileName) throws Exception{
		File fXmlFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName("files");
		Node dayType = nList.item(0);
		Element eElement = (Element) dayType;

		flickrDataFile = eElement.getElementsByTagName("flickrdata").item(0).getTextContent();
		tripPlanerDataFile = eElement.getElementsByTagName("tripplanerdata").item(0).getTextContent();
		patternsFile = eElement.getElementsByTagName("patterns").item(0).getTextContent();
		pedestrianVertexesFile = eElement.getElementsByTagName("pedestrianvertexes").item(0).getTextContent();
		pedestrianArcsFile = eElement.getElementsByTagName("pedestrianarcs").item(0).getTextContent();

	}
}
