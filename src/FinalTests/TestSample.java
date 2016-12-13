package FinalTests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import math.tools.MatrixTool;
import math.tools.UniformDistribution;
import paths.PathCalculator;
import pedestrian.handeler.*;
import poi.handeler.BussNetwork;
import poi.handeler.*;
import simulation.Scenario;
import simulation.Simulator;

public class TestSample {


	static String flickrDataFile;
	static String tripPlanerDataFile;
	static String patternsFile;
	static String pedestrianVertexesFile;
	static String pedestrianArcsFile;


	public static void main(String[] args) throws Exception {

		/* Scenario */
		System.out.println("** Loading Scenario **");
		Scenario s = new Scenario("./test/scenario.xml");
		System.out.println("** Scenario Loaded **");


		/* Input Data*/
		System.out.println("** Loading Data **");
		loadXml("./test/inputdata.xml");
		double[] xbs = {-0.2 , 1.};
		double[] ybs = {-0.2 , 1.};
		PedestrianNetwork net = new PedestrianNetwork(pedestrianVertexesFile, pedestrianArcsFile, patternsFile,xbs,ybs);
		System.out.println("** Data Loaded **");

		/* Buss network*/
		BussNetwork bn = new BussNetwork();

		BussStop bs = new BussStop(0,0.55,0.45);
		bs.setIncoming(1000);
		bs.setIncomingDistribution(new UniformDistribution(0,12*3600));
		bs.setOutgoing(1000);
		bs.setOutgoingDistribution(new UniformDistribution(12*3600,24*3600));
		bs.findClosetPedestrianVertexAndArcs(net.getVertexes());
		bn.addBussStop(bs);

		bs = new BussStop(1,0.4,0.5);
		bs.findClosetPedestrianVertexAndArcs(net.getVertexes());
		bs.setIncoming(1000);
		bs.setIncomingDistribution(new UniformDistribution(0,12*3600));
		bs.setOutgoing(1000);
		bs.setOutgoingDistribution(new UniformDistribution(12*3600,24*3600));
		bn.addBussStop(bs);
		

		/* Compute Paths*/
		System.out.println("** Loading Paths **");
		PathCalculator paths = new PathCalculator(net);
		paths.computePaths();
		//paths.SavePath("./test/distance.csv", "./test/next.csv");
		//paths.LoadPaths("./test/distance.csv", "./test/next.csv");


		/*int id1= 0, id2 = 6;
		ArrayList<PedestrianArc> l = paths.FindPathBetweenInArcs(net.getVertex(id1), net.getVertex(id2));
		System.out.println("Path Between "+ id1 + " and " + id2 + " :");
		for(PedestrianArc a:l){
			System.out.println(a.getID());
		}*/
		//paths.updateParametersBis(visits, 00, 12);

		/*Simulation*/

		/*System.out.println("** Starting the simulation **");*/
		Simulator sim = new Simulator(s,net, bn, 1.);
		sim.view(false);
		/*int timeStart = 0;
		int toDo = 24*3600;

		double startTime = System.currentTimeMillis();
		for(int i = timeStart; i < timeStart + toDo ; i++){
			if (i % 3600 == 0){
				System.out.println("*****time hour********: " + i/3600);
				System.out.println("REMAINING : "+ Math.ceil(100*(toDo - i)/toDo) + "%");
			}

			if (i % (15*60) == 0){
				sim.snapShot();
			}

			if (i == 12*3600 || i == 0){
				Visits visits = new Visits(flickrDataFile);
				for(Visit v:visits.getAllVisits()){
					v.getVisitLoc().findClosetPedestrianVertexAndArcs(net.getVertexes());
				}
				paths.updateParametersBis(visits, i/3600, i/3600 + 12);
			}
			sim.preStep((double)i);
			sim.executeStep();
		}*/
		sim.loadHistory("./test/simulatonHistory.csv");
		
		TripPlannerLocs tpls = new TripPlannerLocs(tripPlanerDataFile);
		for(TripPlannerLoc tpl:tpls.getTripPlannerLocs()){
			tpl.findClosetPedestrianVertexAndArcs(net.getVertexes());
		}
		
		tpls.saveTravelTimeHistory("./test/travelTimesBetwwenPoIs", paths);
		//double endTime = System.currentTimeMillis();
		//double execTime = endTime - startTime;
		//System.out.println("Simulation took :" + execTime + " to finish.");
		/*for(PedestrianArc a:net.getArcs()){
			if (a.getID() == 26)
			System.out.println(a.getID() + ";" + a.getHistory());
		}
		System.out.println("travel at first is :" + net.getArc(26).getTravelTimeAt(3600));*/
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
