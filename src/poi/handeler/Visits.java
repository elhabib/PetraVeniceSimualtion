package poi.handeler;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.text.html.HTMLDocument.Iterator;

import math.tools.DateHandeler;

public class Visits {

	ArrayList<String> visitorIDs;
	ArrayList<ArrayList<Visit>> visitsByVisitor;

	public Visits(){
		visitorIDs = new ArrayList<String>();
		visitsByVisitor = new ArrayList<ArrayList<Visit>>();
	}

	public Visits(String FileName){
		this();
		FileInputStream fstream;
		String photoID;
		String visitorID;
		DateHandeler pictureUploadedTime;
		DateHandeler pictureTakenTime;
		double x;
		double y;
		String PoI;
		double xPoI;
		double yPoI;
		try {
			fstream = new FileInputStream(FileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;

			//Read File Line By Line
			//strLine = br.readLine();
			int li = 1;
			while ((strLine = br.readLine()) != null)   {
				String[] data = strLine.split(",");
				photoID = data[0];
				visitorID = data[1];
				// Mon Jul 13 23:00:43 CEST 2015
				String[] date1 = data[2].split(" ");
				String[] time1 = date1[3].split(":");
				pictureUploadedTime = new DateHandeler(new Integer(date1[5]), DateHandeler.monthIndex(date1[1]), DateHandeler.dayIndex(date1[0]) ,
						new Integer(date1[2]),new Integer(time1[0]), new Integer(time1[1]), new Integer(time1[2]));

				String[] date2 = data[3].split(" ");
				String[] time2 = date2[3].split(":");
				pictureTakenTime = new DateHandeler(new Integer(date2[5]), DateHandeler.monthIndex(date2[1]), DateHandeler.dayIndex(date2[0]) ,
						new Integer(date2[2]),new Integer(time2[0]), new Integer(time2[1]), new Integer(time2[2]));

				x = new Double(data[4]);
				y = new Double(data[5]);
				PoI = data[6];
				xPoI = new Double(data[7]);
				yPoI = new Double(data[8]);
				li++;
				int indexOfVisitor = visitorIDs.indexOf(visitorID) ;
				if ( indexOfVisitor == -1){
					indexOfVisitor = visitorIDs.size();
					visitorIDs.add(visitorID);
					visitsByVisitor.add(new ArrayList<Visit>());
				}
				visitsByVisitor.get(indexOfVisitor).add(new Visit(photoID, visitorID, pictureUploadedTime, pictureTakenTime,  x, y, PoI, xPoI, yPoI));
			}
			int i = 0;
			int allInput = 0;
			for(ArrayList<Visit> l:visitsByVisitor){
				//System.out.println("Sorting :" + i + "  with size " + l.size());
				Collections.sort(l);
				allInput+=l.size();
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


	public ArrayList<ArrayList<Visit>> getVisitsByVisitor(){
		return visitsByVisitor;
	}


	public String toString(){
		StringBuilder sb = new StringBuilder("");
		for(ArrayList<Visit> l : visitsByVisitor){
			if (l.size() == 0)
				sb.append(" --User : " + "No visit!" + "\n");
			else{
				sb.append(" --User : " + l.get(0).getVisitorID() + "\n");
				for(Visit v:l){
					sb.append( v.toString()+ "\n");
				}
			}
		}
		return sb.toString();
	}

	public void filterWeekEnds(){
		for(ArrayList<Visit> l : visitsByVisitor){
			ArrayList<Visit> toRemove = new ArrayList<Visit>();
			for(Visit v:l){
				if(! v.getPictureTakenTime().isWeekEnd())
					toRemove.add(v);
			}
			l.removeAll(toRemove);
		}
	}

	public void filterWeekDays(){
		for(ArrayList<Visit> l : visitsByVisitor){
			ArrayList<Visit> toRemove = new ArrayList<Visit>();
			for(Visit v:l){
				if(v.getPictureTakenTime().isWeekEnd())
					toRemove.add(v);
			}
			l.removeAll(toRemove);
		}
	}

	public ArrayList<Visit> getAllVisits() {
		ArrayList<Visit> res = new ArrayList<Visit>();
		for(ArrayList<Visit> l:visitsByVisitor){
			res.addAll(l);
		}
		return res;
	}
}
