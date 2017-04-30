package controllers;

import businessLayer.WarehouseAdministration;
import connection.ConnectionUrl;
import dataAccessLayer.ProductDAO;
import presentation.WarehouseView;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by radu on 26.04.2017.
 */
public class WarehouseController implements Observer {

    private WarehouseView view;
    private WarehouseAdministration warehouseAdministration;

    public WarehouseController(WarehouseView view) {
        this.view = view;
        this.warehouseAdministration = new WarehouseAdministration(new ProductDAO(ConnectionUrl.warehouseDbUrl));

        warehouseAdministration.addObserver(this);

        view.addProductButtonsListener(new ProductActionListener());
        view.addProductTableListener(new ProductListSelectionListener());
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            view.updateProductTable(warehouseAdministration.getAllProducts());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    class ProductActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "read":
                    try {
                        view.updateProductTable(warehouseAdministration.getAllProducts());
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "add":
                    try {
                        warehouseAdministration.addNewProduct(view.getProductNameInput(), Integer.parseInt(view.getProductStockInput()),
                                Float.parseFloat(view.getProductPriceInput()));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "edit":
                    try {
                        warehouseAdministration.editProduct(Integer.parseInt(view.getProductIdInput()),
                                view.getProductNameInput(), Integer.parseInt(view.getProductStockInput()),
                                Float.parseFloat(view.getProductPriceInput()));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "delete":
                    try {
                        warehouseAdministration.deleteProduct(Integer.parseInt(view.getProductIdInput()));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    break;
            }
        }
    }

    class ProductListSelectionListener implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {
            try {
                if(view.getSelectedProductId() != null) {
                    view.updateProductFields(warehouseAdministration.getMappedProductFields(Integer.parseInt(view.getSelectedProductId())));
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}

