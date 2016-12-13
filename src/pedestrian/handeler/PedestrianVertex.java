package pedestrian.handeler;
import java.util.*;

import math.tools.Flows;
import math.tools.MatrixTool;


public final class PedestrianVertex{
	private int id;
	private double x,y;
	private ArrayList<PedestrianArc> arcs;
	private double[] prio;
	private double[][] P;
	private double[] flow;
	private String type;
	private double accessRate;

	/**
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @throws Exception 
	 */
	public PedestrianVertex(int id, double x, double y) throws Exception{
		this.id = id;
		this.x = x;
		this.y = y; 
		arcs = new ArrayList<PedestrianArc>();
		initializeB();
		updatePrio();
	}

	/**
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param string
	 * @throws Exception 
	 */
	public PedestrianVertex(int id, double x, double y, String string) throws Exception {
		this(id,x,y);
		this.type = string;
	}

	/**
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param arcs
	 * @throws Exception
	 */

	public PedestrianVertex(int id, double x, double y, ArrayList<PedestrianArc> arcs) throws Exception{
		this(id,x,y);
		this.arcs = arcs;
		initializeB();
		updatePrio();
		flow = new double[2*arcs.size()];
	}


	/**
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param arcs
	 * @param prio
	 * @param B
	 * @throws Exception
	 */
	public PedestrianVertex(int id, double x, double y, ArrayList<PedestrianArc> arcs, double[][] B) throws Exception{
		this(id,x,y);
		this.arcs = arcs;
		this.P = B;
		updatePrio();
		this.flow = new double[2*arcs.size()];
	}


	/**
	 * @throws Exception 
	 * 
	 */
	private void initializeB() throws Exception{
		int n = arcs.size();
		if (n == 0){
			return;
		}
		P = new double[n][n];
		if (n != 1){
			for(int i =0;i<n;i++){
				for (int j=0;j<n;j++){
					P[i][j] = (i == j) ? 0 : 1./(n-1);
				}
			}}else{
				P[0][0] = 1;
			}
	}

	/**
	 * @throws Exception 
	 * 
	 */
	private void updatePrio() throws Exception{
		int n =arcs.size();
		if (n <= 1){
			this.prio = new double[1];
			prio[0] = 1;
		}else if (n == 2){
			prio = new double[2];
			prio[0] = 1;
			prio[1] = 1;
		}else{
			prio = new double[n];
			double[][] M = P;
			int i = 0;
			while(MatrixTool.norm(MatrixTool.substract(P[0],P[1])) >= 0.001 && i <100){
				M= MatrixTool.multiply(M, P);
				i++;
			}
			this.prio = M[1];
		}
			
	}

	/**
	 * 
	 * @return
	 */
	public int getID(){
		return id;
	}


	/**
	 * 
	 * @return
	 */
	public double getX(){
		return x;
	}

	/**
	 * 
	 * @return
	 */
	public double getY(){
		return y;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}

	/**
	 * 
	 * @return
	 */
	public double getAccessRate(){
		return accessRate;
	}

	/**
	 * 
	 * @return
	 */
	public double[][] getP(){
		return P;
	}


	public double[] getPrio(){
		return prio;
	}
	/**
	 * 
	 * @param P
	 * @throws Exception
	 */
	public void updateP(double P[][]) throws Exception{
		if (arcs.size() == P.length){
			this.P = P;
		}else{
			throw new Exception("The size of the fraction matrix is different from"
					+ " the number of links");
		}
		updatePrio();
	}
	
	
	/**
	 * 
	 * @param id2
	 * @return
	 */
	public PedestrianArc getArc(int id2) {
		for(PedestrianArc a:arcs){
			if(id == a.getFrom() && a.getTo() == id2){
				return a;
			}
			if(id == a.getTo() && a.getFrom() == id2){
				return a;
			}
		}
		return null;
	}
	
	
	public int getArcIndex(int id2) {
		int i = 0;
		for(PedestrianArc a:arcs){
			if(id == a.getFrom() && a.getTo() == id2){
				return i;
			}
			if(id == a.getTo() && a.getFrom() == id2){
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public int[] getIDsOfEnds(){
		int[] res = new int[arcs.size()];
		int i = 0;
		for(PedestrianArc a:arcs){
			if(id == a.getFrom()){
				res[i] = a.getToVertex().getID();
			}
			if(id == a.getTo()){
				res[i] = a.getFromVertex().getID();
			}
			i++;
		}
		return res;
	}
	/**
	 * 
	 * @return
	 */
	public ArrayList<PedestrianArc> getArcs(){
		return arcs;
	}

	/**
	 * 
	 * @param accessRate
	 */
	public void setAccessRate(double accessRate){
		this.accessRate = accessRate;
	}


	/**
	 * 
	 * @param a
	 * @throws Exception 
	 */
	public void addArc(PedestrianArc a) throws Exception {
		arcs.add(a);
		initializeB();
		updatePrio();
		flow = new double[arcs.size()*2];
	}

	/**
	 * 
	 * @param deltaT
	 * @throws Exception
	 */
	public void executeStep(double deltaT) throws Exception{
		int n =arcs.size();
		if(n == 1)
			return;
		for (int i= 0;i<n;i++){
			if(arcs.get(i).getFrom() != this.id){
				int lastCell = arcs.get(i).getNumberCells()-1;
				double[] q = {flow[i],flow[i+n]};
				arcs.get(i).getCell(lastCell).executeStep(deltaT, q);
			}else{
				double[] q = {flow[i+n],flow[i]};
				arcs.get(i).getCell(0).executeStep(-deltaT,q);
			}
		}
	}


	/**
	 * @throws Exception 
	 * 
	 */
	/*public void updateParameters(ArrayList<PedestrianDest> dests, double t) throws Exception {
		if (arcs.size() <= 2)
			return;
		double[] destinations = new double[arcs.size()];
		for(int i = 0; i< arcs.size(); i++)
			destinations[i] = 1.;
		for(int i = 0; i< dests.size() ; i++){
			PedestrianDest dest = dests.get(i);
			int closest = -1;
			double minDis = Double.MAX_VALUE;
			double[] destc = new double[2];
			destc[0] = dest.getX();
			destc[1] = dest.getY();
			if (arcs.size() <= 0)
				return;
			for(int j = 0; j < arcs.size(); j++){
				PedestrianArc pArc = arcs.get(j);
				PedestrianVertex pVer;
				if (pArc.getFromVertex().getID() != this.id)
					pVer = pArc.getFromVertex();
				else
					pVer = pArc.getToVertex();
				if (pVer.getID() > this.id && !pArc.isDirectionOpen(0))
					continue;
				if (pVer.getID() < this.id && !pArc.isDirectionOpen(1))
					continue;
				double[] pVerc = new double[2];
				pVerc[0] = pVer.getX();
				pVerc[1] = pVer.getY();
				double dis = MatrixTool.distance(destc, pVerc);
				if( dis <= minDis){
					minDis = dis;
					closest = j;
				}
			}
			if(closest == -1)
				continue;
			destinations[closest] += dest.getVistorsAtTime(t);
		}
		double sumDests = MatrixTool.sum(destinations);
		
		/*
		 * Keeping only two number after the dot
		 */
		/*for(int i = 0; i < arcs.size()  ; i++){
			double sum = 0;
			for(int j= 0; j< arcs.size() ; j++){
				if (i != j){
					P[i][j] =  destinations[j]/(sumDests-destinations[i]);
					//P[i][j] =  Math.floor(P[i][j]*100.)/100.;
					sum += P[i][j];
				}else{
					P[i][j] = 0;
				}
			}
			//P[i][arcs.size()-1] = 1. - sum;
		}
		/*double sum = 0;
		for(int j= 0; j< arcs.size() - 2; j++){
				P[arcs.size() - 1][j] =  destinations[j]/(sumDests-destinations[arcs.size() - 1]);
				P[arcs.size() - 1][j] =  Math.floor(P[arcs.size() - 1][j]*100.)/100.;
				sum += P[arcs.size() - 1][j];
		}
		P[arcs.size() - 1][arcs.size()-2] = 1. - sum;*/
		
		
		/*updatePrio();
		//System.out.println("P = " +MatrixTool.toString(P));
	}*/

	private double[] computeFlows() throws Exception{
		int n =arcs.size(); 
		double d[][] = new double[n][2];
		for (int i= 0;i<n;i++){
			if(arcs.get(i).getFrom() != this.id){
				int lastCell = arcs.get(i).getNumberCells()-1;
				d[i][0] = arcs.get(i).getCell(lastCell).getDensities()[0];
				d[i][1] = arcs.get(i).getCell(lastCell).getDensities()[1];
			}else{
				d[i][0] = arcs.get(i).getCell(0).getDensities()[1];
				d[i][1] = arcs.get(i).getCell(0).getDensities()[0];
			}
		}
		//System.out.println("d = " + MatrixTool.toString(d));
		double q[] = Flows.IFNode( d, n, P, prio);
		//System.out.println("q = " + MatrixTool.toString(q));
		return q;
	}

	public void preStep() throws Exception{
		if(arcs.size() > 1){
			flow = computeFlows();
		}
	}


	public String toString(){
		int n =arcs.size(); 
		if ( n <= 0)
			return "Node " + id + "is isolated";
		double d[][] = new double[n][2];
		for (int i= 0;i<n;i++){
			if(arcs.get(i).getFrom() != this.id){
				int lastCell = arcs.get(i).getNumberCells()-1;
				try {
					d[i][0] = arcs.get(i).getCell(lastCell).getDensities()[0];
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					d[i][1] = arcs.get(i).getCell(lastCell).getDensities()[1];
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					d[i][0] = arcs.get(i).getCell(0).getDensities()[1];
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					d[i][1] = arcs.get(i).getCell(0).getDensities()[0];
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "**Node :" + id + ":\n" + " d = " + MatrixTool.toString(d) + "\n flow = " + MatrixTool.toString(flow) +
				"\n"; 
	}

	

}
