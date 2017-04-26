package presentation;

import model.Customer;
import model.Product;
import presentationGenericUtils.CustomerTableModel;
import presentationGenericUtils.GenericTableModel;
import presentationGenericUtils.WarehouseTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by radu on 25.04.2017.
 */
public class WarehouseView extends JFrame {

    private List<Customer> customers = new ArrayList<Customer>();
    private List<Product> products = new ArrayList<Product>();

    private JFrame frame = new JFrame("Warehouse Application");


    private JTabbedPane customerTabbedPane = new JTabbedPane();

    private GenericTableModel customerTableModel = new CustomerTableModel(customers);
    private JTable customerTable = new JTable();
    private JScrollPane customerScrollPane = new JScrollPane(customerTable);
    private JPanel customerPanel = new JPanel();
    private JSplitPane customerSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, customerScrollPane, customerPanel);


    private GenericTableModel warehouseTableModel = new WarehouseTableModel(products);
    private JTable warehouseTable = new JTable();
    private JScrollPane warehouseScrollPane = new JScrollPane(warehouseTable);
    private JPanel warehousePanel = new JPanel();
    private JSplitPane warehouseSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,warehouseScrollPane,warehousePanel);


    public WarehouseView() throws HeadlessException {
        initializeWarehouseView();
    }

    private void initializeWarehouseView(){

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((dim.width-frame.getWidth())/2, (dim.height-frame.getHeight())/2);
        frame.setResizable(false);

        setUpCustomerPanel();
        setUpWarehousePanel();

        customerTabbedPane.add("Customers", customerSplitPane);
        customerTabbedPane.add("Warehouse", warehouseSplitPane);

        frame.add(customerTabbedPane);
        frame.setVisible(true);
    }

    private void setUpCustomerPanel(){
        customerTable.setModel(customerTableModel);
        customerSplitPane.setDividerLocation(300);
    }

    private void setUpWarehousePanel(){
        warehouseTable.setModel(warehouseTableModel);
        warehouseSplitPane.setDividerLocation(300);
    }



    public void updateCustomers(List<Customer> customers){
        this.customers = customers;
        customerTableModel.fireTableDataChanged();
    }

    public void updateProducts(List<Product> products){
        this.products = products;
        customerTableModel.fireTableDataChanged();
    }

}
