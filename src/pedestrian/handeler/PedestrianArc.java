package pedestrian.handeler;
import math.tools.Flows;
import math.tools.MatrixTool;
import simulation.History;

/**
 * 
 * @author moustaidelhabib
 *
 */
public class PedestrianArc {
	private PedestrianCell[] cells;
	private int id;
	private PedestrianVertex from;
	private PedestrianVertex to;
	private double length;
	private double width;
	private String type;
	private double[][] flow;
	private boolean[] activeDirection = {true,true};
	private int color = Integer.MAX_VALUE;
	private History h = new History();

	/**
	 * 
	 * @param from
	 * @param to
	 */
	public PedestrianArc(int id, PedestrianVertex from, PedestrianVertex to){
		this.id = id;
		this.from = from;
		this.to = to;
	}
	/**
	 * 
	 * @param id
	 * @param from
	 * @param to
	 * @param length
	 * @param width
	 * @param type
	 */
	public PedestrianArc(int id, PedestrianVertex from, PedestrianVertex to, double length, double width, String type){
		this(id, from, to);
		this.length = length;
		this.width = width;
		this.type = type;
		int numberCells = (int) Math.ceil(length/Flows.v); // Originally (length/Flows.v)*dt, dt is assumed to be 1. 
		cells = new PedestrianCell[numberCells];
		for (int i = 0; i< cells.length; i++){
			cells[i] = new PedestrianCell(Flows.v);
		}
		flow = new double[numberCells][2];
	}
	/**
	 * 
	 * @param cells
	 * @param from
	 * @param to
	 */
	public PedestrianArc(int id, PedestrianVertex from, PedestrianVertex to,PedestrianCell[] cells){
		this(id, from,to);
		this.cells = cells;
		this.flow = new double[cells.length-1][2];
	}

	/**
	 * 
	 * @param from
	 * @param to
	 * @param cells
	 */

	public PedestrianArc(int id, PedestrianVertex from, PedestrianVertex to, double[][] cells, double deltaX) {
		this(id, from,to);
		this.cells = new PedestrianCell[cells.length];
		for(int i=0 ; i<cells.length ;i++){
			this.cells[i] = new PedestrianCell(cells[i], deltaX);
		}
		this.flow = new double[cells.length-1][2];
	}
	/**
	 * 
	 * @return
	 */
	public int getFrom(){
		return from.getID();
	}

	/**
	 * 
	 * @return
	 */
	public PedestrianVertex getFromVertex(){
		return from;
	}

	/**
	 * 
	 * @return
	 */
	public int getTo(){
		return to.getID();
	}

	/**
	 * 
	 * @return
	 */
	public PedestrianVertex getToVertex(){
		return to;
	}


	/**
	 * 
	 * @return
	 */
	public int getID(){
		return id;
	}

	/**
	 * 
	 * @param i
	 * @return
	 * @throws Exception
	 */
	public PedestrianCell getCell(int i) throws Exception{
		if (i<0 || i>=cells.length)
			throw new Exception("Error: Asking for a non existing cell, cells range is 0 to " + cells.length);
		return cells[i];
	}

	/**
	 * 
	 * @return
	 */
	public int getNumberCells(){
		return cells.length;
	}

	/**
	 * 
	 * @return
	 */
	public PedestrianCell[] getCells(){
		return cells;
	}

	public PedestrianCell getLastCell() {
		return cells[cells.length-1];
	}

	public PedestrianCell getFirstCell() {
		return cells[0];
	}

	public void setCells(PedestrianCell[] cells){
		this.cells = cells;
	}

	public double getWidth(){
		return width;
	}

	public double getLength(){
		return length;
	}

	public void setlength(double length){
		this.length = length;
	}

	public boolean isDirectionOpen(int i){
		return activeDirection[i];
	}


	public void setColor(int color){
		this.color = color;
	}

	public int getColor(){
		return color;
	}


	public void closeDirection(int i){
		activeDirection[i] = false;
	}

	public void openDirection(int i){
		activeDirection[i] = true;
	}
	/**
	 * 
	 * @param i
	 * @return
	 */
	public double getAverageDensities(int i) {
		double mean = 0;
		for (int k = 0; k < cells.length; k++){
			mean += cells[k].getDensity(i);
		}
		mean = mean/cells.length;
		return mean;
	}



	/**
	 * 
	 */
	public void preStep(){
		double[][] q = new double[cells.length-1][2];
		for(int i=0;i< cells.length-1;i++){
			double r1L, r2L, r1R, r2R;
			r1L = cells[i].getDensity(0);
			r2L = cells[i].getDensity(1);
			r1R = cells[i+1].getDensity(0);
			r2R = cells[i+1].getDensity(1);
			q[i] = Flows.IF(r1L,r2L,r1R,r2R);
		}
		flow = q;
	}


	/**
	 * 
	 */
	public void executeStep(double deltaT){
		PedestrianCell Left,Right;
		for(int i=0;i< cells.length-1;i++){
			Left = cells[i];
			Right = cells[i+1];
			Left.executeStep(deltaT,flow[i]);
			Right.executeStep(-deltaT,flow[i]);
			// - sign on Delta is equivalent to have in flow
		}
	}

	public double getTravelTimeNow(){
		double tt = 0.;
		for(int j = 0; j< cells.length -1; j++){
			if(cells[j].getSumDensities() == 0){
				tt+= cells[j].getLength()/Flows.v;
			}else{
				double v1 = Flows.v, v2 = Flows.v;
				if(cells[j].getDensity(0)>0)
					v1 = Math.min(flow[j][0]/cells[j].getDensity(0),Flows.v);
				if(cells[j].getDensity(1)>0)
					v2 = Math.min(flow[j][1]/cells[j].getDensity(1),Flows.v);
				double v = Math.min(v1, v2);
				double alpha = cells[j].getSumDensities()/(2*Flows.rMax);
				double vfinale = (1- alpha)*Flows.v + alpha*v;
				tt += cells[j].getLength()/vfinale;
			}
		}
		return tt;
		/*double tt = 0.;
		for(int j = 0; j< cells.length -1; j++){
			if(cells[j].getSumDensities() == 0){
				System.out.println("ett");
				tt+= cells[j].getLength()/Flows.v;
			}else if(flow[j][1] + flow[j][0] == 0){
				System.out.println("tva");
				tt+= cells[j].getLength()/Flows.vMin;
			}else{
				System.out.println("tre");
				System.out.println(flow[j][0]+flow[j][0]/cells[j].getSumDensities());
				double v = Math.max(Flows.vMin,Math.min(flow[j][0]+flow[j][0]/cells[j].getSumDensities(), Flows.v));
				tt+= (cells[j].getLength()/v);
			}
		}
		return tt;*/
	}

	public void saveCurrentTravelTime(){
		h.addData((int)getTravelTimeNow());
	}


	public int getTravelTimeAt(int i){
		int index = (int) Math.floor(i/(60*15)) - 1;
		return h.getData(index);
	}


	public History getHistory(){
		return h;
	}


	public String toString(){
		String res = "--Arc from " + from.getID() + " to " + to.getID() +":\n Densities = ";
		for (int i =0; i<cells.length;i++){
			res = res + " " + cells[i].toString();
		}
		res = res + "\n Flows = " + MatrixTool.toString(MatrixTool.transpose(flow));
		return res + "\n";
	}

	/**
	 * 
	 */
	public void showState(){
		System.out.println(toString());
	}



}
