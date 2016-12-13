import java.util.ArrayList;

import math.tools.Flows;
import math.tools.MatrixTool;
import math.tools.NormalDistribution;
import math.tools.UniformDistribution;
import pedestrian.handeler.PedestrianArc;
import pedestrian.handeler.PedestrianCell;
import pedestrian.handeler.PedestrianNetwork;
import pedestrian.handeler.PedestrianVertex;
import simulation.Simulator;

public class TestVeniceWorkshopBaseCase {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");



		/*
		 * Initialisation with data
		 */

		/*System.out.println("****Loading Network");

		double[] xbs = {-1000. , 1000.};
		double[] ybs = {-1000. , 1000.};
		PedestrianNetwork net = new PedestrianNetwork("../../Workshop_Data/GeoJSON/NODES.geojson"
				,"../../Workshop_Data/GeoJSON/"
						+ "ROADS.geojson", 
						"../../Workshop_Data/GeoJSON/POIREDUCED.geojson",xbs,ybs);
		Simulator sim = new Simulator(net,1.);
		sim.view(false);*/


		/*
		 * Adding missing data i.e cells 
		 */

		System.out.println("****Completing Data");

		/*ArrayList<PedestrianArc> arcs = sim.getNet().getArcs();
		ArrayList<PedestrianVertex> vertexes = sim.getNet().getVertexes();
		for (PedestrianVertex v:vertexes){
			v.setY(v.getY());
		}
		for(PedestrianArc a:arcs){
			a.getCell(0).setDensity(0, Flows.rMax/2);
			double[] e1 = {a.getFromVertex().getX(),a.getFromVertex().getY()};
			double[] e2 = {a.getToVertex().getX(),a.getToVertex().getY()};
			// real scale is 5.09, using 3.09 to make simulation faster
			double arcLength = MatrixTool.distance(e1, e2)*3.09;
			a.setlength(arcLength);
			double cellLength = Flows.v;
			PedestrianCell[] ps = new PedestrianCell[(int) Math.ceil(arcLength/cellLength)]; 
			for(int i = 0; i<ps.length ; i++){
				ps[i] = new PedestrianCell(Flows.v);
			}
			a.setCells(ps);
		}*/

		/*
		 * Setting Simulation Scenario
		 */

		System.out.println("****Uploading Simulation Scenario");
		/*
		 * Number of visitors
		 */

		int numberOfVisitors = 80000;

		/*
		 * Completing Destinations 
		 */

		/*
		 * Completing Destinations information
		 */

		/*for(PedestrianDest d:net.getDests()){
			d.setVistors(numberOfVisitors);
			d.setDistribution(new UniformDistribution(3600*9,3600*12));
		}*/
		
		/*PedestrianDest pd;
		pd = net.getDests(1);
		pd.setVistors(numberOfVisitors);
		pd.setDistribution(new NormalDistribution(3600*11,3600*2));
		
		pd = net.getDests(2);
		pd.setVistors(numberOfVisitors);
		pd.setDistribution(new NormalDistribution(3600*11.5,3600*2));

		
		pd = new PedestrianDest(100, 388, -181, "Main Road");
		pd.setVistors((int) 0.2*numberOfVisitors);
		pd.setDistribution(new UniformDistribution(3600*9,3600*13));
		sim.getNet().getDests().add(pd);

		pd = new PedestrianDest(101, 334, -364, "Southern Road");
		pd.setVistors((int) 0.1*numberOfVisitors);
		pd.setDistribution(new UniformDistribution(3600*9,3600*13));
		sim.getNet().getDests().add(pd);

		pd = new PedestrianDest(102, 537, -357, "Road left");
		pd.setVistors((int) 0.3*numberOfVisitors);
		pd.setDistribution(new UniformDistribution(3600*9,3600*13));
		sim.getNet().getDests().add(pd);

		pd = new PedestrianDest(103, 619, -315, "Road Right");
		pd.setVistors((int) 0.1*numberOfVisitors);
		pd.setDistribution(new UniformDistribution(3600*9,3600*13));
		sim.getNet().getDests().add(pd);*/

		/*
		 * Putting some people in the network
		 */

		/*for(PedestrianArc a:arcs){
			for(int i = 0; i < a.getNumberCells(); i++){
				if (Math.random() < 0.5){
					a.getCell(i).setDensity(0, 0.3);
					a.getCell(i).setDensity(1, 0.3);
				}
			}
		}*/

		/*
		 * Completing Origins information
		 */

		/*PedestrianOrigin o1 = new PedestrianOrigin(1, 266.0, -261.0, (int) (0.4*numberOfVisitors), new NormalDistribution(3600*10,3600*2), "Santa Lucia");
		o1.findClosetPedestrianVertexAndArcs(vertexes);
		sim.getNet().addOrigin(o1);

		PedestrianOrigin o2 = new PedestrianOrigin(2, 252.0, -324.0, (int) (0.4*numberOfVisitors), new UniformDistribution(3600*9,3600*13), "Piazzale Roma");
		o2.findClosetPedestrianVertexAndArcs(vertexes);
		sim.getNet().addOrigin(o2);

		PedestrianOrigin o3 = new PedestrianOrigin(3, 521.0, -289.0, (int) (0.3*numberOfVisitors), new NormalDistribution(3600*10.5,3600*2), "Rialoto Bridge");
		o3.findClosetPedestrianVertexAndArcs(vertexes);
		sim.getNet().addOrigin(o3);
		
		PedestrianOrigin o32 = new PedestrianOrigin(3, 521.0, -289.0, (int) (0.2*numberOfVisitors), new UniformDistribution(3600*9,3600*13), "Rialoto Bridge locals");
		o32.findClosetPedestrianVertexAndArcs(vertexes);
		sim.getNet().addOrigin(o32);

		PedestrianOrigin o4 = new PedestrianOrigin(4, 598.0, -395.0, (int) (0.3*numberOfVisitors), new NormalDistribution(3600*11,3600*2), "Plaza San Marco");
		o4.findClosetPedestrianVertexAndArcs(vertexes);
		sim.getNet().addOrigin(o4);
		
		PedestrianOrigin o42 = new PedestrianOrigin(4, 598.0, -395.0, (int) (0.2*numberOfVisitors), new UniformDistribution(3600*11,3600*13), "Plaza San Marco locals");
		o42.findClosetPedestrianVertexAndArcs(vertexes);
		sim.getNet().addOrigin(o42);*/

		/*
		 * close some directions 
		 */

		// Those are permanent
		
		//sim.getNet().getArc(5).closeDirection(1);
		
		// Those are temporary

		//sim.getNet().getArc(8).closeDirection(1);


		/*
		 * Adding An event 
		 */
		//sim.getNet().getDests().add(new PedestrianDest(100, 388, -181, "Main Road"));



		/*
		 * Simulation here
		 */

		int toDo =  3600*4;

		int timeStart = 9*3600;

		double startTime = System.currentTimeMillis();
		for(int i = timeStart; i < timeStart + toDo ; i++){

			if (i % 900 == 0){
				System.out.println("*************************************time step: " + i);
			}
			//sim.getNet().updateParametersVertexes(0, vertexes.size(), i);
			//sim.preStep((double)i);
			//System.out.println(sim.getNet().getArc(6).toString());
			//sim.executeStep();

			/*
			 * Calibrating some special nodes
			 */
			/*double aux;
			aux = sim.getNet().getArc(1).getCell(0).getDensity(1);
			sim.getNet().getArc(1).getCell(0).setDensity(1, 0.);
			sim.getNet().getArc(1).getCell(0).setDensity(0, aux);

			aux = sim.getNet().getArc(43).getLastCell().getDensity(0);
			sim.getNet().getArc(43).getLastCell().setDensity(0, 0.);
			sim.getNet().getArc(43).getLastCell().setDensity(1, aux);

			aux = sim.getNet().getArc(6).getFirstCell().getDensity(1);
			sim.getNet().getArc(6).getFirstCell().setDensity(1, 0.);
			sim.getNet().getArc(6).getFirstCell().setDensity(0, aux);*/
		}
		double endTime = System.currentTimeMillis();
		double execTime = endTime - startTime;
		System.out.println("Simulation took :" + execTime + " to finish.");

	}

}
