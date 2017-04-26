package warehouse;

import connection.ConnectionUrl;
import controllers.CustomerController;
import controllers.WarehouseController;
import dataAccessLayer.CustomerDAO;
import dataAccessLayer.ProductDAO;
import model.Customer;
import presentation.WarehouseView;

import java.sql.SQLException;

/**
 * Created by radu on 20.04.2017.
 */
public class Launcher {

//    private static GenericDAO<Customer> customerDAO = new GenericDAO<Customer>();       // asa nu merge
    private static CustomerDAO customerDAO = new CustomerDAO(ConnectionUrl.warehouseDbUrl);
    private static ProductDAO productDAO = new ProductDAO(ConnectionUrl.warehouseDbUrl);

//    static WarehouseView view = new WarehouseView();

    public static void main(String[] args) {

        WarehouseView view = new WarehouseView();

//        new CustomerController(view);
        new WarehouseController(view);


    }

}
