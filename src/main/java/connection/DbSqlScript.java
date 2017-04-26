package connection;

import com.ibatis.common.jdbc.ScriptRunner;
import connection.ConnectionFactory;
import connection.ConnectionUrl;

import java.io.*;
import java.sql.*;


/**
 * Created by radu on 21.04.2017.
 */
public class DbSqlScript {


    private static String sqlScriptFilePath = "sql/warehouse_test.sql";

    /**
     * Creates test db if not exist and run script to reinitialize de db for unit testing
     */
    public static void runTestDbSqlScript()  {
        Connection connection;
        try {
            ConnectionFactory.getConnection(ConnectionUrl.testDbUrl);
        } catch (SQLException e) {
            createNewSchema("warehouse_test");
        }

        try {
            connection = ConnectionFactory.getConnection(ConnectionUrl.testDbUrl);
            // Initialize object for ScripRunner
            ScriptRunner sr = new ScriptRunner(connection, false, false);
            // Give the input file to Reader
            Reader reader = new BufferedReader(
                    new FileReader(sqlScriptFilePath));
            // Exctute script
            sr.runScript(reader);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new db schema with specified name on localhost
     * @param schemaName
     */
    public static void createNewSchema(String schemaName){

        try {
            Connection connection = ConnectionFactory.getConnection("jdbc:mysql://localhost/");
            Statement statement = connection.createStatement();
            String sql = "CREATE DATABASE " + schemaName;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
