package OnlineReading;

public class GateOpening {
	private String station;
	private int id;
	private int validations;
	
	public GateOpening(String s, int id, int validation){
		this.station = s;
		this.id = id;
		this.validations = validation;
	}
	
	public String getStation(){
		return station;
	}
	
	public int getId(){
		return id;
	}
	
	public int getValidations(){
		return validations;
	}
}
