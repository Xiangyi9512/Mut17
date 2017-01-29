package examples;

import java.util.Random;

public class QuickSort  {

	private static void dummy_point1(int i, int j, int pivot, int[] arr) {
	}

	private static void partition(int arr[], int left, int right, 
			SimpleInteger part) {
		
		int i = left, j = right;
		int tmp;
		int pivot = arr[(left + right) / 2];

		while (i <= j) {
			dummy_point1(i, j, pivot, arr);
			while (arr[i] < pivot) {
				i++;
			}
			while (arr[j] > pivot) {
				j--;
			}
			if (i <= j) {
				tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
				i++;
				j--;
			}
		}
		;

		part.value = i;
		
	}

	private static void quicksortRecursive(int arr[], int left, int right) {
		SimpleInteger tempIndex = new SimpleInteger(0);
		partition(arr, left, right,tempIndex);
		int index = tempIndex.value;
		if (left < index - 1) {
			quicksortRecursive(arr, left, index - 1);
		}
		if (index < right) {
			quicksortRecursive(arr, index, right);
		}
	}

	public static void quickSort(int[] arr) {
		quicksortRecursive(arr, 0, arr.length - 1);
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
			
			quickSort(arr);
			
	//		System.out.print("sorted array is [");
	//		for (int x = 0; x < arr.length; x++) {
	//			System.out.print(" " + arr[x]);
	//		}
	//		System.out.println("]");
		}
	}
}