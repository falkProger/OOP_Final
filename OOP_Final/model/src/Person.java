public class Person implements Cloneable{
    private String firstName;
    private String lastName;
    public static final Person UNKNOWN_PERSON = new Person("","");

    public Person(String firstName, String lastName){
        if (firstName == null || lastName == null){
            throw new NullPointerException("Exception: one of the arguments is null!");
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }

    public String toString(){
        return String.format("%s %s",this.firstName,this.lastName);
    }

    public int hashCode(){
        int hash = this.firstName.hashCode()&this.getLastName().hashCode();
        return  hash;
    }

    public boolean equals(Object obj){
        Person otherPerson = (Person) obj;
        return this.lastName == otherPerson.lastName && this.firstName == otherPerson.firstName;
    }

    public Object clone()  {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
