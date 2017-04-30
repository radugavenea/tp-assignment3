package dataAccessLayerGenericUtils;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by radu on 21.04.2017.
 */
public class QueryStringBuilder<T> {

    private final Class<T> type;

    public QueryStringBuilder(Class<T> type) {
        this.type = type;
    }


    protected String createSelectByFieldQuery(String field){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM ");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append(" WHERE " + field + " =?");
        return stringBuilder.toString();
    }


    public String createSelectAllQuery(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM ");
        stringBuilder.append(type.getSimpleName());
        return stringBuilder.toString();
    }


    protected String createInsertQuery2(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO " + type.getSimpleName() + " (");
        String prefix = "";
        for(Field field : type.getDeclaredFields()){
            stringBuilder.append(prefix);
            prefix = ", ";
            stringBuilder.append(field.getName());
        }
        prefix = "";
        stringBuilder.append(") VALUES (");
        for(int i=0; i<type.getDeclaredFields().length; i++){
            stringBuilder.append(prefix);
            prefix = ",";
            stringBuilder.append("?");
        }
        stringBuilder.append(");");
        return stringBuilder.toString();
    }

    protected String createInsertQueryAutoIncrement(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO " + type.getSimpleName() + " (");
        String prefix = "";
        for(int i=1; i<type.getDeclaredFields().length; i++){
            Field field = type.getDeclaredFields()[i];
            stringBuilder.append(prefix);
            prefix = ", ";
            stringBuilder.append(field.getName());
        }
        prefix = "";
        stringBuilder.append(") VALUES (");
        for(int i=1; i<type.getDeclaredFields().length; i++){
            stringBuilder.append(prefix);
            prefix = ",";
            stringBuilder.append("?");
        }
        stringBuilder.append(");");
        return stringBuilder.toString();
    }


    protected String createInsertQueryNonAutoIncrement(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO " + type.getSimpleName() + " (");
        String prefix = "";
        for(int i=0; i<type.getDeclaredFields().length; i++){
            Field field = type.getDeclaredFields()[i];
            stringBuilder.append(prefix);
            prefix = ", ";
            stringBuilder.append(field.getName());
        }
        prefix = "";
        stringBuilder.append(") VALUES (");
        for(int i=0; i<type.getDeclaredFields().length; i++){
            stringBuilder.append(prefix);
            prefix = ",";
            stringBuilder.append("?");
        }
        stringBuilder.append(");");
        return stringBuilder.toString();
    }

//    protected String createUpdateQuery(){
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("UPDATE " + type.getSimpleName() + " SET ");
//        String prefix = "";
//        for(int i=1; i<type.getDeclaredFields().length; i++){
//            stringBuilder.append(prefix);
//            stringBuilder.append(type.getDeclaredFields()[i].getName());
//            stringBuilder.append(" = ?");
//            prefix = ", ";
//        }
//        stringBuilder.append(" WHERE " + type.getDeclaredFields()[0].getName() + " = ?");
//        return stringBuilder.toString();
//    }

    protected String createUpdateQuery(int primaryKeysLength) throws SQLException {
        int fieldsLength = type.getDeclaredFields().length;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE " + type.getSimpleName() + " SET ");
        String prefix = "";
        for(int i=0; i<fieldsLength - primaryKeysLength; i++){
            stringBuilder.append(prefix);
            stringBuilder.append(type.getDeclaredFields()[i + primaryKeysLength].getName());
            stringBuilder.append(" = ?");
            prefix = ", ";
        }
        stringBuilder.append(" WHERE ");
        prefix = "";
        for(int i=fieldsLength - primaryKeysLength; i<fieldsLength; i++){
            stringBuilder.append(prefix);
            stringBuilder.append(type.getDeclaredFields()[i - fieldsLength + primaryKeysLength].getName());
            stringBuilder.append(" = ?");
            prefix = " AND ";
        }
        return stringBuilder.toString();
    }


//    protected String createDeleteByFieldQuery(String field){
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("DELETE FROM " + type.getSimpleName() + " WHERE ");
//        stringBuilder.append(field + " = ?");
//        return stringBuilder.toString();
//    }

    protected String createDeleteByFieldQuery(int size) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM " + type.getSimpleName() + " WHERE ");
        String prefix = "";
        for(int i=0; i<size; i++){
            stringBuilder.append(prefix);
            stringBuilder.append(type.getDeclaredFields()[i].getName() + " = ?");
            prefix = " AND ";
        }
        return stringBuilder.toString();
    }
}
