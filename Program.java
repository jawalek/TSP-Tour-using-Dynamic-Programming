import java.util.*;

public class Program {
	private ArrayList<Integer> outputArray = new ArrayList<Integer>();
	private int g[][], p[][], npow, N, d[][];
	public static double time;
	static private int inputArray[][]; 

	public Program() {
	}

	public ArrayList< Integer> computeTSP(int[][] inputArray, int n) {
		double start = System.nanoTime();
		N = n;
		npow = (int) Math.pow(2, n);
		g = new int[n][npow];
		p = new int[n][npow];
		d = inputArray;
		int i, j, k, l, m, s;
		for (i = 0; i < n; i++) {
			for (j = 0; j < npow; j++) {
				g[i][j] = -1;
				p[i][j] = -1;
			}
		}
		// initialize based on distance matrix
		for (i = 0; i < n; i++) {
			g[i][0] = inputArray[i][0];
		}
		int result = tsp(0, npow - 2);
		outputArray.add(0);
		getPath(0, npow - 2);
		outputArray.add(result);
		double end = System.nanoTime();
		time = (end - start) / 1000;
		return outputArray;
	}

	private int tsp(int start, int set) {
		int masked, mask, result = -1, temp =0;
		if (g[start][set] != -1) {
			return g[start][set];
		} else {
			for (int x = 0; x < N; x++) {
				mask = npow - 1 - (int) Math.pow(2, x);
				masked = set & mask;
				if (masked != set) {
					temp = d[start][x] + tsp(x, masked);
					if (result == -1 || result > temp) {
						result = temp;
						p[start][set] = x;
					}
				}
			}
			//System.out.println();
			g[start][set] = result;
			return result;
		}
	}

	private void getPath(int start, int set) {
		if (p[start][set] == -1) {
			return;
		}
		int x = p[start][set];
		int mask = npow - 1 - (int) Math.pow(2, x);
		int masked = set & mask;
		//System.out.println(set);
		outputArray.add(x);
		getPath(x, masked);
	}
	
	public static void main(String []args){
		Program p = new Program();
		System.out.println("Enter number of cities:");
		int n;
		Scanner reader = new Scanner(System.in);
		n = reader.nextInt();
		inputArray = new int[n + 1][n + 1];
		System.out.println("Enter distance:");
		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
			{
				inputArray[i][j]= reader.nextInt();
			}
		ArrayList<Integer> cost = p.computeTSP(inputArray, n);
		System.out.println("TSP tour cost: "+cost.get(n));
		System.out.println("Shortest Path: ");
		for(int i=0; i<n; i++)
			System.out.print(cost.get(i) + "-->");
		System.out.println(cost.get(0));
		System.out.println("Time Taken : " + time + " micro-second");
	}
}
