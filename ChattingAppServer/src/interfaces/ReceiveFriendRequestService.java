/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author KHoloud
 */
public interface ReceiveFriendRequestService extends Remote{
    public ArrayList<String> getFriendRequests(String email) throws RemoteException;
    public boolean confirmFriendReuest(String friendEmail, String email) throws RemoteException;
    public boolean cancelFriendRequest(String friendEmail, String eamil) throws RemoteException;
}
