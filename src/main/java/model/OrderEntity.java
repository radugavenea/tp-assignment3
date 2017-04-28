package model;

/**
 * Created by radu on 20.04.2017.
 */
public class OrderEntity {

    private int id;
    private String orderNumber;
    private int customerId;

    public OrderEntity(String orderNumber, int customerId) {
        this.orderNumber = orderNumber;
        this.customerId = customerId;
    }

    public OrderEntity(){}

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
