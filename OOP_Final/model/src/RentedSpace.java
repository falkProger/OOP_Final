import jdk.internal.org.objectweb.asm.tree.analysis.Value;

import java.time.LocalDate;
import java.time.Period;

public class RentedSpace extends AbstractSpace implements Cloneable {
    private LocalDate rentEndsDate;

    public RentedSpace(){
        super();
        this.rentEndsDate = LocalDate.now().plusDays(1);
    }
    public RentedSpace(Person person, Vehicle vehicle, LocalDate sinceDate, LocalDate rentEndsDate){
        super(person,vehicle,sinceDate);
        if (rentEndsDate == null){
            throw new NullPointerException("Exception: rentEndsDate is null!");
        }
        if (rentEndsDate.isBefore(sinceDate)){
            throw new java.lang.IllegalArgumentException("Exception: rentEndsDate is incorrect!");
        }
        this.rentEndsDate = rentEndsDate;
    }

    public LocalDate getRentEndsDate(){
        return rentEndsDate;
    }

    public void setRentEndsDate(LocalDate rentEndsDate){
        if (rentEndsDate == null){
            throw new NullPointerException("Exception: rentEndsDate is null!");
        }
        this.rentEndsDate = rentEndsDate;
    }

    public Period beforeRentEndsPeriod(){
        Period period = Period.between(LocalDate.now(), rentEndsDate);
        return period;
    }

    public String toString(){
        return String.format("Tenant: %s",super.toString());
    }
    public int hashCode(){
        int hash = super.hashCode()*53;
        return  hash;
    }
    public Object clone()  {
        return super.clone();
    }
}
