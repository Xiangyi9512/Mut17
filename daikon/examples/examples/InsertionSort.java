package examples;

import java.util.Random;

public class InsertionSort {
	
	public static void insertionSort(int[] arr) {
		int i=0, j=0, newValue=0;
		for (i = 1; i < arr.length; i++) {
			dummyOuter(arr, i, j, newValue);
			newValue = arr[i];
			j = i;
			while (j > 0 && arr[j - 1] > newValue) {
				dummyInner(arr,i,j,newValue);
				arr[j] = arr[j - 1];
				j--;
			}
			arr[j] = newValue;
		}
	}
	
	public static void dummyOuter(int[] arr, int i, int j, int newValue) {
		// do nothing
	}
	
	public static void dummyInner(int[] arr, int i, int j, int newValue) {
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
			
	//		System.out.println("Array size: " + length);
	//				
	//		System.out.print("Original array is [");
	//		for (int x = 0; x < arr.length; x++) {
	//			System.out.print(" " + arr[x]);
	//		}
	//		System.out.println("]\n");
			
			insertionSort(arr);
			
	//		System.out.print("sorted array is [");
	//		for (int x = 0; x < arr.length; x++) {
	//			System.out.print(" " + arr[x]);
	//		}
	//		System.out.println("]");
		}
	}
}
