// This is a mutant program.
// Author : ysma

public class person
{

    private java.lang.String name;

    private int age;

    public person( java.lang.String n, int a )
    {
        this.name = n;
        this.age = a++;
    }

    public  void setName( java.lang.String n )
    {
        name = n;
    }

    public  java.lang.String getName()
    {
        return name;
    }

    public  void setAge( int a )
    {
        age = a;
    }

    public  int getAge()
    {
        return age;
    }

}
