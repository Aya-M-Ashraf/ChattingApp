package server;

import java.rmi.RemoteException;
import java.rmi.registry.*;

/**
 *
 * @author Aya M. Ashraf
 */
public class Server {

    public Server() {

        try {

            ServerImpl serverImplRef = new ServerImpl();
            Registry registry = LocateRegistry.createRegistry(5000);
            registry.rebind("ChatService", serverImplRef);

        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
            new Server();
    }

}
