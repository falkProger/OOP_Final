import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class Vehicle implements Cloneable {
    private String registrationNumber;
    private String manufacturer;
    private String model;
    private VehicleTypes type;
    public static Vehicle NO_VEHICLE  = new Vehicle() ;

    public Vehicle(){
        registrationNumber = "";
        manufacturer = "";
        model = "";
        type = VehicleTypes.NONE;
    }

    public Vehicle(String registrationNumber, String manufacturer, String model, VehicleTypes type){
        if(registrationNumber == null || manufacturer == null || model == null || type== null){
            throw new NullPointerException("Exception: one of the arguments is null!");
        }
        if (!Pattern.matches("[ABEKMHOPCTYX]\\d\\d\\d[ABEKMHOPCTYX][ABEKMHOPCTYX]\\d{2,3}",registrationNumber)){
            throw new RegistrationNumberFormatException("Exception: wrong format registrationNumber!");
        }
        this.registrationNumber = registrationNumber;
        this.manufacturer = manufacturer;
        this.model=model;
        this.type = type;
    }

    public VehicleTypes getType(){
        return type;
    }
    public String getRegistrationNumber(){
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber){
        this.registrationNumber = registrationNumber;
    }

    public  String getManufacturer(){
        return manufacturer;
    }

    public void setManufacturer(String manufacturer){
        this.manufacturer = manufacturer;
    }

    public String getModel(){
        return model;
    }

    public void setModel(String model){
        this.model = model;
    }

    public String toString(){
        if (this.type==VehicleTypes.NONE){
            return String.format("%s",this.type.toString()) ;
        }
        else {
            return String.format("%s %s (%s) regNumber: %s",this.manufacturer,this.model,this.type,this.registrationNumber);
        }
    }

    public int hashCode(){
        int hash = this.manufacturer.hashCode()&this.model.hashCode()&this.type.hashCode()&this.registrationNumber.hashCode();
        return  hash;
    }

    public boolean equals(Object obj){
        Vehicle otherVehicle = (Vehicle) obj;
        return this.manufacturer==otherVehicle.manufacturer && this.model==otherVehicle.model && this.type==otherVehicle.type && this.registrationNumber == otherVehicle.registrationNumber;
    }

    @Override
    public Object clone()  {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
