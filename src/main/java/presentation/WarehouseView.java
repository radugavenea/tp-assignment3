package presentation;

import model.CustomerEntity;
import model.ProductEntity;
import presentationGenericUtils.CustomerTableModel;
import presentationGenericUtils.GenericTableModel;
import presentationGenericUtils.WarehouseTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by radu on 25.04.2017.
 */
public class WarehouseView extends JFrame {

    private JFrame frame = new JFrame("Warehouse Application");


    private JTabbedPane customerTabbedPane = new JTabbedPane();

    private GenericTableModel customerTableModel = new CustomerTableModel();
    private JTable customerTable = new JTable();
    private JScrollPane customerScrollPane = new JScrollPane(customerTable);
    private JPanel customerPanel = new JPanel();
    private JSplitPane customerSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, customerScrollPane, customerPanel);


    private GenericTableModel warehouseTableModel = new WarehouseTableModel();
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



    public void updateCustomerTable(List<CustomerEntity> customerEntities){
        customerTableModel.setDataVector(customerEntities);
        customerTableModel.fireTableDataChanged();
    }

    public void updateProductTable(List<ProductEntity> productEntities){
        warehouseTableModel.setDataVector(productEntities);
        warehouseTableModel.fireTableDataChanged();
    }



    
    private void setUpCustomerPanel(){
        customerTable.setModel(customerTableModel);
        customerSplitPane.setDividerLocation(300);
    }

    private void setUpWarehousePanel(){
        warehouseTable.setModel(warehouseTableModel);
        warehouseSplitPane.setDividerLocation(300);
    }





}
