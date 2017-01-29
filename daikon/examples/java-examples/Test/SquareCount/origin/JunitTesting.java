public class JunitTesting {
	
   	
	public int square(int x){
		return x*x;
	}
	
	public float div(int a, int b){
		if(b == 0){
			return 0.0f;
		}
		return (float)a/(float)b;
	}

	public int countA(String word){
		int count = 0;
		for(int i = 0; i < word.length(); i++){
			if(word.charAt(i)=='a' || word.charAt(i)=='A'){
				count++;
			}
		}
		return count;
	}
	
	 public static void main(String[] args){ 
		 String myString = "a";

		 for(int i = 0; i < 5; i++){
		 	for(int n = 0; n < 5; n++){
		 		JunitTesting test = new JunitTesting();
			 	int squareResult = test.square(i);
			 	System.out.println(squareResult);
			 	myString = myString + "a";
			 	int countResult = test.countA(myString);
			 	System.out.println(countResult);
			 	float divisionResult = test.div(i,n);
			 	System.out.println(divisionResult);
		 	}
			 
		 }
	 }
}
