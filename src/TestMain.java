import java.util.*;

import pedestrian.handeler.PedestrianArc;
import pedestrian.handeler.PedestrianNetwork;
import pedestrian.handeler.PedestrianVertex;
import simulation.Simulator;


public class TestMain {
	public static void main(String[] args) throws Exception {

		/* Construction of network 4 nodes + 3 arcs */
		/* Arcs */
		ArrayList<PedestrianArc> Arcs = new ArrayList<PedestrianArc>();
		double[][] d1 = {{2.,0.},{0.,0.},{0.,0.}};
//		Arcs.add(new PedestrianArc(0,2,d1));
		double[][] d2 = {{2.,0.},{0.,0.},{0.,0.}};
//		Arcs.add(new PedestrianArc(1,2,d2));
		double[][] d3 = {{2.,0.},{0.,0.},{0.,0.}};
//		Arcs.add(new PedestrianArc(3,2,d3));


		/* Nodes */
		ArrayList<PedestrianVertex> Nodes = new ArrayList<PedestrianVertex>();
		PedestrianVertex N = new PedestrianVertex(0,0,0);
		N.addArc(Arcs.get(0));
		Nodes.add(N);
		N = new PedestrianVertex(1,1,1);
		N.addArc(Arcs.get(1));
		Nodes.add(N);
		N = new PedestrianVertex(3,1,2.5);
		N.addArc(Arcs.get(2));
		Nodes.add(N);
		N = new PedestrianVertex(2,1,2);
		N.addArc(Arcs.get(0));
		N.addArc(Arcs.get(1));
		N.addArc(Arcs.get(2));
		Nodes.add(N);

		/* Network */
		PedestrianNetwork net = new PedestrianNetwork(Nodes,Arcs);
		System.out.println(net.toString());
		//Simulator sim = new Simulator(net,1);

		/* Running the simulation */
		for (int i=0; i<5; i++){
			System.out.println("Step: "+i);
			//sim.preStep(i);
			//sim.executeStep();
			System.out.println(net.toString());
		}
	}
}