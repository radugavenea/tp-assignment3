import connection.ConnectionUrl;
import connection.DbSqlScript;
import dataAccessLayer.ProductDAO;
import model.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by radu on 21.04.2017.
 */
public class ProductCRUDTests {

    private ProductDAO productDAO;

    @Before
    public void init(){
        DbSqlScript.runTestDbSqlScript();
        productDAO = new ProductDAO(ConnectionUrl.testDbUrl);
    }

    @Test
    public void getAllProductsTest() throws SQLException {
        assert productDAO.getAll().size() == 7;
    }

    @Test
    public void getProductByIdTest() throws SQLException {
        assert productDAO.getById(1).getName().equals("Moka Pot");
        assert productDAO.getById(4).getStock() == 123;
        assert productDAO.getById(7).getPrice() == 132.5;
    }

    @Test
    public void insertProductTest() throws SQLException {
        int productsCount = productDAO.getAll().size();
        productDAO.addNew(new Product("nume produs",23, new Float(23.4)));
        assert productDAO.getAll().size() == productsCount + 1;
    }

    @Test
    public void updateProductTest() throws SQLException {
        assert productDAO.getById(1).getName().equals("Moka Pot");

        Product product = productDAO.getById(1);
        product.setName("Moka Pot 2");
        productDAO.update(product);

        assert productDAO.getById(1).getName().equals("Moka Pot 2");
    }

    @Test
    public void deleteProductTest() throws SQLException {
        int productsCount = productDAO.getAll().size();
        productDAO.deleteById(2);
        assert productDAO.getAll().size() == productsCount - 1;
    }


    @After
    public void tearDown(){

    }

}
