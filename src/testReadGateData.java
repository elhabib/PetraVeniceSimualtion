import OnlineReading.GateOpenings;

public class testReadGateData{  

	public static void main(String args[])
	{

		try {
			
			GateOpenings go = new GateOpenings("./input/inputdata.xml");
			int t = go.getTravellersAtThisTime();
			System.out.println("Number of travellers is : " + t);
			
		} catch (Throwable t) {
			t.printStackTrace();
		}

	}
}
