package dataAccessLayerGenericUtils;

import connection.ConnectionFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * It only works where there is a table with one primary key, does not work well on tables with multiple primary keys.
 * Created by radu on 20.04.2017.
 */
public class GenericDAO<T> {

    private final Class<T> type;
    private QueryStringBuilder queryBuilder;
    private StatementGenerator statementGenerator;
    private PrimaryKeyHelper primaryKeyHelper;
    private String url;


    public GenericDAO(String connectionUrl) {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        queryBuilder = new QueryStringBuilder(type);
        statementGenerator = new StatementGenerator(type);
        primaryKeyHelper = new PrimaryKeyHelper(type,connectionUrl);
        url = connectionUrl;
    }

    /**
     * Gets entity from db based on id
     * @param id
     * @return the entity with the specified id
     * @throws SQLException
     */
    public T getById(int id) throws SQLException {
        String query = queryBuilder.createSelectByFieldQuery(type.getDeclaredFields()[0].getName());

        Connection connection = ConnectionFactory.getConnection(url);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        List<T> list = statementGenerator.createObjects(resultSet);

        resultSet.close();
        statement.close();
        connection.close();

        return (list != null ? list.get(0) : null);
    }

    /**
     * Gets all the records from db
     * @return the list of all records
     * @throws SQLException
     */
    public List<T> getAll() throws SQLException {
        String query = queryBuilder.createSelectAllQuery();

        Connection connection = ConnectionFactory.getConnection(url);
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<T> list = statementGenerator.createObjects(resultSet);

        resultSet.close();
        statement.close();
        connection.close();

        return list;
    }

    /**
     * Adds new record to the db
     * @param instance
     * @return 0 if everything works fine
     * @throws SQLException
     */
    public int addNew(T instance) throws SQLException {
        if(primaryKeyHelper.hasAutoIncrementPrimaryKey()){
            addNewAutoIncrement(instance);
        }
        else {
            addNewNonAutoIncrement(instance);
        }
        return 0;
    }

    /**
     * Updates record from db
     * @param instance
     * @return 0 if everything works fine
     * @throws SQLException
     */
    public int update(T instance) throws SQLException {
        int numberOfPrimaryKeys = primaryKeyHelper.primaryKeysIndices().size();
        String query = queryBuilder.createUpdateQuery(numberOfPrimaryKeys);

        Connection connection = ConnectionFactory.getConnection(url);
        PreparedStatement statement = connection.prepareStatement(query);

        statementGenerator.prepareUpdateStatement(statement, instance, numberOfPrimaryKeys);
        statement.executeUpdate();

        statement.close();
        connection.close();

        return 0;
    }

    /**
     * Deletes a record from db based on id
     * @return 0 if everything works fine
     * @throws SQLException
     */
    public int delete(T instance) throws SQLException {
        int numberOfPrimaryKeys = primaryKeyHelper.primaryKeysIndices().size();
        String query = queryBuilder.createDeleteByFieldQuery(numberOfPrimaryKeys);

        Connection connection = ConnectionFactory.getConnection(url);
        PreparedStatement statement = connection.prepareStatement(query);
        statementGenerator.prepareDeleteStatement(statement, instance, numberOfPrimaryKeys);
        statement.executeUpdate();
        statement.close();
        connection.close();

        return 0;
    }


    /**
     * Deletes a record from db based on id
     * @return
     * @throws SQLException
     */
    public int deleteById(int id) throws SQLException {
        int numberOfPrimaryKeys = primaryKeyHelper.primaryKeysIndices().size();
        String query = queryBuilder.createDeleteByFieldQuery(numberOfPrimaryKeys);

        Connection connection = ConnectionFactory.getConnection(url);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1,id);
        statement.executeUpdate();
        statement.close();
        connection.close();

        return 0;
    }


    /**
     * Add new record based in table with auto-incremented primary key
     * @param instance
     * @return 0 if everything works fine
     * @throws SQLException
     */
    private int addNewAutoIncrement(T instance) throws SQLException {
        String query = queryBuilder.createInsertQueryAutoIncrement();

        Connection connection = ConnectionFactory.getConnection(url);
        PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

        statementGenerator.prepareInsertStatementAutoIncrement(statement, instance);
        statement.executeUpdate();

        statement.close();
        connection.close();

        return 0;
    }

    /**
     * Add new record based in table with no auto-incremented primary key
     * @param instance
     * @return 0 if everything works fine
     * @throws SQLException
     */
    private int addNewNonAutoIncrement(T instance) throws SQLException {
        String query = queryBuilder.createInsertQueryNonAutoIncrement();

        Connection connection = ConnectionFactory.getConnection(url);
        PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

        statementGenerator.prepareInsertStatementNonAutoIncrement(statement, instance);
        statement.executeUpdate();

        statement.close();
        connection.close();

        return 0;
    }


}