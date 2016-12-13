import java.util.ArrayList;

import org.graphstream.graph.Graph;

import math.tools.Flows;
import pedestrian.handeler.PedestrianArc;
import pedestrian.handeler.PedestrianNetwork;
import pedestrian.handeler.PedestrianVertex;
import simulation.Simulator;

public class TestVisualisationGraph {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		PedestrianVertex v1 = new PedestrianVertex(1,0,0);
		PedestrianVertex v2 = new PedestrianVertex(2,1,0);
		PedestrianVertex v3 = new PedestrianVertex(3,2,0);
		//PedestrianVertex v4 = new PedestrianVertex(4,1,1);
		ArrayList<PedestrianVertex> vertexes = new ArrayList<PedestrianVertex>();
		vertexes.add(v1);
		vertexes.add(v2);
		vertexes.add(v3);
		//vertexes.add(v4);
		
		double densitiesMax[][] = {{0, 0}};
		double densitiesMax2[][] = {{Flows.rMax/2, 0}};
		double densitiesMax3[][] = {{Flows.rMax/2, 0}};
		PedestrianArc a12 = new PedestrianArc(1,v1,v2,densitiesMax2,1);
		//v2.addArc(a12);
		PedestrianArc a23 = new PedestrianArc(2,v2,v3,densitiesMax3,1);
		//v2.addArc(a23);
		//PedestrianArc a24 = new PedestrianArc(3,2,4,densitiesMax3,1);
		//v2.addArc(a24);
		ArrayList<PedestrianArc> arcs = new ArrayList<PedestrianArc>();
		arcs.add(a12);
		arcs.add(a23);
		//arcs.add(a24);
		
		//double P[][] = {{0., 0.5, 0.5},{0.5,0.,0.5},{0.5,0.5,0.}};
		//v2.setP(P);
				
		PedestrianNetwork net = new PedestrianNetwork(vertexes,arcs);
		//Simulator sim = new Simulator(net,1./Flows.v);
		//System.out.println(sim.toString());
		//sim.view(false);
		for(int step = 0;  step < 40; step++){
			System.out.println("Step :" + step);
			//sim.preStep(step);
			//sim.executeStep();
			//sim.sleep(100);
		}
		//System.out.println(sim.toString());
	}

}
