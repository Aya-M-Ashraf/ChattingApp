package view;

import controller.Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
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

    User user = new User();

    Controller controller = new Controller();

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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPageForm.fxml"));
                    Parent homePageParent = loader.load();
                   
                    Label userNameLabel = (Label) homePageParent.lookup("#nameLabel");
                    userNameLabel.setText(user.getEmail());
                    ComboBox<String> userStatusComboBox = (ComboBox<String>) homePageParent.lookup("#statusComboBox");
                    userStatusComboBox.setValue(user.getStatus());
                    ListView listView = (ListView) homePageParent.lookup("#listView");

                    
                    ObservableList<User> observableList = FXCollections.observableList(user.getFriendsList());
                    

                    listView.setItems(observableList);

                    listView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {

                        @Override
                        public ListCell<User> call(ListView<User> userLists) {
                            return new ListCell<User>() {
                                @Override
                                protected void updateItem(User item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item == null || empty) {
                                        setGraphic(null);
                                    } else {
                                        try {
                                            Parent root = FXMLLoader.load(getClass().getResource("ContactCard.fxml"));

                                            Label emailLabel = (Label) root.lookup("#emailLabel");
                                            emailLabel.setText(item.getEmail());

                                            Label genderLabel = (Label) root.lookup("#genderLabel");
                                            genderLabel.setText(item.getGender());

                                            Label statusLabel = (Label) root.lookup("#statusLabel");
                                            statusLabel.setText(item.getStatus());

                                            
                                            setGraphic(root);
                                        } catch (IOException ex) {
                                            Logger.getLogger(view.MainPageFormController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }

                                }
                            };
                        }
                    });

                    Scene homePageScene = new Scene(homePageParent);
                    Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    homeStage.setScene(homePageScene);
                    homeStage.show();

                } catch (IOException ex) {
                    Logger.getLogger(SignInFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //if null error occured
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
