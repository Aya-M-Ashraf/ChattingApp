package view;

import controller.Controller;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.pojo.User;

/**
 * FXML Controller class
 *
 * @author Aya M. Ashraf
 */
public class FriendsGroupAdderController implements Initializable {
    
    Controller controller;
    ArrayList<User> friendsList = new ArrayList<>();
    int chatRoomID;
    
    @FXML
    private ListView listView;
    @FXML
    private Button addButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void passController(Controller controller, int ID) {
        this.controller = controller;
        friendsList = this.controller.getOnlineFriendList();
        chatRoomID = ID;
    }
    public void passFriends(ArrayList<String> arrayList) {
        ArrayList<String> friendsEmails = new ArrayList<>();
        for (User friend : friendsList) {
            if (!arrayList.contains(friend.getEmail())) {
                friendsEmails.add(friend.getEmail());
            }
        }
        ObservableList<String> observableList = FXCollections.observableList(friendsEmails);
        listView.setItems(observableList);
    }  
    
    public void handleAddButton(){
        ObservableList<String> observableList = listView.getSelectionModel().getSelectedItems();
        Object [] array = new String [observableList.size()];
        array = observableList.toArray();
        ArrayList<String> list = new ArrayList<>();
        for (Object obj:array)
        {
          list.add((String)obj);
        }
        controller.addFriendsToChatRoom(list,chatRoomID);
    }
}
