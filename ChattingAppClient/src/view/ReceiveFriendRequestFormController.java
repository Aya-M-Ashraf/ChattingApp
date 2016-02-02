/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author KHoloud
 */
public class ReceiveFriendRequestFormController implements Initializable {

    @FXML
    private Label requestLabel;
    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label introLabel;
    @FXML
    private Label endLabel;

    Controller controller;
    String myEmail;
    //MainPageFormController mainPageFormController = new MainPageFormController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initData(String request, String myEmail, Controller controller) {
        this.controller = controller;
        requestLabel.setText(request);
        this.myEmail = myEmail;
    }

    public void confirmFriendRequestHandel() {
        System.out.println(requestLabel.getText());
        System.out.println(myEmail);
        controller.confirmReceivedFriendRequest(requestLabel.getText(), myEmail);
        confirmButton.setDisable(true);
        cancelButton.setDisable(true);
        introLabel.setText(null);
        endLabel.setText("Your request has been confirmed.");
    }

    public void cancelFriendRequestHandel() {
        System.out.println(requestLabel.getText());
        System.out.println(myEmail);
        controller.deleteReceivedFriendRequest(requestLabel.getText(), myEmail);
        cancelButton.setDisable(true);
        confirmButton.setDisable(true);
        introLabel.setText("Request has been deleted.");
        endLabel.setText(null);
        controller.updateMyFriendsList(myEmail);
        controller.updateMyFriendsList(requestLabel.getText());
    }

}
