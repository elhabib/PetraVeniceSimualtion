import java.util.ArrayList;
import java.util.List;

import pedestrian.handeler.PedestrianArc;
import pedestrian.handeler.PedestrianNetwork;
import pedestrian.handeler.PedestrianVertex;
import simulation.Simulator;

/**
 * 
 * @author Elhabib Moustaid
 *
 */
public abstract class TestTwoArcs {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/* Arcs */
		ArrayList<PedestrianArc> Arcs = new ArrayList<PedestrianArc>();
		double[][] d1 = {{0.,0.},{3.,7.},};
//		Arcs.add(new PedestrianArc(0,1,d1));
		double[][] d2 = {{0.,3.},{0.,0.},};
//		Arcs.add(new PedestrianArc(2,1,d2));
		/* Nodes */
		ArrayList<PedestrianVertex> Nodes = new ArrayList<PedestrianVertex>();
		PedestrianVertex N = new PedestrianVertex(0,0,0);
		N.addArc(Arcs.get(0));
		Nodes.add(N);
//		N = new PedestrianVertex(1);
		N.addArc(Arcs.get(0));
		N.addArc(Arcs.get(1));
		Nodes.add(N);
//		N = new PedestrianVertex(2);
		N.addArc(Arcs.get(1));
		Nodes.add(N);
		
		/*Network*/
		PedestrianNetwork net = new PedestrianNetwork(Nodes,Arcs);
		System.out.println(net.toString());

		/*Simulation*/
		/*Simulator sim = new Simulator(null,net,null,null,0.);
		int i = 0;
		for (i = 0; i<1; i++){
			sim.preStep(i);
			sim.executeStep();
			System.out.println(net.toString());
		}*/
	}

}
