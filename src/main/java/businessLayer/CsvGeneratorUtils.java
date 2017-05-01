package businessLayer;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Created by radu on 01.05.2017.
 */

public class CsvGeneratorUtils {

    private static final char SEPARATOR = ',';


    public static void writeLine(Writer writer, List<String> values) throws IOException {
        writeLine(writer,values,SEPARATOR,' ');
    }

    public static void writeLine(Writer writer, List<String> values, char separator) throws IOException {
        writeLine(writer, values, separator, ' ');
    }

    public static void writeLine(Writer writer, List<String> values, char separator, char customQuote) throws IOException {
        boolean first = true;
        if(separator == ' '){
            separator = SEPARATOR;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(String value : values){
            if(!first){
                stringBuilder.append(separator);
            }
            if(customQuote == ' '){
                stringBuilder.append(followCSVformat(value));
            }
            else{
                stringBuilder.append(customQuote).append(followCSVformat(value)).append(customQuote);
            }
            first = false;
        }
        stringBuilder.append("\n");
        writer.append(stringBuilder.toString());
    }


    private static String followCSVformat(String value){
        String result = value;
        if(result.contains("\"")){
            result = result.replace("\"","\"\"");
        }
        return result;
    }
}

