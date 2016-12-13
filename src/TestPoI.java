import java.util.ArrayList;

import math.tools.Flows;
import math.tools.UniformDistribution;
import pedestrian.handeler.*;
import poi.handeler.*;
import simulation.Simulator;

public class TestPoI {

	public static void main(String[] args) throws Exception {
		
		PedestrianVertex pv1;
		PedestrianVertex pv2;
		PedestrianVertex pv3;
		ArrayList<PedestrianVertex> vs = new ArrayList<PedestrianVertex>();
		
		
		pv1 = new PedestrianVertex(0,0.,0.,"x");
		vs.add(pv1);
		pv2 = new PedestrianVertex(1,0.,1.,"x");
		vs.add(pv2);
		pv3 = new PedestrianVertex(2,1.,0.,"x");
		vs.add(pv3); 
		
		ArrayList<PedestrianArc> as = new ArrayList<PedestrianArc>();
		
		PedestrianArc pa1,pa2;
		
		pa1 = new PedestrianArc(0, pv1 , pv2, 2, 1.,"");
		as.add(pa1);
		pa2 = new PedestrianArc(1, pv2 , pv3, 2, 1.,"");
		as.add(pa2);
		
		
		PedestrianNetwork pn = new PedestrianNetwork(vs,as); 
		
		BussNetwork bn = new BussNetwork();
		BussStop bs = new BussStop(0,0.0,0.0);
		bs.setIncoming(0);
		bs.setIncomingDistribution(new UniformDistribution(00,24*3600));
		bs.setOutgoing(1000);
		bs.setOutgoingDistribution(new UniformDistribution(0,24*3600));
		bs.findClosetPedestrianVertexAndArcs(pn.getVertexes());
		bn.addBussStop(bs);
		
		bs = new BussStop(1,0.,0.9);
		bs.findClosetPedestrianVertexAndArcs(pn.getVertexes());
		bs.setIncoming(0);
		bs.setIncomingDistribution(new UniformDistribution(0,24*3600));
		bs.setOutgoing(1000);
		bs.setOutgoingDistribution(new UniformDistribution(0,24*3600));
		bn.addBussStop(bs);
		
		pa1.getCell(0).setDensity(1, 0.1);
		pa1.getLastCell().setDensity(0, 0.1);
		pa2.getFirstCell().setDensity(1, 0.7);
		pa2.getLastCell().setDensity(0, 0.1);
		
		/*Simulator sim = new Simulator(null,pn,null, bn, 1.);
		
		System.out.println("initial" + sim);
		sim.preStep(0);
		System.out.println("after per" + sim);
		//sim.executeStep();
		//System.out.println("after exec" + sim);*/
		
	}

}
