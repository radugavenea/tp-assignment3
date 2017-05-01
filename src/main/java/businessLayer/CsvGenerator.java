package businessLayer;

import model.ProductEntity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by radu on 01.05.2017.
 */

public class CsvGenerator {

    public void generateReport(List<ProductEntity> productEntities) throws IOException {

        String csvFile = "reports/csv/" + LocalDate.now().toString() + " " + LocalTime.now().toString() + ".csv";
        File file = new File(csvFile);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        if (!file.exists())
            file.createNewFile();
        FileWriter writer = new FileWriter(csvFile);

        List<String> columnTitles = new ArrayList<>();
        columnTitles.add("Product id");
        columnTitles.add("Product name");
        columnTitles.add("Quantity");
        columnTitles.add("Price");
        CsvGeneratorUtils.writeLine(writer,columnTitles);

        for(int i=0; i<productEntities.size(); i++){
            List<String> fields = new ArrayList<>();
            fields.add(Integer.toString(productEntities.get(i).getId()));
            fields.add(productEntities.get(i).getName());
            fields.add(Integer.toString(productEntities.get(i).getStock()));
            fields.add(Float.toString(productEntities.get(i).getPrice()));
            CsvGeneratorUtils.writeLine(writer, fields);
        }
        writer.flush();
        writer.close();
    }
}
