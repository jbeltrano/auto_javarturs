package Models;

public class Vehicle {
    
    private String id;
    private int vehicleClass;
    private int model;
    private String brand;
    private String line;
    private int capacity;
    private int vehicleServiceId;
    private String bodyworkType;
    private String engineNumber;
    private String chassisNumber;
    private String ownerId;
    private boolean isInPark;

    public Vehicle(String id, int vehicleClass, int model, String brand, String line, int capacity, int vehicleServiceId, String bodyworkType, String engineNumber, String chassisNumber, String ownerId) {
        this.id = id;
        this.vehicleClass = vehicleClass;
        this.model = model;
        this.brand = brand;
        this.line = line;
        this.capacity = capacity;
        this.vehicleServiceId = vehicleServiceId;
        this.bodyworkType = bodyworkType;
        this.engineNumber = engineNumber;
        this.chassisNumber = chassisNumber;
        this.ownerId = ownerId;
        this.isInPark = false; // Default value
    }

    public Vehicle(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(int vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getVehicleServiceId() {
        return vehicleServiceId;
    }

    public void setVehicleServiceId(int vehicleServiceId) {
        this.vehicleServiceId = vehicleServiceId;
    }

    public String getBodyworkType() {
        return bodyworkType;
    }

    public void setBodyworkType(String bodyworkType) {
        this.bodyworkType = bodyworkType;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isInPark() {
        return isInPark;
    }

    public void setInPark(boolean inPark) {
        isInPark = inPark;
    }
}
