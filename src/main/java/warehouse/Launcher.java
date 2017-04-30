package warehouse;

import controllers.CustomerController;
import controllers.WarehouseController;
import presentation.WarehouseView;

import javax.swing.*;


/**
 * Created by radu on 20.04.2017.
 */
public class Launcher {


    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                WarehouseView view = new WarehouseView();
                new CustomerController(view);
                new WarehouseController(view);
            }
        });

    }

}
