package dataAccessLayer;

import dataAccessLayerGenericUtils.GenericDAO;
import model.OrderEntity;

/**
 * Created by radu on 21.04.2017.
 */
public class OrderDAO extends GenericDAO<OrderEntity> {
    public OrderDAO(String connectionUrl) {
        super(connectionUrl);
    }
}
