package dataAccessLayer;

import dataAccessLayerGenericUtils.GenericDAO;
import model.Product;

/**
 * Created by radu on 20.04.2017.
 */
public class ProductDAO extends GenericDAO<Product> {

    public ProductDAO(String connectionUrl) {
        super(connectionUrl);
    }
}
