package server;

import java.rmi.RemoteException;
import java.rmi.registry.*;
import services.SignInServiceImpl;
import services.SignUpServiceImpl;

/**
 *
 * @author Aya M. Ashraf
 */
public class Server {

    public Server() {

        try {

            Registry registry = LocateRegistry.createRegistry(5000);
            
            SignInServiceImpl SignInServiceImplRef = new SignInServiceImpl();
            registry.rebind("SignInService", SignInServiceImplRef);
            
            SignUpServiceImpl SignUpServiceRef = new SignUpServiceImpl();
            registry.rebind("SignUpService", SignUpServiceRef);

        } catch (RemoteException ex) {
            ex.printStackTrace();
        }         
    }

    public static void main(String[] args) {
            new Server();
            
    }

}
