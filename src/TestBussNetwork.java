import poi.handeler.BussNetwork;

public class TestBussNetwork {

	public static void main(String[] args) {
		
		BussNetwork nt = new BussNetwork("./input/TicketValidations.csv");
		nt.ticketValidationByStop();
	}

}