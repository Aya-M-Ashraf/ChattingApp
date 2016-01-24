package services;

import controller.Controller;
import interfaces.SignInServerService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import model.pojo.User;

public class SignInServiceImpl extends UnicastRemoteObject implements SignInServerService{

   Controller controller = new Controller();
    
    public SignInServiceImpl() throws RemoteException {
    }

    @Override
    public User signIn(String email, String password) throws RemoteException {
        
        System.out.println("Sign in in Server side");
        return  controller.signIn(email, password);
  }
    
}
