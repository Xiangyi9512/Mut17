package examples;

import java.util.Random;

public class FourTupleSort {
	
	public static void fourTupleSort(SimpleInteger q0, SimpleInteger q1, 
			SimpleInteger q2, SimpleInteger q3) {
		
		boolean swapping = true;
		int tmp;
		
		while (swapping) {
			swapping = false;
			if (q0.value > q1.value) {
				tmp = q0.value;
				q0.value = q1.value;
				q1.value = tmp;
				swapping = true;
			} else if (q1.value > q2.value) {
				tmp = q1.value;
				q1.value = q2.value;
				q2.value = tmp;
				swapping = true;
			} else if (q2.value > q3.value) {
				tmp = q2.value;
				q2.value = q3.value;
				q3.value = tmp;
				swapping = true;
			}
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int seed = Integer.parseInt(args[0]);
		Random rand = new Random(seed);
		SimpleInteger q0 = new SimpleInteger(rand.nextInt(201)-100);
		SimpleInteger q1 = new SimpleInteger(rand.nextInt(201)-100);
		SimpleInteger q2 = new SimpleInteger(rand.nextInt(201)-100);
		SimpleInteger q3 = new SimpleInteger(rand.nextInt(201)-100);
		//System.out.print("Tuple input: ");
		//System.out.println(q0 + " " + q1 + " " + q2 + " " + q3);
		fourTupleSort(q0,q1,q2,q3);
		//System.out.print("Tuple output: ");
		//System.out.println(q0 + " " + q1 + " " + q2 + " " + q3);
	}

}
