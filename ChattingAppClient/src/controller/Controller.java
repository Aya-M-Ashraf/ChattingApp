package controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientImpl;
import interfaces.ServerInterface;
import view.ClientFrame;

/**
 *
 * @author Aya M. Ashraf
 */
public class Controller {

    ClientImpl clientImplRef;
    ClientFrame viewFrame;
    ServerInterface serverRef;

    public Controller() {

        try {
            clientImplRef = new ClientImpl(this);
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 5000);
            serverRef = (ServerInterface) registry.lookup("ChatService");
            serverRef.register(clientImplRef);                          //add client to server Vector
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (NotBoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        viewFrame = new ClientFrame(this);
    }

    public void sendMsg(String msg) {
        try {
            serverRef.tellOthers(msg);
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void unregister() throws RemoteException {
        serverRef.unregister(clientImplRef);
    }

    public void displayMsg(String msg) {
        viewFrame.display(msg);
    }

    public static void main(String[] args) {
        new Controller();
    }

}
