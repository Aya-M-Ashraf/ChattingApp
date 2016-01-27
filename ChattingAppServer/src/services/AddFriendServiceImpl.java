package services;

import controller.Controller;
import interfaces.AddFriendServerService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Aya M. Ashraf
 */
public class AddFriendServiceImpl extends UnicastRemoteObject implements AddFriendServerService{

    Controller controller = new Controller();
    
    public AddFriendServiceImpl() throws RemoteException {}
    
    @Override
    public boolean checkIfUserExist(String email) {
        return controller.searchForUserByEMail(email);
    }

    @Override
    public boolean sendFriendRequest(String userEmail, String emailToAdd) {
        
        return controller.addFriendRequest(userEmail,emailToAdd);
    }
    
}
