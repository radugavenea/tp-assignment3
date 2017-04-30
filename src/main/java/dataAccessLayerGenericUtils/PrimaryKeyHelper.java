package dataAccessLayerGenericUtils;

import connection.ConnectionFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by radu on 30.04.2017.
 */
public class PrimaryKeyHelper<T> {

    private final Class<T> type;
    private final String url;

    public PrimaryKeyHelper(Class<T> type, String url) {
        this.type = type;
        this.url = url;
    }

    /**
     * Verifies if there is an auto-incremented primary key in db table
     * @return boolean
     * @throws SQLException
     */
    protected boolean hasAutoIncrementPrimaryKey() throws SQLException {
        boolean isAutoIncremented = false;

        Connection connection = ConnectionFactory.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + type.getSimpleName());

        for(int i : primaryKeysIndices()){
            if(resultSet.getMetaData().isAutoIncrement(i)){
                isAutoIncremented = true;
            }
        }

        resultSet.close();
        statement.close();
        connection.close();

        return isAutoIncremented;
    }

    /**
     * Gets the index of the auto-incremented primary key from table
     * @return index if there is auto-incremented primary key, -1 otherwise
     * @throws SQLException
     */
    protected int autoIncrementPrimaryKeyIndex() throws SQLException {
        int index = -1;

        Connection connection = ConnectionFactory.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + type.getSimpleName());

        for(int i : primaryKeysIndices()){
            if(resultSet.getMetaData().isAutoIncrement(i)){
                index = i;
            }
        }

        resultSet.close();
        statement.close();
        connection.close();

        return index;
    }


    /**
     * Gets the indices of of the primary keys for table
     * @return list of primary keys indices
     * @throws SQLException
     */
    protected List<Integer> primaryKeysIndices() throws SQLException {
        List<Integer> primaryKeysIndices = new ArrayList<Integer>();

        Connection connection = ConnectionFactory.getConnection(url);
        ResultSet resultSet = connection.getMetaData().getPrimaryKeys(null,null,type.getSimpleName());

        if (resultSet.next()){
            for(int i=0; i<type.getDeclaredFields().length; i++){
                Field field = type.getDeclaredFields()[i];
                if (isPrimaryKey(field.getName())){
                    primaryKeysIndices.add(i+1);
                }
            }
        }

        resultSet.close();
        connection.close();

        return primaryKeysIndices;
    }


    /**
     * Verifies if a specified field by name is primary key
     * @param name
     * @return true if primary key, false otherwise
     * @throws SQLException
     */
    protected boolean isPrimaryKey(String name) throws SQLException {
        boolean isPrimaryKey = false;

        Connection connection = ConnectionFactory.getConnection(url);
        ResultSet resultSet = connection.getMetaData().getPrimaryKeys(null,null,type.getSimpleName());
        while (resultSet.next()){
            if(resultSet.getString("COLUMN_NAME").equals(name)){
                isPrimaryKey = true;
            }
        }
        resultSet.close();
        connection.close();

        return isPrimaryKey;
    }
}
