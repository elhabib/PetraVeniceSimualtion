package math.tools;


public class MultiModalDistribution implements Distribution {

	
	
	double alpha;
	double mean1;
	double mean2;
	double var1;
	double var2;
	
	
	public MultiModalDistribution(double mean1, double var1, double mean2, double var2, double alpha) throws Exception{
		if (var1 < 0 || var2 < 0)
			throw new Exception("Unvalid Normal distribution: " + var1 + ", and " + var2 +" can not be non positive");
		if (alpha < 0 || alpha > 1 )
			throw new Exception("Unvalid Normal distribution: " + var1 + ", and " + var2 +" can not be non positive");
		this.mean1 = mean1;
		this.mean2 = mean2;
		this.var1 = var1;
		this.var2 = var2;
		this.alpha = alpha;
	}
	
	@Override
	public double getRealization(double time) {
		return  alpha*Math.exp(-(time-mean1)*(time-mean1) / (2*var1)) / Math.sqrt(2 * Math.PI * var1) +
				(1.-alpha)*Math.exp(-(time-mean2)*(time-mean2) / (2*var2)) / Math.sqrt(2 * Math.PI * var2);
	}
	
	

}
