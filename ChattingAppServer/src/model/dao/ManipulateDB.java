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
            }
        } catch (SQLException ex) {
            System.out.println("Can't select Query");
            //Logger.getLogger(ManipulateDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

}
