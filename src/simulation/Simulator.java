package simulation;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import math.tools.Flows;
import pedestrian.handeler.*;
import poi.handeler.*;
/**
 * 
 * @author Elhabib Moustaid
 *
 */
public class Simulator {
	private Scenario s;
	private PedestrianNetwork net;
	private BussNetwork bn;
	private double deltaT = 1.;
	private boolean visu = true;
	private SingleGraph pedestrianGraph = new SingleGraph("");

	public Simulator(Scenario s, PedestrianNetwork net, BussNetwork bn,  double deltaT){
		this.s = s;
		this.deltaT = deltaT;
		this.bn = bn;
		this.net = net;
		filterVisits();
		setBussStopDistributions();
		
		if (visu) {
			for(PedestrianVertex v:net.getVertexes()){
				String id = "" + v.getID();
				pedestrianGraph.addNode(id);
				Node n = pedestrianGraph.getNode(id);
				n.addAttribute("xyz", v.getX(),v.getY(),0);
			}
			for(PedestrianArc a:net.getArcs()){
				String id = "" + a.getID();
				String from = "" + a.getFrom();
				String to = "" + a.getTo();
				pedestrianGraph.addEdge(id, from, to);
				Edge e = pedestrianGraph.getEdge(id);
				e.setAttribute("ui.color", (a.getAverageDensities(0)+a.getAverageDensities(1))/Flows.rMax);
			}
			/**for(PedestrianDest d:net.getDests()){
				String id = "" + d.getID();
				Sprite s = destinations.addSprite("id");
				s.addAttribute("xyz",d.getX(),d.getY(),0);
			}**/
			pedestrianGraph.addAttribute("ui.stylesheet", "url(./style-sheet.css)");
		}
	}

	public PedestrianNetwork getNet(){
		return net;
	}

	public double getDeltaT(){
		return deltaT;
	} 

	public void preStep(double t) throws Exception {
		bn.preStep(t);
		net.preStep(t);
	}

	public void executeStep() throws Exception{
		bn.executeStep(deltaT);
		net.executeStep(deltaT);
		if (visu)
			updateAttributes(pedestrianGraph);
	}

	public void snapShot(){
		for(PedestrianArc a:net.getArcs()){
			a.saveCurrentTravelTime();
		}
	}
	
	public void saveHistory(String fileName){
		StringBuilder sb = new StringBuilder();
		sb.append("");
		for(PedestrianArc a:net.getArcs()){
			sb.append(a.getID());
			sb.append(";");
			sb.append(a.getHistory().toString());
			sb.append("\n");
		}
		PrintWriter writer;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
			writer.print(sb.toString());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadHistory(String fileName) throws NumberFormatException, Exception{
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				String[] data = strLine.split(";");
				String[] allData = data[1].split(",");
				PedestrianArc a = net.getArc(new Integer(data[0]));
				for(int j = 0; j < allData.length; j++ ){
					a.getHistory().addData(new Integer(allData[j]));
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void filterVisits(){
		System.out.println("filter visit to do");
	}
	
	public void setBussStopDistributions(){
		// ADD Also Piazzale Roma, and Train Station
		System.out.println("setBussStopDistrbution to Implement");
	}
	
	/* Visualizing Methods
	 */
	public SingleGraph getPedestrianGraph(){
		return pedestrianGraph;
	}

	public void display(boolean placement){
		pedestrianGraph.display(placement);
	}

	public Viewer view(boolean placement){
		return pedestrianGraph.display(placement);
	}

	private void updateAttributes(Graph pedestrianGraph) {
		for(PedestrianArc a:net.getArcs()){
			String id = "" + a.getID();
			Edge e = pedestrianGraph.getEdge(id);
			e.setAttribute("ui.color", (a.getAverageDensities(0)+a.getAverageDensities(1))/Flows.rMax);
		}
	}

	public String toString(){
		return net.toString();
	}

	public void sleep(int t) {
		try { Thread.sleep(t); } catch (Exception e) {}
	}
}
