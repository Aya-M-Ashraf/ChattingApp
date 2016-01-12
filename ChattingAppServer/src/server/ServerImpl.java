package server;

import interfaces.ClientInterface;
import interfaces.ServerInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 *
 * @author Aya M. Ashraf
 */
public class ServerImpl extends UnicastRemoteObject implements ServerInterface {

    Vector<ClientInterface> clientsVector = new Vector<>();

    public ServerImpl() throws RemoteException {
    }

    public void tellOthers(String msg) throws RemoteException {

        System.out.println("Message is recieved");
        for (ClientInterface client : clientsVector) {
            try {
                client.receive(msg);
            } catch (RemoteException ex) {
                System.out.println("Can't send message to client!");
                ex.printStackTrace();
            }
        }
    }

    public void register(ClientInterface clientRef) throws RemoteException {
        clientsVector.add(clientRef);
        System.out.println("Client has been added");
    }
    
     public void unregister(ClientInterface clientRef) throws RemoteException {
        clientsVector.remove(clientRef);
        System.out.println("Client has been removed");
    }

}
