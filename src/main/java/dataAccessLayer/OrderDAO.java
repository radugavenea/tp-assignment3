package dataAccessLayer;

import dataAccessLayerGenericUtils.GenericDAO;
import model.Order;

/**
 * Created by radu on 21.04.2017.
 */
public class OrderDAO extends GenericDAO<Order> {

    public OrderDAO(String connectionUrl) {
        super(connectionUrl);
    }
}
