package controller;

import controller.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.pojo.User;

/**
 *
 * @author Aya M. Ashraf
 */
public class ContactCardController implements Initializable {

    @FXML
    private Label emailLabel, checkOnLineLabel, statusLabel;
    @FXML
    private ImageView imageView;

    Controller controller;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
    }

    void passUser(User item, Controller controller) {
        emailLabel.setText(item.getEmail());
        if(item.isIsOnline()){
            checkOnLineLabel.setText("OnLine");
            statusLabel.setText(item.getStatus());
        }else{
            checkOnLineLabel.setText("Offline");
        }
        
        this.controller = controller;
        if ("Available".equals(item.getStatus()) && item.isIsOnline() == true) {
            imageView.setImage(new Image(getClass().getResource("/view/images/Online_status.png").toExternalForm()));
        } else if ("Away".equals(item.getStatus()) && item.isIsOnline() == true) {
            imageView.setImage(new Image(getClass().getResource("/view/images/Away_status.png").toExternalForm()));
        } else if ("Busy".equals(item.getStatus()) && item.isIsOnline() == true) {
            imageView.setImage(new Image(getClass().getResource("/view/images/Busy_status.png").toExternalForm()));
        } else if (item.isIsOnline() == false) {
            imageView.setImage(new Image(getClass().getResource("/view/images/Offline_status.png").toExternalForm()));
        } else {
            imageView.setImage(new Image(getClass().getResource("/view/images/default.png").toExternalForm()));
        }
    }
}
