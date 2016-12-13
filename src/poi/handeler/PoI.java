package poi.handeler;
import java.util.ArrayList;

import math.tools.Distribution;
import math.tools.Flows;
import math.tools.MatrixTool;
import pedestrian.handeler.PedestrianArc;
import pedestrian.handeler.PedestrianVertex;

public abstract class PoI {

	protected int id;
	protected double x,y;
	protected PedestrianVertex closestVertex;
	protected ArrayList<PedestrianArc> closestArcs;
	protected int incoming;
	protected int outgoing;
	protected int type;
	protected double incomingAtT = 0, outgoingAtT = 0;
	protected Distribution incomingDistribution = null, outgoingDistribution = null;
	protected String name;


	public PoI(int id, double x, double y){
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public PoI(int id, double x, double y, int incoming, int outgoing){
		this(id,x,y);
		this.incoming = incoming;
		this.outgoing = outgoing;
	}

	public PoI(int id, double x, double y, int incoming, int outgoing, Distribution incomingDistribution, Distribution outgoingDistribution){
		this(id,x,y,incoming,outgoing);
		this.incomingDistribution = incomingDistribution;
		this.outgoingDistribution = outgoingDistribution;
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
	public Distribution getIncomingDistribution(){
		return incomingDistribution;
	}


	/**
	 * 
	 * @return
	 */
	public Distribution getOutgoingDistribution(){
		return outgoingDistribution;
	}


	/**
	 * 
	 * @param d
	 */
	public void setIncomingDistribution(Distribution d){
		this.incomingDistribution = d;
	}


	/**
	 * 
	 * @param d
	 */
	public void setOutgoingDistribution(Distribution d){
		this.outgoingDistribution = d;
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
	 * @param x
	 */
	public void setX(double x){
		this.x = x;
	}

	/**
	 * 
	 * @return
	 */
	public double getY(){
		return y;
	}

	/**
	 * 
	 * @param y
	 */
	public void setY(double y){
		this.y = y;
	}


	/**
	 * 
	 * @return
	 */
	public int getIncomingTravelers(){
		return incoming;
	}

	/**
	 * 
	 * @return
	 */
	public int getOutgoingTravelers(){
		return outgoing;
	}

	/**
	 * 
	 * @param 
	 */

	public void setIncoming(int tr){
		this.incoming = tr;
	}

	/**
	 * 
	 * @param 
	 */

	public void setOutgoing(int tr){
		this.outgoing = tr;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}


	public void distributeIncoming(int t) throws Exception{
		int travelers = getIncomersAtTime(t);
		if (travelers == 0)
			return;
		double s = closestArcs.get(0).getWidth()*closestArcs.get(0).getLastCell().getLength();
		double r = ((double) travelers) / s;
		int openLinks = 0;
		for(PedestrianArc a:closestArcs){
			if(a.getFrom() == closestVertex.getID()){
				if (a.isDirectionOpen(0))
					openLinks++;
			}else{
				if (a.isDirectionOpen(1))
					openLinks++;
			}
		}
		if(openLinks == 0)
			return;
		for(PedestrianArc a:closestArcs){
			if(a.getFrom() == closestVertex.getID()){
				if (! a.isDirectionOpen(0))
					continue;
				double ri = r/openLinks;
				if(a.getID() == 0){
				}
				int i = 0;
				while(ri > 0 && i < a.getNumberCells()){
					double ralloc = Math.min(Flows.rMax - a.getCell(i).getSumDensities(),ri);
					ri-=ralloc;
					a.getCell(i).setDensity(0, a.getCell(i).getDensity(0) + ralloc);
					i++;
				}
			}else{
				if (! a.isDirectionOpen(1))
					continue;
				double ri = r/openLinks;
				if(a.getID() == 1){
				}
				int i = a.getNumberCells()-1;
				while(ri > 0 && i >= 0){
					double ralloc = Math.min(Flows.rMax - a.getCell(i).getSumDensities(),ri);
					ri-=ralloc;
					a.getCell(i).setDensity(1, a.getCell(i).getDensity(1) + ralloc);
					i--;
				}
			}
		}
	}

	public void absorbOutgoing(int t) throws Exception{
		int travelers = getOutgoersAtTime(t);
		if( travelers == 0)
			return;
		double s = closestArcs.get(0).getWidth()*closestArcs.get(0).getLastCell().getLength();
		double r = (double) travelers / s;
		int openLinks = closestArcs.size();
		for(PedestrianArc a:closestArcs){
			if(a.getFrom() == closestVertex.getID()){
				double ri = r/openLinks;
				int i = 0;
				while(ri > 0 && i < a.getNumberCells()){
					double ralloc = Math.min(a.getCell(i).getDensity(1),ri);
					ri-=ralloc;
					a.getCell(i).setDensity(1, a.getCell(i).getDensity(1) - ralloc);
					i++;
				}
			}else{
				double ri = r/openLinks;
				int i = a.getNumberCells()-1;
				while(ri > 0 && i >= 0){
					double ralloc = Math.min(a.getCell(i).getDensity(0),ri);
					ri-=ralloc;
					a.getCell(i).setDensity(0, a.getCell(i).getDensity(0) - ralloc);
					i--;
				}
			}
		}
	}

	/**
	 * 
	 * @param PedestrianVertexes
	 * @throws Exception
	 */
	public void findClosetPedestrianVertexAndArcs(ArrayList<PedestrianVertex> PedestrianVertexes) throws Exception{
		double dist = Double.MAX_VALUE;
		double destcords[] = {x,y};
		double distbis;
		for(PedestrianVertex pv:PedestrianVertexes){
			double pvcords[] = {pv.getX(),pv.getY()};
			distbis = MatrixTool.distance(pvcords, destcords);
			if (distbis <= dist){
				closestVertex = pv;
				dist = distbis;
			}
		}
		closestArcs = closestVertex.getArcs();
	}

	public PedestrianVertex getClostPedestrianVertex(){
		return closestVertex;
	}

	private int getIncomersAtTime(int t){
		incomingAtT += incomingDistribution.getRealization(t)*incoming;
		if (t % (60*5) == 0){
			int ret = (int) Math.round(incomingAtT);
			incomingAtT = 0.;
			return ret;
		}else{
			return 0;
		}
	}

	private int getOutgoersAtTime(int t){
		outgoingAtT += outgoingDistribution.getRealization(t)*outgoing;
		if (t % (60*5) == 0){
			int ret = (int) Math.round(outgoingAtT);
			outgoingAtT = 0;
			return ret;
		}else{
			return 0;
		}
	}

	public void preStep(double t){
		try {
			absorbOutgoing((int) t);
		} catch (Exception e) {
			System.out.println("Error in absorb outgoings at PoI:"+ id);
			e.printStackTrace();
		}
		try {
			distributeIncoming((int) t);
		} catch (Exception e) {
			System.out.println("Error in distributing incomings at PoI:"+ id);
			e.printStackTrace();
		}
	}

	public void executeStep(double t){

	}

}