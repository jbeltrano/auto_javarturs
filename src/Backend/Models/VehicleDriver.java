package backend.models;

public class VehicleDriver {
 
    private String vehicleId;
    private String driverId;

    public VehicleDriver(String vehicleId, String driverId) {
        this.vehicleId = vehicleId;
        this.driverId = driverId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
