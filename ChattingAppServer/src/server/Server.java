package server;

import java.rmi.RemoteException;
import java.rmi.registry.*;
import services.SignInServiceImpl;

/**
 *
 * @author Aya M. Ashraf
 */
public class Server {

    public Server() {

        try {

            Registry registry = LocateRegistry.createRegistry(5000);
            
            ServerImpl serverImplRef = new ServerImpl();
            registry.rebind("ChatService", serverImplRef);
            
            SignInServiceImpl SignInServiceImplRef = new SignInServiceImpl();
            registry.rebind("SignInService", SignInServiceImplRef);

        } catch (RemoteException ex) {
            ex.printStackTrace();
        }         
    }

    public static void main(String[] args) {
            new Server();
    }

}
