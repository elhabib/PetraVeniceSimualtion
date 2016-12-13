package pedestrian.handeler;
import math.tools.Flows;
import math.tools.MatrixTool;

public class PedestrianCell{

	private double density[] = {0., 0.};
	private double deltaX;
	
	/**
	 * 
	 */
	public PedestrianCell()
	{
	}
	/**
	 * 
	 * @param d
	 */
	public PedestrianCell(double length) {
		deltaX = length;
	}

	/**
	 * 
	 * @param density
	 */
	public PedestrianCell(double density[], double deltaX)
	{
		this.density = density;
		this.deltaX = deltaX;
	}


	public double[] getDensities(){
		return density;
	}
	
	/**
	 * 
	 */
	public double getDensity(int i) {
		return density[i];
	}
	/**
	 * 
	 * @param i
	 * @return
	 */
	public void setDensity(int i,double d) {
		density[i] = d;
	}
	
	
	public double getLength(){
		return deltaX;
	}
	
	public void updateDensity(int i, double d) {
		density[i] += d;
	}

	/**
	 * 
	 * @param deltaT
	 * @param ds
	 */
	public void executeStep(double deltaT, double[] flow) {
		density[0] = Math.max(0.,density[0] - (deltaT/deltaX)*flow[0]);
		density[1] = Math.max(0.,Math.min(density[1] + (deltaT/deltaX)*flow[1], Flows.rMax - density[0]));
		density[0] = Math.round(density[0]*100.)/100.;
		density[1] = Math.round(density[1]*100.)/100.;
	}
	
	/**
	 * 
	 */
	public String toString(){
		return "["+MatrixTool.show(density[0],3)+","+ MatrixTool.show(density[1],3)+"]";
	}
	public double getSumDensities() {
		return density[0] + density[1];
	}

	

	}
