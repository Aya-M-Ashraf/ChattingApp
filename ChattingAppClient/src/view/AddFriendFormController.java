package view;

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
 * @author Aya M. Ashraf
 */
public class AddFriendFormController implements Initializable , FXMLControllersInterface {

    Controller controller;
    @FXML 
    private TextField emailTextField;
    @FXML
    private Button addFriendButton;
    
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
        addFriendButton.setDisable(true);
        addFriendButton.getScene().getWindow().hide();
    }   

    @Override
    public void displayAdd(String adMessege) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateList(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
