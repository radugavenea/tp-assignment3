package presentation;

import controllers.OrderController;
import model.CustomerEntity;
import model.OrderProductEntity;
import model.ProductEntity;
import presentationTableUtils.*;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private JPanel customerPanel = makeTextPanel();
    private JPanel customerButtonPanel = new JPanel();
    private JPanel customerOrderPanel = new JPanel();
    private JSplitPane customerInsideInsideSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, customerButtonPanel, customerOrderPanel);
    private JSplitPane customerInsideSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, customerPanel, customerInsideInsideSplitPane);
    private JSplitPane customerSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, customerScrollPane, customerInsideSplitPane);

    private JLabel customerIdLabel = new JLabel("Customer id: ");
    private JLabel customerNameLabel = new JLabel("Customer name: ");
    private JLabel customerAddressLabel = new JLabel("Customer address: ");
    private JTextField customerNameInput = new JTextField(30);
    private JTextField customerAddressInput = new JTextField(30);
    private JTextField customerIdInput = new JTextField(30);
    private JButton readCustomerButton = new JButton("Read");
    private JButton addCustomerButton = new JButton("Add");
    private JButton editCustomerButton = new JButton("Edit");
    private JButton deleteCustomerButton = new JButton("Delete");
    private JButton startOrderSessionButton = new JButton("Start order session for client");

    private GenericTableModel warehouseTableModel = new WarehouseTableModel();
    private JTable warehouseTable = new JTable();
    private JScrollPane warehouseScrollPane = new JScrollPane(warehouseTable);
    private JPanel warehousePanel = makeTextPanel();
    private JPanel warehouseButtonPanel = new JPanel();
    private JPanel warehouseOrderPanel = new JPanel();
    private JSplitPane warehouseInsideInsideSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,warehouseButtonPanel,warehouseOrderPanel);
    private JSplitPane warehouseInsideSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,warehousePanel,warehouseInsideInsideSplitPane);
    private JSplitPane warehouseSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,warehouseScrollPane,warehouseInsideSplitPane);

    private JLabel productIdLabel = new JLabel("Product id: ");
    private JLabel productNameLabel = new JLabel("Product name: ");
    private JLabel productStockLabel = new JLabel("Product stock: ");
    private JLabel productPriceLabel = new JLabel("Product price: ");
    private JTextField productIdInput = new JTextField(30);
    private JTextField productNameInput = new JTextField(30);
    private JTextField productStockInput = new JTextField(30);
    private JTextField productPriceInput = new JTextField(30);
    private JButton readProductButton = new JButton("Read");
    private JButton addProductButton = new JButton("Add");
    private JButton editProductButton = new JButton("Edit");
    private JButton deleteProductButton = new JButton("Delete");

    private JLabel productQuantityLabel = new JLabel("Quantity: ");
    private JTextField productQuantityInput = new JTextField(10);
    private JButton addProductToBasketButton = new JButton("Add to basket");

    private GenericTableModel orderProductTableModel = new OrderProductTableModel();
    private JTable orderProductTable = new JTable();
    private JScrollPane orderScrollPane = new JScrollPane(orderProductTable);
    private JPanel orderPanel = new JPanel();
    private JSplitPane orderSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,orderScrollPane,orderPanel);

    private JButton finalizeOrderButton = new JButton("Place order");
    private JButton dismissOrderButton = new JButton("Dismiss order");

    public WarehouseView() throws HeadlessException {
        initializeWarehouseView();
    }


    private void initializeWarehouseView(){

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((dim.width-frame.getWidth())/2, (dim.height-frame.getHeight())/2);
        frame.setResizable(false);

        setUpCustomerTab();
        setUpWarehouseTab();
        setUpOrderPanelTab();

        customerTabbedPane.add("Customers", customerSplitPane);
        customerTabbedPane.add("Warehouse", warehouseSplitPane);
        customerTabbedPane.add("Basket", orderSplitPane);

        frame.add(customerTabbedPane);
        frame.setVisible(true);
    }

    /**
     * Listeners
     * @param listener
     */

    public void addCustomerButtonsListener(ActionListener listener){
        xButtonListener(listener,readCustomerButton,addCustomerButton,editCustomerButton,deleteCustomerButton);
    }
    public void addProductButtonsListener(ActionListener listener){
        xButtonListener(listener,readProductButton,addProductButton,editProductButton,deleteProductButton);
    }
    public void addCustomerTableListener(ListSelectionListener listSelectionListener){
        customerTable.getSelectionModel().addListSelectionListener(listSelectionListener);
    }
    public void addProductTableListener(ListSelectionListener listSelectionListener){
        warehouseTable.getSelectionModel().addListSelectionListener(listSelectionListener);
    }
    public void addOrderSessionButtonListener(ActionListener listener){
        startOrderSessionButton.addActionListener(listener);
    }
    public void addToBasketButtonListener(ActionListener listener){
        addProductToBasketButton.addActionListener(listener);
    }
    public void addPlaceOrderButtonListener(ActionListener listener) {
        finalizeOrderButton.addActionListener(listener);
        finalizeOrderButton.setActionCommand("finalize");
        dismissOrderButton.addActionListener(listener);
        dismissOrderButton.setActionCommand("dismiss");
    }

    /**
     * Gets customer id from the selected row in the table
     * @return id or null if no selection is made
     */
    public String getSelectedCustomerId(){
        int row = customerTable.getSelectedRow();
        return row > -1 ? customerTable.getValueAt(row,0).toString() : null;
    }

    /**
     * Gets product id from the selected row in the warehouse table
     * @return id or null if no selection is made
     */
    public String getSelectedProductId(){
        int row = warehouseTable.getSelectedRow();
        return row > -1 ? warehouseTable.getValueAt(row,0).toString() : null;
    }

    public void updateCustomerTable(List<CustomerEntity> customerEntities){
        customerTableModel.setDataVector(customerEntities);
        customerTableModel.fireTableDataChanged();
    }

    public void updateProductTable(List<ProductEntity> productEntities){
        warehouseTableModel.setDataVector(productEntities);
        warehouseTableModel.fireTableDataChanged();
    }

    public void updateOrderProductTable(List<OrderProductEntity> orderProductEntities) {
        orderProductTableModel.setDataVector(orderProductEntities);
        orderProductTableModel.fireTableDataChanged();
    }

    public void clearOrderProductTable() {
        orderProductTableModel.setDataVector(new ArrayList<OrderProductEntity>());
        orderProductTableModel.fireTableDataChanged();
    }

    public void updateCustomerFields(List<String> customerFields) {
        customerIdInput.setText(customerFields.get(0));
        customerNameInput.setText(customerFields.get(1));
        customerAddressInput.setText(customerFields.get(2));
    }

    public void updateProductFields(List<String> productFields){
        productIdInput.setText(productFields.get(0));
        productNameInput.setText(productFields.get(1));
        productStockInput.setText(productFields.get(2));
        productPriceInput.setText(productFields.get(3));
    }

    /**
     * Message dialogs methods
     */
    public void showDialogNoSessionErrorMessage() {
        JOptionPane.showMessageDialog(frame, "A customer must be selected and order session must be started in Customer Tab!");
    }
    public void showDialogOrderSessionStartedMessage(String orderNumber, String customerId) {
        JOptionPane.showMessageDialog(frame, "Order session with number " + orderNumber + " for client " + customerId + " started!");
    }

    public void showDialogCustomerNotSelectedErrorMessage() {
        JOptionPane.showMessageDialog(frame, "A customer must be selected!");
    }

    public void showDialogOrderSessionIsActiveMessage() {
        JOptionPane.showMessageDialog(frame, "A order session is already started! Please finalize the current order.");
    }

    public void showFinalizedOrderMessage() {
        JOptionPane.showMessageDialog(frame, "Your order has been placed. Bye bye!");
    }


    /**
     * Getters and setters
     * @return
     */
    public String getCustomerIdInput(){
        return customerIdInput.getText();
    }
    public void setCustomerIdInput(String customerId){
        customerIdInput.setText(customerId);
    }
    public String getCustomerNameInput() {
        return customerNameInput.getText();
    }
    public void setCustomerNameInput(String customerName){
        customerNameInput.setText(customerName);
    }
    public String getCustomerAddressInput(){
        return customerAddressInput.getText();
    }
    public void setCustomerAddressInput(String customerAddress){
        customerAddressInput.setText(customerAddress);
    }
    public String getProductIdInput(){
        return productIdInput.getText();
    }
    public void setProductIdInput(String productId){
        productIdInput.setText(productId);
    }
    public String getProductNameInput(){
        return productNameInput.getText();
    }
    public void setProductNameInput(String productName){
        productNameInput.setText(productName);
    }
    public String getProductStockInput(){
        return productStockInput.getText();
    }
    public void setProductStockInput(String productStock){
        productStockInput.setText(productStock);
    }
    public String getProductPriceInput(){
        return productPriceInput.getText();
    }
    public void setProductPriceInput(String productPrice){
        productPriceInput.setText(productPrice);
    }
    public String getProductQuantityInput(){
        return productQuantityInput.getText();
    }
    public void setProductQuantityInput(String productQuantity){
        productQuantityInput.setText(productQuantity);
    }


    private void setUpCustomerTab(){
        customerTable.setModel(customerTableModel);
        customerSplitPane.setDividerLocation(300);
        customerIdInput.setEditable(false);
        customerPanel.add(customerIdLabel);
        customerPanel.add(customerIdInput);
        customerPanel.add(customerNameLabel);
        customerPanel.add(customerNameInput);
        customerPanel.add(customerAddressLabel);
        customerPanel.add(customerAddressInput);
        customerButtonPanel.add(readCustomerButton);
        customerButtonPanel.add(addCustomerButton);
        customerButtonPanel.add(editCustomerButton);
        customerButtonPanel.add(deleteCustomerButton);
        customerOrderPanel.add(startOrderSessionButton);
    }

    private void setUpWarehouseTab(){
        warehouseTable.setModel(warehouseTableModel);
        warehouseSplitPane.setDividerLocation(300);
        productIdInput.setEditable(false);
        warehousePanel.add(productIdLabel);
        warehousePanel.add(productIdInput);
        warehousePanel.add(productNameLabel);
        warehousePanel.add(productNameInput);
        warehousePanel.add(productStockLabel);
        warehousePanel.add(productStockInput);
        warehousePanel.add(productPriceLabel);
        warehousePanel.add(productPriceInput);
        warehouseButtonPanel.add(readProductButton);
        warehouseButtonPanel.add(addProductButton);
        warehouseButtonPanel.add(editProductButton);
        warehouseButtonPanel.add(deleteProductButton);
        warehouseOrderPanel.add(productQuantityLabel);
        warehouseOrderPanel.add(productQuantityInput);
        warehouseOrderPanel.add(addProductToBasketButton);
    }

    private void setUpOrderPanelTab(){
        orderProductTable.setModel(orderProductTableModel);
        orderSplitPane.setDividerLocation(400);
        orderPanel.add(finalizeOrderButton);
        orderPanel.add(dismissOrderButton);
    }


    private void xButtonListener(ActionListener listener, JButton readButton, JButton addButton, JButton editButton, JButton deleteButton){
        readButton.addActionListener(listener);
        readButton.setActionCommand("read");
        addButton.addActionListener(listener);
        addButton.setActionCommand("add");
        editButton.addActionListener(listener);
        editButton.setActionCommand("edit");
        deleteButton.addActionListener(listener);
        deleteButton.setActionCommand("delete");
    }

    protected JPanel makeTextPanel() {
        JPanel panel = new JPanel(false);
        panel.setLayout(new GridLayout(0, 2));
        return panel;
    }

}
