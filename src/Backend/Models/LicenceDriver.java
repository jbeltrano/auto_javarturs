package backend.models;

public class LicenceDriver {
    
    private String driverId;
    private int categoryId;
    private String expiryDate;

    public LicenceDriver(String driverId, int categoryId, String expiryDate) {
        this.driverId = driverId;
        this.categoryId = categoryId;
        this.expiryDate = expiryDate;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getLicenceNumber() {
        return driverId;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.driverId = licenceNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
