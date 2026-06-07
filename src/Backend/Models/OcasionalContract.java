package Backend.Models;

public class OcasionalContract {
    
    private int id;
    private String contractorId;
    private String inputDate;
    private String outputDate;
    private int originId;
    private int destinationId;
    private long contractValue;
    private int contractTypeId;

    public OcasionalContract(int id, String contractorId, String inputDate, String outputDate, int originId, int destinationId, long contractValue, int contractTypeId) {
        this.id = id;
        this.contractorId = contractorId;
        this.inputDate = inputDate;
        this.outputDate = outputDate;
        this.originId = originId;
        this.destinationId = destinationId;
        this.contractValue = contractValue;
        this.contractTypeId = contractTypeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContractorId() {
        return contractorId;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
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

    public long getContractValue() {
        return contractValue;
    }

    public void setContractValue(long contractValue) {
        this.contractValue = contractValue;
    }

    public int getContractTypeId() {
        return contractTypeId;
    }

    public void setContractTypeId(int contractTypeId) {
        this.contractTypeId = contractTypeId;
    }
}
