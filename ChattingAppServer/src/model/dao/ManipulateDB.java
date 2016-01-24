package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DBConnection.DataBaseConnection;
import model.pojo.User;

/**
 *
 * @author KHoloud
 */
public class ManipulateDB {

    Connection connection;

    public ManipulateDB() throws ClassNotFoundException {
        DataBaseConnection DBConnection = new DataBaseConnection();
        connection = DBConnection.getConnection();
    }

    public User selectAllfromUser() {
        User user = new User();
        try {
            PreparedStatement pst = connection.prepareStatement("Select * from user");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
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
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManipulateDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public User selectAllfromUserWhereEmail(String email){
        User user = new User();
        try {
            PreparedStatement pst = connection.prepareStatement("Select * from user where Email = ? ");
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
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
                return user;
            }
        } catch (SQLException ex) {
            System.out.println("Can't select Query");
            //Logger.getLogger(ManipulateDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DBConnection.DataBaseConnection;
import model.pojo.User;

/**
 *
 * @author KHoloud
 */
public class ManipulateDB {

    Connection connection;

    public ManipulateDB() throws ClassNotFoundException {
        DataBaseConnection DBConnection = new DataBaseConnection();
        connection = DBConnection.getConnection();
    }

    public User selectAllfromUser() {
        User user = new User();
        try {
            PreparedStatement pst = connection.prepareStatement("Select * from user");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

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
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManipulateDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public boolean searchForUserByEMail(String eMail) {
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("Select * from user where Email = ?");
            pst.setString(1, eMail);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {

             
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManipulateDB.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return false;
    }

    public boolean insertUser(User user) {
                
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("insert into user values(?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getFirstName());
            pst.setString(3, user.getLastName());
            pst.setString(4, user.getPassword());
            pst.setString(5, user.getCountry());
            pst.setString(6, user.getCity());
            pst.setBoolean(7, user.isOnline());
            pst.setString(8, user.getStatus());
            pst.setString(9, user.getGender());
            pst.setString(10, user.getSecuirtyQuestion());
            pst.setString(11, user.getSecurityAnswer());
            pst.executeUpdate();
            System.out.println("insertUser(User user)  ok");

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }
}
