package controllers;

import connection.ConnectionUrl;
import dataAccessLayer.CustomerDAO;
import model.Customer;
import presentation.WarehouseView;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by radu on 26.04.2017.
 */
public class CustomerController {

    private WarehouseView view;

    public CustomerController(WarehouseView view) {
        this.view = view;

        doShitAndBeHappyTest();
    }

    private void doShitAndBeHappyTest(){

        CustomerDAO customerDAO = new CustomerDAO(ConnectionUrl.warehouseDbUrl);
        try {
            List<Customer> customers = customerDAO.getAll();
            view.updateCustomerTable(customers);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
