package math.tools;

/**
 * 
 * @author Elhabib Moustaid
 *
 */
public class Flows {
	public static double v = 1.5;
	public static double rMax = 5.091;
	public static double D = 0.27;
	public static double maxIt = 10000;
	public static double maxGap = 0.0001;
	public static double cSwap = rMax*v / (2*(1+D*v*rMax));
	public static double vMin = 0.2; 

	/**
	 *
	 * @param q
	 * @param r2
	 * @param D
	 * @param rMax
	 * @param v
	 * @return
	 */

	public Flows(double v, double rMax, double D){
		this.v = v;
		this.rMax = rMax;
		this.D = D;
	}

	public void changeConvergenceParameters(double maxIt, double maxGap){
		this.maxGap = maxGap;
		this.maxIt = maxIt;
	}

	public static double capacity(double r2){
		if (r2 <= rMax/2){
			double r1c = rMax/(2+D*v*rMax) * (D*v*r2 +1);
			double q[] = FD_analyt(r1c, r2);
			return q[0];
		}else{
			double q[] = FD_analyt(rMax-r2,r2);
			return q[0];
		}

	}

	public static double[] FD_analyt(double r1, double r2) {
		double q[];
		if (r2 <= r1){
			q = FD_analyt_lower(r1,r2);
			return q;
		}
		else{
			q = FD_analyt_lower(r2,r1);
			double aux = q[0];
			q[0] = q[1];
			q[1] = aux;
			return q;
		}
	}

	private static double[] FD_analyt_lower(double r1, double r2) {
		double q[] = new double[2];
		if (r2 >= r1 * (2 + D * v * rMax) / D / v / rMax - 1 / D / v){
			double fact = v / (1 + D * v * (r1 +r2 ));
			q[0] = r1 * (1 + D * v* (r1 -r2)) * fact;
			q[1] = r2 * (1 + D * v* (r2 -r1)) * fact;
		}else{
			double fact = v/ (1 + D * v* rMax);
			q[0] = (rMax - r1) * fact;
			q[1] = r2 * fact;
		}
		return q;
	}

	public static double capSend(double q, double r2){
		double r1bis = rMax;
		double c1 = Double.MAX_VALUE;
		if (r2 != 0){
			double fact = v* Math.max(0, 1 - D*q) /(1+D*v*r2) + q/r2;
			r1bis = rMax*v/(1+D*v*rMax) * (1/fact);
			c1 = r1bis*v*Math.max(0,1-D*q)/(1+D*v*r2);
		}
		double r2bis = (rMax/(1+D*v*rMax)) * (1/(Math.max(0, 1-D*q)/(1+D*v*r2)+ 1/(1+D*v*rMax)));
		double c2 = Math.max(0, v*(rMax-r2bis))/(1+D*v*rMax);
		if (c2 <= c1)
			return c2;
		else
			return c1;
	}

	public static double send(double q, double r1, double r2, double cS){
		return Math.min(v * r1 * Math.max(0, 1-D*q)/(1.+ D*v*r2), cS);
	}

	public static double capReceive(double q, double r2){
		if (r2 != 0){
			double fact = v *Math.max(0, 1-D*q) / (1+D*v*r2) + q/r2 ;
			double rR = rMax*v/(1+D*v*rMax) * (1/fact);
			return rR* v * Math.max(0, 1- D*q) / (1 + D*v*r2);
		}else
		{
			return Double.MAX_VALUE;
		}
	}

	public static double receive(double q, double r1, double r2, double cR){
		if (r2 != 0){
			return Math.min(Math.max(0,(v * rMax)/(1+D*v*rMax) - q*r1/r2), cR);
		}else
		{
			return Double.MAX_VALUE;
		}
	}

	public static double capReceiveBis(double q, double r2){
		double rK = rMax/(1+D*v*rMax) * 1/(Math.max(0, 1-D*q)/(1+D*v*r2) + 1/(1+D*v*rMax));
		return v*(rMax-rK) / (1+D*v*rMax);
	}

	public static double receiveBis(double r1, double cK){
		return Math.min(cK,Math.max(0, v*(rMax-r1))/(1 + D*v*rMax));
	}

	/**
	 * 
	 * @param left
	 * @param right
	 * @return
	 */

	public static double[] IF(double r1L, double r2L, double r1R, double r2R){
		double[] q = {0.,0.};
		double[] qOld = {Double.MAX_VALUE,Double.MAX_VALUE};
		boolean converged = false;
		int it = 0;
		double S1, S2, R1, R2, K1, K2, capS1, capS2, capR1, capR2, capK1, capK2;
		while (!converged && it <= maxIt){

			capS1 = capacity(r2L);
			capR1 = capacity(r2R);
			capK1 = capacity(r2R);

			capS2 = capacity(r1R);
			capR2 = capacity(r1L);
			capK2 = capacity(r1L);

			S1 = send(q[1],r1L,r2L,capS1);
			S2 = send(q[0],r2R,r1R,capS2);

			R1 = receive(q[1],r1R,r2R,capR1);
			R2 = receive(q[0],r2L,r1L,capR2);

			K1 = receiveBis(r1R,capK1);
			K2 = receiveBis(r2L,capK2);

			if (Math.min(R1, K1) < S2){
				q[0] = Math.min(S1, Math.min(S2, cSwap));
			}else
				q[0] = Math.min(S1, Math.min(R1, K1));
			if (Math.min(R2, K2) < S1)
				q[1] = Math.min(S2, Math.min(S1, cSwap));
			else
				q[1] = Math.min(S2, Math.min(R2,K2));

			it++;
			converged = (Math.max(Math.abs(q[1]-qOld[1]), Math.abs(q[0]-qOld[0])) <= maxGap);
			for (int i =0;i<2;i++){
				qOld[i] = q[i];
			}
		}
		if (!converged){
			System.out.println("Warning : IF didn't converge for the values"+ r1L +"," + r2L + "," + r1R +","+r2R +". \n");
		}
		q[0] = Math.round(q[0]*1000.)/1000.;
		q[1] = Math.round(q[1]*1000.)/1000.;
		return q;
	}

	/**
	 * 
	 * @param d
	 * @param n
	 * @return
	 * @throws Exception 
	 */
	public static double[] IFNode(double[][] d, int n, double[][] P, double[] prio) throws Exception {
		double q[] = new double[n*2];
		double capS[] = new double[n], s[] = new double[n];
		double capR[] = new double[n], r[] = new double[n];
		double capK[] = new double[n], k[] = new double[n];
		double cS;
		double cR;
		double cK;
		int iter = 0;
		double cswaps[] = MatrixTool.multiply(prio, n*cSwap);
		boolean converged = false;
		while(!converged & iter < maxIt){
			for(int i = 0; i<n ; i++){
				cS = capacity(d[i][1]);
				cR = capacity(d[i][0]);
				cK = capacity(d[i][0]); 
				s[i] = send(q[i+n], d[i][0], d[i][1],cS);
				r[i] = receive(q[i], d[i][1],d[i][0],cR);
				k[i] = receiveBis(d[i][1],cK);
			}
			capS = s;
			for(int i = 0;i <n; i++){
				if(Math.min(r[i], k[i]) <= s[i])
					capR[i] = Math.min(s[i], Math.max(Math.min(r[i], k[i]), cswaps[i]));
				else
					capR[i] = Math.min(r[i], k[i]);
			}
			double[] qnew = INM(capR,capS,P, prio, n);
			converged = (MatrixTool.norm(MatrixTool.substract(qnew,q))<=maxGap);
			iter++;
			q = qnew;
		}
		if (!converged)
			System.out.println("not converged");
		for (int i = 0; i < 2*n; i++){
			q[i] = Math.round(q[i]*1000.)/1000.;
		}
		return q;
	}

	public static double[] INM(double[] r, double[] s, double[][] B, double[] prio, int n) throws Exception{
		double[] q = new double[2*n];
		for (int i = 0;i<2*n;i++){
			q[i] = 0.;
		}
		int[] D = demand(q,r,s,B,n);
		double[][] Bt = MatrixTool.transpose(B);
		double[] phi1,phi2;
		double teta;
		int k = 0;
		while (MatrixTool.max(D) !=0){
			phi1 = MatrixTool.multiplyComponent(prio, MatrixTool.extract(D,0,n-1));
			phi2 = MatrixTool.multiply(Bt,phi1);
			phi2 = MatrixTool.multiplyComponent(phi2, MatrixTool.extract(D,n,2*n-1));
			teta = computeTeta(D, q, phi1, phi2, r, s, n);
			q = MatrixTool.add(q, MatrixTool.multiply(MatrixTool.concatenate(phi1,phi2),teta));
			D = demand(q,r,s,B, n);
			k++;
			if (k == maxIt)
				throw new Exception("INM Doesn't converge for \ns = " + MatrixTool.toString(s)
				+ "\nr = " + MatrixTool.toString(r)
				+ "\nprio = " + MatrixTool.toString(prio)
				+ "\nB = " + MatrixTool.toString(B));
		}
		return q;
	}

	public static int[] demand(double[] q, double[] r, double[] s, double[][] B, int n){
		int[] demand = new int[2*n];
		for (int i=0;i<n;i++){
			demand[i] = (q[i] >= s[i])?0:1;
			if (q[i] < s[i]){
				for (int j=0;j<n;j++){
					if (B[i][j] > 0){
						if (q[n+j] >= r[j]){
							demand[i] = 0;
						}
					}
				}
			}
		}
		for(int j=n;j<2*n;j++){
			demand[j] = 0;
			if(q[j]<= r[j-n]){
				for(int i=0;i<n;i++){
					if (B[i][j-n] > 0 && demand[i] == 1){
						demand[j] = 1;
					}
				}
			}
		}
		return demand;
	}

	public static double computeTeta(int[] D, double[] q, double[] phi1, double[] phi2,
			double[] r, double[] s, int n){
		double teta = Double.MAX_VALUE;
		for(int i = 0; i<n;i++){
			if (D[i] == 1){
				teta = Math.min(teta, (s[i]-q[i])/phi1[i]);
			}
		}
		for (int i=0;i<n;i++){
			if (D[i+n] == 1){
				teta = Math.min(teta, (r[i]-q[i+n])/phi2[i]);
			}
		}
		return teta;
	}


}
