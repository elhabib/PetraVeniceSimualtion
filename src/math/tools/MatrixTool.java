package math.tools;
/**
 * This class contains necessary math operations for the functioning of the algorithms implemented  
 * @author moustaidelhabib
 *
 */
public class MatrixTool {

	/**
	 * tested
	 * @param v1
	 * @param v2
	 * @return  v1 + v2
	 * @throws Exception
	 */
	public static double[] add(double[] v1, double[] v2) throws Exception{
		if (v1.length != v2.length){
			throw new Exception("Dimensions do not match");
		}
		double[] result = new double[v1.length];
		for (int i=0;i<v1.length;i++){
			result[i] = v1[i] + v2[i];
		}
		return result;
	}

	/**
	 * tested
	 * @param v
	 * @param d
	 * @return v + d
	 */
	public static double[] add(double[] v, double d){
		double[] result = new double[v.length];
		for(int i=0;i<v.length;i++){
			result[i] = v[i]+d;
		}
		return result;
	}

	/**
	 * 
	 * @param M
	 * @param v
	 * @return v*M
	 */
	public static double[][] add(double[][] M, double v){
		double[][] result = new double[M.length][M[0].length];
		for(int i=0;i<M.length;i++){
			for(int j=0;j<M[0].length;j++){
				result[i][j] = M[i][j]+v;
			}
		}
		return result;
	}

	/**
	 * tested
	 * @param v
	 * @param d
	 * @return d*v
	 */
	public static double[] multiply(double[] v, double d){
		double[] result = new double[v.length];
		for(int i=0;i<v.length;i++){
			result[i] = v[i]*d;
		}
		return result;
	}

	/**
	 * tested
	 * @param M
	 * @param v
	 * @return M*v
	 */
	public static double[][] multiply(double[][] M, double v){
		double[][] result = new double[M.length][M[0].length];
		for(int i=0;i<M.length;i++){
			for(int j=0;j<M[0].length;j++){
				result[i][j] = M[i][j]*v;
			}
		}
		return result;
	}

	/**
	 * tested
	 * @param M
	 * @param v
	 * @return M*v
	 * @throws Exception
	 */
	public static double[] multiply(double[][] M, double[] v) throws Exception{
		if (M.length == 0 || M[0].length != v.length){
			throw new Exception("Dimensions don't match");
		}
		double[] result = new double[M.length];
		for(int i=0;i<M.length;i++){
			result[i] = 0;
			for(int j=0;j<M[0].length;j++){
				result[i]+= M[i][j]*v[j];
			}
		}
		return result;
	}


	public static double[][] multiply(double[][] M1, double[][] M2) throws Exception{
		if (M1[0].length != M2.length){
			throw new Exception("Dimensions don't match");
		}
		double[][] M = new double[M1.length][M2[0].length];
		for(int i=0;i<M.length;i++){
			for(int j=0; j<M[0].length; j++){
				M[i][j]= 0;
				for(int l=0;l<M1[0].length;l++){
					M[i][j]+= M1[i][l]*M2[l][j];
				}
			}
		}
		return M;
	}

	/**
	 * tested
	 * @param M
	 * @return
	 */
	public static double[][] transpose(double[][] M){
		double[][] transpose = new double[M[0].length][M.length];
		for(int i = 0; i< M.length;i++){
			for(int j = 0; j < M[0].length; j++){
				transpose[j][i] = M[i][j];
			}
		}
		return transpose;
	}

	/**
	 * tested
	 * @param v
	 * @return
	 */
	public static double min(double[] v){
		double min = Double.MAX_VALUE;
		for(int i = 0; i<v.length;i++){
			if (v[i] <= min)
				min = v[i];
		}
		return min;
	}

	/**
	 * tested
	 * @param v
	 * @return
	 */
	public static double min(double[][] v){
		double min = Double.MAX_VALUE;
		for(int i = 0; i<v.length;i++){
			for(int j=0; j<v[0].length;j++)
				if (v[i][j] <= min)
					min = v[i][j];
		}
		return min;
	}

	/**
	 * tested
	 * @param v
	 * @return
	 */
	public static double max(double[] v){
		double max = -Double.MAX_VALUE;
		for(int i = 0; i<v.length;i++){
			if (v[i] >= max){
				max = v[i];
			}
		}
		return max;
	}

	/**
	 * tested
	 * @param v
	 * @return
	 */
	public static int max(int[] v){
		int max = Integer.MIN_VALUE;
		for(int i = 0; i<v.length;i++){
			if (v[i] >= max)
				max = v[i];
		}
		return max;
	}

	/**
	 * tested
	 * @param v
	 * @return
	 */
	public static double max(double[][] v){
		double max = -Double.MAX_VALUE;
		for(int i = 0; i<v.length;i++){
			for(int j=0; j<v[0].length;j++)
				if (v[i][j] >= max)
					max = v[i][j];
		}
		return max;
	}

	/**
	 * tested
	 * @param v1
	 * @param v2
	 * @return
	 * @throws Exception
	 */
	public static double[] multiplyComponent(double[] v1,int[] v2 ) throws Exception{
		if (v1.length != v2.length){
			throw new Exception("Dimensions don't match");
		}
		double[] result = new double[v1.length];
		for (int i=0;i<v1.length;i++){
			result[i] = v1[i] * v2[i];
		}
		return result;
	}

	/**
	 * tested
	 * @param v1
	 * @param v2
	 * @return
	 * @throws Exception
	 */
	public static double[][] multiplyComponent(double[][] v1,double[][] v2 ) throws Exception{
		if (v1.length != v2.length || v1[0].length != v2[0].length){
			throw new Exception("Dimensions don't match");
		}
		double[][] result = new double[v1.length][v1[0].length];
		for (int i=0;i<v1.length;i++){
			for (int j=0;j<v2.length;j++){
				result[i][j] = v1[i][j] * v2[i][j];
			}
		}
		return result;
	}

	/**
	 * tested
	 * @param v1
	 * @param v2
	 * @return
	 * @throws Exception
	 */
	public static double scalarProduct(double[] v1,double[] v2 ) throws Exception{
		if (v1.length != v2.length){
			throw new Exception("Dimensions don't match");
		}
		double result = 0;
		for (int i=0;i<v1.length;i++){
			result += v1[i] * v2[i];
		}
		return result;
	}

	/**
	 * tested
	 * @param d
	 * @param i
	 * @param j
	 * @return
	 * @throws Exception
	 */
	public static int[] extract(int[] d, int i, int j) throws Exception {
		if (i>j || i<0 || j>=d.length){
			throw new Exception("Dimensions don't match");
		}
		int[] result = new int[j-i+1];
		for(int k=i;k<=j;k++){
			result[k-i] = d[k];
		}
		return result;
	}

	/**
	 * tested
	 * @param phi1
	 * @param phi2
	 * @return
	 */
	public static double[] concatenate(double[] phi1, double[] phi2) {
		double[] result = new double[phi1.length+phi2.length];
		for (int i = 0; i<phi1.length;i++){
			result[i] = phi1[i];
		}
		for (int i = 0; i<phi2.length;i++){
			result[i+phi1.length] = phi2[i];
		}
		return result;
	}

	/**
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 * @throws Exception
	 */
	public static double[] substract(double[] v1, double[] v2) throws Exception {
		if (v1.length != v2.length){
			throw new Exception("Dimensions don't match");
		}
		double[] result = new double[v1.length];
		for (int i=0;i<v1.length;i++){
			result[i] = v1[i] - v2[i];
		}
		return result;
	}


	public static double sum(double[] v){
		double result = 0;
		for (int i = 0; i < v.length ; i++){
			result += v[i];
		}
		return result;
	}

	/**
	 * @throws Exception 
	 * 
	 */
	public static double distance(double[] v1, double[] v2) throws Exception{
		return Math.sqrt(scalarProduct(substract(v2,v1),substract(v2,v1)));
	}
	/**
	 * tested
	 * @param v1
	 * @return
	 */
	public static double norm(double[] v1) {
		double result = 0;
		for (int i=0;i<v1.length;i++){
			result += v1[i] * v1[i];
		}
		return Math.sqrt(result);
	}

	/**
	 * tested
	 * @param v
	 * @return
	 */
	public static String toString(double[] v){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i<v.length ;i++){
			sb.append(v[i]);
			if(i < v.length-1) 
				sb.append(",\n ");
			else
				sb.append("");
		}
		sb.append("]");
		return sb.toString();
	}


	/**
	 * tested
	 * @param v
	 * @return
	 */
	public static String toString(double[][] v){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
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
		sb.append("]");
		return sb.toString();
	}

	/**
	 * tested
	 * @param v
	 * @return
	 */
	public static String toString(int[] v){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i<v.length ;i++){
			sb.append(v[i]);
			if(i < v.length-1) 
				sb.append(",\n ");
			else
				sb.append("");
		}
		sb.append("]");
		return sb.toString();
	}

	public static String toString(int[][] v){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
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
		sb.append("]");
		return sb.toString();
	}

	/**
	 * tested
	 * @param d
	 * @param n
	 * @return
	 */
	public static double show(double d, int n){
		return Math.ceil(d*Math.pow(10, n))/Math.pow(10, n);
	}
}
