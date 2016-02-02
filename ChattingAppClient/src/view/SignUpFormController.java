/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import controller.Controller;
import controller.Validation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.pojo.User;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class SignUpFormController implements Initializable , FXMLControllersInterface {


    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField eMail;
    @FXML
    private TextField question;
    @FXML
    private TextField answer;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private ComboBox<String> country;
    @FXML
    private ComboBox<String> city;

    //reference to the user 
    User user;
    Controller controller;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        controller = new Controller();

        country.getItems().addAll("Egypt", "korea");
        gender.getItems().addAll("Female", "Male");
        
            city.getItems().addAll("Benha", "Cairo", "Alex");
        
    
    }    
    @FXML
    public void onSubmit() {
        if (Validation.nameValidation(firstName.getText())) {
            if (Validation.nameValidation(lastName.getText())) {
                if (Validation.eMailValidation(eMail.getText())) {
                    if (Validation.passwordValidation(password.getText())) {
                        user = new User(eMail.getText(), firstName.getText(), lastName.getText(), password.getText(), country.getValue(), city.getValue(), question.getText(), answer.getText(), "Available", gender.getValue(), true);
                        controller.sendUserToServer(user);
                    } else {
                        System.out.println("password is invalid");
                    }
                } else {
                    System.out.println("e_mail is invalid");
                }
            } else {
                System.out.println("last name is false");
            }
        } else {
            System.out.println("first name is incorrect");
        }

    }

    @Override
    public void displayAdd(String adMessege) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void updateList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
