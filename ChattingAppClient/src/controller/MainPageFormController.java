package controller;

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
import javafx.scene.control.Alert;
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
    @FXML
    private Label firstNameLabel;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddFriendForm.fxml"));
            Parent homePageParent = loader.load();
            AddFriendFormController AddFriendController = loader.getController();
            AddFriendController.initData(controller.getEmail(),controller);
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
            Parent signInPage = FXMLLoader.load(getClass().getResource("/view/SignInForm.fxml"));
            Scene signInPageScene = new Scene(signInPage);
            Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            homeStage.setScene(signInPageScene);
            homeStage.show();

        } catch (IOException ex) {
            Logger.getLogger(SignInFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller.signOutOneUser(user.getEmail());
        controller.unregisterMe();
    }

    public void statusChangeHandeling() {
        userStatus = statusComboBox.getSelectionModel().getSelectedItem();
        controller.updateUserStatus(nameLabel.getText(), userStatus);
    }

    @Override
    public void passUser(User user) {
        this.user = user;
        nameLabel.setText(user.getEmail());
        statusComboBox.setValue(user.getStatus());
        imageView.setImage(new Image(getClass().getResource("/view/images/default.png").toExternalForm()));
        controller.getOfflineFriendRequest(controller.getEmail());
        firstNameLabel.setText(user.getFirstName());
        updateListView();
    }

    @Override
    public void updateList(User user) {
        this.user = user;
        for (User friend : user.getFriendsList()) {
            System.out.println(friend.getEmail());
        }

        ObservableList<User> observableList = FXCollections.observableList(user.getFriendsList());
        listView.setItems(observableList);
        imageView.setImage(new Image(getClass().getResource("/view/images/default.png").toExternalForm()));
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
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ContactCard.fxml"));
                                Parent root = loader.load();
                                ContactCardController mainPageController = loader.getController();
                                mainPageController.passUser(item, controller);
                                setGraphic(root);
                            } catch (IOException ex) {
                                Logger.getLogger(controller.MainPageFormController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                };
            }
        });
    }

    @Override
    public void updateListView() {
        ObservableList<User> observableList = FXCollections.observableList(user.getFriendsList());
        listView.setItems(observableList);
        imageView.setImage(new Image(getClass().getResource("/view/images/default.png").toExternalForm()));
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
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ContactCard.fxml"));
                                Parent root = loader.load();
                                ContactCardController mainPageController = loader.getController();
                                mainPageController.passUser(item, controller);
                                setGraphic(root);
                            } catch (IOException ex) {
                                Logger.getLogger(controller.MainPageFormController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                };
            }
        });
    }

    @Override
    public void displayAdd(String adMessege) {
        adLabel.setText(adMessege);
    }

    public void handleListClicks(MouseEvent event) {
        User user = (User) listView.getSelectionModel().getSelectedItem();
        if (user != null && user.isIsOnline()) {
            try {
                if (controller.getCurrentChatControllersMap().containsKey(user.getEmail())) { //getting focus to it
                    controller.getCurrentChatControllersMap().get(user.getEmail()).getLabel().getScene().getWindow().requestFocus();
                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChatBox.fxml"));
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
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WRARING");
            alert.setHeaderText(null);
            alert.setContentText("Your friend is offline");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleGroupButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FriendsChooser.fxml"));
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
    
    @Override
    public Label getNameLabel() {
        return nameLabel;
    }

}
