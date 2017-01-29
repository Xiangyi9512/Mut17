package examples;
import java.util.Random;

public class MinArray {

	public static void minArray(int n, int[] b, SimpleInteger x) {
		int i = 1;
		while (i != n) {
			dummy(n,b,x,i);
			if (x.value >= b[i]) {
				x.value = b[i];
				i++;
			} else if (x.value <= b[i]) {
				i++;
			} else {
				System.err.println("IF FAILED");
				System.exit(-1);
			}
		}
	}
	
	public static void dummy(int n, int[] b, SimpleInteger x, int i) {
		// do nothing
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		for(int j=0; j<100; j++){
			// int seed = Integer.parseInt("220240967");
			Random rand = new Random();
			int n = rand.nextInt(100) + 1;
			int[] b = new int[n];
			for (int i=0;i<n;i++) {
				b[i] = rand.nextInt(201)-100;
			}
			SimpleInteger x = new SimpleInteger(b[0]);
	//		System.out.println("Input array b: " + Util.arrayToString(b));
	//		System.out.println("Input n: " + n);
			minArray(n, b, x);
	//		System.out.println("Output x: " + x);
		}
	}
}
