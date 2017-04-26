package presentationGenericUtils;

import model.Customer;

import java.util.List;

/**
 * Created by radu on 26.04.2017.
 */
public class CustomerTableModel extends GenericTableModel<Customer> {

    public CustomerTableModel(List<Customer> modelData) {
        super(modelData);
    }

    public CustomerTableModel() {
        super();
    }
}
