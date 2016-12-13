package FinalExec;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import paths.PathCalculator;
import pedestrian.handeler.PedestrianNetwork;
import poi.handeler.TripPlannerLoc;
import poi.handeler.TripPlannerLocs;
import simulation.Simulator;

public class ExecCrowdedLevel {
	
	static String flickrDataFile;
	static String tripPlanerDataFile;
	static String patternsFile;
	static String pedestrianVertexesFile;
	static String pedestrianArcsFile;
	
	public static void main(String[] args) throws Exception {

		/* Input Data*/
		System.out.println("** Loading Pedestrian Network **");
		loadXml("./input/inputdata.xml");
		double[] xbs = {12.3018 , 12.3692};
		double[] ybs = {45.4227 , 45.4495};
		PedestrianNetwork net = new PedestrianNetwork(pedestrianVertexesFile, pedestrianArcsFile, patternsFile,xbs,ybs,0);
		System.out.println("** Pedestrian Network loaded **");

		/* Loading PoI network*/
		System.out.println("** Loading PoIs **");
		TripPlannerLocs tploc = new TripPlannerLocs(tripPlanerDataFile);
		for(TripPlannerLoc t:tploc.tripPlannerLocs){
			t.findClosetPedestrianVertexAndArcs(net.getVertexes());
		}
		System.out.println("Paths loaded");
		
		/* Compute Paths*/
		System.out.println("** Loading Paths **");
		PathCalculator paths = new PathCalculator(net);
		paths.loadPaths("./input/next.csv");
		System.out.println("Paths loaded");
		
		/* Loading History*/
		
		System.out.println("** Loading Simulation History **");
		Simulator sim = new Simulator(null,net, null, 1.);
		sim.loadHistory("./output/simulationHistoryCrowdedWeekday.csv");
		System.out.println("** Simulation History Loaded **");
		
		/* Saving PoIs Travel times */
		System.out.println("** Saving Crowd Level between PoIs **");
		// weekday 0.2 0.5 0.9
		// weekend 0.3 0.7 1
		tploc.saveCrowdednessLevel("./output/PoIsCrowdLevelNonCrowdedWeekday.csv", paths, 0.2);
		System.out.println("** Crowd Level between PoIs Saved **");
	}
	
	
	static void loadXml(String fileName) throws Exception{
		File fXmlFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();


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
