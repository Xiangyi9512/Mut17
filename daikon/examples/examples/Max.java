package examples;

import java.util.Random;

/**
 * Based on Chapter 14, Page 172 of Gries.
 * @author drw
 *
 */
public class Max {

	public static void max(int x, int y, SimpleInteger z) {
		
		if (x >= y) {
			z.value = x;
		} else if (y >= x) {
			z.value = y;
		} else {
			System.err.println("IF Failed");
			System.exit(-1);
		}
	}
	
	public static void main(String[] args) {

		for(int j=0; j<100; j++){
			// int seed = Integer.parseInt("220240967");
			Random rand = new Random();
		
			int x = rand.nextInt(201)-100;
			int y = rand.nextInt(201)-100;
			
	//		System.out.println("Input x: " + x);
	//		System.out.println("Input y: " + y);
			SimpleInteger z = new SimpleInteger(0);
			max(x,y,z);
	//		System.out.println("Ouput z: " + z);
		}
	}
}
