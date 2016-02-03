package view;

import controller.Controller;
import controller.Validation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.pojo.User;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class SignUpFormController implements Initializable, FXMLControllersInterface {

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

    public void handleSignInHyperLink(ActionEvent event) {
        try {
            Parent homePageParent = FXMLLoader.load(getClass().getResource("SignInForm.fxml"));
            Scene homePageScene = new Scene(homePageParent);
            Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.show();
        } catch (IOException ex) {
            Logger.getLogger(SignInFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
