package services;

import controller.Controller;
import interfaces.SignUpServerService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import model.pojo.User;

/**
 *
 * @author Aya M. Ashraf
 */
public class SignUpServiceImpl extends UnicastRemoteObject implements SignUpServerService {

    Controller controller;

    public SignUpServiceImpl(Controller controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public boolean clientSignUp(User user) {
        if (!controller.searchForUserByEMail(user.getEmail())) 
            return controller.insertUser(user);
        return false;
    }

}
