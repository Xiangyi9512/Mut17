import java.util.Arrays;

public class quicksort {
	public static void main(String[] args) {
		int[] input1 = {24,2,45,20,56,32,43};
        int[] input2 = {2,3,4,5,6,7,8,9,10};
        int[] input3 = {8,7,6,5,4,3,2,1};
        int[] input4 = {1,1,1,1,1,1,1,1,1,1,1};
        int[] input5 = {1,1,1,2,2,3};
 
		int low = 0;
		int high = input1.length - 1;
		quickSort(input1, low, high);
		System.out.println(Arrays.toString(input1));
		high = input2.length - 1;
		quickSort(input2, low, high);
		System.out.println(Arrays.toString(input2));
		high = input3.length - 1;
		quickSort(input3, low, high);
		System.out.println(Arrays.toString(input3));
		high = input4.length - 1;
		quickSort(input4, low, high);
		System.out.println(Arrays.toString(input4));
		high = input5.length - 1;
		quickSort(input5, low, high);
		System.out.println(Arrays.toString(input5));
	}
 
	public static void quickSort(int[] arr, int low, int high) {
		if (arr == null || arr.length == 0)
			return;
 
		if (low >= high)
			return;
 
		// pick the pivot
		int middle = low + (high - low) / 2;
		int pivot = arr[middle];
 
		// make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j) {
			while (arr[i] < pivot) {
				i++;
			}
 
			while (arr[j] > pivot) {
				j--;
			}
 
			if (i <= j) {
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}
 
		// recursively sort two sub parts
		if (low < j)
			quickSort(arr, low, j);
 
		if (high > i)
			quickSort(arr, i, high);
	}
}