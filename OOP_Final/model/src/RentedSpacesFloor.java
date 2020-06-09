import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class  RentedSpacesFloor implements Floor, Cloneable{
    private Node head;
    private int size;
    public RentedSpacesFloor(){
        initialHead();
    }

    public RentedSpacesFloor(Space[] spaces){
        for (Space space : spaces) {
            addNode(space);
        }
    }

    private void initialHead(){
        head = new Node(null);
        head.next = head;
        head.previous = head;
    }

    private Node getNodeByIndex(int index) {
        if (index<0 || index>size){
            throw new IndexOutOfBoundsException("Exception: IndexOutOfBoundsException!");
        }
        Node node = head;
        for (; index != 0; index--) {
            node = node.next;
        }
        return node;
    }

    private void addNode(Space space) {
        if (space == null){
            throw new NullPointerException("Exception: space is null!");
        }
        if (head == null) {
            initialHead();
        }
        Node node = head;
        for (int i = 0; i < size; i++) {
            node = node.next;
        }
        node.next = new Node(space, node.next, node);
        node.previous = node.next;
        size++;
    }

    @Override
    public boolean add(Space space) {
        if (space == null){
            throw new NullPointerException("Exception: space is null!");
        }
        return add(size,space);
    }

    @Override
    public boolean add(int index, Space space) {
        if (space == null){
            throw new NullPointerException("Exception: space is null!");
        }
        if (index<0 || index>size){
            throw new IndexOutOfBoundsException("Exception: IndexOutOfBoundsException!");
        }
        Node newNodes = new Node(space);
        Node currentNodes = getNodeByIndex(index);
        newNodes.next = currentNodes.next;
        newNodes.previous = currentNodes;
        currentNodes.next = newNodes;
        size++;
        return true;
    }

    @Override
    public Space get(int index) {
        if (index<0 || index>size){
            throw new IndexOutOfBoundsException("Exception: IndexOutOfBoundsException!");
        }
        return getNodeByIndex(index + 1).value;
    }

    @Override
    public Space get(String registrationNumber) {
        if (registrationNumber == null){
            throw new NullPointerException("Exception: registrationNumber is null!");
        }
        if (!Pattern.matches("[ABEKMHOPCTYX]\\d\\d\\d[ABEKMHOPCTYX][ABEKMHOPCTYX]\\d{2,3}",registrationNumber)){
            throw new RegistrationNumberFormatException("Exception: wrong format registrationNumber!");
        }
        Node node = head.next;
        for (int i = 0; i < size; i++, node = node.next) {
            if (node.value.getVehicle().getRegistrationNumber().equals(registrationNumber)) {
                return node.value;
            }
        }
        throw new NoSuchElementException("Exception: not found space with registrationNumber!");
    }

    @Override
    public int hasSpace(String registrationNumber) {
        if (registrationNumber == null){
            throw new NullPointerException("Exception: registrationNumber is null!");
        }
        if (!Pattern.matches("[ABEKMHOPCTYX]\\d\\d\\d[ABEKMHOPCTYX][ABEKMHOPCTYX]\\d{2,3}",registrationNumber)){
            throw new RegistrationNumberFormatException("Exception: wrong format registrationNumber!");
        }
        Node node = head.next;
        for (int i = 0; i < size; i++, node = node.next) {
            if (node.value.getVehicle().getRegistrationNumber().equals(registrationNumber)) {
                return i;
            }
        }
        throw new NoSuchElementException("Exception: not found space with registrationNumber!");
    }

    @Override
    public Space set(int index, Space space) {
        if (space == null){
            throw new NullPointerException("Exception: space is null!");
        }
        if (index<0 || index>size){
            throw new IndexOutOfBoundsException("Exception: IndexOutOfBoundsException!");
        }
        Node node = getNodeByIndex(index + 1);
        Space oldSpace = node.value;
        node.value = space;
        return oldSpace;
    }

    @Override
    public Space remove(int index) {
        if (index<0 || index>size){
            throw new IndexOutOfBoundsException("Exception: IndexOutOfBoundsException!");
        }
        Node node = getNodeByIndex(index);
        Space removedSpace = node.next.value;
        node.next = node.next.next;
        node.next.next.previous = node;
        size--;
        return removedSpace;
    }

    @Override
    public Space remove(String registrationNumber) {
        if (registrationNumber == null){
            throw new NullPointerException("Exception: registrationNumber is null!");
        }
        if (!Pattern.matches("[ABEKMHOPCTYX]\\d\\d\\d[ABEKMHOPCTYX][ABEKMHOPCTYX]\\d{2,3}",registrationNumber)){
            throw new RegistrationNumberFormatException("Exception: wrong format registrationNumber!");
        }
        return remove(hasSpace(registrationNumber));
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean contains(Object o) {
        Node node = head.next;
        for (int i = 0; i < size; i++, node = node.next) {
            if (node.value.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        Space[] newInstance = (Space[]) Array.newInstance(head.next.value.getClass(), size);
        for (int i = 0; i < size; i++) {
            newInstance[i] = get(i);
        }
        return newInstance;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public <T> T[] toArray(T[] a) {
        T[] newInstance = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        for (int i = 0; i < size; i++) {
            newInstance[i] = (T) get(i);
        }
        return newInstance;
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
    public boolean addAll(Collection<? extends Space> c) {
        for (Space object : c) {
            add(object);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean collectionsHasChanged = false;
        for (Object x : c) {
            int counter = size;
            for (int i = 0; i < counter; i++) {
                if (head.next.value.equals(x)) {
                    head.next = head.next.next;
                    size--;
                    collectionsHasChanged = true;
                } else {
                    head = head.next;
                }
            }
            head = head.next;
        }
        return collectionsHasChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean collectionHasChanged = false;
        Node node = head;
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
        head.value = null;
        head.next = head;
        size = 0;
    }
    @Override
    public List<Vehicle> getVehicles() {
        Vehicle[] vehicles = new Vehicle[size];
        Node node = head.next;
        for (int i = 0; i < size; i++, node = node.next) {
            vehicles[i] = node.value.getVehicle();
        }
        return Arrays.asList(vehicles);
    }

    @Override
    public void increaseArray() {

    }

    @Override
    public Space[] getSpaces() {
        Space[] spaces = new Space[size];
        Node node = head.next;
        for (int i = 0; i < size; i++, node = node.next) {
            spaces[i] = node.value;
        }
        return spaces;
    }

    public List<Space> getSpaces(VehicleTypes vehicleTypes) {
        if (vehicleTypes == null){
            throw new NullPointerException("Exception: vehicleTypes is null!");
        }
        Objects.requireNonNull(vehicleTypes);
        List<Space> spacesList = new ArrayList<>();
        Node node = head.next;
        for (int i = 0; i < size; i++, node = node.next) {
            if (node.value.getVehicle().getType().equals(vehicleTypes)) {
                spacesList.add(node.value);
            }
        }
        return spacesList;
    }


    public Deque<Space> getEmptySpaces() {
        Space[] spaces = new Space[size];
        Node node = head.next;
        int j = 0;
        for (int i = 0; i < size; i++, node = node.next) {
            if (node.value.isEmpty()){
                spaces[i] = node.value;
                j++;
            }
        }
        Space[] returnSpace = new Space[j];
        int k = 0;
        for (int i = 0; i < j; i++) {
            if (spaces[i]!=null){
                returnSpace[k] = spaces[i];
                k++;
            }
        }
        return new LinkedList<>(Arrays.asList(returnSpace));
    }

    @NotNull
    @Override
    public Iterator<Space> iterator() {
        return new SpaceIterator();
    }


    public class Node implements Cloneable{
        Node next;
        Node previous;
        Space value;
        Node(Space value){
            this.value = value;
        }
        Node(Space value, Node next, Node previous){
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("Rented spaces:\n");
        Space[] returnSpace = (Space[]) getSpaces();
        for (int i = 0; i < returnSpace.length; i++) {
            sb.append(returnSpace[i].toString()+"\n");
        }
        return sb.toString();
    }

    public int hashCode(){
        int hash = 53* getSpaces().length ^getSpaces().hashCode();
        return  hash;
    }

    public boolean equals(Object obj){
        if (obj == null){
            throw new NullPointerException("Exception: obj is null!");
        }
        RentedSpacesFloor otherRentedSpacesFloor = (RentedSpacesFloor) obj;
        return this.size == otherRentedSpacesFloor.size() && this.head == otherRentedSpacesFloor.head;
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
        Node node = head.next;
        for (int i = 0; i < size; i++, node = node.next) {
            if (node.value.equals(space)){
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
        Node node = head.next;
        for (int i = 0; i < size; i++, node = node.next) {
            if (node.value.equals(space)){
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
        Node node = head.next;
        int counter = 0;
        for (int i = 0; i < size; i++, node = node.next) {
            if (node.value.getPerson().equals(person)){
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

    private class SpaceIterator implements java.util.Iterator<Space>{
        Node node = head.next;

        @Override
        public boolean hasNext() {
            return node.next.value != null;
        }

        @Override
        public Space next() throws IndexOutOfBoundsException{
            if (hasNext()) {
                node = node.next;
                return node.value;

            }
            throw new NoSuchElementException();
        }
    }
}
