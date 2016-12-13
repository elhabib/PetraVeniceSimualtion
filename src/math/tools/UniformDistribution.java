package math.tools;

public class UniformDistribution implements Distribution {

	double a;
	double b;
	
	public UniformDistribution(double a, double b) throws Exception{
		if (a == b){
			throw new Exception("Unvalid uniform distribution: " + a +" can not be equal to " + b + ".");
		}
		this.a = a;
		this.b = b;
	}
	
	public double getRealization(double time) {
		if (time < a || time > b)
			return 0;
		else
			return 1/(b-a);
	}

}
