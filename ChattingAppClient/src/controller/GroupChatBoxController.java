package controller;

import controller.Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Aya M. Ashraf
 */
public class GroupChatBoxController implements Initializable {

    @FXML
    private ListView friendsListView;
    @FXML
    private TextField chatField;
    @FXML
    private TextArea chatArea;
    @FXML
    private Label testLabel;
                      
    Controller controller;
    int ID;
    ArrayList<String> arrayList;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
     public void setData(int ID, ArrayList<String> arrayList) {
        this.ID = ID;
        this.arrayList = arrayList;
        ObservableList<String> observableList = FXCollections.observableList(arrayList);
        friendsListView.setItems(observableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
    }

    public void passController(Controller controller) {
        this.controller = controller;
        testLabel.setText(controller.getEmail());
    }

    @FXML
    public void handleSendButton() {
        String message = chatField.getText().trim();
        if (!message.equals("")) {
            controller.sendMsgToGroupChat(this.ID,message,controller.getEmail());
            chatField.setText("");
        }
    }
    
    @FXML
    public void handleAddFriendButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FriendsGroupAdder.fxml"));
            Parent homePageParent = loader.load();
            FriendsGroupAdderController friendsGroupAdderController = loader.getController();
            friendsGroupAdderController.passController(controller,ID);
            friendsGroupAdderController.passFriends(arrayList);
            Scene homePageScene = new Scene(homePageParent);
            Stage homeStage = new Stage();
            homeStage.setScene(homePageScene);
            homeStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }    
    }

    public void recieveMsg(String msg, String sender) {
        chatArea.appendText(sender + ": " +msg);
        chatArea.appendText("\n");
    }

    public void updateList(ArrayList<String> friends) {
        this.arrayList = friends;
        ObservableList<String> observableList = FXCollections.observableList(friends);
        friendsListView.setItems(observableList);
    }
}
