/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class YesNoBoxController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button YesButton;

    @FXML
    private Button NoButton;

    @FXML

    private Label messageLabel;

    private int clickedButton = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        messageLabel.setText("Are you sure that you want to receive The file ?");
    }

    @FXML
    public void onYes() {

        clickedButton = 1;

    }

    @FXML
    public void onNo() {

        clickedButton = -1;
        
     
    }

    public int getClickedButton() {
        return clickedButton;
    }

    public Button getYesButton() {
        return YesButton;
    }

    public Button getNoButton() {
        return NoButton;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }
    
    

}
