package businessLayer;

import dataAccessLayer.OrderDAO;
import dataAccessLayer.OrderProductDAO;
import dataAccessLayer.ProductDAO;
import model.OrderEntity;
import model.OrderProductEntity;
import model.ProductEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Observable;

/**
 * Created by radu on 26.04.2017.
 */
public class OrderProcessing extends Observable {

    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private OrderProductDAO orderProductDAO;

    public OrderProcessing(OrderDAO orderDAO, ProductDAO productDAO, OrderProductDAO orderProductDAO) {
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
        this.orderProductDAO = orderProductDAO;


    }

    public List<ProductEntity> getAllProducts() throws SQLException {
        return productDAO.getAll();
    }

    public List<OrderProductEntity> getAllOrderProducts() throws SQLException {
        return orderProductDAO.getAll();
    }

    public List<OrderProductEntity> getOrderProductsById(int orderId) throws SQLException {
        return orderProductDAO.getAllById(orderId);
    }

    public int addNewOrder(String orderNumber, int customerId) throws SQLException {
        OrderEntity order = new OrderEntity(orderNumber,customerId);
        return orderDAO.addNew(order);
    }

    public void processProductOrder(int orderId, int productId , int quantity) throws SQLException {
        ProductEntity product = productDAO.getById(productId);

        if(isStockSufficient(quantity,product.getStock())){
            orderProductDAO.addNew(new OrderProductEntity(orderId,productId,quantity));
            product.setStock(product.getStock() - quantity);
            productDAO.update(product);
            setChanged();
            notifyObservers(productDAO);
        }
    }

    private boolean isStockSufficient(int quantityToBuy, int inStock){
        return quantityToBuy <= inStock ? true : false;
    }

}

