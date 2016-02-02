package view;

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
    private Label emailLabel, genderLabel, statusLabel;
    private ImageView imageView;

    Controller controller;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void passUser(User item, Controller controller) {
        emailLabel.setText(item.getEmail());
        genderLabel.setText(item.getGender());
        statusLabel.setText(item.getStatus());
        this.controller = controller;
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
    }
}
