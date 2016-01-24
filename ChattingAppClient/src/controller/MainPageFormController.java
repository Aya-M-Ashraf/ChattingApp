package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author KHoloud
 */
public class MainPageFormController implements Initializable {

    @FXML
    private ImageView imageView;
    
    @FXML
    private Label nameLabel;
    
    @FXML
    private ComboBox<String> statusComboBox;
    
    @FXML
    private ImageView friendImage;
    
    @FXML
    private Label friendNameLabel;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        statusComboBox.getItems().addAll("Available","Busy","Away");
        statusComboBox.setValue("Available");
    }    
    
}
