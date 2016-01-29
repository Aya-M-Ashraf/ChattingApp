package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Aya M. Ashraf
 */
public interface ClientServices extends Remote {
    public void printEmail() throws RemoteException;
}
