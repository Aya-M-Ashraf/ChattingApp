package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Aya M. Ashraf
 */
public interface AddFriendServerService extends Remote{
    public boolean checkIfUserExist(String email)throws RemoteException;
    public boolean sendFriendRequest(String userEmail, String emailToAdd)throws RemoteException;
}
