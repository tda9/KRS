/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Duc Anh
 */
public class DAO<T> extends DBConnect {

    public Vector<T> getAll(String tableName, Class<T> entityClass)  {
        Vector<T> list = new Vector<>();
        String sql = "SELECT * FROM " + tableName;

        try (PreparedStatement stm = con.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            
            while (rs.next()) {
                T entity = entityClass.getDeclaredConstructor().newInstance();
                for (Field field : entityClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        if (isColumnPresent(rsmd, field.getName())) {
                            Object value = rs.getObject(field.getName());
                            if (value != null) {
                                field.set(entity, value);
                            }
                        }
                    } catch (SQLException | IllegalAccessException e) {
                        Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
                list.add(entity);
            }
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void create(String tableName, T entity) throws SQLException, IllegalAccessException {
        Class<?> entityClass = entity.getClass();
        Field[] fields = entityClass.getDeclaredFields();
        System.out.println(fields);
        StringBuilder fieldNames = new StringBuilder();
        StringBuilder fieldValues = new StringBuilder();
        for (Field field : fields) {
            field.setAccessible(true);
            fieldNames.append(field.getName()).append(",");
            fieldValues.append("?").append(",");
        }
        fieldNames.setLength(fieldNames.length() - 1);
        fieldValues.setLength(fieldValues.length() - 1);
        
        String sql = "INSERT INTO " + tableName + " (" + fieldNames.toString() + ") VALUES (" + fieldValues.toString() + ")";
        
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            int index = 1;
            for (Field field : fields) {
                stm.setObject(index++, field.get(entity));
            }
            stm.executeUpdate();
        }
    }

    public void update(String tableName, T entity, Object id) throws SQLException, IllegalAccessException {
        Class<?> entityClass = entity.getClass();
        Field[] fields = entityClass.getDeclaredFields();
        StringBuilder fieldUpdates = new StringBuilder();
        for (Field field : fields) {
            field.setAccessible(true);
            fieldUpdates.append(field.getName()).append("=?,");
        }
        fieldUpdates.setLength(fieldUpdates.length() - 1);
        
        String sql = "UPDATE " + tableName + " SET " + fieldUpdates.toString() + " WHERE id=?";
        
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            int index = 1;
            for (Field field : fields) {
                stm.setObject(index++, field.get(entity));
            }
            stm.setObject(index, id);
            stm.executeUpdate();
        }
    }
    private boolean isColumnPresent(ResultSetMetaData rsmd, String columnName) throws SQLException {
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            if (columnName.equalsIgnoreCase(rsmd.getColumnName(i))) {
                return true;
            }
        }
        return false;
    }
    
    
    public Vector<T> getDetails(String tableName, Class<T> entityClass,int id)  {
        Vector<T> list = new Vector<>();
        String sql = "SELECT * FROM " + tableName + " where " + tableName.toLowerCase() +"_id="+ id;

        try (PreparedStatement stm = con.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            
            if (rs.next()) {
                T entity = entityClass.getDeclaredConstructor().newInstance();
                for (Field field : entityClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        if (isColumnPresent(rsmd, field.getName())) {
                            Object value = rs.getObject(field.getName());
                            if (value != null) {
                                field.set(entity, value);
                            }
                        }
                    } catch (SQLException | IllegalAccessException e) {
                        Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
                list.add(entity);
            }
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public Vector<T> getAllWithCustomSQL(String tableName, Class<T> entityClass, String sql)  {
        Vector<T> list = new Vector<>();
        try (PreparedStatement stm = con.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            
            while (rs.next()) {
                T entity = entityClass.getDeclaredConstructor().newInstance();
                for (Field field : entityClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        if (isColumnPresent(rsmd, field.getName())) {
                            Object value = rs.getObject(field.getName());
                            if (value != null) {
                                field.set(entity, value);
                            }
                        }
                    } catch (SQLException | IllegalAccessException e) {
                        Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
                list.add(entity);
            }
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
