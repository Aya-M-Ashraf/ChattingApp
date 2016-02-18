package controller;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.pojo.User;

/**
 * FXML Controller class
 *
 * @author KHoloud
 */
public class SignInFormController implements Initializable, FXMLControllersInterface {

    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Label validatePassword;
//    @FXML
//    private TextField regIP;
    @FXML
    private Label validateEmail;

    User user;
    Controller controller;

    public SignInFormController() {

    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        regIP.setText("127.0.0.1");
        this.user = new User();
        this.controller = new Controller();
    }

    public void handleSignInButton(MouseEvent event) {
        String email = null, password = null;
        if (emailTextField.getText() != null) {
            email = emailTextField.getText();
        } else {
            validateEmail.setText("You must enter your email");
        }

        if (passwordTextField.getText() != null) {
            password = passwordTextField.getText();
        } else {
            validatePassword.setText("You must enter your password.");
        }

        if (email != null && password != null) {

            user = controller.signIn(email, password);
            if (user != null) {
                try {
                    controller.registerMe();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainPageForm.fxml"));
                    Parent homePageParent = loader.load();
                    MainPageFormController mainPageController = loader.getController();
                    mainPageController.passController(controller);
                    mainPageController.passUser(user);
                    FXMLControllersInterface mainPageFormController = loader.getController();
                    controller.getCurrentControllers().put("mainPageFormController", mainPageFormController);
                    Scene homePageScene = new Scene(homePageParent);
                    Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    homeStage.setScene(homePageScene);
                    homeStage.setResizable(false);
                    homeStage.setOnCloseRequest(e -> {
                        user.setIsOnline(false);
                        controller.signOutOneUser(user.getEmail());
                        controller.unregisterMe();
                        Platform.exit();
                        System.exit(0);
                    });
                    homeStage.show();

                } catch (IOException ex) {
                    Logger.getLogger(SignInFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
//                if(user.getEmail() == null){
//                    validateEmail.setText("You must enter your email.");
//                }else{
//                    validatePassword.setText("You must enter your password.");
//                }
                if(user == null){
                    validateEmail.setText("Your email incorrect.");
                } else if(user.getPassword() != password){
                    validatePassword.setText("Your password incorrect.");
                }
            }
        }
    }

    public void handleSignUpLink(MouseEvent event) {
        try {
            Parent homePageParent = FXMLLoader.load(getClass().getResource("/view/SignUpForm.fxml"));
            Scene homePageScene = new Scene(homePageParent);
            Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.show();
            homeStage.setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(SignInFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleForgetPasswordLink(MouseEvent event) {
        try {
            Parent homePageParent = FXMLLoader.load(getClass().getResource("/view/EnterEmailForm.fxml"));
            Scene homePageScene = new Scene(homePageParent);
            Stage homeStage = new Stage();
            homeStage.setScene(homePageScene);
            homeStage.show();
            homeStage.setResizable(false);
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

    @Override
    public void updateListView() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void passUser(User user) {
        this.user = user;
    }

    @Override
    public Label getNameLabel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
