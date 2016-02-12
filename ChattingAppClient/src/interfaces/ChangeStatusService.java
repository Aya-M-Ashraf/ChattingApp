package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.pojo.User;

/**
 *
 * @author KHoloud
 */
public interface ChangeStatusService extends Remote{
    public void tellFriendsMyStatus(User user,String status) throws RemoteException;
}
