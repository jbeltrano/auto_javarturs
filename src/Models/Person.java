package Models;

public class Person {
    
    private String id;
    private int idType;
    private String name;
    private String phoneNumber;
    private int cityId;
    private String address;
    private String email;

    public Person(String id, int idType, String name, String phoneNumber, int cityId, String address, String email) {
        this.id = id;
        this.idType = idType;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.cityId = cityId;
        this.address = address;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public int getIdType() {
        return idType;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getCityId() {
        return cityId;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }
}
