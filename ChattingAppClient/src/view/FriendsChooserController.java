package view;

import controller.Controller;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import model.pojo.User;

/**
 * FXML Controller class
 *
 * @author Ahmed Ashraf
 */
public class FriendsChooserController implements Initializable {

    @FXML
    private ListView listView;
   
    User user;
    private Controller controller;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }   
    
    public void passController(Controller controller) {
        this.controller = controller;
    }

    public void passUser(User user) {
        this.user=user;
        ArrayList<String> friendsEmails = new ArrayList<>();
        for(User friend : user.getOnlineFriendsList())
        {
            friendsEmails.add(friend.getEmail());
        }
        ObservableList<String> observableList = FXCollections.observableList(friendsEmails);
        listView.setItems(observableList);
    }
    
    public void handleCreateRoomButton(MouseEvent event)
    {
        ObservableList<String> observableList = listView.getSelectionModel().getSelectedItems();
        Object [] array = new String [observableList.size()];
        array = observableList.toArray();
        ArrayList<String> list = new ArrayList<>();
        for (Object obj:array)
        {
          list.add((String)obj);
        }
        list.add(user.getEmail());
        controller.createGroupChat(list);  
        listView.getScene().getWindow().hide();
    }
    
}
