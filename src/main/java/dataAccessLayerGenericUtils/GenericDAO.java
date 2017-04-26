package dataAccessLayerGenericUtils;

import connection.ConnectionFactory;

import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.List;

/**
 * Created by radu on 20.04.2017.
 */
public class GenericDAO<T> {

    private final Class<T> type;
    private QueryStringBuilder queryBuilder;
    private StatementGenerator statementGenerator;
    private String url;


    public GenericDAO(String connectionUrl) {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        queryBuilder = new QueryStringBuilder(type);
        statementGenerator = new StatementGenerator(type);
        url = connectionUrl;
    }

    /**
     * Gets entity from db based on id
     * @param id
     * @return
     * @throws SQLException
     */
    public T getById(int id) throws SQLException {
        String query = queryBuilder.createSelectByFieldQuery("id");

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
     * @return
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
     * @return
     * @throws SQLException
     */
    public int addNew(T instance) throws SQLException {
        String query = queryBuilder.createInsertQuery();

        Connection connection = ConnectionFactory.getConnection(url);
        PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

        statementGenerator.prepareInsertStatement(statement, instance);
        statement.executeUpdate();

        statement.close();
        connection.close();

        return 0;
    }

    /**
     * Updates record from db
     * @param instance
     * @return
     * @throws SQLException
     */
    public int update(T instance) throws SQLException {
        String query = queryBuilder.createUpdateQuery();

        Connection connection = ConnectionFactory.getConnection(url);
        PreparedStatement statement = connection.prepareStatement(query);

        statementGenerator.prepareUpdateStatement(statement, instance);
        statement.executeUpdate();

        statement.close();
        connection.close();

        return 0;
    }

    /**
     * Deletes a record from db based on id
     * @param id
     * @return
     * @throws SQLException
     */
    public int deleteById(int id) throws SQLException {
        String query = queryBuilder.createDeleteByFieldQuery("id");

        Connection connection = ConnectionFactory.getConnection(url);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
        connection.close();

        return 0;
    }
}