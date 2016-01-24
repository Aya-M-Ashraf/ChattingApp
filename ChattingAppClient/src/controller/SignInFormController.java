package controller;

import controller.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.pojo.User;

/**
 * FXML Controller class
 *
 * @author KHoloud
 */
public class SignInFormController implements Initializable {

    @FXML
    private Button signInButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    
    User user = new User();
    
    Controller controller = new Controller();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    public void handleSignInButton(){
        String email = null,password = null;
        if(emailTextField.getText() != null)
        {
            email = emailTextField.getText();
        }else
        {
            //do anything shreer
        }
        
        if(passwordTextField.getText() != null)
        {
            password = passwordTextField.getText();
        }else
        {
            //do anything shreer
        }
        
        if (email!=null && password!=null){
            user = controller.signIn(email, password);
            //if not null give it to another form .. to actually start the app, if null error occured
        }
    }
    
}
