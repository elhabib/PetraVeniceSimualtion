import java.util.*;

import math.tools.Flows;
import math.tools.MatrixTool;
import pedestrian.handeler.PedestrianArc;
import pedestrian.handeler.PedestrianVertex;

public class TestArc {

	public static void main(String[] args) throws Exception {


		PedestrianArc pa = new PedestrianArc(0,new PedestrianVertex(0,1,1), new PedestrianVertex(1,2,2), 1500, 2, "");
		
		double[][] tt = new double[11][11];
		
		for(double p1 = 0.; p1<= 1.; p1+=0.1){
			for(double p2 = 0.; p2<1.-p1;p2+=0.1){
				for(int i = 0; i<pa.getCells().length;i++){
					pa.getCell(i).setDensity(0, p1*Flows.rMax);
					pa.getCell(i).setDensity(1, p2*Flows.rMax);
				}
				pa.preStep();
				tt[(int)(p1/0.1)][(int)(p2/0.1)] = pa.getTravelTimeNow()/60;
			}
		}
		//pa.preStep();
		//System.out.println(pa);
		//System.out.println("travel time = " +pa.getTravelTimeNow()/60);
		System.out.println("Travel Times are :\n" + MatrixTool.toString(tt));
	}
}

