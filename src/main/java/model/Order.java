package model;

/**
 * Created by radu on 20.04.2017.
 */
public class Order {

    private int id;
    private String orderNumber;
    private int customerId;

    public Order(int id, String orderNumber, int quantity, int customerId) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerId = customerId;
    }

    public Order(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
