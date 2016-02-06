/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.pojo.User;

/**
 * FXML Controller class
 *
 * @author KHoloud
 */
public class ForgetPasswordController implements Initializable {

    @FXML
    private Label questionLabel;
    @FXML
    private TextField answerTextField;
    @FXML
    private Label checkLabel;
    
    User user;
    Controller controller;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
    public void init(Controller controller, User user){
        this.controller = controller;
        this.user = user;
        questionLabel.setText(user.getSecuirtyQuestion());
    }
    
    public void handleForgetPassword(){
        System.out.println("Correct answer "+user.getSecurityAnswer());
        if(answerTextField.getText().equals(user.getSecurityAnswer())){
            checkLabel.setText("Your password: "+user.getPassword());
        }else{
            checkLabel.setText("Your answer incorrect.");
        }
    }
    
}
