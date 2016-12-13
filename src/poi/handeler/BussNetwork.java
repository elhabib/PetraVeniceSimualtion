package poi.handeler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import math.tools.DateHandeler;

public class BussNetwork {

	protected ArrayList<BussStop> network;
	protected HashMap<Integer,ArrayList<TicketValidation>> networkSortedByBussStop = new HashMap<Integer,ArrayList<TicketValidation>>();
	public BussNetwork(){
		network = new ArrayList<BussStop>();
	}

	public BussNetwork(String fileName){
		this();
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));


			String strLine;
			int Line = 0;
			//Read File Line By Line
			strLine = br.readLine();
			while ((strLine = br.readLine()) != null)   {
				Line++;
				String[] data = strLine.split(";");
				BussStop bs;

				String[] idS = data[1].split(" ");
				int id; 
				if(idS.length == 2){
					id = (new Integer(idS[0]))*1000 + (new Integer(idS[1]));
				}else{
					id = new Integer(idS[0]);
				}

				String[] DateData = data[3].split("-");
				DateHandeler dh = new DateHandeler(new Integer(DateData[2].split(" ")[0]), new Integer(DateData[0]), 0, new Integer(DateData[1]), 0, 0, 0);
				String[] nvS = data[0].split(" ");
				int nv;
				if(nvS.length == 2){
					nv = (new Integer(nvS[0]))*1000 + (new Integer(nvS[1]));
				}else{
					nv = new Integer(nvS[0]);
				}
				if(! networkSortedByBussStop.containsKey(id)){
					ArrayList<TicketValidation> l = new ArrayList<TicketValidation>();
					l.add(new TicketValidation(dh,nv));
					networkSortedByBussStop.put(id, l);
					network.add(new BussStop(id,0,0,data[2]));
				}else{
					networkSortedByBussStop.get(id).add(new TicketValidation(dh,nv));
				}
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

	public void addBussStop(BussStop bs){
		network.add(bs);
	}

	public ArrayList<BussStop> getNetowrk(){
		return network;
	}

	public BussStop getBusStop(int id){
		for(BussStop bs:network){
			if (bs.id == id)
				return bs;
		}
		return null;
	}

	public void preStep(double t) {
		for(BussStop bs:network){
			bs.preStep(t);
		}
	}

	public void executeStep(double t) {
		for(BussStop bs:network){
			bs.executeStep(t);
		}
	}

	public void ticketValidationByStop(){
		System.out.println("network has" + network.size());
		ArrayList<Integer> totals = new ArrayList<Integer>();
		HashMap<String,Integer> totalByStop = new HashMap<String,Integer>();
		int all0 = 0;
		for(BussStop bs:network){
			ArrayList<TicketValidation> l = networkSortedByBussStop.get(bs.getID());
			int total = 0;
			for(TicketValidation tv:l){
				total+= tv.NumberValidations;
			}
			if(!totalByStop.containsKey(bs.name)){	
				totalByStop.put(bs.name,total);
			}else{
				totalByStop.put(bs.name,totalByStop.get(bs.name)+total);
			}
			all0 += total;
			totals.add(total);
			System.out.println("Buss Stop: " + bs.getID() + " , Name: "+ bs.name +" has :" + total);
		}
		ArrayList<Integer> sortedTotals = totals;
		System.out.println("******************************************* By name ***********************************************");
		Collection<Integer> listByName1 = totalByStop.values();
		ArrayList<Integer> listByName = new ArrayList<Integer>();
		listByName.addAll(listByName1);
		Collections.sort(listByName);
		int all1 = 0;
		for(String s:totalByStop.keySet()){
			all1 += totalByStop.get(s);
			if ( totalByStop.get(s) > 0.1*listByName.get(listByName.size()-1)){
				System.out.println("Stop " + s + " : \n" + totalByStop.get(s));
			}
		}
		System.out.println("all1 = " + all1);
		//Collections.sort(sortedTotals);
		//int i = 0;
		/*for(BussStop bs:network){
			if (totals.get(i) >= sortedTotals.get((int) Math.floor(sortedTotals.size()*0.75)))
				System.out.println("Buss Stop: " + bs.getID() + " , Name: "+ bs.name +" has :" + totals.get(i));
			i++;
		}*/
	}
}
