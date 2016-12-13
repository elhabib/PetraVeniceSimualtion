package poi.handeler;

import math.tools.DateHandeler;
import math.tools.Distribution;

public class BussStop extends PoI{

	String name;
	
	public BussStop(int id, double x, double y){
		super(id,x,y);
	}

	public BussStop(int id, double x, double y, String name){
		this(id,x,y);
		this.name = name;
	}
	
	public BussStop(int id, double x, double y, int incoming, int outgoing){
		super(id,x,y,incoming,outgoing);
	}

	public BussStop(int id, double x, double y, int incoming, int outgoing, Distribution incomingDistribution, Distribution outgoingDistribution){
		super(id,x,y,incoming,outgoing,incomingDistribution,outgoingDistribution);
	}
}