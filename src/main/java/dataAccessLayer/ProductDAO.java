package dataAccessLayer;

import dataAccessLayerGenericUtils.GenericDAO;
import model.ProductEntity;

/**
 * Created by radu on 20.04.2017.
 */
public class ProductDAO extends GenericDAO<ProductEntity> {

    public ProductDAO(String connectionUrl) {
        super(connectionUrl);
    }
}
