package controllers;

import businessLayer.CustomerAdministration;
import connection.ConnectionUrl;
import dataAccessLayer.CustomerDAO;
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
public class CustomerController implements Observer {

    private WarehouseView view;
    private CustomerAdministration customerAdministration;

    public CustomerController(WarehouseView view) {
        this.view = view;
        this.customerAdministration = new CustomerAdministration(new CustomerDAO(ConnectionUrl.warehouseDbUrl));

        updateCustomerTable();

        customerAdministration.addObserver(this);

        view.addCustomerButtonsListener(new CustomerActionListener());
        view.addCustomerTableListener(new CustomerListSelectionListener());
    }

    @Override
    public void update(Observable o, Object arg) {
        updateCustomerTable();
    }


    private void updateCustomerTable(){
        try {
            view.updateCustomerTable(customerAdministration.getAllCustomers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    class CustomerActionListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "read":
                    try {
                        view.updateCustomerTable(customerAdministration.getAllCustomers());
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "add":
                    try {
                        customerAdministration.addNewCustomer(view.getCustomerNameInput(),view.getCustomerAddressInput());
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "edit":
                    try {
                        customerAdministration.editCustomer(Integer.parseInt(view.getCustomerIdInput()),view.getCustomerNameInput(),view.getCustomerAddressInput());
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "delete":
                    try {
                        customerAdministration.deleteCustomerById(Integer.parseInt(view.getCustomerIdInput()));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    break;
            }
        }
    }

    class CustomerListSelectionListener implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {
            try {
                if(view.getSelectedCustomerId() != null){
                    view.updateCustomerFields(customerAdministration.getMappedCustomerFields(Integer.parseInt(view.getSelectedCustomerId())));
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

}
