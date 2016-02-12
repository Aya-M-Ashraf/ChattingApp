package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.pojo.User;


public class AddFriendFormController implements Initializable , FXMLControllersInterface {

    Controller controller;
    @FXML 
    private TextField emailTextField;
    @FXML
    private Button addFriendButton;
    
    private String userEmail;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    void initData(String text,Controller controller) {
        userEmail = text;
        this.controller = controller;
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

    @Override
    public void updateListView() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void passUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Label getNameLabel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
