import java.time.LocalDate;
import java.time.Period;

public interface Space {
    public Vehicle getVehicle();
    public Person getPerson();
    public void setVehicle(Vehicle vehicle);
    public void setPerson(Person person);
    public boolean isEmpty();
    public String toString();
    public int hashCode();
    public boolean equals(Object obj);
    public Object clone();
    public LocalDate getSinceDate();
    public void setSinceDate(LocalDate sinceDate);
    public Period period();
}
