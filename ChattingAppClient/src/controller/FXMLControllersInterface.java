/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.scene.control.Label;
import model.pojo.User;

/**
 *
 * @author KHoloud
 */
public interface FXMLControllersInterface {
    
    public void displayAdd(String adMessege);
    public void updateList(User user);
    public void updateListView();
    public Label getNameLabel();
    public void passUser(User user);

}
