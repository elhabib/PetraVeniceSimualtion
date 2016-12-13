package poi.handeler;

import math.tools.Distribution;

public class VisitLoc extends PoI{
	
	
	public VisitLoc(int id, double x, double y){
		super(id,x,y);
	}
	
	public VisitLoc(int id, double x, double y, int incoming, int outgoing){
		super(id,x,y,incoming,outgoing);
	}
	
	public VisitLoc(int id, double x, double y, int incoming, int outgoing, Distribution incomingDistribution, Distribution outgoingDistribution){
		super(id,x,y,incoming,outgoing,incomingDistribution,outgoingDistribution);
	}
}
