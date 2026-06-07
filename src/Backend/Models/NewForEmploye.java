package backend.models;

public class NewForEmploye {
    
    private String EmployeId;
    private int type;
    private int days;
    private boolean state;
    private String inputDate;
    private String outputDate;

    public NewForEmploye(String EmployeId, int type, boolean state, String inputDate, String outputDate) {
        this.EmployeId = EmployeId;
        this.type = type;
        this.state = state;
        setInputDate(inputDate);
        setOutputDate(outputDate);
    }

    // Getters and setters for each field
    public String getEmployeId() {
        return EmployeId;
    }

    public void setEmployeId(String EmployeId) {
        this.EmployeId = EmployeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        // Hacer una funcion para calcular los dias
        this.inputDate = inputDate;
    }

    public String getOutputDate() {
        return outputDate;
    }

    public void setOutputDate(String outputDate) {
        this.outputDate = outputDate;
    }
}
