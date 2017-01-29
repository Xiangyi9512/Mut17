public class gcd {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//a=b
		int methodOne1 = gcdMethodOne(13,13);
		int methodTwo1 = gcdMethodTwo(13,13);
		System.out.println(methodOne1 + " " + methodTwo1);
		//first argument is a prime
		int methodOne2 = gcdMethodOne(37, 600);
		int methodTwo2 = gcdMethodTwo(37, 600);
		System.out.println(methodOne2 + " " + methodTwo2);
		//one is multiple of other
		int methodOne3 = gcdMethodOne(20, 100);
		int methodTwo3 = gcdMethodTwo(20, 100);
		System.out.println(methodOne3 + " " + methodTwo3);
		//straight case
		int methodOne4 = gcdMethodOne(624129, 2061517);
		int methodTwo4 = gcdMethodTwo(624129, 2061517);
		System.out.println(methodOne4 + " " + methodTwo4);
	}
	
	public static int gcdMethodOne(int n1, int n2){
        int r;
         
        while(n2 != 0)
        {
            r = n1 % n2;
            n1 = n2;
            n2 = r;
        }
        return n1;
	}
	
	public static int gcdMethodTwo(int n1, int n2){
		 while(n1 != n2)
         {
             if(n1 > n2)
                 n1 = n1-n2;
             else
                 n2 = n2-n1;
         }
         return n1;
	}
}
