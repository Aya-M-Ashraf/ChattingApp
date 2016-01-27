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
    public boolean updateUserIsOnlineByEmail(String email) throws RemoteException;
    public boolean updateUserStatusByEmail(String email, String status) throws RemoteException;
}
