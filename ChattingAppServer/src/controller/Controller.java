package controller;

import interfaces.ClientServices;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dao.ManipulateDB;
import model.dao.QueryDB;
import model.pojo.User;
import services.AddFriendServiceImpl;
import services.SignInServiceImpl;
import services.SignUpServiceImpl;
import services.ChangeStatusServiceImpl;
import services.SignOutServiceImpl;

public class Controller {

    ManipulateDB manipulateDBObj;
    QueryDB queryDB;
    Vector<User> usersVector = new Vector<User>();
    
    Vector<ClientServices> usersInterfacesVector = new Vector<>();

    public Vector<ClientServices> getUsersInterfacesVector() {
        return usersInterfacesVector;
    }

    public void setUsersInterfacesVector(Vector<ClientServices> usersInterfacesVector) {
        this.usersInterfacesVector = usersInterfacesVector;
    }

    public Controller() {
        try {
            manipulateDBObj = new ManipulateDB();
            queryDB = new QueryDB();
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

    public boolean removeUserFromVector(String eMail) {

        User user = manipulateDBObj.selectAllfromUserWhereEmail(eMail);

        if (user != null) {
            System.out.println("user removed");
            return usersVector.remove(user);

        } else {
            System.out.println("user is equal to null in selectAllfromUserWhereEmail(eMail) in removeUserFromVector(String eMail)");
            return false;
        }

    }

    public boolean setUserOff(String eMail) {
        return manipulateDBObj.setUserOff(eMail);
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

            SignInServiceImpl SignInServiceImplRef = new SignInServiceImpl(this);
            registry.rebind("SignInService", SignInServiceImplRef);

            SignOutServiceImpl SignOutServiceImplRef = new SignOutServiceImpl(this);
            registry.rebind("SignOutService", SignOutServiceImplRef);

            SignUpServiceImpl SignUpServiceRef = new SignUpServiceImpl(this);
            registry.rebind("SignUpService", SignUpServiceRef);

            AddFriendServiceImpl AddFriendServiceRef = new AddFriendServiceImpl(this);
            registry.rebind("AddFriendService", AddFriendServiceRef);

            ChangeStatusServiceImpl changeStatusServiceImplRef = new ChangeStatusServiceImpl(this);
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
            registry.unbind("SignOutService");
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

    public ArrayList<User> getAllUsers() {
        return queryDB.selectAllUsers();
    }

    public ArrayList<User> selectAllUsersWhere(String query) {
        return queryDB.selectAllUsersWhere(query);
    }

    public int getOnlineUsersCount() {
        return manipulateDBObj.selectAllOnlineUsers().size();
    }

    public int getOfflineUsersCount() {
        return manipulateDBObj.selectAllOfflineUsers().size();
    }
}
