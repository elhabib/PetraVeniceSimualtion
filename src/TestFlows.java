import math.tools.Flows;
import math.tools.MatrixTool;

public class TestFlows {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		int n = 3;
		double P[][] = {{0,0.36,0.64},{0.25,0.,0.75},{0.52,0.48,0}};
		double M[][] = P;
		double prio[];
		int i = 0;
		while(MatrixTool.norm(MatrixTool.substract(M[0],M[1])) >= 0.001 && i <100){
			M= MatrixTool.multiply(M, P);
			i++;
		}
		prio = M[0];
		double rMax = Flows.rMax;
		double d[][] = {{3*rMax/4,rMax/4},{2*rMax/5,rMax/5},{rMax/6,5*rMax/6}};
		double[] q = Flows.IFNode(d, n, P, prio);
		System.out.println("q = " + MatrixTool.toString(q));
		}
}
