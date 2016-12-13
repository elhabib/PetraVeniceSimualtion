package math.tools;


public class NormalDistribution implements Distribution {

	double mean;
	double var;

	public NormalDistribution(double mean, double var) throws Exception{
		if (var < 0)
			throw new Exception("Unvalid Normal distribution: " + var +" can not be non positive");
		this.mean = mean;
		this.var = var;
	}

	public double getRealization(double time) {
		return Math.exp(-(time-mean)*(time-mean) / (2*var)) / Math.sqrt(2 * Math.PI * var);
	}

}