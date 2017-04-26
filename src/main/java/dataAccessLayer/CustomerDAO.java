package dataAccessLayer;

import dataAccessLayerGenericUtils.GenericDAO;
import model.Customer;


/**
 * Created by radu on 20.04.2017.
 */
public class CustomerDAO extends GenericDAO<Customer> {

    public CustomerDAO(String connectionUrl) {
        super(connectionUrl);
    }
}
