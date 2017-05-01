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
            try {
                OrderSession.orderId = orderProcessing.addNewOrder(Integer.toString(++OrderSession.orderNumber),
                        Integer.parseInt(view.getSelectedCustomerId()));
//                OrderSession.orderId = orderProcessing.getOrderByField(Integer.toString(OrderSession.orderNumber)).getInstanceId();
                OrderSession.customerId = Integer.parseInt(view.getSelectedCustomerId());
                OrderSession.isActive = true;
            } catch (SQLException e1) {
                e1.printStackTrace();
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
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }
    }
}
