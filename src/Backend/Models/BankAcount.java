package Backend.Models;

public class BankAcount {
    
    private String employeId;
    private int bankId;
    private String accountNumber;

    public BankAcount(String employeId, int bankId, String accountNumber) {
        this.employeId = employeId;
        this.bankId = bankId;
        this.accountNumber = accountNumber;
    }

    public String getEmployeId() {
        return employeId;
    }

    public void setEmployeId(String employeId) {
        this.employeId = employeId;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
