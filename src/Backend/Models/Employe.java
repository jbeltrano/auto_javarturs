package Backend.Models;

public class Employe {
    
    private String id;
    private int employeTypeId;
    private int epsId; 
    private int afpId;
    private int cesantiasId;
    private String inputDate;
    private String outputDate;
    private long salary;
    private boolean active;

    public Employe(String id, int employeTypeId, int epsId, int afpId, int cesantiasId, String inputDate, String outputDate, long salary, boolean active) {
        this.id = id;
        this.employeTypeId = employeTypeId;
        this.epsId = epsId;
        this.afpId = afpId;
        this.cesantiasId = cesantiasId;
        this.inputDate = inputDate;
        this.outputDate = outputDate;
        this.salary = salary;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEmployeTypeId() {
        return employeTypeId;
    }

    public void setEmployeTypeId(int employeTypeId) {
        this.employeTypeId = employeTypeId;
    }

    public int getEpsId() {
        return epsId;
    }

    public void setEpsId(int epsId) {
        this.epsId = epsId;
    }

    public int getAfpId() {
        return afpId;
    }

    public void setAfpId(int afpId) {
        this.afpId = afpId;
    }

    public int getCesantiasId() {
        return cesantiasId;
    }

    public void setCesantiasId(int cesantiasId) {
        this.cesantiasId = cesantiasId;
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

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
