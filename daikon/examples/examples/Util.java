package examples;

public class Util {

	public static String arrayToString(int[] x) {
		String result = "";
		for (int i=0;i<x.length;i++) {
			result = result + x[i] + " ";
		}
		return result.trim();
	}
	
	public static String arrayToString(int[][] x) {
		String result = "";
		for (int i=0;i<x.length;i++) {
			for (int j=0;j<x[i].length;j++) {
				result = result + x[i][j] + "\t";
			}
			result = result + "\n";
		}
		return result.trim();
	}
}
