import java.util.ArrayList;

import math.tools.Flows;
import pedestrian.handeler.PedestrianArc;
import pedestrian.handeler.PedestrianNetwork;
import pedestrian.handeler.PedestrianVertex;
import simulation.Simulator;

public class TestVaticanCityCase {

	public static void main(String[] args) throws Exception {

		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		int stepsPhase1 = 150, stepsPhase2 = 350, stepsPhase3 = 100;
		PedestrianNetwork net;
		//net = createVaticanCityNetwork();
		/*Simulator sim = new Simulator(net,1.);
		sim.view(false);
		sim.sleep(100);
		for (int i = 0; i < stepsPhase1; i++){
			System.out.println("Time: " + i);
			/* Fixing access and exits 
			
			sim.getNet().getArc(1).getFirstCell().setDensity(0, Flows.rMax/2.);
			sim.getNet().getArc(1).getFirstCell().setDensity(1, 0);
			
			sim.getNet().getArc(9).getLastCell().setDensity(1, Flows.rMax/8.);
			sim.getNet().getArc(9).getLastCell().setDensity(0, 0);
			
			sim.getNet().getArc(10).getLastCell().setDensity(1, Flows.rMax/8.);
			sim.getNet().getArc(10).getLastCell().setDensity(0, 0);
			
			sim.getNet().getArc(11).getLastCell().setDensity(1, Flows.rMax/8.);
			sim.getNet().getArc(11).getLastCell().setDensity(0, 0);
			
			sim.getNet().getArc(22).getFirstCell().setDensity(0, Flows.rMax/8.);
			sim.getNet().getArc(22).getFirstCell().setDensity(1, 0);
			
			sim.getNet().getArc(25).getLastCell().setDensity(1, Flows.rMax/8.);
			sim.getNet().getArc(25).getLastCell().setDensity(0, 0);
			
			sim.getNet().getArc(26).getLastCell().setDensity(1, Flows.rMax/8.);
			sim.getNet().getArc(26).getLastCell().setDensity(0, 0);
			
			/* Computing and executing flows inside the cells ;
			
			sim.preStep(i);
			sim.executeStep();
			sim.sleep(100);
		}
		
		
		
		for (int i = stepsPhase1; i < stepsPhase2; i++){
			System.out.println("Time: " + i);
			
			sim.getNet().getArc(1).getFirstCell().setDensity(0, sim.getNet().getArc(1).getFirstCell().getDensity(1));
			sim.getNet().getArc(1).getFirstCell().setDensity(1, 0);
			
			sim.getNet().getArc(9).getLastCell().setDensity(1, sim.getNet().getArc(9).getLastCell().getDensity(0));
			sim.getNet().getArc(9).getLastCell().setDensity(0, 0);
			
			sim.getNet().getArc(10).getLastCell().setDensity(1,sim.getNet().getArc(10).getLastCell().getDensity(0));
			sim.getNet().getArc(10).getLastCell().setDensity(0, 0);
			
			sim.getNet().getArc(11).getLastCell().setDensity(1,sim.getNet().getArc(11).getLastCell().getDensity(0));
			sim.getNet().getArc(11).getLastCell().setDensity(0, 0);
			
			sim.getNet().getArc(22).getFirstCell().setDensity(0, sim.getNet().getArc(22).getFirstCell().getDensity(1));
			sim.getNet().getArc(22).getFirstCell().setDensity(1, 0);
			
			sim.getNet().getArc(25).getLastCell().setDensity(1, sim.getNet().getArc(25).getLastCell().getDensity(0));
			sim.getNet().getArc(25).getLastCell().setDensity(0, 0);
			
			sim.getNet().getArc(26).getLastCell().setDensity(1, sim.getNet().getArc(26).getLastCell().getDensity(0));
			sim.getNet().getArc(26).getLastCell().setDensity(0, 0);
			
			/* Computing and executing flows inside the cells ;
			
			sim.preStep(i);
			sim.executeStep();
			sim.sleep(100);
		}
		
		System.out.println(net.toString());
	}

	public static PedestrianNetwork createVaticanCityNetwork() throws Exception{

		
		int y = 650;

		/**
		 * Positions
		 
		double position[][] =  {{0.,0.}, {348.,y - 275.},{337.,y - 457.},{362,y - 494}, {355,y - 513}, {314,y - 534},{314,y - 559},{242,y - 572},{213,y - 595},{186,y - 619},{292,y - 687},{262,y - 701}, {46,y - 701,}, {384,y - 550},{385,y - 494},{385,y - 518}, {415,y - 494},
				 {408,y - 525},{545,y - 487},{545,y - 572}, {440,y - 572}, {573,y - 609},{583,y - 585}, {661,y - 625},{611,y - 637},{661,y - 550}, {613,y - 550}};
		
		/**
		 * Vertexes creation
		 
		ArrayList<PedestrianVertex> vertexes = new ArrayList<PedestrianVertex>();
		vertexes.add(new PedestrianVertex(1,348,y - 275,"access"));
		vertexes.add(new PedestrianVertex(2,337,y - 457,"intermediate"));
		vertexes.add(new PedestrianVertex(3,362,y - 494,"intermediate"));
		vertexes.add(new PedestrianVertex(4,355,y - 513,"intermediate"));
		vertexes.add(new PedestrianVertex(5,314,y - 534,"intermediate"));
		vertexes.add(new PedestrianVertex(6,314,y - 559,"intermediate"));
		vertexes.add(new PedestrianVertex(7,242,y - 572,"intermediate"));
		vertexes.add(new PedestrianVertex(8,213,y - 595,"intermediate"));
		vertexes.add(new PedestrianVertex(9,186,y - 619,"intermediate"));
		vertexes.add(new PedestrianVertex(10,292,y - 687,"access"));
		vertexes.add(new PedestrianVertex(11,262,y - 701,"access"));
		vertexes.add(new PedestrianVertex(12,46,y - 701,"access"));
		vertexes.add(new PedestrianVertex(13,384,y - 550,"access"));
		vertexes.add(new PedestrianVertex(14,385,y - 494,"intermediate" ));
		vertexes.add(new PedestrianVertex(15,385,y - 518,"intermediate"));
		vertexes.add(new PedestrianVertex(16,415,y - 494,"intermediate"));
		vertexes.add(new PedestrianVertex(17,408,y - 525,"intermediate"));
		vertexes.add(new PedestrianVertex(18,545,y - 487,"intermediate"));
		vertexes.add(new PedestrianVertex(19,545,y - 572,"intermediate"));
		vertexes.add(new PedestrianVertex(20,440,y - 572,"intermediate"));
		vertexes.add(new PedestrianVertex(21,573,y - 609,"intermediate"));
		vertexes.add(new PedestrianVertex(22,583,y - 585,"intermediate"));
		vertexes.add(new PedestrianVertex(23,661,y - 625,"intermediate"));
		vertexes.add(new PedestrianVertex(24,611,y - 637,"access"));
		vertexes.add(new PedestrianVertex(25,661,y - 550,"intermediate"));
		vertexes.add(new PedestrianVertex(26,613,y - 550,"intermediate"));

		/**
		 * Arcs
		 
		
		int n = 5;
		ArrayList<PedestrianArc> arcs = new ArrayList<PedestrianArc>();
		/*arcs.add(new PedestrianArc(1,1,2,new double[(int) Math.ceil(MatrixTool.distance(position[1],position[2])/n)][2],1));
		arcs.add(new PedestrianArc(2,2,3,new double[(int) Math.ceil(MatrixTool.distance(position[2],position[3])/n)][2],1));
		arcs.add(new PedestrianArc(3,3,4,new double[(int) Math.ceil(MatrixTool.distance(position[3],position[4])/n)][2],1));
		arcs.add(new PedestrianArc(4,4,5,new double[(int) Math.ceil(MatrixTool.distance(position[4],position[5])/n)][2],1));
		arcs.add(new PedestrianArc(5,5,6,new double[(int) Math.ceil(MatrixTool.distance(position[5],position[6])/n)][2],1));
		arcs.add(new PedestrianArc(6,6,7,new double[(int) Math.ceil(MatrixTool.distance(position[6],position[7])/n)][2],1));
		arcs.add(new PedestrianArc(7,7,8,new double[(int) Math.ceil(MatrixTool.distance(position[7],position[8])/n)][2],1));
		arcs.add(new PedestrianArc(8,8,9,new double[(int) Math.ceil(MatrixTool.distance(position[8],position[9])/n)][2],1));
		arcs.add(new PedestrianArc(9,9,11,new double[(int) Math.ceil(MatrixTool.distance(position[9],position[11])/n)][2],1));
		arcs.add(new PedestrianArc(10,8,10,new double[(int) Math.ceil(MatrixTool.distance(position[8],position[10])/n)][2],1));
		arcs.add(new PedestrianArc(11,9,12,new double[(int) Math.ceil(MatrixTool.distance(position[9],position[12])/n)][2],1));
		arcs.add(new PedestrianArc(12,3,14,new double[(int) Math.ceil(MatrixTool.distance(position[3],position[14])/n)][2],1));
		arcs.add(new PedestrianArc(13,4,15,new double[(int) Math.ceil(MatrixTool.distance(position[4],position[15])/n)][2],1));
		arcs.add(new PedestrianArc(14,15,17,new double[(int) Math.ceil(MatrixTool.distance(position[15],position[17])/n)][2],1));
		arcs.add(new PedestrianArc(15,13,15,new double[(int) Math.ceil(MatrixTool.distance(position[13],position[15])/n)][2],1));
		arcs.add(new PedestrianArc(16,14,16,new double[(int) Math.ceil(MatrixTool.distance(position[14],position[16])/n)][2],1));
		arcs.add(new PedestrianArc(17,16,17,new double[(int) Math.ceil(MatrixTool.distance(position[16],position[17])/n)][2],1));
		arcs.add(new PedestrianArc(18,17,20,new double[(int) Math.ceil(MatrixTool.distance(position[17],position[20])/n)][2],1));
		arcs.add(new PedestrianArc(19,19,20,new double[(int) Math.ceil(MatrixTool.distance(position[19],position[20])/n)][2],1));
		arcs.add(new PedestrianArc(20,18,19,new double[(int) Math.ceil(MatrixTool.distance(position[18],position[19])/n)][2],1));
		arcs.add(new PedestrianArc(21,19,26,new double[(int) Math.ceil(MatrixTool.distance(position[19],position[26])/n)][2],1));
		arcs.add(new PedestrianArc(22,25,26,new double[(int) Math.ceil(MatrixTool.distance(position[25],position[26])/n)][2],1));
		arcs.add(new PedestrianArc(23,19,22,new double[(int) Math.ceil(MatrixTool.distance(position[19],position[22])/n)][2],1));
		arcs.add(new PedestrianArc(24,19,21,new double[(int) Math.ceil(MatrixTool.distance(position[19],position[21])/n)][2],1));
		arcs.add(new PedestrianArc(25,21,24,new double[(int) Math.ceil(MatrixTool.distance(position[21],position[24])/n)][2],1));
		arcs.add(new PedestrianArc(26,22,23,new double[(int) Math.ceil(MatrixTool.distance(position[22],position[23])/n)][2],1));
		arcs.add(new PedestrianArc(27,14,15,new double[(int) Math.ceil(MatrixTool.distance(position[14],position[15])/n)][2],1));
		arcs.add(new PedestrianArc(28,16,18,new double[(int) Math.ceil(MatrixTool.distance(position[16],position[18])/n)][2],1)); 

		PedestrianNetwork net = new PedestrianNetwork(vertexes,arcs);
		return net;*/
	}
	
	public static void fixTurningFractions(PedestrianNetwork net) throws Exception{
		double P3[][] = {{0.,0.5,0.5},{0.7,0.,0.3},{0.7,0.3,0.}};
		net.getVertexes().get(2).updateP(P3);
		
		double P4[][] = {{0.,0.5,0.5},{0.7,0.,0.3},{0.7,0.3,0.}};
		net.getVertexes().get(3).updateP(P4);
		
		double P8[][] = {{0,0.75,0.25},{1.,0.,0.},{1.,0.,0.}};
		net.getVertexes().get(7);
		
		double P9[][] = {{0,0.75,0.25},{1.,0.,0.},{1.,0.,0.}};
		net.getVertexes().get(8);
		
		}
}