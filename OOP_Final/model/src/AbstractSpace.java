import com.sun.org.apache.bcel.internal.generic.LADD;

import java.time.LocalDate;
import java.time.Period;

public abstract class AbstractSpace implements  Space, Cloneable {
    private Person person;
    private Vehicle vehicle;
    private LocalDate sinceDate;

    protected AbstractSpace(){
        person = Person.UNKNOWN_PERSON;
        vehicle = Vehicle.NO_VEHICLE;
        sinceDate = LocalDate.now();
    }
    protected AbstractSpace(Person person, LocalDate sinceDate){
        if (sinceDate == null){
            throw new NullPointerException("Exception: sinceDate is null!");
        }
        if (sinceDate.isAfter(LocalDate.now())){
            throw new java.lang.IllegalArgumentException("Exception: date is incorrect!");
        }
        this.sinceDate = sinceDate;
        this.person = person;
        vehicle = Vehicle.NO_VEHICLE;
    }
    protected AbstractSpace(Person person, Vehicle vehicle, LocalDate sinceDate){
        if (sinceDate == null){
            throw new NullPointerException("Exception: sinceDate is null!");
        }
        if (sinceDate.isAfter(LocalDate.now())){
            throw new java.lang.IllegalArgumentException("Exception: date is incorrect!");
        }
        this.sinceDate = sinceDate;
        this.person = person;
        this.vehicle = vehicle;
    }

    public Person getPerson(){
        return person;
    }
    public Vehicle getVehicle(){
        return vehicle;
    }

    public LocalDate getSinceDate(){
        return sinceDate;
    }

    public  void setPerson(Person person){
        if (person == null){
            throw new NullPointerException("Exception: argumet person is null!");
        }
        this.person = person;
    }

    public  void setVehicle(Vehicle vehicle){
        if (vehicle == null){
            throw new NullPointerException("Exception: argumet vehicle is null!");
        }
        this.vehicle = vehicle;
    }

    public void setSinceDate(LocalDate sinceDate){
        if (sinceDate == null){
            throw new NullPointerException("Exception: argumet sinceDate is null!");
        }
        this.sinceDate = sinceDate;
    }

    public boolean isEmpty(){
        return vehicle.equals(Vehicle.NO_VEHICLE)||vehicle.getType().equals(VehicleTypes.NONE);
    }

    public Period period(){
        return Period.between(sinceDate,LocalDate.now());
    }

    public String toString(){
        return String.format("%s ТС: %s Date: %s",this.person,this.vehicle,this.sinceDate);
    }

    public int hashCode(){
        int hash = this.person.hashCode()*this.vehicle.hashCode()*this.sinceDate.hashCode();
        return  hash;
    }

    public boolean equals(Object obj){
        AbstractSpace otherSpace = (AbstractSpace) obj;
        return this.person == otherSpace.person && this.vehicle == otherSpace.vehicle && this.sinceDate == otherSpace.sinceDate;
    }

    public Object clone()  {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


}
