package services;

import controller.Controller;
import interfaces.SignOutServerService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Amr
 */
public class SignOutServiceImpl extends UnicastRemoteObject implements SignOutServerService {

    Controller controller = new Controller();

    public SignOutServiceImpl() throws RemoteException {

    }

    @Override
    public boolean signOutOneUser(String eMail) throws RemoteException {

        System.out.println("iam in signout  server");
        controller.removeUserFromVector(eMail);

        System.out.println("iam in signout  server");
        return controller.setUserOff(eMail);

    }

}
