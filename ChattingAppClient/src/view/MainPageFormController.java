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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author KHoloud
 */
public class MainPageFormController implements Initializable {

    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private Label nameLabel;
    
    String userStatus;
    Controller controller = new Controller();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        statusComboBox.getItems().addAll("Available", "Busy", "Away");
        statusComboBox.setValue("Available");
    }

    @FXML
    public void handleAddFriendButton(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddFriendForm.fxml"));
            Parent homePageParent = loader.load();
            AddFriendFormController controller = loader.getController();
        
            controller.initData(nameLabel.getText());

            Scene homePageScene = new Scene(homePageParent);
            Stage homeStage = new Stage();
            homeStage.setScene(homePageScene);
            homeStage.show();
        } catch (IOException ex) {
            Logger.getLogger(SignInFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void onSignOut(MouseEvent event) {
        try {
            System.out.println("sign out button pressed");
            Parent signInPage = FXMLLoader.load(getClass().getResource("SignInForm.fxml"));
            Scene signInPageScene = new Scene(signInPage);
            Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            homeStage.setScene(signInPageScene);
            homeStage.show();        
    
        } catch (IOException ex) {
            System.out.println("signout putton pressed");
            Logger.getLogger(SignInFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(nameLabel.getText());
        controller.signOutOneUser(nameLabel.getText());
    }
    
    public void statusChangeHandeling(){
        userStatus = statusComboBox.getSelectionModel().getSelectedItem();
        System.out.println(userStatus);
        System.out.println("MAIL "+nameLabel.getText());
        controller.updateUserStatus(nameLabel.getText(),userStatus);
    }
    
    
}
