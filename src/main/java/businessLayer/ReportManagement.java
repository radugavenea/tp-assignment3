package businessLayer;

import connection.ConnectionUrl;
import dataAccessLayer.ProductDAO;
import model.ProductEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by radu on 01.05.2017.
 */
public class ReportManagement {

    private CsvGenerator csvGenerator;
    private ProductDAO productDAO;

    public ReportManagement(CsvGenerator csvGenerator) {
        this.csvGenerator = csvGenerator;
        this.productDAO = new ProductDAO(ConnectionUrl.warehouseDbUrl);
    }


    public void generateUnderStockReport() throws SQLException, IOException {
        csvGenerator.generateReport(getAllUnderStockProducts());
    }

    public void generatePriceRangeReport() throws SQLException, IOException {
        csvGenerator.generateReport(getAllPriceRangeProducts());
    }


    private List<ProductEntity> getAllUnderStockProducts() throws SQLException {
        List<ProductEntity> underStockProducts = new ArrayList<>();
        List<ProductEntity> products = productDAO.getAll();
        for(int i=0; i<products.size(); i++){
            if(products.get(i).getStock() < 10){
                underStockProducts.add(products.get(i));
            }
        }
        return underStockProducts.isEmpty() ? null : underStockProducts;
    }

    private List<ProductEntity> getAllPriceRangeProducts() throws SQLException {
        List<ProductEntity> priceRangeProducts = new ArrayList<>();
        List<ProductEntity> products = productDAO.getAll();
        for(int i=0; i<products.size(); i++){
            if(products.get(i).getPrice() < 200 && products.get(i).getPrice() >= 100){
                priceRangeProducts.add(products.get(i));
            }
        }
        return priceRangeProducts.isEmpty() ? null : priceRangeProducts;
    }
}
