package warehouse;

import connection.ConnectionUrl;
import dataAccessLayer.CustomerDAO;
import dataAccessLayer.ProductDAO;
import model.Customer;

import java.sql.SQLException;

/**
 * Created by radu on 20.04.2017.
 */
public class Launcher {

//    private static AbstractDAO<Customer> customerDAO = new AbstractDAO<Customer>();       // asa nu merge
    private static CustomerDAO customerDAO = new CustomerDAO(ConnectionUrl.warehouseDbUrl);
    private static ProductDAO productDAO = new ProductDAO(ConnectionUrl.warehouseDbUrl);

    public static void main(String[] args) {


        try {
//            System.out.println(customerDAO.getById(0).getName());
//            System.out.println(productDAO.getById(0).getName());

            System.out.println("\n");

//            System.out.println(customerDAO.getAll().get(0).getAddress());
//            System.out.println(customerDAO.getAll().get(1).getAddress());
//            System.out.println(productDAO.getAll());


            System.out.println("\n");

            System.out.println(customerDAO.addNew(new Customer("cici","undeva in lume")));
//            System.out.println(customerDAO.deleteById(2));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
