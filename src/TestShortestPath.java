import math.tools.MatrixTool;

public class TestShortestPath {

	public static void main(String[] args) throws Exception {

		int N = 6;
		int[][] w = new int[N][N];
		double[][] dist = new double[N][N];
		int[][] edges = new int[N][N];
		int[][] next = new int[N][N];
		int narcs = 0;
		/* Initialize Weights*/
		w[0][1] = 5;
		w[1][2] = 10;
		w[0][5] = 11;
		w[4][5] = 5;
		w[3][4] = 5;
		w[2][4] = 12;
		w[2][3] = 6;
		w[1][3] = 5;
		w[0][2] = 19;

		/* Initialize edges */
		edges[0][1] = 1;
		edges[1][2] = 1;
		edges[0][5] = 1;
		edges[4][5] = 1;
		edges[3][4] = 1;
		edges[2][4] = 1;
		edges[2][3] = 1;
		edges[1][3] = 1;
		edges[0][2] = 1;

		for(int i = 0;i <N; i++){
			for(int j= 0; j<i; j++){
				w[i][j] = w[j][i];
				edges[i][j] = edges[j][i];
			}
		}

		System.out.println(MatrixTool.toString(w));

		for(int i = 0; i<N; i++){
			for(int j = 0; j<N ; j++){
				if(edges[i][j] ==1){
					dist[i][j] = w[i][j];
					next[i][j] = j;
				}else{
					dist[i][j] = Double.MAX_VALUE;
					next[i][j] = -1;
				}
			}
			dist[i][i] = 0;
		}
		System.out.println(MatrixTool.toString(dist));

		System.out.println("Initializing done");
		double startTime = System.currentTimeMillis();
		double interTime;

		for(int k = 0; k<N; k++){

			if ((k%400) == 0 & k!=0){	
				System.out.println((double) k/N*100 + "% done ...");
				interTime = System.currentTimeMillis();
				double t = interTime - startTime;
				int min = (int) t/(1000*60);
				int sec = ((int) (t/1000)) % 60;
				System.out.println("Time till now: " + min +":" + sec );
				double tRem = (1*t)/((double) k/N);
				int minRem = (int) tRem/(1000*60);
				int secRem = ((int) (tRem/1000)) % 60;
				System.out.println("Time remaining: " + minRem +":" + secRem );
			}

			for(int i = 0; i<N; i++){
				for(int j = 0; j<N; j++){
					if(dist[i][j] > dist[i][k] + dist[k][j]){
						dist[i][j] = dist[i][k] + dist[k][j];
						next[i][j] = next[i][k];
					}
				}
			}
		}



		String[][] paths = new String[N][N];
		for (int i = 0; i < N; i++){
			for(int j= 0; j <N; j++){
				paths[i][j] = "";
				int k = i;
				if (next[i][j] == -1)
					continue;
				paths[i][j] = "(" + i;
				while(next[k][j] != j){
					k = next[k][j];
					paths[i][j]+= ",";
					paths[i][j]+= k;
				}
				paths[i][j] += ",";
				paths[i][j] += j;
				paths[i][j] += ")";

			}
		}
		
		for (int i = 0; i < N; i++){
			for(int j= 0; j <N; j++){
				System.out.println("Path from "+ i + " to " + j + ": " + paths[i][j]);
			}
		}
		System.out.println(MatrixTool.toString(dist));
		double endTime = System.currentTimeMillis();
		double execTime = endTime - startTime;
		System.out.println("Simulation took :" + execTime + " to finish.");

		/*double[][] M = new double[8000][8000];
		double[][] N = new double[M.length][M.length];
		double randomN = 100000.;
		for(int i = 0; i< M.length; i++){
			for(int j = 0; j <M.length; j++){
				M[i][j] = randomN*Math.random();
				N[i][j] = randomN*Math.random();
			}
		}
		System.out.println("Initializing done");
		double startTime = System.currentTimeMillis();
		double[][] Res = MatrixTool.multiply(M, N);
		System.out.println("Done");
		double endTime = System.currentTimeMillis();
		double execTime = endTime - startTime;
		System.out.println("Simulation took :" + execTime + " to finish.");*/

	}

}
