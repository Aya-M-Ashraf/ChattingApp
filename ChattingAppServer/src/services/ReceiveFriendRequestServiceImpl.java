/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import controller.Controller;
import interfaces.ReceiveFriendRequestService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author KHoloud
 */
public class ReceiveFriendRequestServiceImpl extends UnicastRemoteObject implements ReceiveFriendRequestService {

    Controller controller;

    public ReceiveFriendRequestServiceImpl(Controller controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public ArrayList<String> getFriendRequests(String email) throws RemoteException {
        ArrayList<String> frindsList = new ArrayList<>();
        frindsList = controller.getMyFriendsReuests(email);
        return frindsList;
    }

    @Override
    public boolean confirmFriendReuest(String friendEmail, String email) throws RemoteException {
        return controller.addFriendToMyList(email, friendEmail);
    }

    @Override
    public boolean cancelFriendRequest(String friendEmail, String email) throws RemoteException {
        return controller.deleteFriendRequest(friendEmail, email);
    }
}
