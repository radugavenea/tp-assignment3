package warehouse;

import connection.ConnectionUrl;
import dataAccessLayer.CustomerDAO;
import dataAccessLayer.ProductDAO;
import model.Customer;
import presentation.WarehouseView;

import java.sql.SQLException;

/**
 * Created by radu on 20.04.2017.
 */
public class Launcher {

//    private static AbstractDAO<Customer> customerDAO = new AbstractDAO<Customer>();       // asa nu merge
    private static CustomerDAO customerDAO = new CustomerDAO(ConnectionUrl.warehouseDbUrl);
    private static ProductDAO productDAO = new ProductDAO(ConnectionUrl.warehouseDbUrl);

//    static WarehouseView view = new WarehouseView();

    public static void main(String[] args) {

        new WarehouseView();


    }

}
