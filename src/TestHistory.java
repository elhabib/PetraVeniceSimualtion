import simulation.History;

public class TestHistory {

	public static void main(String[] args) {
		History h = new History();
		for(int i = 0;i<1; i++){
			h.addData(i);
		}
		System.out.println(h);
	}

}
