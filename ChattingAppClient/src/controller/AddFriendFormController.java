package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Aya M. Ashraf
 */
public class AddFriendFormController implements Initializable {

    Controller controller;
    @FXML 
    private TextField emailTextField;
    
    private String userEmail;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = new Controller();
    }
    
    void initData(String text) {
        userEmail = text;
    }
    
    public void handleAddFirendButton(){
        
        String emailToAdd = emailTextField.getText();
        controller.addFriendToUser(userEmail,emailToAdd);
    }   
}
