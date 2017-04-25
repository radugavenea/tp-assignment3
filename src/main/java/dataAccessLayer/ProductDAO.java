package dataAccessLayer;

import model.Product;

/**
 * Created by radu on 20.04.2017.
 */
public class ProductDAO extends AbstractDAO<Product> {

    public ProductDAO(String connectionUrl) {
        super(connectionUrl);
    }
}
