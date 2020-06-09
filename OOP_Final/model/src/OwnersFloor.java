import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;


public class OwnersFloor implements Floor, Cloneable {
    private Space[] spaces;
    private int size;
    private int count;
    public OwnersFloor(){
        this.size = 16;
        this.spaces = new Space[size];
        this.count=0;
    }

    public OwnersFloor(int size){
        this.size = size;
        this.spaces = new Space[size];
        this.count=0;
    }

    public OwnersFloor(Space[] spaces){
        this.size = size();
        this.spaces = spaces;
    }



    public boolean add(Space space){
        if (space == null){
            throw new NullPointerException("Exception: space is null!");
        }
        boolean temp = false;
        for (int i = size-1; i >= 0; i--){
            if (spaces[i]==null){
                spaces[i] =space;
                count++;
                temp = true;
                break;
            }
        }
        return temp;
    }

    @Override
    public boolean remove(Object o) {
        return remove((Space) o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        for (Object x : c) {
            if (!contains(x)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Space> c) {
        for (Space object : c) {
            add(object);
        }
        return true;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        boolean collectionsHasChanged = false;
        for (Object x : c) {
            if (contains(x)) {
                remove(x);
                count--;
                collectionsHasChanged = true;
            }
        }
        return collectionsHasChanged;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        boolean collectionHasChanged = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(get(i))) {
                remove(i--);
                collectionHasChanged = true;
            }
        }
        return collectionHasChanged;
    }

    @Override
    public void clear() {
        spaces = new Space[16];
        count = 0;
    }

    public boolean add(int index, Space space){
        if (index<0 || index>size){
            throw new IndexOutOfBoundsException("Exception: IndexOutOfBoundsException!");
        }
        if (space == null){
            throw new NullPointerException("Exception: space is null!");
        }
        if (index<size && spaces[index]==null){
            spaces[index] =space;
            count++;
            return true;
        }
        else{
            return false;
        }
    }

    public Space get(int index){
        if (index<0 || index>size){
            throw new IndexOutOfBoundsException("Exception: IndexOutOfBoundsException!");
        }
        return spaces[index];
    }

    public Space get(String registrationNumber){
        if (registrationNumber == null){
            throw new NullPointerException("Exception: registrationNumber is null!");
        }
        if (!Pattern.matches("[ABEKMHOPCTYX]\\d\\d\\d[ABEKMHOPCTYX][ABEKMHOPCTYX]\\d{2,3}",registrationNumber)){
            throw new RegistrationNumberFormatException("Exception: wrong format registrationNumber!");
        }
        for (int i = 0;i<size;i++)
        {
            if (spaces[i]!=null && spaces[i].getVehicle().getRegistrationNumber().equals(registrationNumber)){
                return spaces[i];
            }
        }
        throw new NoSuchElementException("Exception: not found space with registrationNumber!");
    }

    public int hasSpace(String registrationNumber){
        if (registrationNumber == null){
            throw new NullPointerException("Exception: registrationNumber is null!");
        }
        if (!Pattern.matches("[ABEKMHOPCTYX]\\d\\d\\d[ABEKMHOPCTYX][ABEKMHOPCTYX]\\d{2,3}",registrationNumber)){
            throw new RegistrationNumberFormatException("Exception: wrong format registrationNumber!");
        }
        for (int i = 0;i<size;i++)
        {
            if (spaces[i]!=null && spaces[i].getVehicle().getRegistrationNumber().equals(registrationNumber)){
                return i;
            }
        }
        throw new NoSuchElementException("Exception: not found space with registrationNumber!");
    }

    public Space set(int index, Space space){
        if (index<0 || index>size){
            throw new IndexOutOfBoundsException("Exception: IndexOutOfBoundsException!");
        }
        if (space == null){
            throw new NullPointerException("Exception: space is null!");
        }
        if (index < size){
            spaces[index] = space;
            return spaces[index];
        }
        else {
            return null;
        }
    }

    public Space remove(int index){
        if (index<0 || index>size){
            throw new IndexOutOfBoundsException("Exception: IndexOutOfBoundsException!");
        }
        if (index < size && spaces[index]!=null){
            Space space = spaces[index];
            count--;
            for (int i=index+1;i<size;i++){
                spaces[i-1] = spaces[i];
            }

            spaces[size-1]=null;
            return space;
        }
        else {
            return null;
        }
    }

    public Space remove(String registrationNumber){
        if (registrationNumber == null){
            throw new NullPointerException("Exception: registrationNumber is null!");
        }
        if (!Pattern.matches("[ABEKMHOPCTYX]\\d\\d\\d[ABEKMHOPCTYX][ABEKMHOPCTYX]\\d{2,3}",registrationNumber)){
            throw new RegistrationNumberFormatException("Exception: wrong format registrationNumber!");
        }
        for (int i = 0;i<size;i++)
        {
            if (spaces[i]!=null && spaces[i].getVehicle().getRegistrationNumber().equals(registrationNumber)){
                Space space = spaces[i];
                count--;
                for (int j = i+1;j<size;j++){
                    spaces[j-1] = spaces[j];
                }
                spaces[size-1]=null;
                return space;
            }
        }
        throw new NoSuchElementException("Exception: not found space with registrationNumber!");
}

    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count==0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (spaces[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    public Space[] getSpaces() {
        Space[] getSpaces = new Space[count];
        int j = 0;
        for (int i = 0; i < size; i++) {
            if (spaces[i]!=null){
                getSpaces[j]=spaces[i];
                j++;
            }
        }
        return getSpaces;
    }

    public Deque<Space> getEmptySpaces() {
        Space[] getSpaces = new Space[getSpaces().length];
        int j = 0;
        for (int i = 0; i < getSpaces().length; i++) {
            if (getSpaces()[i].isEmpty()){
                getSpaces[j]=getSpaces()[i];
                j++;
            }
        }
        Space[] returnSpace = new Space[j];
        int k = 0;
        for (int i = 0; i < j; i++) {
            if (getSpaces[i]!=null){
                returnSpace[k] = getSpaces[i];
                k++;
            }
        }
        return new LinkedList<>(Arrays.asList(returnSpace));
    }

    public List<Space> getSpaces(VehicleTypes vehicleTypes){
        if (vehicleTypes == null){
            throw new NullPointerException("Exception: vehicleTypes is null!");
        }
        List<Space> spacesList = new ArrayList<>();
        for (Space space : spaces) {
            if (space.getVehicle().getType().equals(vehicleTypes)) {
                spacesList.add(space);
            }
        }
        return spacesList;
    }

    public List<Vehicle> getVehicles(){
        Vehicle[] getVehicles = new Vehicle[count];
        int j= 0;
        for (int i = 0; i < size; i++) {
            if (spaces[i] != null) {
                getVehicles[j] = spaces[i].getVehicle();
                j++;
            }
        }
        return Arrays.asList(getVehicles);
    }

    public void increaseArray(){
        Space newSpaces[] = new Space[this.size*2];
        for (int i = 0;i<size;i++)
        {
            newSpaces[i]=spaces[i];
        }
        size *=2;
        spaces = newSpaces;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("Spaces:\n");
        Space[] returnSpace = getSpaces();
        for (int i = 0; i < returnSpace.length; i++) {
            sb.append(returnSpace[i].toString()+"\n");
        }
        return sb.toString();
    }

    public int hashCode(){
        int hash = 71* getSpaces().length^getSpaces().hashCode();
        return  hash;
    }

    public boolean equals(Object obj){
        if (obj == null){
            throw new NullPointerException("Exception: obj is null!");
        }
        OwnersFloor ownedFloor = (OwnersFloor) obj;
        return this.count == ownedFloor.size();
    }

    public Object clone()  {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean remove(Space space) {
        if (space == null){
            throw new NullPointerException("Exception: space is null!");
        }
        for (int i = 0; i < getSpaces().length; i++) {
            if (getSpaces()[i].equals(space)){
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(Space space) {
        if (space == null){
            throw new NullPointerException("Exception: space is null!");
        }
        for (int i = 0; i < getSpaces().length; i++) {
            if (getSpaces()[i].equals(space)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int spacesQuantity(Person person) {
        if (person == null){
            throw new NullPointerException("Exception: person is null!");
        }
        int counter = 0;
        for (int i = 0; i < getSpaces().length; i++) {
            if (getSpaces()[i].getPerson().equals(person)){
                counter++;
            }
        }
        return counter;
    }

    public RentedSpace[] rentedSpaces(){
        int counterRentSpaces=0;
        for (int i = 0; i < getSpaces().length; i++) {
            if (getSpaces()[i].getClass().getName().equals("RentedSpace")){
                counterRentSpaces++;
            }
        }
        RentedSpace[] returnSpace = new RentedSpace[counterRentSpaces];
        int k = 0;
        for (int i = 0; i < getSpaces().length; i++) {
            if (getSpaces()[i].getClass().getName().equals("RentedSpace")){
                returnSpace[k]= (RentedSpace) getSpaces()[i];
                k++;
            }
        }
        return returnSpace;
    }

    @Override
    public LocalDate nearestRentEndsDate() throws NoRentedSpaceException {
        RentedSpace rentedSpace = (RentedSpace) spaceWithNearestRentEndsDate();
        return rentedSpace.getRentEndsDate();
    }

    @Override
    public Space spaceWithNearestRentEndsDate() throws NoRentedSpaceException {
        RentedSpace[] rentedSpace = rentedSpaces();
        if (rentedSpace.length==0){
            throw new NoRentedSpaceException("Exception: No RentedSpaces in this Floor!");
        }
        RentedSpace nearestDate = rentedSpace[0];
        for (int i = 0; i < rentedSpace.length; i++) {
            if (nearestDate.getRentEndsDate().isAfter(rentedSpace[i].getRentEndsDate())){
                nearestDate = rentedSpace[i];
            }
        }
        return nearestDate;
    }

    @Override
    public int compareTo(@NotNull Floor o) {
        return this.size() - o.size();
    }

    @NotNull
    @Override
    public Iterator<Space> iterator() {
        return new SpaceIterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return spaces;
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return (T[]) spaces;
    }

    private class SpaceIterator implements Iterator<Space> {
        int index;

        @Override
        public boolean hasNext() {
            return index < spaces.length;
        }

        @Override
        public Space next()throws IndexOutOfBoundsException {
            if (hasNext()) {
                return spaces[index++];
            }
            throw new NoSuchElementException();
        }
    }




}
