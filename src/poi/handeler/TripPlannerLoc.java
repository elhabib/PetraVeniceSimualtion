package poi.handeler;

import math.tools.Distribution;

public class TripPlannerLoc extends PoI{
	
	public String idString;
	
	public TripPlannerLoc(int id, double x, double y){
		super(id,x,y);
	}
	
	
	public TripPlannerLoc(int id, double x, double y, int incoming, int outgoing){
		super(id,x,y,incoming,outgoing);
	}
	
	public TripPlannerLoc(int id, double x, double y, int incoming, int outgoing, Distribution incomingDistribution, Distribution outgoingDistribution){
		super(id,x,y,incoming,outgoing,incomingDistribution,outgoingDistribution);
	}


	public TripPlannerLoc(int id, double x, double y, String idString2) {
		this(id,x,y);
		idString = idString2;
	}
	
	public String toString(){
		return idString;
	}
}
