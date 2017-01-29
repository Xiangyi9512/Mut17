public class person {
	
	private String name;
	private int age;
	
	public person(String n,int a){
		 name = n;
		 age = a;
	 }
	
	public void setName(String n){
		name = n;
	}
	
	public String getName(){
		return name;
	}
	
	public void setAge(int a){
		age = a;
	}
	
	public int getAge(){
		return age;
	}
}
