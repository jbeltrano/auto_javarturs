package Backend.Models;

public class OcasionalExtract {
    
    private String vehicleId;
    private int consecutive;
    private int contractId;

    public OcasionalExtract(String vehicleId, int consecutive, int contractId) {
        this.vehicleId = vehicleId;
        this.consecutive = consecutive;
        this.contractId = contractId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getConsecutive() {
        return consecutive;
    }

    public void setConsecutive(int consecutive) {
        this.consecutive = consecutive;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }
}
