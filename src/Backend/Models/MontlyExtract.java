package backend.models;

public class MontlyExtract {
    
    private String vehicleId;
    private int consecutive;
    private int contractId;
    private String inputDate;
    private String outputDate;
    private int originId;
    private int destinationId;

    public MontlyExtract(String vehicleId, int consecutive, int contractId, String inputDate, String outputDate, int originId, int destinationId) {
        this.vehicleId = vehicleId;
        this.consecutive = consecutive;
        this.contractId = contractId;
        this.inputDate = inputDate;
        this.outputDate = outputDate;
        this.originId = originId;
        this.destinationId = destinationId;
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

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getOutputDate() {
        return outputDate;
    }

    public void setOutputDate(String outputDate) {
        this.outputDate = outputDate;
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }
}
