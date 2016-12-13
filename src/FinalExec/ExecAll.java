package FinalExec;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import math.tools.*;
import paths.PathCalculator;
import pedestrian.handeler.*;
import poi.handeler.BussNetwork;
import poi.handeler.BussStop;
import poi.handeler.Visit;
import poi.handeler.Visits;
import simulation.Scenario;
import simulation.Simulator;

public class ExecAll {


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
		PedestrianNetwork net = new PedestrianNetwork(pedestrianVertexesFile, pedestrianArcsFile, patternsFile,xbs,ybs,0);
		System.out.println("** Pedestrian Network loaded **");

		System.out.println("** Loading busses data **");

		BussNetwork bn = getBussNetwork(net.getVertexes(),s);

		System.out.println("** buss data loaded **");

		/* Compute Paths*/
		System.out.println("** Loading Paths **");
		PathCalculator paths = new PathCalculator(net);
		double startTime = System.currentTimeMillis();
		paths.loadPaths("./input/next.csv");

		/* Initializing parameters */

		System.out.println("** Starting the simulation **");
		Simulator sim = new Simulator(s,net, bn, 1.);
		sim.view(false);

		int timeStart = 0;
		int toDo = 24*3600;

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
				if(s.day == 0 || s.day == 6){
					System.out.println("Updating Parameters based on Weekend Visits");
					visits.filterWeekEnds();
				}
				else{
					System.out.println("Updating Parameters based on Weekday Visits");
					visits.filterWeekDays();
				}
				for(Visit v:visits.getAllVisits()){
					v.getVisitLoc().findClosetPedestrianVertexAndArcs(net.getVertexes());
				}
				paths.updateParametersBis(visits, i/3600, i/3600 + 12);
				System.out.println("Done Updating");
			}
			sim.preStep((double)i);
			sim.executeStep();
		}
		sim.saveHistory("./output/simulationHistoryCrowdedWeekend.csv");
		double totalExecTime = (System.currentTimeMillis() - startTime);
		System.out.println("Simulation done:" + totalExecTime*1.67*0.00001 + " to finish.");
	}


	static void loadXml(String fileName) throws Exception{
		File fXmlFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

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

	public static BussNetwork getBussNetwork(ArrayList<PedestrianVertex> pv, Scenario s) throws Exception{
		BussNetwork bn = new BussNetwork();
		//int[] ticketvalidations = {2334509,1781509,6555156,4649097,1286352,3199348,6224130};
		String[] IDs = {"S. Marco", "Tronchetto", "P.le Roma", "Ferrovia","Academia","Rialto", "S.Zaccaria"};
		int[] ids = {0,1,2,3,4,5,6};
		double[] x = {12.337978,12.306115,12.319017,12.321925,12.328493,12.328493,12.342745};
		double[] y = {45.432548,45.432548,45.438715,45.441043,45.431711,45.431711,45.433885};
		double[] p = {0.08, 0.06, 0.25, 0.17, 0.04, 0.12, 0,23};
		//int totalValidationByDay = 71315;
		int totalValidationByDay = 45000;
		/* The second value comes from the report Graph 8.5 and Table 9.2*/

		/*Crowdedness Level */
		if (s.crowd == 2){
			totalValidationByDay *= 1.3;
		}else if(s.crowd == 0){
			totalValidationByDay *= 0.7;
		}

		if (s.day == 0 || s.day == 6){
			totalValidationByDay *= 1.3;
		}		
		if (s.bieannale == true ){

		}

		for(int i = 0; i< 7; i++){
			BussStop bs = new BussStop(ids[i],x[i], y[i],IDs[i]);
			bs.findClosetPedestrianVertexAndArcs(pv);
			bs.setIncoming((int)(p[i]*totalValidationByDay));
			if (s.day == 0 || s.day == 6)
				bs.setIncomingDistribution(new NormalDistribution(11*3600,4*4*(3600*3600)));
			else
				bs.setIncomingDistribution(new MultiModalDistribution(8.5*3600,(3600*3600),13*3600,4*4*(3600*3600),0.3));
			bs.setOutgoing((int)(p[i]*totalValidationByDay));
			if (s.day == 0 || s.day == 6)
				bs.setOutgoingDistribution(new NormalDistribution(16*3600,4*4*(3600*3600)));
			else
				bs.setOutgoingDistribution(new MultiModalDistribution(14*3600,4*4*(3600*3600),19*3600,2*2*(3600*3600),0.8));
			
			bn.addBussStop(bs);
		}

		return bn;
	}

}
