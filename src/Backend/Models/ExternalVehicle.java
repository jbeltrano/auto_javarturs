package Backend.Models;

public class ExternalVehicle {
    
    private String vehicleId;
    private String personID;

    public ExternalVehicle(String vehicleId, String personID) {
        this.vehicleId = vehicleId;
        this.personID = personID;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getpersonID() {
        return personID;
    }

    public void setpersonID(String personID) {
        this.personID = personID;
    }
}
