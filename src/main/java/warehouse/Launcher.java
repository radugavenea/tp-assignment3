package warehouse;

import connection.ConnectionUrl;
import dataAccessLayer.OrderDAO;
import dataAccessLayer.OrderProductDAO;
import model.OrderEntity;
import model.OrderProductEntity;

import java.sql.SQLException;
import java.util.Comparator;

/**
 * Created by radu on 20.04.2017.
 */
public class Launcher {

//    private static GenericDAO<CustomerEntity> customerDAO = new GenericDAO<CustomerEntity>();       // asa nu merge
//    private static CustomerDAO customerDAO = new CustomerDAO(ConnectionUrl.warehouseDbUrl);
//    private static ProductDAO productDAO = new ProductDAO(ConnectionUrl.warehouseDbUrl);


    public static void main(String[] args) {

        OrderProductDAO orderProductDAO = new OrderProductDAO(ConnectionUrl.warehouseDbUrl);
        OrderDAO orderDAO = new OrderDAO(ConnectionUrl.warehouseDbUrl);
        try {
            orderDAO.addNew(new OrderEntity("vrvrver",1));
            orderProductDAO.addNew(new OrderProductEntity(1,1,1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        WarehouseView view = new WarehouseView();

//        new CustomerController(view);
//        new WarehouseController(view);


    }

}
