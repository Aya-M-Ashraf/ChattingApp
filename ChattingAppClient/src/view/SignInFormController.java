package view;

import controller.Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.pojo.User;

/**
 * FXML Controller class
 *
 * @author KHoloud
 */
public class SignInFormController implements Initializable {

    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;

    User user;
    Controller controller;

    public SignInFormController() {
        this.user = new User();
        this.controller = new Controller();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void handleSignInButton(MouseEvent event) {
        String email = null, password = null;
        if (emailTextField.getText() != null) {
            email = emailTextField.getText();
        } else {
            //do anything shreer email field is empty 
        }

        if (passwordTextField.getText() != null) {
            password = passwordTextField.getText();
        } else {
            //do anything shreer
        }

        if (email != null && password != null) {
            
            user = controller.signIn(email, password);
            if (user != null) {                         
                try {
                    
                    controller.registerMe();
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPageForm.fxml"));
                    Parent homePageParent = loader.load();
                    MainPageFormController controller = loader.getController();
                    controller.passUser(user);
                                        
                    Scene homePageScene = new Scene(homePageParent);
                    Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    homeStage.setScene(homePageScene);
                    homeStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(SignInFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void handleSignUpLink(MouseEvent event) {
        try {
            Parent homePageParent = FXMLLoader.load(getClass().getResource("SignUpForm.fxml"));
            Scene homePageScene = new Scene(homePageParent);
            Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.show();
        } catch (IOException ex) {
            Logger.getLogger(SignInFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
