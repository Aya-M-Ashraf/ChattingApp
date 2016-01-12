package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Aya M. Ashraf
 */
public interface ClientInterface extends Remote
{
    public void receive(String msg) throws RemoteException;
}
