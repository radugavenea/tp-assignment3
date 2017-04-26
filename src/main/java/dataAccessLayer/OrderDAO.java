package dataAccessLayer;

import dataAccessLayerGenericUtils.AbstractDAO;
import model.Order;

/**
 * Created by radu on 21.04.2017.
 */
public class OrderDAO extends AbstractDAO<Order> {

    public OrderDAO(String connectionUrl) {
        super(connectionUrl);
    }
}
