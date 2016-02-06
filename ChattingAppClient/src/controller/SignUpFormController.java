package controller;

import controller.Controller;
import controller.Validation;
import static controller.Validation.nameValidation;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    private TextField country;
    @FXML
    private TextField city;
    @FXML
    private Label validateFirestName;
    @FXML
    private Label validateLastName;
    @FXML
    private Label validateEmail;
    @FXML
    private Label validateGender;
    @FXML
    private Label validatePassword;
    @FXML
    private Label validateCountry;
    @FXML
    private Label validateCity;
    @FXML
    private Label validateQuestion;
    @FXML
    private Label validateAnswer;

    //reference to the user 
    User user;
    Controller controller;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = new Controller();
//        country.getItems().addAll("Egypt", "korea");
        gender.getItems().addAll("Female", "Male");
//        city.getItems().addAll("Benha", "Cairo", "Alex");
    }

    @FXML
    public void onSubmit() {

        if (Validation.nameValidation(firstName.getText())) {
            if (Validation.nameValidation(lastName.getText())) {
                if (Validation.eMailValidation(eMail.getText())) {
                    if (Validation.passwordValidation(password.getText())) {

                        if (nameValidation(country.getText())) {

                            if (nameValidation(city.getText())) {

                                if (nameValidation(question.getText())) {

                                    if (nameValidation(answer.getText())) {

                                        user = new User(eMail.getText(), firstName.getText(), lastName.getText(), password.getText(), country.getText(), city.getText(), question.getText(), answer.getText(), "Available", gender.getValue(), true);
                                        controller.sendUserToServer(user);

                                    } else {
                                        validateAnswer.setText("You must answer the question.");
                                    }
                                } else {
                                    validateQuestion.setText("You must enter question.");
                                }
                            } else {
                                validateCity.setText("You must enter your city.");
                            }
                        } else {
                            validateCountry.setText("You must enter your country.");
                        }
                    } else {
                        validatePassword.setText("password should contain capital letter, small letter, special character, numbers and at least 8 digits");
                    }
                } else {
                    validateEmail.setText("E-Mail is invalid");
                }
            } else {
                validateLastName.setText("Last name is invalid");
            }
        } else {
            //System.out.println("first name is incorrect");
            validateFirestName.setText("First name is invalid");
        }

    }

    public void handleSignInHyperLink(ActionEvent event) {
        try {
            Parent homePageParent = FXMLLoader.load(getClass().getResource("/view/SignInForm.fxml"));
            Scene homePageScene = new Scene(homePageParent);
            Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.show();

        } catch (IOException ex) {
            Logger.getLogger(SignInFormController.class
                    .getName()).log(Level.SEVERE, null, ex);
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
