import connection.ConnectionUrl;
import connection.DbSqlScript;
import dataAccessLayer.CustomerDAO;
import model.CustomerEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by radu on 26.04.2017.
 */
public class CustomerCRUDTests {

    private CustomerDAO customerDAO;

    @Before
    public void init(){
        DbSqlScript.runTestDbSqlScript();
        customerDAO = new CustomerDAO(ConnectionUrl.testDbUrl);
    }

    @Test
    public void getAllCustomersTest() throws SQLException {
        assert customerDAO.getAll().size() == 6;
    }

    @Test
    public void getCustomerByIdTest() throws SQLException {
        assert customerDAO.getById(1).getName().equals("Mirel Zeama");
        assert customerDAO.getById(4).getAddress().equals("la ea acasa");
    }

    @Test
    public void insertCustomerTest() throws SQLException {
        int customerCount = customerDAO.getAll().size();
        customerDAO.addNew(new CustomerEntity("Petunia Mironica","in coltul strazi 35"));
        assert customerDAO.getAll().size() == customerCount + 1;
    }

    @Test
    public void updateCustomerTest() throws SQLException {
        assert customerDAO.getById(1).getName().equals("Mirel Zeama");

        CustomerEntity customerEntity = customerDAO.getById(1);
        customerEntity.setName("Gica");
        customerDAO.update(customerEntity);

        assert !customerDAO.getById(1).getName().equals("Mirel Zeama");
        assert customerDAO.getById(1).getName().equals("Gica");
    }

    @Test
    public void deleteCustomerTest() throws SQLException {
        int customerCount = customerDAO.getAll().size();
        customerDAO.deleteById(6);
        assert customerDAO.getAll().size() == customerCount - 1;
    }


    @After
    public void tearDown(){

    }

}
