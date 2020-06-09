import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

public class Parking implements java.lang.Iterable<Floor> {
    private Floor[] floors;
    private int size;
    private int count;

    public Parking(int size){
        this.size = size;
        this.floors = new Floor[size];
    }

    public Parking(Floor[] ownersFloors){
        this.floors = ownersFloors;
    }

    public boolean add(Floor floor){
        if (floor == null){
            throw new NullPointerException("Exception: floor is null!");
        }
        boolean temp = false;
        for (int i = size-1; i >= 0; i--){
            if (floors[i]==null){
                floors[i] =floor;
                count++;
                temp = true;
                break;
            }
        }
        if (temp){
            return temp;
        }
        else{
            increaseArray();
            return temp;
        }

    }

    public boolean add(int index, Floor floor){
        if (index<0 || index>size){
            throw new IndexOutOfBoundsException("Exception: IndexOutOfBoundsException!");
        }
        if (floor == null){
            throw new NullPointerException("Exception: floor is null!");
        }
        if (index<size && floors[index]==null){
            floors[index] =floor;
            count++;
            return true;
        }
        else{
            increaseArray();
            return false;
        }
    }

    public Floor get(int index){
        if (index<0 || index>size){
            throw new IndexOutOfBoundsException("Exception: IndexOutOfBoundsException!");
        }
        return floors[index];
    }

    public Floor set(int index, Floor floor){
        if (index<0 || index>size){
            throw new IndexOutOfBoundsException("Exception: IndexOutOfBoundsException!");
        }
        if (floor == null){
            throw new NullPointerException("Exception: floor is null!");
        }
        if (index < size){
            floors[index] = floor;
            return floors[index];
        }
        else {
            return null;
        }
    }

    public Floor remove(int index){
        if (index<0 || index>size){
            throw new IndexOutOfBoundsException("Exception: IndexOutOfBoundsException!");
        }
        if (index < size && floors[index]!=null){
            Floor floor = floors[index];
            count--;
            for (int i=index+1;i<size;i++){
                floors[i-1] = floors[i];
            }

            floors[size-1]=null;
            return floor;
        }
        else {
            return null;
        }
    }

    public int size(){
        return count;
    }

    public Floor[] getFloors() {
        Arrays.sort(floors);
        return floors;
    }

    public List<Floor> getFloorsSort(){
        Floor[] newArray = new Floor[size];
        System.arraycopy(size, 0, newArray, 0, size);
        Arrays.sort(newArray);
        return Arrays.asList(newArray);
    }

    public Collection<Vehicle> getVehicle(){
        List<Vehicle> vehicles = new ArrayList<>();
        for (Floor ownersFloor : floors) {
            vehicles.addAll(ownersFloor.getVehicles());
        }
        return vehicles;
    }

    public Space getSpace(String registrationNumber){
        if (registrationNumber == null){
            throw new NullPointerException("Exception: registrationNumber is null!");
        }
        if (!Pattern.matches("[ABEKMHOPCTYX]\\d\\d\\d[ABEKMHOPCTYX][ABEKMHOPCTYX]\\d{2,3}",registrationNumber)){
            throw new RegistrationNumberFormatException("Exception: wrong format registrationNumber!");
        }
        for (Floor ownersFloor : floors) {
            Space[] getSpace = ownersFloor.getSpaces();
            for (int j = 0; j < ownersFloor.size(); j++) {
                if (getSpace[j].getVehicle().getRegistrationNumber().equals(registrationNumber)){
                    return getSpace[j];
                }
            }
        }
        throw new NoSuchElementException("Exception: not found space with registrationNumber!");
    }

    public Space removeSpace(String registrationNumber){
        if (registrationNumber == null){
            throw new NullPointerException("Exception: registrationNumber is null!");
        }
        if (!Pattern.matches("[ABEKMHOPCTYX]\\d\\d\\d[ABEKMHOPCTYX][ABEKMHOPCTYX]\\d{2,3}",registrationNumber)){
            throw new RegistrationNumberFormatException("Exception: wrong format registrationNumber!");
        }
        for (Floor ownersFloor : floors) {
            Space[] getSpace = ownersFloor.getSpaces();
            for (int j = 0; j < ownersFloor.size(); j++) {
                if (getSpace[j].getVehicle().getRegistrationNumber().equals(registrationNumber)){
                    return ownersFloor.remove(registrationNumber);
                }
            }
        }
        throw new NoSuchElementException("Exception: not found space with registrationNumber!");
    }

    public Space setSpace(String registrationNumber, Space space){
        if (registrationNumber == null){
            throw new NullPointerException("Exception: registrationNumber is null!");
        }
        if (space == null){
            throw new NullPointerException("Exception: space is null!");
        }
        if (!Pattern.matches("[ABEKMHOPCTYX]\\d\\d\\d[ABEKMHOPCTYX][ABEKMHOPCTYX]\\d{2,3}",registrationNumber)){
            throw new RegistrationNumberFormatException("Exception: wrong format registrationNumber!");
        }
        for (Floor ownersFloor : floors) {
            Space[] getSpace = ownersFloor.getSpaces();
            for (int j = 0; j < ownersFloor.size(); j++) {
                if (getSpace[j].getVehicle().getRegistrationNumber().equals(registrationNumber)){
                    return ownersFloor.set(j,space);

                }
            }
        }
        throw new NoSuchElementException("Exception: not found space with registrationNumber!");
    }

    public int imptySpacesQuantity(){
        int count = 0;
        Floor floors[] = getFloors();
        for (Floor ownersFloor : floors) {
            count += ownersFloor.getEmptySpaces().toArray().length;
        }
        return count;
    }

    public int vehiclesQuantity(VehicleTypes vehicleTypes){
        if (vehicleTypes == null){
            throw new NullPointerException("Exception: vehicleTypes is null!");
        }
        int count = 0;
        Floor floors[] = getFloors();
        for (Floor ownersFloor : floors) {
            count+=ownersFloor.getSpaces(vehicleTypes).toArray().length;
        }
        return count;
    }

    public void increaseArray(){
        Floor newFloors[] = new Floor[this.size*2];
        for (int i = 0;i<size;i++)
        {
            newFloors[i]=floors[i];
        }
        size *=2;
        floors = newFloors;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder(String.format("Floors (%d total):\n",size()));
        Floor[] returnSpace = getFloors();
        for (int i = 0; i < returnSpace.length; i++) {
            sb.append(returnSpace[i].toString()+"\n");
        }
        return sb.toString();
    }

    public Set<Floor> getFloorsWithPerson(Person person){
        if (person == null){
            throw new NullPointerException("Exception: person is null!");
        }
        Set<Floor> floorSet = new HashSet<>();
        for (Floor ownersFloor : floors) {
            for (int i = 0; i < ownersFloor.size(); i++) {
                if (ownersFloor.get(i).getPerson().equals(person)) {
                    floorSet.add(ownersFloor);
                    break;
                }
            }
        }
        return floorSet;
    }

    @Override
    public Iterator<Floor> iterator() {
        return new ParkingIterator();
    }

    private class ParkingIterator implements Iterator<Floor> {

        int index;

        @Override
        public boolean hasNext() {
            return index < floors.length;
        }

        @Override
        public Floor next() {
            if (hasNext()) {
                return floors[index++];
            }
            return null;
        }
    }
}
