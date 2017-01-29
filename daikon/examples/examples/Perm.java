package examples;

import java.util.Random;

/*
 * Order x and y numerically.
 * Wrapper class needed for Java's pass-by-value paradigm.
 */
public class Perm {

	// Order x and y
	public static void perm(SimpleInteger x, SimpleInteger y) {
		if (x.value <= y.value) {
			;
		} else if (y.value <= x.value) {
			int tmp = y.value;
			y.value = x.value;
			x.value = tmp;
		} else {
			System.err.println("IF FAILED");
			System.exit(-1);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int seed = Integer.parseInt(args[0]);
		Random rand = new Random(seed);
		SimpleInteger x = new SimpleInteger(rand.nextInt(201)-100);
		SimpleInteger y = new SimpleInteger(rand.nextInt(201)-100);
//		System.out.println("Input x: " + x.value);
//		System.out.println("Input y: " + y.value);
		perm(x,y);
//		System.out.println("Output x: " + x.value);
//		System.out.println("Output y: " + y.value);
	}

}
