import java.time.LocalDate;
import java.util.*;

public interface Floor extends java.lang.Comparable<Floor>, java.lang.Iterable<Space>,java.util.Collection<Space> {
    public boolean add(Space space);
    public boolean add(int index, Space space);
    public Space get(int index);

    public default Space get(String registrationNumber) {
        for (Iterator<Space> it = iterator(); it.hasNext(); ) {
            Space iter = it.next();
            if (iter!=null && iter.getVehicle().getRegistrationNumber().equals(registrationNumber)){
                return iter;
            }

        }
        throw new NoSuchElementException("Exception: not found space with registrationNumber!");
    }

    public default int hasSpace(String registrationNumber){
        int index = 0;
        for (Iterator<Space> it = iterator(); it.hasNext(); ) {
            Space iter = it.next();
            if (iter!=null && iter.getVehicle().getRegistrationNumber().equals(registrationNumber)){
                return index;
            }
            index++;
        }
        throw new NoSuchElementException("Exception: not found space with registrationNumber!");
    }

    public Space set(int index, Space space);
    public Space remove(int index);
    public default Space remove(String registrationNumber){
        return remove(hasSpace(registrationNumber));
    }
    public int size();
    public default Collection<Vehicle> getVehicles(){
        ArrayList<Vehicle>  getVehicles = new ArrayList<Vehicle>();
        Space[] spaces = (Space[]) getSpaces();
        int j= 0;
        for (int i = 0; i < getSpaces().length; i++) {
            if (getSpaces()[i] != null) {
                getVehicles.add(spaces[i].getVehicle());
                j++;
            }
        }
        return getVehicles;
    }
    public void increaseArray();
    public Space[] getSpaces();
    public default List<Space> getSpaces(VehicleTypes vehicleTypes){
        Space[] returnSpace = new Space[size()];
        int i = 0;
        for (Iterator<Space> it = iterator(); it.hasNext(); ) {
            Space iter = it.next();
            if (iter.getVehicle().getType().equals(vehicleTypes)){
                returnSpace[i] = iter;
                i++;
            }
        }
        return Arrays.asList(returnSpace);
    }
    public default Deque<Space> getEmptySpaces(){
        Space[] returnSpace = new Space[size()];
        int i = 0;
        for (Iterator<Space> it = iterator(); it.hasNext(); ){
            Space iter = it.next();
            if(iter.isEmpty()){
                returnSpace[i] = iter;
                i++;
            }
        }
        return new LinkedList<>(Arrays.asList(returnSpace));
    }
    public String toString();
    public int hashCode();
    public boolean equals(Object obj);
    public Object clone();
    public boolean remove(Space space);
    public default int indexOf(Space space){
        int i = 0;
        for (Iterator<Space> it = iterator(); it.hasNext(); ){
            Space iter = it.next();
            if(iter.equals(space)){
                return i;
            }
            i++;
        }
        return -1;
    }
    public default int spacesQuantity(Person person){
        int counter = 0;
        for (Iterator<Space> it = iterator(); it.hasNext(); ){
            Space iter = it.next();
            if (iter.getPerson().equals(person)){
                counter++;
            }
        }
        return counter;
    }
    public default LocalDate nearestRentEndsDate() throws NoRentedSpaceException{
        RentedSpace rentedSpace = (RentedSpace) spaceWithNearestRentEndsDate();
        return rentedSpace.getRentEndsDate();
    }
    public Space spaceWithNearestRentEndsDate() throws NoRentedSpaceException;
}
