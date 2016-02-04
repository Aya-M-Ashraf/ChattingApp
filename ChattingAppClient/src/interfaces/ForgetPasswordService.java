package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.pojo.User;

/**
 *
 * @author KHoloud
 */
public interface ForgetPasswordService extends Remote{
    
    public User selectUserWhereEmail(String email) throws RemoteException;
}
