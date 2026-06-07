package backend.models;

public class VehicleDocument {
    
    private String vehicleId;
    private String intern;
    private String soatDate;
    private String rtmDate;
    private String rccDate;
    private String rceDate;
    private int top;
    private String topDate;

    public VehicleDocument(String vehicleId, String intern, String soatDate, String rtmDate, String rccDate, String rceDate, int top, String topDate) {
        this.vehicleId = vehicleId;
        this.intern = intern;
        this.soatDate = soatDate;
        this.rtmDate = rtmDate;
        this.rccDate = rccDate;
        this.rceDate = rceDate;
        this.top = top;
        this.topDate = topDate;
    }

    public VehicleDocument() {
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getIntern() {
        return intern;
    }

    public void setIntern(String intern) {
        this.intern = intern;
    }

    public String getSoatDate() {
        return soatDate;
    }

    public void setSoatDate(String soatDate) {
        this.soatDate = soatDate;
    }

    public String getRtmDate() {
        return rtmDate;
    }

    public void setRtmDate(String rtmDate) {
        this.rtmDate = rtmDate;
    }

    public String getRccDate() {
        return rccDate;
    }

    public void setRccDate(String rccDate) {
        this.rccDate = rccDate;
    }

    public String getRceDate() {
        return rceDate;
    }

    public void setRceDate(String rceDate) {
        this.rceDate = rceDate;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getTopDate() {
        return topDate;
    }

    public void setTopDate(String topDate) {
        this.topDate = topDate;
    }
}
