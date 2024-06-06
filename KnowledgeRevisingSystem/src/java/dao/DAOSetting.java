/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Duc Anh
 */
public class DAOSetting extends DBConnect {

    public Vector<Setting> getAllSetting() {
        Vector<Setting> list = new Vector<>();
        String sql = "SELECT * FROM Setting";

        try (PreparedStatement stm = con.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                list.add(new Setting(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getInt(6)));
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOSetting.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Vector<Setting> getSettingDetails(String sql) {
        Vector<Setting> list = new Vector<>();

        try (PreparedStatement stm = con.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                list.add(new Setting(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getInt(6)));
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOSetting.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Vector<String> getOneColumn(String sql) {
        Vector<String> list = new Vector<>();

        try (PreparedStatement stm = con.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOSetting.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getPageNumber(int n) {
        int pageNumber = 0;
        pageNumber = n / 3;
        if (n % 3 != 0) {
            pageNumber++;
        }
        return pageNumber;
    }

    public Vector<Setting> getSettingForOnePage(int currentPage) {
        Vector<Setting> list = new Vector<>();
        String sql = "SELECT *"
                + "FROM Setting\n"
                + " ORDER BY setting_id LIMIT 3"
                + " OFFSET " + currentPage * 3;

        try (PreparedStatement stm = con.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                list.add(new Setting(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getInt(6)));
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOSetting.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void update(Setting s) {

        String sqlUpdate = "UPDATE `g5_krs_db`.`setting`\n"
                + "SET\n"
                + "`name` = ?,\n"
                + "`type` = ?,\n"
                + "`description` = ?,\n"
                + "`status` = ?,\n"
                + "`order` = ?\n"
                + "WHERE `setting_id` = ?;";
        try {
            PreparedStatement statement = con.prepareStatement(sqlUpdate);
            statement.setString(1, s.getName());
            statement.setString(2, s.getType());
            statement.setString(3, s.getDescription());
            statement.setBoolean(4, s.isStatus());
            statement.setInt(5, s.getOrder());
            statement.setInt(6, s.getSetting_id());
            statement.executeUpdate();
        } catch (SQLException ex) {
            //log
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DAOSetting o = new DAOSetting();
        Vector<Setting> list = o.getSettingForOnePage(0);
        System.out.println(list.get(0));
    }

    public void insert(String name, String type, String description, boolean status, int order) {
        String sql = "INSERT INTO `g5_krs_db`.`setting`\n"
                + "(`name`,`type`,`description`,`status`,`order`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?)";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, name);
            stm.setString(2, type);
            stm.setString(3, description);
            stm.setBoolean(4, status);
            stm.setInt(5, order);
            stm.executeUpdate();
        } catch (SQLException ex) {
            //log
            ex.printStackTrace();
        }
    }

    public boolean checkExisted(String type, String name, boolean status) {
        String sql = "select * from Setting where type =? and name = ? and status =?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(2, name);
            stm.setString(1, type);
            stm.setBoolean(3, status);
            ResultSet rs = stm.executeQuery();
            

            if (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOSetting.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
