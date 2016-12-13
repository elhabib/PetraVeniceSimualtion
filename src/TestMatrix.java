import math.tools.MatrixTool;

public class TestMatrix {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double d = -1;
		double[] v1 = {1., 2.};
		double[] v2 = {1., 1.};
		double[] v3 = {-0.5, -1., -2.5};
		double[][] M1 = {{1., 0.},{2.5,2.5}};
		double[][] M2 = {{1., 0.},{0.,1.}};
		double[][] M3 = {{1., 0.},{0.,2.},{3.0,0}};
		double[][] M4 = {{1.},{0.},{3.}};
		int[] vi1 = {1, 2, 3, 4, 5, 6};
		int[] vi2 = {0, 1};
		int[][] Mi1 = {{1, 0},{0,1}};
		int[][] Mi2 = {{1, 2},{2,5}};
		
		String s;
		try {
			s = MatrixTool.toString(Mi1);
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
