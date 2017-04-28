package model;


/**
 * Created by radu on 20.04.2017.
 */
public class CustomerEntity {

    private int id;
    private String name;
    private String address;

    public CustomerEntity(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public CustomerEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
