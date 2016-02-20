/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DBConnection.DataBaseConnection;
import model.pojo.User;

/**
 *
 * @author Amr
 */
public class QueryDB {

    Connection connection;

    public QueryDB() throws ClassNotFoundException {
        DataBaseConnection DBConnection = new DataBaseConnection();
        connection = DBConnection.getConnection();
    }

    public ArrayList<User> selectAllUsersInfo() {

        ArrayList<User> AllUsers = new ArrayList<User>();

        User user;

        try {
            PreparedStatement pst = connection.prepareStatement("select Fname , Lname , Country , city , Online , Status , Gender ,Email from user");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                user = new User();
                user.setFirstName(rs.getString(1));
                user.setLastName(rs.getString(2));
                user.setCountry(rs.getString(3));
                user.setCity(rs.getString(4));
                user.setIsOnline(rs.getBoolean(5));
                user.setStatus(rs.getString(6));
                user.setGender(rs.getString(7));
                user.setEmail(rs.getString(8));
                AllUsers.add(user);
            }
            //System.out.println(AllUsers.get(2).getFirstName());

            return AllUsers;
        } catch (SQLException ex) {
            Logger.getLogger(ManipulateDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<User> selectAllUsers() {

        ArrayList<User> AllUsers = new ArrayList<User>();

        User user;

        try {
            PreparedStatement pst = connection.prepareStatement("Select * from user");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                user = new User();
                user.setEmail(rs.getString(1));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setPassword(rs.getString(4));
                user.setCountry(rs.getString(5));
                user.setCity(rs.getString(6));
                user.setIsOnline(rs.getBoolean(7));
                user.setStatus(rs.getString(8));
                user.setGender(rs.getString(9));
                user.setSecuirtyQuestion(rs.getString(10));
                user.setSecurityAnswer(rs.getString(11));

                AllUsers.add(user);

            }
            System.out.println(AllUsers.get(2).getFirstName());

            return AllUsers;
        } catch (SQLException ex) {
            Logger.getLogger(ManipulateDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<User> selectAllUsersWhere(String query) {

        ArrayList<User> AllUsers = new ArrayList<User>();

        User user;

        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                user = new User();
                user.setEmail(rs.getString(1));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setPassword(rs.getString(4));
                user.setCountry(rs.getString(5));
                user.setCity(rs.getString(6));
                user.setIsOnline(rs.getBoolean(7));
                user.setStatus(rs.getString(8));
                user.setGender(rs.getString(9));
                user.setSecuirtyQuestion(rs.getString(10));
                user.setSecurityAnswer(rs.getString(11));

                AllUsers.add(user);

            }
            System.out.println(AllUsers.get(2).getFirstName());

            return AllUsers;
        } catch (SQLException ex) {
            Logger.getLogger(ManipulateDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
