package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.pojo.User;

/**
 *
 * @author Aya M. Ashraf
 */
public interface SignUpServerService extends Remote {
  
   public boolean clientSignUp(User user) throws RemoteException; 

}
