public class main {

	
	public static void main(String[] args) {
		person person1 = new person("Tom", 27);
		person1.setName("Steve");
		System.out.println(person1.getName() + " is " + Integer.toString(person1.getAge()));
		person person2 = new person("Jane", 22);
		System.out.println(person2.getName() + " is " + Integer.toString(person2.getAge()));
	}

}
