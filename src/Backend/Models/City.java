package Models;

public class City {
    
    private int id;
    private String name;
    private int departament;

    public City(int id, String name, int departament) {
        this.id = id;
        this.name = name;
        this.departament = departament;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDepartament() {
        return departament;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartament(int departament) {
        this.departament = departament;
    }
}
