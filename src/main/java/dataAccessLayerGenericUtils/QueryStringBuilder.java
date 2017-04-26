package dataAccessLayerGenericUtils;

import java.lang.reflect.Field;

/**
 * Created by radu on 21.04.2017.
 */
public class QueryStringBuilder<T> {

    private final Class<T> type;

    public QueryStringBuilder(Class<T> type) {
        this.type = type;
    }


    public String createSelectByFieldQuery(String field){
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


    public String createInsertQuery2(){
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

    public String createInsertQuery(){
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


    public String createUpdateQuery(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE " + type.getSimpleName() + " SET ");
        String prefix = "";
        for(int i=1; i<type.getDeclaredFields().length; i++){
            stringBuilder.append(prefix);
            stringBuilder.append(type.getDeclaredFields()[i].getName());
            stringBuilder.append(" = ?");
            prefix = ", ";
        }
        stringBuilder.append(" WHERE " + type.getDeclaredFields()[0].getName() + " = ?");
        return stringBuilder.toString();
    }


    public String createDeleteByFieldQuery(String field){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM " + type.getSimpleName() + " WHERE ");
        stringBuilder.append(field + " = ?");
        return stringBuilder.toString();
    }
}
