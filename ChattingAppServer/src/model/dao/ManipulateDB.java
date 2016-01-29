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
 * @author KHoloud
 */
public class ManipulateDB {

    Connection connection;

    public ManipulateDB() throws ClassNotFoundException {
        DataBaseConnection DBConnection = new DataBaseConnection();
        connection = DBConnection.getConnection();
    }

    public User selectAllfromUser() {    //where we use this strange function !!!?
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

    public User selectAllfromUserWhereEmail(String email) {
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
                user.setFriendsList(selectUserFriends(user));
                return user;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Can't execute select Query");
        }
        return null;
    }

    public User selectAllFromUserWhereEmailwithoutFriendList(String email) {
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
            ex.printStackTrace();
            System.out.println("Can't execute select Query");
        }
        return null;
    }

    public boolean insertUser(User user) {

        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("insert into user values(?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getFirstName());
            pst.setString(3, user.getLastName());
            pst.setString(4, user.getPassword());
            pst.setString(5, user.getCountry());
            pst.setString(6, user.getCity());
            pst.setBoolean(7, user.isIsOnline());
            pst.setString(8, user.getStatus());
            pst.setString(9, user.getGender());
            pst.setString(10, user.getSecuirtyQuestion());
            pst.setString(11, user.getSecurityAnswer());
            pst.setString(12, null);
            pst.executeUpdate();
            System.out.println("insertUser(User user)  ok");

            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean insertFriendRequest(String userEmail, String emailToAdd) {
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("insert into user_has_friend_request values(?,?)");
            pst.setString(1, userEmail);
            pst.setString(2, emailToAdd);
            pst.executeUpdate();
            System.out.println("request is inserted to DB");
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public ArrayList<User> selectUserFriends(User user) {

        User friend = new User();
        ArrayList<User> friendList = new ArrayList<>();
        try {
            String friendMail;
            PreparedStatement pst = connection.prepareStatement("Select Friend_Email from user_has_friend where User_Email = ? ");
            pst.setString(1, user.getEmail());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                friendMail = rs.getString(1);
                friend = selectAllFromUserWhereEmailwithoutFriendList(friendMail);
                friendList.add(friend);
                System.out.println("1");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Can't execute select Query");
        }
        return friendList;
    }
    
    public ArrayList<User> selectAllOnlineUsers() {

        User user = new User();
        ArrayList<User> onlineUsers = new ArrayList<>();
        try {
            String userMail;
            PreparedStatement pst = connection.prepareStatement("Select Email from user where Online = true ");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                userMail = rs.getString(1);
                user = selectAllFromUserWhereEmailwithoutFriendList(userMail);
                onlineUsers.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Can't execute selectAllOnlineUsers Query");
        }
        return onlineUsers;
    }
     
    public ArrayList<User> selectAllOfflineUsers() {

        User user = new User();
        ArrayList<User> offlineUsers = new ArrayList<>();
        try {
            String userMail;
            PreparedStatement pst = connection.prepareStatement("Select Email from user where Online = false ");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                userMail = rs.getString(1);
                user = selectAllFromUserWhereEmailwithoutFriendList(userMail);
                offlineUsers.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Can't execute selectAllOnlineUsers Query");
        }
        return offlineUsers;
    }

    public boolean updateUserIsOnlineByEmail(String email) {
        //User user = new User();
        try {
            PreparedStatement pst;
            pst = connection.prepareStatement("UPDATE  user SET Online = 1 WHERE  Email = ? ");
            pst.setString(1, email);
            pst.executeUpdate();
            System.out.println("isOnline updated");
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ManipulateDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateUserStatusByEmail(String email, String status) {
        try {
            PreparedStatement pst;
            pst = connection.prepareStatement("UPDATE  user SET Status = ? WHERE  Email = ? ");
            pst.setString(1, status);
            pst.setString(2, email);
            pst.executeUpdate();
            System.out.println("status updated");
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ManipulateDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
   
    public boolean setUserOff(String eMail) {
        try {
            PreparedStatement pst;
            pst = connection.prepareStatement("UPDATE user SET Online=0 WHERE Email=?");
          
            pst.setString(1, eMail);
            System.out.println(pst.toString());
              System.out.println("offline");
            pst.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("i cant set the user online or offline");
            
           // Logger.getLogger(ManipulateDB.class.getName()).log(Level.SEVERE, null, ex);
                       
            return false;
        } 
}
}
