package businessLayer;

import dataAccessLayer.CustomerDAO;
import model.CustomerEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by radu on 26.04.2017.
 */
public class CustomerAdministration extends Observable {

    private CustomerDAO customerDAO;

    public CustomerAdministration(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<CustomerEntity> getAllCustomers() throws SQLException {
        return customerDAO.getAll();
    }

    public void addNewCustomer(String name, String address) throws SQLException {
        customerDAO.addNew(new CustomerEntity(name, address));
        setChanged();
        notifyObservers();
    }

    public void editCustomer(int id, String name, String address) throws SQLException {
        CustomerEntity customer = new CustomerEntity(id,name,address);
        customerDAO.update(customer);
        setChanged();
        notifyObservers();
    }

    public void deleteCustomerById(int id) throws SQLException {
        customerDAO.deleteById(id);
        setChanged();
        notifyObservers();
    }


    public List<String> getMappedCustomerFields(int id) throws SQLException {
        List<String> customerFields = new ArrayList<>();
        CustomerEntity customer = customerDAO.getById(id);
        if(customer != null){
            customerFields.add(Integer.toString(customer.getId()));
            customerFields.add(customer.getName());
            customerFields.add(customer.getAddress());
        }
        return customerFields.isEmpty() ? null : customerFields;
    }

}
