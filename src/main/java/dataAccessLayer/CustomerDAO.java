package dataAccessLayer;

import dataAccessLayerGenericUtils.GenericDAO;
import model.CustomerEntity;


/**
 * Created by radu on 20.04.2017.
 */
public class CustomerDAO extends GenericDAO<CustomerEntity> {

    public CustomerDAO(String connectionUrl) {
        super(connectionUrl);
    }
}
