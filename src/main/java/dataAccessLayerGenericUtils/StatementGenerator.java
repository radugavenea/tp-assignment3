package dataAccessLayerGenericUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by radu on 21.04.2017.
 */
public class StatementGenerator<T> {

    private final Class<T> type;

    public StatementGenerator(Class<T> type) {
        this.type = type;
    }

    public void prepareInsertStatementAutoIncrement(PreparedStatement statement, T instance){
        try {
            for (int i=1; i<type.getDeclaredFields().length; i++){
                Field field = type.getDeclaredFields()[i];
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                Object result = method.invoke(instance);
                statement.setObject(i, result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void prepareInsertStatementNonAutoIncrement(PreparedStatement statement, T instance){
        try {
            for (int i=0; i<type.getDeclaredFields().length; i++){
                Field field = type.getDeclaredFields()[i];
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                Object result = method.invoke(instance);
                statement.setObject(i+1, result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

//    public void prepareUpdateStatement(PreparedStatement statement, T instance){
//        try {
//            for(int i=1;  i<instance.getClass().getDeclaredFields().length; i++){
//                Field field = instance.getClass().getDeclaredFields()[i];
//                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
//                Method method = propertyDescriptor.getReadMethod();
//                Object value = method.invoke(instance);
//                statement.setObject(i,value);
//            }
//            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(instance.getClass().getDeclaredFields()[0].getName(), type);
//            Method method = propertyDescriptor.getReadMethod();
//            Object value = method.invoke(instance);
//            statement.setObject(instance.getClass().getDeclaredFields().length,value);
//
//        } catch (IntrospectionException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void prepareUpdateStatement(PreparedStatement statement, T instance, int primaryKeysLength) throws SQLException {
        int fieldsLength = instance.getClass().getDeclaredFields().length;
        try {
            for(int i=0;  i<fieldsLength - primaryKeysLength; i++){
                Field field = instance.getClass().getDeclaredFields()[primaryKeysLength + i];
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                Object value = method.invoke(instance);
                statement.setObject(i+1,value);
            }
            for(int i=fieldsLength - primaryKeysLength;  i<fieldsLength; i++){
                Field field = instance.getClass().getDeclaredFields()[i - fieldsLength + primaryKeysLength];
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                Object value = method.invoke(instance);
                statement.setObject(i+1,value);
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    protected void prepareDeleteStatement(PreparedStatement statement, T instance, int primaryKeysLength) throws SQLException {
         try {
             for(int i=0;  i<primaryKeysLength; i++){
                 Field field = instance.getClass().getDeclaredFields()[i];
                 PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                 Method method = propertyDescriptor.getReadMethod();
                 Object value = method.invoke(instance);
                 statement.setObject(i+1,value);
             }
         } catch (IntrospectionException e) {
            e.printStackTrace();
         } catch (IllegalAccessException e) {
             e.printStackTrace();
         } catch (InvocationTargetException e) {
             e.printStackTrace();
         }
    }

    public List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        try {
            while (resultSet.next()){
                T instance = type.newInstance();
                for(Field field : type.getDeclaredFields()){
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(),type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance,value);
                }
                list.add(instance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return list.isEmpty() ? null : list;
    }
}
