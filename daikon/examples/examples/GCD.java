package examples;

import java.util.Random;

public class GCD {

	public static void gCD(SimpleInteger x, SimpleInteger y) {
		boolean working = true;
		int X = x.value;
		int Y = y.value;
		while (working) {
			dummy(x,y,X,Y);
			working = false;
			if (x.value > y.value) {
				x.value = x.value - y.value;
				working = true;
			} else if (y.value > x.value) {
				y.value = y.value - x.value;
				working = true;
			}
		}
	}

	public static void dummy(SimpleInteger x, SimpleInteger y, int X, int Y) {
		// do nothing
	}
	
	public static void main(String[] args) {
		for(int i=0; i<100; i++){
			// int seed = Integer.parseInt("220240967");
			Random rand = new Random();
			SimpleInteger x;
			SimpleInteger y;
			if (args.length > 1 && args[1].equals("specialGCD")) {
				int d = rand.nextInt(9)+2;
				x = new SimpleInteger(d*(rand.nextInt(100/d)+1));
				y = new SimpleInteger(d*(rand.nextInt(100/d)+1));	
			} else {
				x = new SimpleInteger(rand.nextInt(100)+1);
				y = new SimpleInteger(rand.nextInt(100)+1);
			}
			
			//System.out.println("x input: " + x.value);
			//System.out.println("y input: " + y.value);
			gCD(x,y);
			//System.out.println("x output: " + x.value);
		}
	}

}
