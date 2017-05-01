package controllers;

import businessLayer.OrderProcessing;
import connection.ConnectionUrl;
import dataAccessLayer.OrderDAO;
import dataAccessLayer.OrderProductDAO;
import dataAccessLayer.ProductDAO;
import presentation.WarehouseView;
import warehouse.OrderSession;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by radu on 30.04.2017.
 */
public class OrderController implements Observer {

    private WarehouseView view;
    private OrderProcessing orderProcessing;

    public OrderController(WarehouseView view) {
        this.view = view;
        this.orderProcessing = new OrderProcessing(new OrderDAO(ConnectionUrl.warehouseDbUrl),
                new ProductDAO(ConnectionUrl.warehouseDbUrl),
                new OrderProductDAO(ConnectionUrl.warehouseDbUrl));

        orderProcessing.addObserver(this);

        view.addOrderSessionButtonListener(new OrderSessionButtonActionListener());
        view.addToBasketButtonListener(new AddToBasketButtonListener());
        view.addPlaceOrderButtonListener(new PlaceOrderButtonListener());
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ProductDAO){
            try {
                view.updateProductTable(orderProcessing.getAllProducts());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    class OrderSessionButtonActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(view.getSelectedCustomerId() != null){
                if(OrderSession.isActive == false){
                    try {
                        OrderSession.orderId = orderProcessing.addNewOrder(Integer.toString(++OrderSession.orderNumber),
                                Integer.parseInt(view.getSelectedCustomerId()));
                        OrderSession.customerId = Integer.parseInt(view.getSelectedCustomerId());
                        OrderSession.isActive = true;
                        view.showDialogOrderSessionStartedMessage(Integer.toString(OrderSession.orderNumber),Integer.toString(OrderSession.customerId));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                else {
                    view.showDialogOrderSessionIsActiveMessage();
                }
            }
            else {
                view.showDialogCustomerNotSelectedErrorMessage();
            }
        }
    }

    class AddToBasketButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(OrderSession.isActive){
                    orderProcessing.processProductOrder(OrderSession.orderId, Integer.parseInt(view.getSelectedProductId()),
                        Integer.parseInt(view.getProductQuantityInput()));

                    view.updateOrderProductTable(orderProcessing.getOrderProductsById(OrderSession.orderId));
                }
                else{
                    view.showDialogNoSessionErrorMessage();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }
    }

    class PlaceOrderButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "finalize":
                    if(OrderSession.isActive){
                        closeSession();
                    }
                    else {
                        view.showDialogNoSessionErrorMessage();
                    }
                    break;
                case "dismiss":
                    if(OrderSession.isActive){
                        closeSession();
                    }
                    else{
                        view.showDialogNoSessionErrorMessage();
                    }
                    break;
            }
        }
    }

    private void closeSession(){
        OrderSession.isActive = false;
        OrderSession.customerId = -1;
        OrderSession.orderId = -1;
        view.clearOrderProductTable();
        view.showFinalizedOrderMessage();
    }
}
