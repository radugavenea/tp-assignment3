package presentationGenericUtils;

import model.Product;

import java.util.List;

/**
 * Created by radu on 26.04.2017.
 */
public class WarehouseTableModel extends GenericTableModel<Product> {

    public WarehouseTableModel(List<Product> modelData) {
        super(modelData);
    }

    public WarehouseTableModel() {
        super();
    }
}
