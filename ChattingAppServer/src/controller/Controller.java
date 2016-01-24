package controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.dao.ManipulateDB;
import model.pojo.User;

/**
 *
 * @author KHoloud
 */
public class Controller {
     ManipulateDB manipulateDBObj;

    public Controller() {
         try {
             
             this.manipulateDBObj = new ManipulateDB();
             
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
     
     public User signIn(String email, String password) {
        
        return  manipulateDBObj.selectAllfromUserWhereEmail(email);
  }
}
