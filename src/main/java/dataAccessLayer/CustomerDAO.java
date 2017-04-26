package dataAccessLayer;

import dataAccessLayerGenericUtils.AbstractDAO;
import model.Customer;


/**
 * Created by radu on 20.04.2017.
 */
public class CustomerDAO extends AbstractDAO<Customer> {

    public CustomerDAO(String connectionUrl) {
        super(connectionUrl);
    }
}
