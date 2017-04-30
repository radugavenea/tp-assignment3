package businessLayer;

import dataAccessLayer.ProductDAO;
import model.ProductEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by radu on 26.04.2017.
 */
public class WarehouseAdministration extends Observable {

    private ProductDAO productDAO;

    public WarehouseAdministration(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<ProductEntity> getAllProducts() throws SQLException {
        return productDAO.getAll();
    }

    public void addNewProduct(String name, int stock, float price) throws SQLException {
        ProductEntity product = new ProductEntity(name,stock,price);
        productDAO.addNew(product);
        setChanged();
        notifyObservers();
    }

    public void editProduct(int id, String name, int stock, float price) throws SQLException {
        ProductEntity product = new ProductEntity(id,name,stock,price);
        productDAO.update(product);
        setChanged();
        notifyObservers();
    }

    public void deleteProduct(int id) throws SQLException {
        productDAO.deleteById(id);
        setChanged();
        notifyObservers();
    }


    public List<String> getMappedProductFields(int id) throws SQLException {
        List<String> productFields = new ArrayList<>();
        ProductEntity productEntity = productDAO.getById(id);
        if(productEntity != null){
            productFields.add(Integer.toString(productEntity.getId()));
            productFields.add(productEntity.getName());
            productFields.add(Integer.toString(productEntity.getStock()));
            productFields.add(Float.toString(productEntity.getPrice()));
        }
        return productFields.isEmpty() ? null : productFields;
    }

}
