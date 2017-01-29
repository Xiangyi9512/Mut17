package examples;

import java.util.Random;

public class Search2DArrayV2 {
	
	public static void search2DArrayV2(int m, int n, int x, int[][] b,
			SimpleInteger i, SimpleInteger j) {
		
		boolean searching = true;
		while (searching) {
			searching = false;
			if ((i.value !=m) & (j.value !=n) && (x != b[i.value][j.value])) {
				j.value++;
				searching = true;
			} else if ((i.value != m) && (j.value == n)) {
				i.value++;
				j.value=0;
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int seed = Integer.parseInt(args[0]);
		Random rand = new Random(seed);
		int m = rand.nextInt(100) + 1;
		int n = rand.nextInt(100) + 1;
		int[][] b = new int[m][n];
		for (int i=0;i<m;i++) {
			for (int j=0;j<n;j++) {
				b[i][j] = rand.nextInt(201)-100;;
			}
		}
		int x = b[rand.nextInt(m)+1][rand.nextInt(n)+1];
//		System.out.println("Input array b:\n" + Util.arrayToString(b));
//		System.out.println("Input x: " + x);
//		System.out.println("Input m: " + m);
//		System.out.println("Input n: " + n);
		SimpleInteger i=new SimpleInteger(0),j=new SimpleInteger(0);
		search2DArrayV2(m,n,x,b,i,j);
//		System.out.println("Output i: " + result.i);
//		System.out.println("Output j: " + result.j);
	}

}
