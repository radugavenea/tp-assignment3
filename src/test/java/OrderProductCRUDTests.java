import connection.ConnectionUrl;
import connection.DbSqlScript;
import dataAccessLayer.OrderProductDAO;
import model.OrderProductEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by radu on 30.04.2017.
 */
public class OrderProductCRUDTests {

    private OrderProductDAO orderProductDAO;

    @Before
    public void init(){
        DbSqlScript.runTestDbSqlScript();
        orderProductDAO = new OrderProductDAO(ConnectionUrl.testDbUrl);
    }

    @Test
    public void getAllOrderProductsTest() throws SQLException {
        assert orderProductDAO.getAll().size() == 2;
    }

    @Test
    public void getOrderProductByIdTest() throws SQLException {
        assert orderProductDAO.getById(1).getQuantity() == 1;
    }

    @Test
    public void insertOrderProductTest() throws SQLException {
        int orderProductCount = orderProductDAO.getAll().size();
        orderProductDAO.addNew(new OrderProductEntity(1,2,2));
        assert orderProductDAO.getAll().size() == orderProductCount + 1;
    }

    @Test
    public void updateOrderProductTest() throws SQLException {
        assert orderProductDAO.getById(1).getQuantity() == 1;

        OrderProductEntity orderProductEntity = orderProductDAO.getById(1);
        orderProductEntity.setQuantity(222);
        orderProductDAO.update(orderProductEntity);

        assert orderProductDAO.getById(1).getQuantity() != 1;
        assert orderProductDAO.getById(1).getQuantity() == 222;
    }

    @Test
    public void deleteOrderProductTest() throws SQLException {
        int orderProductCount = orderProductDAO.getAll().size();
        orderProductDAO.delete(orderProductDAO.getById(1));
        assert orderProductDAO.getAll().size() == orderProductCount - 1;
    }

    @After
    public void tearDown(){

    }

}
