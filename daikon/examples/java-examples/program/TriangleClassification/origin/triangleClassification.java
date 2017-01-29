import java.util.*;

public class triangleClassification {
	public enum TriangleType {
	    EQUILATERAL, INVALID, ISOSCELES, SCALENE
	}
	
	static TriangleType tri_type(int a, int b, int c) {
		int type;
		if (a > b) { int t = a; a = b; b = t; }
		if (a > c) { int t = a; a = c; c = t; }
		if (b > c) { int t = b; b = c; c = t; }
		if (a + b <= c) {
			TriangleType triangleType = TriangleType.INVALID;
			return triangleType;
		} 
		else {
			TriangleType triangleType;
			if (a == b && b == c){
				triangleType = TriangleType.EQUILATERAL;
				return triangleType;
			} 
			else if (a == b || b == c) {
				triangleType = TriangleType.ISOSCELES;
				return triangleType;
			}
			triangleType = TriangleType.SCALENE;
			return triangleType;
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TriangleType triangleType;
		triangleType = tri_type(2,3,6);
		System.out.println(triangleType);
		triangleType = tri_type(6,6,6);
		System.out.println(triangleType);
		triangleType = tri_type(4,4,5);
		System.out.println(triangleType);
		triangleType = tri_type(3,5,7);
		System.out.println(triangleType);
	}
	

}
