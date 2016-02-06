/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.pojo.User;

/**
 * FXML Controller class
 *
 * @author KHoloud
 */
public class EnterEmailFormController implements Initializable {

    @FXML
    private TextField emailTextField;
    @FXML
    private Label validationLabel;

    Controller controller;
    User user;

    public EnterEmailFormController() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.user = new User();
        controller = new Controller();
    }

    public void checkEmail(MouseEvent event) {
        System.out.println("Enter check mail");
        if (emailTextField.getText() != null) {
            user = controller.forgetPasswordHandle(emailTextField.getText());
            System.out.println("textfild not null");
        }
        if (user != null) {
            try {
                System.out.println("reterned user not null");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ForgetPassword.fxml"));
                Parent forgetPasswordPageParent = loader.load();
                ForgetPasswordController forgetPasswordPageController = loader.getController();
                forgetPasswordPageController.init(controller, user);
                System.out.println("userEmail " + user.getEmail());
//                FXMLControllersInterface forgetPasswordController = loader.getController();
//                controller.getCurrentControllers().put("forgetPasswordController",forgetPasswordController);

                Scene forgetPasswordPageScene = new Scene(forgetPasswordPageParent);
                Stage forgetPasswordStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                forgetPasswordStage.setScene(forgetPasswordPageScene);
                forgetPasswordStage.show();
            } catch (IOException ex) {
                Logger.getLogger(EnterEmailFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            validationLabel.setText("Email not found.");
        }
    }
}
