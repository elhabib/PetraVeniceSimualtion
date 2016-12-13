package poi.handeler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import math.tools.DateHandeler;
import math.tools.Flows;
import math.tools.MatrixTool;
import paths.PathCalculator;
import pedestrian.handeler.*;

public class TripPlannerLocs {

	public ArrayList<TripPlannerLoc> tripPlannerLocs = new ArrayList<TripPlannerLoc>();


	public TripPlannerLocs(){

	}

	public TripPlannerLocs(String fileName){
		this();
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));


			String strLine;
			int Line = 0;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				Line++;
				String[] data = strLine.split(",");
				int id = Line; 
				String idString = data[0];
				double x = new Double(data[data.length-2]);
				double y = new Double(data[data.length-3]);
				tripPlannerLocs.add(new TripPlannerLoc(id,x,y,idString));
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addTripPlannerLoc(TripPlannerLoc tpl){
		tripPlannerLocs.add(tpl);
	}


	public TripPlannerLoc getTripPlannerLoc(String idString){
		for(TripPlannerLoc tpl:tripPlannerLocs){
			if(tpl.idString.compareTo(idString) != 0){
				return tpl;
			}
		}
		return null;
	}

	public void saveTravelTimeHistory(String fileName, PathCalculator p){
		StringBuilder sb = new StringBuilder();
		for(TripPlannerLoc tpl:tripPlannerLocs){
			sb.append(tpl.idString);
			sb.append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("\n");
		for(int i = 15; i <= 24*60 ;i+=15){
			sb.append(i);
			sb.append(";\n");
			for(TripPlannerLoc tpl1:tripPlannerLocs){
				PedestrianVertex v1 = tpl1.getClostPedestrianVertex();
				for(TripPlannerLoc tpl2:tripPlannerLocs){
					PedestrianVertex v2 = tpl2.getClostPedestrianVertex();
					int travelTime = 0;
					ArrayList<PedestrianArc> path = p.FindPathBetweenInArcs(v1, v2);
					for(PedestrianArc pa:path){
						travelTime += pa.getTravelTimeAt(i*60);
					}
					sb.append(travelTime);
					sb.append(",");
				}
				sb.deleteCharAt(sb.lastIndexOf(","));
				sb.append("\n");
			}
		}
		try {
			PrintWriter writer;
			writer = new PrintWriter(fileName, "UTF-8");
			writer.print(sb.toString());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveCrowdednessLevel(String fileName, PathCalculator p, double dayfactor){
		StringBuilder sb = new StringBuilder();
		for(TripPlannerLoc tpl:tripPlannerLocs){
			sb.append(tpl.idString);
			sb.append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("\n");
		for(int i = 15; i <= 24*60 ;i+=15){
			sb.append(i);
			sb.append(";\n");
			for(TripPlannerLoc tpl1:tripPlannerLocs){
				PedestrianVertex v1 = tpl1.getClostPedestrianVertex();
				for(TripPlannerLoc tpl2:tripPlannerLocs){
					PedestrianVertex v2 = tpl2.getClostPedestrianVertex();
					double crowdednessLevel = 0.;
					ArrayList<PedestrianArc> path = p.FindPathBetweenInArcs(v1, v2);
					for(PedestrianArc pa:path){
						if (pa.getColor() == 0){
							//System.out.println("1 :" );
							crowdednessLevel += 0.;
						}
						else if (pa.getColor() == -1){
							//System.out.println("2 :" );
							//double fact = Math.min(Flows.v, pa.getLength()/Math.max(pa.getTravelTimeAt(i*60),1.));
							double fact = Math.abs(pa.getTravelTimeAt(i*60)-(pa.getLength()/Flows.v));
							crowdednessLevel += 0.33*(2. - Math.exp(-fact));
						}
						else if (pa.getColor() == 1){
							//System.out.println("3 :" );
							//double fact = Math.min(Flows.v, pa.getLength()/Math.max(pa.getTravelTimeAt(i*60),1.));
							double fact = Math.abs(pa.getTravelTimeAt(i*60)-(pa.getLength()/Flows.v));
							crowdednessLevel += 0.66*(1.5 - Math.exp(-fact));
						}
					}
					if (path.size() > 0)
						crowdednessLevel = crowdednessLevel/path.size();
					crowdednessLevel *= dayfactor;
					crowdednessLevel *= 10;
					crowdednessLevel = Math.round(crowdednessLevel)/10.;
					//System.out.println("result is :" + crowdednessLevel);
					sb.append(crowdednessLevel);
					sb.append(",");
				}
				sb.deleteCharAt(sb.lastIndexOf(","));
				sb.append("\n");
			}
		}
		try {
			PrintWriter writer;
			writer = new PrintWriter(fileName, "UTF-8");
			writer.print(sb.toString());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<TripPlannerLoc>  getTripPlannerLocs() {
		// TODO Auto-generated method stub
		return tripPlannerLocs;
	}
}
