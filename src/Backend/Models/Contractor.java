package Models;

public class Contractor {
    
    private String contractorId;
    private String responsibleId;

    public Contractor(String contractorId, String responsibleId) {
        this.contractorId = contractorId;
        this.responsibleId = responsibleId;
    }

    public String getContractorId() {
        return contractorId;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
    }

    public String getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(String responsibleId) {
        this.responsibleId = responsibleId;
    }
}