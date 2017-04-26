package controllers;

import connection.ConnectionUrl;
import dataAccessLayer.ProductDAO;
import presentation.WarehouseView;

import java.sql.SQLException;

/**
 * Created by radu on 26.04.2017.
 */
public class WarehouseController {

    private WarehouseView view;

    public WarehouseController(WarehouseView view) {
        this.view = view;

        updateTest();
    }


    private void updateTest(){
        ProductDAO productDAO = new ProductDAO(ConnectionUrl.warehouseDbUrl);
        try {
            view.updateProductTable(productDAO.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

