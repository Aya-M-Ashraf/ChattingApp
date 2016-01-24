package model;

import interfaces.ClientInterface;
import controller.Controller;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Aya M. Ashraf
 */
public class ClientImpl extends UnicastRemoteObject implements ClientInterface{
    
    Controller controller;
    
    public ClientImpl(Controller controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public void receive(String msg) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    
}
