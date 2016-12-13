import java.util.ArrayList;

import math.tools.Flows;
import math.tools.UniformDistribution;
import pedestrian.handeler.PedestrianArc;
import pedestrian.handeler.PedestrianNetwork;
import pedestrian.handeler.PedestrianVertex;
import simulation.Simulator;

public class TestVeniceSimulator {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/*System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		double[] xbs = {12.3018 , 12.3692};
		double[] ybs = {45.4227 , 45.4495};
		PedestrianNetwork net = new PedestrianNetwork("../../DataAndReports/Cartography/Cartography/GeoJSON/NODES.geojson"
				,"../../DataAndReports/"
					+ "Cartography/Cartography/GeoJSON/"
					+ "ROADS.geojson", 
					"../../DataAndReports/Cartography/"
					+ "Cartography/GeoJSON/POIREDUCED.geojson",xbs,ybs);
		Simulator sim = new Simulator(net,1.);
		sim.view(false);
		for (PedestrianDest d:sim.getNet().getDests()){
			d.setVistors(100000);
			d.setDistribution(new UniformDistribution(0,3600));
		}
		sim.getNet().updateParametersVertexes(0,sim.getNet().getVertexes().size(), 100);
		int toDo = 3600;
		ArrayList<PedestrianArc> arcs = sim.getNet().getArcs();
		ArrayList<PedestrianVertex> vertexes = sim.getNet().getVertexes();
		for(PedestrianArc a:arcs){
			a.getCell(0).setDensity(0, Flows.rMax/2*Math.random());
			a.getCell(0).setDensity(1, Flows.rMax/2*Math.random());
		}
		for(int i = 0; i < toDo ; i++){
			System.out.println("Step " + i);
			sim.preStep(i);
			sim.executeStep();
			sim.sleep(100);
		}*/
	}
}
