package examples;

import java.util.Random;

public class Abs {

	public static void abs(SimpleInteger x) {
		if (x.value >= 0) {
			;
		} else if (x.value <= 0) {
			x.value = - x.value;
		} else {
			System.err.println("IF FAILED");
			System.exit(-1);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i=0; i<100; i++){
			// int seed = Integer.parseInt("220240967");
			Random rand = new Random();
			SimpleInteger x = new SimpleInteger(rand.nextInt(201)-100);
			//System.out.println("Input x = " + x);
			abs(x);
			//System.out.println("Abs(input) = " + x);
		}
	}

}
