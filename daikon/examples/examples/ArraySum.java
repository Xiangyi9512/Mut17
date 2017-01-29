package examples;

import java.util.Random;

public class ArraySum {

	public static void arraySum(int n, int[] b, SimpleInteger s) {
		int i = 0;
		while (i != n) {
			loopDummy(i, s, n, b);
			s.value = s.value + b[i];
			i++;
		}
	}
	
	public static void loopDummy(int i, SimpleInteger s, int n, int[] b) {
		// do nothing;
	}
	
	public static void main(String[] args) {
		for(int j=0; j<100; j++){
			// int seed = Integer.parseInt("220240967");
			Random rand = new Random();
			int n = rand.nextInt(100) + 1;
			int[] b = new int[n];
			for (int i=0;i<n;i++) {
				b[i] = rand.nextInt(201)-100;
			}
			//System.out.println("Input array b: " + Util.arrayToString(b));
			//System.out.println("Input n: " + n);
			SimpleInteger s = new SimpleInteger(0);
			arraySum(n, b, s);
			//System.out.println("Output array sum s: " + s);
		}
	}

}
