package controller;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
    
    public void startServer(Registry registry){
        try {

            
            SignInServiceImpl SignInServiceImplRef = new SignInServiceImpl();
            registry.rebind("SignInService", SignInServiceImplRef);
            
            
            SignUpServiceImpl SignUpServiceRef = new SignUpServiceImpl();
            registry.rebind("SignUpService", SignUpServiceRef);

        } catch (RemoteException ex) {
            System.out.println("can't start");
            ex.printStackTrace();
        } 
    }
    
    public void stopServer(){
        try {

            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 5000);
    
            registry.unbind("SignInService");
            registry.unbind("SignUpService");

        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (NotBoundException ex) { 
             Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
         } 
    }
}
