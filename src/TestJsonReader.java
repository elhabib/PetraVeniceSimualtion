import java.io.*;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TestJsonReader {

	public static void main(String[] args) throws FileNotFoundException {
		try {
			FileReader f = new FileReader("../../DataAndReports/Cartography/Cartography/GeoJSON/NODES.geojson");
			JSONParser parser = new JSONParser();
			JSONObject jsonobj = (JSONObject) parser.parse(f);
			JSONArray nodes = (JSONArray) jsonobj.get("features");
			Iterator<JSONObject> iterator = nodes.iterator();
			int i = 0;
			while(iterator.hasNext()){
				
				JSONObject obj = (JSONObject) iterator.next();
				JSONObject jprop = (JSONObject) obj.get("properties");
				Long sid = (Long) jprop.get("NODE_ID");
				int id = sid.intValue();
				
				JSONObject jgeo = (JSONObject) obj.get("geometry");
				JSONArray jcor = (JSONArray) jgeo.get("coordinates");
				Double x = (Double) jcor.get(0);
				Double y = (Double) jcor.get(1);
				String typeA = (String) jgeo.get("type");
				
				System.out.println(x + " , " + y + " : " + typeA);
				
				i++;
				
				System.out.println("row : " + i);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			FileReader f = new FileReader("../../DataAndReports/"
					+ "Cartography/Cartography/GeoJSON/"
					+ "POIREDUCED.geojson");
			JSONParser parser = new JSONParser();
			JSONObject jsonobj = (JSONObject) parser.parse(f);
			JSONArray pois = (JSONArray) jsonobj.get("features");
			Iterator<JSONObject> iterator = pois.iterator();
			int j = 0;
			while(iterator.hasNext()){
				
				JSONObject obj = (JSONObject) iterator.next();
				JSONObject jprop = (JSONObject) obj.get("properties");
				Long sid = (Long) jprop.get("POI_ID");
				int id = sid.intValue();
				
				String name = (String) jprop.get("POI_NAME");
				
				JSONObject jgeo = (JSONObject) obj.get("geometry");
				
				JSONArray jcor = (JSONArray) jgeo.get("coordinates");
				Double x = (Double) jcor.get(0);
				Double y = (Double) jcor.get(1);
				
				String typeA = (String) jgeo.get("type");
				
				System.out.println(" id: " + id + " name: "+ name + " x: "+ x + ", y: " + y);
				
				j++;
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
