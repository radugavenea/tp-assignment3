package model;

/**
 * Created by radu on 20.04.2017.
 */
public class OrderEntity {

    private int id;
    private String order_number;
    private int customer_id;

    public OrderEntity(String order_number, int customer_id) {
        this.order_number = order_number;
        this.customer_id = customer_id;
    }

    public OrderEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
}
