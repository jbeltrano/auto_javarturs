package Backend.Models;

public class OcasionalExtractConsecutive {
    
    private String VehicleId;
    private int consecutive;

    public OcasionalExtractConsecutive(String vehicleId, int consecutive) {
        VehicleId = vehicleId;
        this.consecutive = consecutive;
    }

    public String getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(String vehicleId) {
        VehicleId = vehicleId;
    }

    public int getConsecutive() {
        return consecutive;
    }

    public void setConsecutive(int consecutive) {
        this.consecutive = consecutive;
    }
}
