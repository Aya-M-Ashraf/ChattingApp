package view;

import controller.Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.pojo.User;

/**
 * FXML Controller class
 *
 * @author KHoloud
 */
public class MainPageFormController implements Initializable, FXMLControllersInterface {

    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private Label nameLabel;
    @FXML
    private ListView listView;
    @FXML
    private Label adLabel;
    @FXML
    private ImageView imageView;

    String userStatus;
    Controller controller;

    User user = new User();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        statusComboBox.getItems().addAll("Available", "Busy", "Away");
    }

    void passController(Controller controller) {
        this.controller = controller;
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
            user.setIsOnline(false);
            System.out.println("sign out button pressed");
            Parent signInPage = FXMLLoader.load(getClass().getResource("SignInForm.fxml"));
            Scene signInPageScene = new Scene(signInPage);
            Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            homeStage.setScene(signInPageScene);
            homeStage.show();

        } catch (IOException ex) {
            System.out.println("signout button pressed");
            Logger.getLogger(SignInFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(nameLabel.getText());
        controller.signOutOneUser(user.getEmail());
        controller.unregisterMe();
    }

    public void statusChangeHandeling() {
        userStatus = statusComboBox.getSelectionModel().getSelectedItem();
        System.out.println(userStatus);
        System.out.println("MAIL " + nameLabel.getText());
        //Controller controller = new Controller();
        controller.updateUserStatus(nameLabel.getText(), userStatus);
    }

    public void passUser(User user) {
        this.user = user;
        nameLabel.setText(user.getEmail());
        statusComboBox.setValue(user.getStatus());
        imageView.setImage(new Image(getClass().getResource("images/default.png").toExternalForm()));
        controller.getOfflineFriendRequest(controller.getEmail());
        updateList(user);
    }

    public void updateList(User user) {
        this.user = user;
        System.out.println("inside updateList() userFriendList is : ");
        for(User friend : user.getFriendsList())
            System.out.println(friend.getEmail());
        
        ObservableList<User> observableList = FXCollections.observableList(user.getFriendsList());
        listView.setItems(observableList);
        imageView.setImage(new Image(getClass().getResource("images/default.png").toExternalForm()));
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
                                //FXMLLoader loader = new FXMLLoader(getClass().getResource("ContactCard.fxml"));
                                //Parent root = loader.load();
                                //ContactCardController mainPageController = loader.getController();
                                //mainPageController.passUser(item, controller);
                                Parent root = FXMLLoader.load(getClass().getResource("ContactCard.fxml"));

                                Label emailLabel = (Label) root.lookup("#emailLabel");
                                emailLabel.setText(item.getEmail());

                                Label genderLabel = (Label) root.lookup("#genderLabel");
                                genderLabel.setText(item.getGender());

                                Label statusLabel = (Label) root.lookup("#statusLabel");
                                statusLabel.setText(item.getStatus());

                                ImageView imageView = (ImageView) root.lookup("#imageView");
                                if ("Available".equals(item.getStatus()) && item.isIsOnline() == true) {
                                    imageView.setImage(new Image(getClass().getResource("images/Online_status.png").toExternalForm()));
                                } else if ("Away".equals(item.getStatus()) && item.isIsOnline() == true) {
                                    imageView.setImage(new Image(getClass().getResource("images/Away_status.png").toExternalForm()));
                                } else if ("Busy".equals(item.getStatus()) && item.isIsOnline() == true) {
                                    imageView.setImage(new Image(getClass().getResource("images/Busy_status.png").toExternalForm()));
                                } else if (item.isIsOnline() == false) {
                                    imageView.setImage(new Image(getClass().getResource("images/Offline_status.png").toExternalForm()));
                                } else {
                                    imageView.setImage(new Image(getClass().getResource("images/default.png").toExternalForm()));
                                }

                                setGraphic(root);
                            } catch (IOException ex) {
                                Logger.getLogger(view.MainPageFormController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                };
            }
        });
    }

    public void displayAdd(String adMessege) {
        adLabel.setText(adMessege);
    }

    public void handleListClicks(MouseEvent event) {
        User user = (User) listView.getSelectionModel().getSelectedItem();
        if (user != null) {
            try {
                if (controller.getCurrentChatControllersMap().containsKey(user.getEmail())) { //getting focus to it
                    controller.getCurrentChatControllersMap().get(user.getEmail()).getLabel().getScene().getWindow().requestFocus();
                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatBox.fxml"));
                    Parent homePageParent = loader.load();
                    ChatBoxController chatBoxController = loader.getController();
                    chatBoxController.passUser(user);
                    chatBoxController.passController(controller);
                    controller.getCurrentChatControllersMap().put(user.getEmail(), chatBoxController);

                    Scene homePageScene = new Scene(homePageParent);
                    Stage homeStage = new Stage();
                    homeStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            controller.getCurrentChatControllersMap().remove(user.getEmail(), chatBoxController);
                        }
                    });
                    homeStage.setScene(homePageScene);
                    homeStage.show();
                }
            } catch (IOException ex) {
                Logger.getLogger(MainPageFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    public void handleGroupButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FriendsChooser.fxml"));
            Parent homePageParent = loader.load();
            FriendsChooserController friendsChooserController = loader.getController();
            friendsChooserController.passUser(user);
            friendsChooserController.passController(controller);
            Scene homePageScene = new Scene(homePageParent);
            Stage homeStage = new Stage();
            homeStage.setScene(homePageScene);
            homeStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainPageFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
