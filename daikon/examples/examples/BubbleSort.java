package examples;

import java.util.Random;

public class BubbleSort {
	
	public static void bubbleSort(int[] x) {
		int n = x.length;
		boolean doMore = true;
		while (doMore) {
			dummyOuter(x,n);
			n--;
			doMore = false; // assume this is our last pass over the array
			for (int i = 0; i < n; i++) {
				dummyInner(x,n,i);
				if (x[i] > x[i + 1]) {
					// exchange elements
					int temp = x[i];
					x[i] = x[i + 1];
					x[i + 1] = temp;
					doMore = true; // after an exchange, must look again
				}
			}
		}
	}
	
	public static void dummyOuter(int[] x, int n) {
		// do nothing
	}
	
	public static void dummyInner(int[] x, int n, int i) {
		// do nothing
	}
	
	public static void main(String[] args) {
		
		for(int j=0; j<100; j++){
			// int seed = Integer.parseInt("220240967");
			Random rand = new Random();
			int length = rand.nextInt(100) + 1;
			
			int[] arr = new int[length];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = rand.nextInt(201) - 100;
			}
			
			//System.out.println("Array size: " + length);
					
			//System.out.print("Original array is [");
			//for (int x = 0; x < arr.length; x++) {
			//System.out.print(" " + arr[x]);
			//}
			//System.out.println("]\n");
			
			bubbleSort(arr);
			
			//System.out.print("sorted array is [");
			//for (int x = 0; x < arr.length; x++) {
			//	System.out.print(" " + arr[x]);
			//}
			//System.out.println("]");
		}
	}
}
