package controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dao.ManipulateDB;
import model.pojo.User;
import services.SignInServiceImpl;
import services.SignUpServiceImpl;


public class Controller {
     ManipulateDB manipulateDBObj;

    public Controller() {
         try {
             manipulateDBObj = new ManipulateDB();
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    public boolean searchForUserByEMail(String eMail) {
        User user;
        user = manipulateDBObj.selectAllfromUserWhereEmail(eMail);
        if (user == null)
            return false;
        else
            return true;
    }
     
    public User signIn(String email, String password) {
        
        return  manipulateDBObj.selectAllfromUserWhereEmail(email);
  }
    
    public boolean insertUser(User user) {
        
        return  manipulateDBObj.insertUser(user);
  }
}
