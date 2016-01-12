package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Aya M. Ashraf
 */
public interface ServerInterface extends Remote {

   public void tellOthers(String msg) throws RemoteException;
   public void register(ClientInterface clientRef) throws RemoteException;
   public void unregister(ClientInterface clientRef) throws RemoteException;

}
