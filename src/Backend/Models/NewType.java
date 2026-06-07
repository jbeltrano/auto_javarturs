package Backend.Models;

public class NewType {
    
    private int id;
    private String name1;
    private String name2;

    public NewType(int id, String name1, String name2) {
        this.id = id;
        this.name1 = name1;
        this.name2 = name2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }
}
