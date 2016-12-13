package pedestrian.handeler;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import scala.collection.concurrent.Debug;

/**
 * 
 * @author Elhabib Moustaid
 *
 */
public class PedestrianNetwork {
	private ArrayList<PedestrianVertex> vertexes;
	private ArrayList<PedestrianArc> arcs;


	/**
	 * 
	 */
	public PedestrianNetwork(){
		vertexes = new ArrayList<PedestrianVertex>();
		arcs = new ArrayList<PedestrianArc>();
	}

	/**
	 * 
	 * @param fvertexes
	 * @param farcs
	 * @param fpois
	 * @param xboundries
	 * @param yboundries
	 * @throws Exception
	 */
	public PedestrianNetwork(String fvertexes, String farcs, String fpatterns, double[] xboundries, double[] yboundries) throws Exception{
		/** Nodes **/
		this();
		HashMap<Integer,PedestrianVertex> existingVertexes = new HashMap<Integer,PedestrianVertex>();
		FileReader f;
		try {
			f = new FileReader(fvertexes);
			JSONParser parser = new JSONParser();
			JSONObject jsonobj = (JSONObject) parser.parse(f);
			JSONArray nodes = (JSONArray) jsonobj.get("features");
			@SuppressWarnings("unchecked")
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
				if ( (x >= xboundries[0] && x <= xboundries[1]) && y >= yboundries[0] && y <= yboundries[1] ){
					PedestrianVertex pv = new PedestrianVertex(id,x,y,typeA);
					vertexes.add(pv);
					existingVertexes.put(id,pv);
					i++;
				}
			}
			System.out.println("Number of nodes is: "+i);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/** Arcs **/
		try {
			HashMap<Integer,Integer> existingArcs = new HashMap<Integer,Integer>();
			f = new FileReader(farcs);
			JSONParser parser = new JSONParser();
			JSONObject jsonobj = (JSONObject) parser.parse(f);
			JSONArray roads = (JSONArray) jsonobj.get("features");
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator = roads.iterator();
			while(iterator.hasNext()){

				JSONObject obj = (JSONObject) iterator.next();
				JSONObject jprop = (JSONObject) obj.get("properties");
				Long sid = (Long) jprop.get("ROAD_ID");
				int id = sid.intValue();

				double width = (Double) jprop.get("SM_WIDTH");				
				double length = (Double) jprop.get("LENGTH");

				Long jfrom = (Long) jprop.get("FROM_NODE");
				int from = jfrom.intValue();

				Long jto = (Long) jprop.get("TO_NODE");
				int to = jto.intValue();

				int Max = Math.max(from, to);
				int Min = Math.min(from, to);
				from = Min;
				to = Max;

				JSONObject jgeo = (JSONObject) obj.get("geometry");
				String typeA = (String) jgeo.get("type");

				if(existingVertexes.containsKey(from) && existingVertexes.containsKey(to))
				{
					if(existingArcs.containsKey(from)){	
						if (existingArcs.get(from) != to){
							arcs.add(new PedestrianArc(id, existingVertexes.get(from), existingVertexes.get(to),length, width,typeA));
							existingArcs.put(from, to);
						}
					}else{
						arcs.add(new PedestrianArc(id, existingVertexes.get(from), existingVertexes.get(to),length, width,typeA));
						existingArcs.put(from, to);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/** Patterns **/
		FileInputStream fstream = new FileInputStream(fpatterns);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		strLine = br.readLine();
		while ((strLine = br.readLine()) != null)   {
			String[] data = strLine.split(";");
			PedestrianArc a = getArc(new Integer(data[0]));
			if (a != null)
				a.setColor(new Integer(data[3]));
		}
		br.close();


		HashMap<PedestrianVertex,Boolean> toRemove = new HashMap<PedestrianVertex,Boolean>();
		for (PedestrianVertex v:vertexes){
			int occ = 0;
			for(PedestrianArc a:arcs){
				if(v.getID() == a.getFrom() || v.getID() == a.getTo() ){
					v.addArc(a);
					occ++;
				}
			}
			if (occ == 0)
				toRemove.put(v, true);
		}

		for(PedestrianVertex v:toRemove.keySet()){
			vertexes.remove(v);
		}
	}

	public PedestrianNetwork(String fvertexes, String farcs, String fpatterns, double[] xboundries, double[] yboundries, int dumb ) throws Exception{
		/** Nodes **/
		this();
		HashMap<Integer,PedestrianVertex> existingVertexes = new HashMap<Integer,PedestrianVertex>();
		FileReader f;
		try {
			f = new FileReader(fvertexes);
			JSONParser parser = new JSONParser();
			JSONObject jsonobj = (JSONObject) parser.parse(f);
			JSONArray nodes = (JSONArray) jsonobj.get("features");
			@SuppressWarnings("unchecked")
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
				if ( (x >= xboundries[0] && x <= xboundries[1]) && y >= yboundries[0] && y <= yboundries[1] ){
					PedestrianVertex pv = new PedestrianVertex(id,x,y,typeA);
					vertexes.add(pv);
					existingVertexes.put(id,pv);
					i++;
				}
			}
			System.out.println("Number of nodes is: "+i);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/** Arcs **/
		try {
			HashMap<Integer,Integer> existingArcs = new HashMap<Integer,Integer>();
			f = new FileReader(farcs);
			JSONParser parser = new JSONParser();
			JSONObject jsonobj = (JSONObject) parser.parse(f);
			JSONArray roads = (JSONArray) jsonobj.get("features");
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator = roads.iterator();
			while(iterator.hasNext()){

				JSONObject obj = (JSONObject) iterator.next();
				JSONObject jprop = (JSONObject) obj.get("properties");
				Long sid = (Long) jprop.get("ROAD_ID");
				int id = sid.intValue();

				double width = (Double) jprop.get("SM_WIDTH");				
				double length = (Double) jprop.get("LENGTH");
				

				Long jfrom = (Long) jprop.get("FROM_NODE");
				int from = jfrom.intValue();

				Long jto = (Long) jprop.get("TO_NODE");
				int to = jto.intValue();


				JSONObject jgeo = (JSONObject) obj.get("geometry");
				Double xFrom, yFrom, xTo, yTo;
				
				JSONArray allCoords = (JSONArray) jgeo.get("coordinates");
				Iterator<JSONArray> cordIt = allCoords.iterator();
				JSONArray cordObj = (JSONArray) cordIt.next();
				
				xFrom = (Double) cordObj.get(0);
				yFrom = (Double) cordObj.get(1);
				
				while(cordIt.hasNext()){
					cordObj = (JSONArray) cordIt.next();
				}
				
				xTo = (Double) cordObj.get(0);
				yTo = (Double) cordObj.get(1);
				
				String typeA = (String) jgeo.get("type");
				
				if(existingVertexes.containsKey(from) && existingVertexes.containsKey(to))
				{
					
					existingVertexes.get(from).setX(xFrom);
					existingVertexes.get(from).setY(yFrom);
					
					existingVertexes.get(to).setX(xTo);
					existingVertexes.get(to).setY(yTo);
					
					int Max = Math.max(from, to);
					int Min = Math.min(from, to);
					from = Min;
					to = Max;
					
					if(existingArcs.containsKey(from)){	
						if (existingArcs.get(from) != to){
							arcs.add(new PedestrianArc(id, existingVertexes.get(from), existingVertexes.get(to),length, width,typeA));
							existingArcs.put(from, to);
						}
					}else{
						arcs.add(new PedestrianArc(id, existingVertexes.get(from), existingVertexes.get(to),length, width,typeA));
						existingArcs.put(from, to);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/** Patterns **/
		FileInputStream fstream = new FileInputStream(fpatterns);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		strLine = br.readLine();
		while ((strLine = br.readLine()) != null)   {
			String[] data = strLine.split(";");
			PedestrianArc a = getArc(new Integer(data[0]));
			if (a != null)
				a.setColor(new Integer(data[3]));
		}
		br.close();


		HashMap<PedestrianVertex,Boolean> toRemove = new HashMap<PedestrianVertex,Boolean>();
		for (PedestrianVertex v:vertexes){
			int occ = 0;
			for(PedestrianArc a:arcs){
				if(v.getID() == a.getFrom() || v.getID() == a.getTo() ){
					v.addArc(a);
					occ++;
				}
			}
			if (occ == 0)
				toRemove.put(v, true);
		}

		for(PedestrianVertex v:toRemove.keySet()){
			vertexes.remove(v);
		}
	}

	
	
	public PedestrianNetwork(ArrayList<PedestrianVertex> vertexes, ArrayList<PedestrianArc> arcs) throws Exception{
		this.vertexes = vertexes;
		this.arcs = arcs;
		for (PedestrianVertex v:vertexes){
			for(PedestrianArc a:arcs){
				if(v.getID() == a.getFrom() || v.getID() == a.getTo() ){
					v.addArc(a);
				}
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<PedestrianVertex> getVertexes(){
		return vertexes;
	}


	/**
	 * 
	 * @return
	 */
	public ArrayList<PedestrianArc> getArcs(){
		return arcs;
	}

	/**
	 * 
	 * @return
	 */
	/**public ArrayList<PedestrianDest> getDests(){
		return dests;
	}**/


	/**public ArrayList<PedestrianOrigin> getOrigins(){
		return origins;
	}**/
	/**
	 * 
	 * @param a
	 */
	public void addArc(PedestrianArc a){
		//TODO Change this implementation
		arcs.add(a);
	}


	/**
	 * 
	 * @param v
	 */
	public void addVertex(PedestrianVertex v){
		//TODO Change this implementation
		vertexes.add(v);
	}

	/**
	 * 
	 * @param o
	 */
	/**public void addOrigin(PedestrianOrigin o){
		origins.add(o);
	}**/

	/**public void addDest(PedestrianDest d){
		dests.add(d);
	}


	/**
	 * 
	 * @param firstVertex
	 * @param lastVertex
	 * @param time
	 * @throws Exception
	 */
	/**public void updateParametersVertexes(int firstVertex, int lastVertex, double time) throws Exception{
		List<PedestrianVertex> sub = vertexes.subList(firstVertex, lastVertex);
		for (PedestrianVertex v:sub){
			v.updateParameters(dests, time);
		}
	}**/

	/**
	 * 
	 * @throws Exception
	 */
	public void preStep(double t) throws Exception {
		for (int i=0; i<vertexes.size() ;i++){
			vertexes.get(i).preStep();
		}
		for (int i=0; i<arcs.size() ;i++){
			arcs.get(i).preStep();
		}
		/**for (int i=0;i<origins.size(); i++){
			origins.get(i).preStep(t);
		}**/
	}


	/**
	 * 
	 * @param deltaT
	 * @throws Exception
	 */
	public void executeStep(double deltaT) throws Exception{
		for (int i=0; i<vertexes.size() ;i++){
			vertexes.get(i).executeStep(deltaT);
		}
		for (int i=0; i<arcs.size() ;i++){
			arcs.get(i).executeStep(deltaT);
		}
	}

	/**
	 * 
	 * @param ID
	 * @return
	 * @throws Exception
	 */
	public PedestrianArc getArc(int ID) throws Exception {
		for(PedestrianArc a:arcs){
			if(a.getID() == ID){
				return a;
			}
		}
		//System.out.println("Arc: "+ ID +" does not exist");
		return null;
	}

	/**
	 * 
	 * @param ID
	 * @return
	 * @throws Exception
	 */
	public PedestrianVertex getVertex(int ID) throws Exception {
		for(PedestrianVertex v:vertexes){
			if(v.getID() == ID){
				return v;
			}
		}
		System.out.println("Vertex: "+ ID +" does not exist");
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public Graph toGraph(){
		Graph g = new SingleGraph("Pedestrian");
		for (PedestrianVertex v: vertexes){
			String s = "" + v.getID();
			g.addNode(s);
		}
		for (PedestrianArc a: arcs){
			String s1 = "" + a.getID();
			String s2 = "" + a.getFrom();
			String s3 = "" + a.getTo();
			g.addEdge(s1, s2, s3);
		}
		return g;
	}

	/**
	 * 
	 */
	public String toString(){
		String res = "Arcs";
		for (int i=0; i < arcs.size() ;i++){
			res = res + arcs.get(i).toString();
		}
		res = res + "\n";
		return res;
	}

	/**public PedestrianDest getDests(int ID) {
		for(int i=0; i<dests.size();i++){
			if(dests.get(i).getID() == ID){
				return dests.get(i);
			}
		}
		return null;

	}**/
}