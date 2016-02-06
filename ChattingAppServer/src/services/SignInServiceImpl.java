package services;

import controller.Controller;
import interfaces.ClientServices;
import interfaces.SignInServerService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import model.pojo.User;

public class SignInServiceImpl extends UnicastRemoteObject implements SignInServerService {

    Controller controller;

    public SignInServiceImpl(Controller controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public User signIn(String email, String password) throws RemoteException {

        System.out.println("Sign in in Server side");
        return controller.signIn(email, password);
    }

    @Override
    public boolean updateUserIsOnlineByEmail(String email) throws RemoteException {
        return controller.updateUserIsOnlineByEmail(email);
    }

    @Override
    public boolean updateUserStatusByEmail(String email, String status) throws RemoteException {
        return controller.updateUserStatusByEmail(email, status);
    }

    @Override
    public void registerUser(ClientServices userInterface) throws RemoteException {
        controller.getUsersInterfacesVector().add(userInterface);
        controller.tellMyFriendsIamOnline(userInterface);
        System.out.println("client added " + controller.getUsersInterfacesVector().size());
    }

    @Override
    public void unregisterUser(ClientServices userInterface) throws RemoteException {
        controller.getUsersInterfacesVector().remove(userInterface);
        controller.tellMyFriendsIamOffline(userInterface);
        System.out.println("client removed " + controller.getUsersInterfacesVector().size());
    }

    @Override
    public void sendAdToOnlineUsers(String AdMsg) throws RemoteException {
        controller.sendAddToOnlineUsers(AdMsg);
    }
    
    @Override
    public void changeFriendStatus(String friendEmail, String newStatus) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
