package paths;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import math.tools.MatrixTool;
import pedestrian.handeler.PedestrianArc;
import pedestrian.handeler.PedestrianNetwork;
import pedestrian.handeler.PedestrianVertex;
import poi.handeler.Visit;
import poi.handeler.Visits;

public class PathCalculator {

	private int N;
	private double adjacency[][];
	private double weight[][];
	public double distance[][];
	public int next[][];
	private PedestrianVertex pedestrianVertexes[];

	public PathCalculator(PedestrianNetwork pn){
		N = pn.getVertexes().size();
		adjacency = new double[N][N];
		weight = new double[N][N];
		distance = new double[N][N];
		next = new int[N][N];
		pedestrianVertexes = new PedestrianVertex[N];
		for(int i = 0; i<N; i++){
			PedestrianVertex v = pn.getVertexes().get(i);
			pedestrianVertexes[i] = v;
		}
		for(int i=0; i<N; i++){
			for(int j=0; j<N ; j++){
				adjacency[i][j] = 0;
				weight[i][j] = 0;
				distance[i][j] = 0;
				next[i][j] = 0;
			}
		}
		for(int i = 0; i<N; i++){
			PedestrianVertex v = pedestrianVertexes[i];
			for(PedestrianArc a:v.getArcs()){
				PedestrianVertex toV;
				if (a.getToVertex() != v)
					toV = a.getToVertex();
				else
					toV = a.getFromVertex();
				try {
					int j = getPedestrianVertexLocalIndex(toV);
					adjacency[i][j] = 1;
					adjacency[j][i] = 1;
					if (a.getColor() == 0){
						weight[i][j] = 10.*a.getLength();
						weight[j][i] = 10.*a.getLength();
					}else if(a.getColor() == -1){
						weight[i][j] = 5.*a.getLength();
						weight[j][i] = 5.*a.getLength();
					}else if(a.getColor() == 1){
						weight[i][j] = 1.*a.getLength();
						weight[j][i] = 1.*a.getLength();
					}else{
						throw new Exception("no color for arc (id): " + a.getID() );
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	public void computePaths(){
		System.out.println("- Computing paths");
		System.out.println("-- Initializing data");
		for(int i = 0; i<N; i++){
			for(int j = 0; j<N ; j++){
				if(adjacency[i][j] ==1){
					distance[i][j] = weight[i][j];
					next[i][j] = j;
				}else{
					distance[i][j] = Double.MAX_VALUE;
					next[i][j] = -1;
				}
			}
			distance[i][i] = 0;
		}
		System.out.println("-- Data Initializiation done");
		for(int k = 0; k<N; k++){
			if (k % ((int) Math.ceil(N/100)+1) == 0){
				System.out.println("Computing : "+Math.floor(100*(double)k/(double)N)+" %done");
			}
			for(int i = 0; i<N; i++){
				for(int j = 0; j<N; j++){
					if(distance[i][j] > distance[i][k] + distance[k][j]){
						distance[i][j] = distance[i][k] + distance[k][j];
						next[i][j] = next[i][k];
					}
				}
			}
		}
		distance = null;
		weight = null;
		adjacency = null;
	}



	private static String matrixString(int[][] v){
		StringBuilder sb = new StringBuilder();
		sb.append("");
		for (int i = 0; i<v.length ;i++){
			for(int j= 0; j<v[0].length;j++){
				sb.append(v[i][j]);
				if ((j < v[0].length-1) )
					sb.append(",");
				else
					sb.append("");
			}
			if(i < v.length-1)
				sb.append("\n");
			else
				sb.append("");
		}
		return sb.toString();
	}

	public void savePath(String fileNameNext){
		try {
			PrintWriter writer2 = new PrintWriter(fileNameNext, "UTF-8");
			writer2.print(matrixString(next));
			writer2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}


	public void loadPaths(String fileNameNext){
		try {

			FileInputStream fstream = new FileInputStream(fileNameNext);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			int i = 0;
			while ((strLine = br.readLine()) != null)   {
				String[] data = strLine.split(",");
				for(int j = 0; j < data.length; j++ ){
					next[i][j] = new Integer(data[j]);
				}
				i++;
			}
			br.close();
			distance = null;
			weight = null;
			adjacency = null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<PedestrianVertex> FindPathBetween(PedestrianVertex v1, PedestrianVertex v2){
		int first = 0,last = 0;
		try {
			first = getPedestrianVertexLocalIndex(v1);
			last = getPedestrianVertexLocalIndex(v2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<PedestrianVertex> p = new ArrayList<PedestrianVertex>();
		if(next[first][last] == -1)
			return p;
		int k = first;
		p.add(pedestrianVertexes[k]);
		while(next[k][last] != last){
			k = next[k][last];
			p.add(pedestrianVertexes[k]);
		}
		p.add(pedestrianVertexes[last]);
		return p;
	}


	public ArrayList<PedestrianArc> FindPathBetweenInArcs(PedestrianVertex v1, PedestrianVertex v2){
		int first = 0,last = 0;
		try {
			first = getPedestrianVertexLocalIndex(v1);
			last = getPedestrianVertexLocalIndex(v2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<PedestrianArc> p = new ArrayList<PedestrianArc>();

		if (next[first][last] == -1)
			return p;
		if (next[first][last] == last){
			p.add(pedestrianVertexes[first].getArc(pedestrianVertexes[last].getID()));
			return p;
		}
		int k = first;
		int kOld;
		while(next[k][last] != last){
			kOld = k;
			k = next[k][last];
			p.add(pedestrianVertexes[kOld].getArc(pedestrianVertexes[k].getID()));
		}
		kOld = k;
		k = next[k][last];
		p.add(pedestrianVertexes[kOld].getArc(pedestrianVertexes[k].getID()));
		return p;
	}


	public void updateParameters(Visits visits, int h1, int h2) throws Exception{
		ArrayList<double[][]> allPs = new ArrayList<double[][]>();
		for (int i = 0; i < pedestrianVertexes.length; i++){
			int n = pedestrianVertexes[i].getArcs().size();
			double p[][] = new double[n][n];
			for(int j = 0; j < n; j++ ){
				for(int k = 0; k < n; k++){
					if(j!= k)
						p[j][k] = 1;
				}
			}
			allPs.add(p);
		}
		for(ArrayList<Visit> l:visits.getVisitsByVisitor()){
			for(int i = 0; i<l.size()-1; i++){
				Visit v1 = l.get(i), v2 = l.get(i+1);
				if(v1.isAtSameDayAs(v2) && v1.getPictureTakenTime().isWithin(h1,h2)){
					PedestrianVertex from = v1.getVisitLoc().getClostPedestrianVertex();
					PedestrianVertex to = v2.getVisitLoc().getClostPedestrianVertex();
					if (from == to){
						continue;
					}
					ArrayList<PedestrianVertex> path = FindPathBetween(from,to);
					Iterator<PedestrianVertex> it = path.iterator();
					PedestrianVertex current = it.next();
					while(it.hasNext()){
						PedestrianVertex next = it.next();
						int indexOfAbsorbant = current.getArcIndex(next.getID());
						int n = current.getArcs().size();
						int moverIndexInArray = getPedestrianVertexLocalIndex(current);
						for(int k = 0; k < n; k++){
							if(k != indexOfAbsorbant)
								allPs.get(moverIndexInArray)[k][indexOfAbsorbant]++;
						}
						current = next;
					}
				}
			}
		}

		for(double[][] p:allPs){
			for(int i = 0; i< p.length; i++){
				p[i][i] = 0;
			}
			for(int i = 0; i< p.length; i++){
				p[i] = MatrixTool.multiply(p[i], 1./MatrixTool.sum(p[i]));
			}
		}

		/*for(int i = 0; i < pedestrianVertexes.length; i++){
			pedestrianVertexes[i].updateP(allPs.get(i));
			System.out.println("Indexes of"+pedestrianVertexes[i].getID() + " = "
					+ MatrixTool.toString(pedestrianVertexes[i].getIDsOfEnds()));
			System.out.println("P"+pedestrianVertexes[i].getID() + " ="
					+ MatrixTool.toString(pedestrianVertexes[i].getP()));
		}*/
	}


	public void updateParametersBis(Visits visits, int h1, int h2) throws Exception{
		ArrayList<double[][]> allPs = new ArrayList<double[][]>();
		for (int i = 0; i < pedestrianVertexes.length; i++){
			int n = pedestrianVertexes[i].getArcs().size();
			double p[][] = new double[n][n];
			for(int j = 0; j < n; j++ ){
				for(int k = 0; k < n; k++){
					if(j!= k)
						p[j][k] = 1;
				}
			}
			allPs.add(p);
		}
		for(ArrayList<Visit> l:visits.getVisitsByVisitor()){
			for(int i = 0; i<l.size()-1; i++){
				Visit v1 = l.get(i), v2 = l.get(i+1);
				if(v1.isAtSameDayAs(v2) && v1.getPictureTakenTime().isWithin(h1,h2)){
					PedestrianVertex from = v1.getVisitLoc().getClostPedestrianVertex();
					PedestrianVertex to = v2.getVisitLoc().getClostPedestrianVertex();
					if (from == to){
						continue;
					}
					ArrayList<PedestrianVertex> path = FindPathBetween(from,to);
					if(path.size() == 2){
						continue;
					}
					Iterator<PedestrianVertex> it = path.iterator();
					PedestrianVertex pred = it.next();
					PedestrianVertex current = it.next();
					while(it.hasNext()){
						PedestrianVertex next = it.next();
						int indexOfPred = current.getArcIndex(pred.getID());
						int indexOfNext = current.getArcIndex(next.getID());
						int currentIndexInArray = getPedestrianVertexLocalIndex(current);
						allPs.get(currentIndexInArray)[indexOfPred][indexOfNext]++;
						pred = current;
						current = next;
					}
				}
			}
		}

		for(double[][] p:allPs){
			if (p.length == 1){
				p[0][0]= 1;
				continue;
			}
			if (p.length == 1){
				p[0][1] = 1;
				p[1][0] = 1;
				p[0][0] = 0;
				p[1][1] = 0;
				continue;
			}
			for(int i = 0; i< p.length; i++){
				p[i][i] = 0;
			}
			for(int i = 0; i< p.length; i++){
				p[i] = MatrixTool.multiply(p[i], 1./MatrixTool.sum(p[i]));
			}
		}

		/*for(int i = 0; i < pedestrianVertexes.length; i++){
			pedestrianVertexes[i].updateP(allPs.get(i));
			System.out.println("Indexes of"+pedestrianVertexes[i].getID() + " = "
					+ MatrixTool.toString(pedestrianVertexes[i].getIDsOfEnds()));
			System.out.println("P"+pedestrianVertexes[i].getID() + " ="
					+ MatrixTool.toString(pedestrianVertexes[i].getP()));
		}*/
	}

	private int getPedestrianVertexLocalIndex(PedestrianVertex v) throws Exception{
		int i = 0;
		while(i<N && pedestrianVertexes[i] != v){
			i++;
		}
		if(i >= N)
			throw new Exception("Vetrex not existant");
		return i;
	}


}