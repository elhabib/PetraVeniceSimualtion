package simulation;

import java.util.LinkedList;

public class History {
	LinkedList<Integer> data;
	
	public History(){
		data = new LinkedList<Integer>();
	}
	
	public void addData(int d){
		data.add(d);
	}
	
	public String toString(){
		if (data.size() == 0)
			return "";
		StringBuilder sb = new StringBuilder("");
		for(double d:data){
			sb.append((int) d);
			sb.append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		return sb.toString();
	}

	public int getData(int index) {
		return data.get(index);
	}
	
}
