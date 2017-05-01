package controllers;

import businessLayer.CsvGenerator;
import businessLayer.ReportManagement;
import presentation.WarehouseView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by radu on 01.05.2017.
 */
public class ReportController {

    WarehouseView view;
    ReportManagement reportManagement;

    public ReportController(WarehouseView view) {
        this.view = view;
        this.reportManagement = new ReportManagement(new CsvGenerator());

        view.addUnderStockButtonListener(new UnderStockButtonListener());
        view.addPriceRangeButtonListener(new PriceRangeButtonListener());
    }


    class UnderStockButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                reportManagement.generateUnderStockReport();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    class PriceRangeButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                reportManagement.generatePriceRangeReport();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
