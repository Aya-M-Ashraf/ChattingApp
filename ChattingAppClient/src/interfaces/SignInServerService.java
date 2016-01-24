package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.pojo.User;

/**
 *
 * @author KHoloud
 */
public interface SignInServerService extends Remote {
    public User signIn(String email, String password) throws RemoteException;
}

