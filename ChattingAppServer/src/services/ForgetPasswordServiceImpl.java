/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import controller.Controller;
import interfaces.ForgetPasswordService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import model.pojo.User;

/**
 *
 * @author KHoloud
 */
public class ForgetPasswordServiceImpl extends UnicastRemoteObject implements ForgetPasswordService{

    Controller controller;
    
    public ForgetPasswordServiceImpl(Controller controller) throws RemoteException{
        this.controller = controller;
    }
    
    @Override
    public User selectUserWhereEmail(String email) throws RemoteException {
        System.out.println("enter service");
        return controller.selectUserWhereEmail(email);
    }
    
}
