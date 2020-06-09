import java.time.LocalDate;

public class OwnedSpace extends AbstractSpace implements Cloneable{
    public OwnedSpace(){
        super();
    }
    public OwnedSpace(Person person, LocalDate sinceDate){
        super(person, sinceDate);
    }
    public OwnedSpace(Person person,Vehicle vehicle, LocalDate sinceDate){
        super(person,vehicle, sinceDate);
    }
    public String toString(){
        return String.format("Owner: %s",super.toString());
    }
    public int hashCode(){
        int hash = super.hashCode()*71;
        return  hash;
    }
    public Object clone()  {
        return super.clone();
    }
}
