import connection.ConnectionUrl;
import connection.DbSqlScript;
import dataAccessLayer.OrderDAO;
import model.OrderEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by radu on 27.04.2017.
 */
public class OrderCRUDTests {

    private OrderDAO orderDAO;

    @Before
    public void init(){
        DbSqlScript.runTestDbSqlScript();
        orderDAO = new OrderDAO(ConnectionUrl.testDbUrl);
    }

    @Test
    public void getAllOrdersTest() throws SQLException {
        assert orderDAO.getAll().size() == 3;
    }

    @Test
    public void getOrderByIdTest() throws SQLException {
        assert orderDAO.getById(1).getOrderNumber().equals("756756");
        assert orderDAO.getById(3).getCustomerId() == 5;
    }

    @Test
    public void insertOrderTest() throws SQLException {
        int orderCount = orderDAO.getAll().size();
        orderDAO.addNew(new OrderEntity("43534",2));
        assert orderDAO.getAll().size() == orderCount + 1;
    }

    @Test
    public void updateOrderTest() throws SQLException {
        assert orderDAO.getById(1).getOrderNumber().equals("756756");

        OrderEntity orderEntity = orderDAO.getById(1);
        orderEntity.setOrderNumber("222");
        orderDAO.update(orderEntity);

        assert !orderDAO.getById(1).getOrderNumber().equals("756756");
        assert orderDAO.getById(1).getOrderNumber().equals("222");
    }

    @Test
    public void deleteOrderTest() throws SQLException {
        int orderCount = orderDAO.getAll().size();
        orderDAO.deleteById(3);
        assert orderDAO.getAll().size() == orderCount - 1;
    }

    @After
    public void tearDown(){

    }

}
