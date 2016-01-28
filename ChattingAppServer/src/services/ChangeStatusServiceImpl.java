/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import interfaces.ChangeStatusService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import model.pojo.User;

/**
 *
 * @author KHoloud
 */
public class ChangeStatusServiceImpl extends UnicastRemoteObject implements ChangeStatusService{

    public ChangeStatusServiceImpl() throws RemoteException{
    }

    @Override
    public boolean tellFriendsMyStatus(User user, String newStatus) throws RemoteException {
        ArrayList<User> friendsList = user.getFriendsList();
        user.setStatus(newStatus);
        for(int i=0 ; i<friendsList.size(); i++){
            if(friendsList.contains(user)){
                friendsList.set(i, user);
                System.out.println("tell friends my status event fired.");
                return true;
            }
        }
        return false;
    }
    
}
