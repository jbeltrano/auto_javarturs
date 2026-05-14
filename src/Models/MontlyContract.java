package Models;

public class MontlyContract {
    
    private int id;
    private String contractorId;

    public MontlyContract(int id, String contractorId) {
        this.id = id;
        this.contractorId = contractorId;
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
}
