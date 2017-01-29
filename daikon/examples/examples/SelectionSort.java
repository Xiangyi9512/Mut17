package examples;

import java.util.Random;

/**
 * Selection sort class.  
 * 
 * @author drw
 */
public class SelectionSort {

	public static void selectionSort(int[] arr) {
		int i=0, j=0, minIndex=0, tmp=0;
		int n = arr.length;
		for (i = 0; i < n - 1; i++) {
			dummyOuter(arr,i,j,minIndex,tmp,n);
			minIndex = i;
			for (j = i + 1; j < n; j++) {
				dummyInner(arr,i,j,minIndex,tmp,n);
				if (arr[j] < arr[minIndex]) {
					minIndex = j;
				}
			}
			if (minIndex != i) {
				tmp = arr[i];
				arr[i] = arr[minIndex];
				arr[minIndex] = tmp;
			}
		}
	}
	
	public static void dummyOuter(int[] arr, int i, int j, int minIndex,
									int tmp, int n) {
		// do nothing
	}
	
	public static void dummyInner(int[] arr, int i, int j, int minIndex,
			int tmp, int n) {
		// do nothing
	}
	public static void main(String[] args) {
		
		int seed = Integer.parseInt(args[0]);
		
		Random rand = new Random(seed);
		
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
		
		selectionSort(arr);
		
//		System.out.print("sorted array is [");
//		for (int x = 0; x < arr.length; x++) {
//			System.out.print(" " + arr[x]);
//		}
//		System.out.println("]");

	}
}
