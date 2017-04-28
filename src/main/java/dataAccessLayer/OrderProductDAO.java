package dataAccessLayer;

import dataAccessLayerGenericUtils.GenericDAO;
import model.OrderProductEntity;

/**
 * Created by radu on 28.04.2017.
 */
public class OrderProductDAO extends GenericDAO<OrderProductEntity> {
    public OrderProductDAO(String connectionUrl) {
        super(connectionUrl);
    }
}
