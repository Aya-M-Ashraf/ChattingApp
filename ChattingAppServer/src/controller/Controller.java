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
import services.AddFriendServiceImpl;
import services.SignInServiceImpl;
import services.SignUpServiceImpl;
import services.ChangeStatusServiceImpl;

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
        if (user == null) {
            System.out.println("no user with this mail is found");
            return false;
        } else {
            System.out.println("user with this mail is found");
            return true;
        }
    }

    public User signIn(String email, String password) {

        return manipulateDBObj.selectAllfromUserWhereEmail(email);
    }

    public boolean insertUser(User user) {

        return manipulateDBObj.insertUser(user);
    }

    public boolean updateUserIsOnlineByEmail(String email) {

        return manipulateDBObj.updateUserIsOnlineByEmail(email);
    }

    public boolean updateUserStatusByEmail(String email, String status) {
        return manipulateDBObj.updateUserStatusByEmail(email, status);
    }

    public void startServer(Registry registry) {
        try {

            SignInServiceImpl SignInServiceImplRef = new SignInServiceImpl();
            registry.rebind("SignInService", SignInServiceImplRef);

            SignUpServiceImpl SignUpServiceRef = new SignUpServiceImpl();
            registry.rebind("SignUpService", SignUpServiceRef);

            AddFriendServiceImpl AddFriendServiceRef = new AddFriendServiceImpl();
            registry.rebind("AddFriendService", AddFriendServiceRef);
            
            ChangeStatusServiceImpl changeStatusServiceImplRef = new ChangeStatusServiceImpl();
            registry.rebind("ChangeStatusService", changeStatusServiceImplRef);

        } catch (RemoteException ex) {
            System.out.println("can't start");
            ex.printStackTrace();
        }
    }

    public void stopServer() {
        try {

            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 5000);

            registry.unbind("SignInService");
            registry.unbind("SignUpService");
            registry.unbind("AddFriendService");
            registry.unbind("ChangeStatusService");

        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (NotBoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean addFriendRequest(String userEmail, String emailToAdd) {
        return manipulateDBObj.insertFriendRequest(userEmail, emailToAdd);
    }
    
//    public boolean tellFriendsMyStatus(User user, String newStatus){
//        return 
//    }
}
