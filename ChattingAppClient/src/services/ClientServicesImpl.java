package services;

import controller.Controller;
import interfaces.ClientServices;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Ahmed Ashraf
 */
public class ClientServicesImpl extends UnicastRemoteObject implements ClientServices{

    Controller controller;
    public ClientServicesImpl(Controller controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public void printEmail() throws RemoteException {
        System.out.println(controller.getEmail());
    }
    
    @Override
    public void recieveAd(String adMessege) throws RemoteException{
        controller.ReceiveAdd(adMessege);
    }
}
