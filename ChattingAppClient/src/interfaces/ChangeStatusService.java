/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.pojo.User;

/**
 *
 * @author KHoloud
 */
public interface ChangeStatusService extends Remote{
    public void tellFriendsMyStatus(User user,String status) throws RemoteException;
}
