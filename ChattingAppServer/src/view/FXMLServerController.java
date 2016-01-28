package view;

import controller.Controller;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import model.pojo.User;

/**
 * FXML Controller class
 *
 * @author Amr
 */
public class FXMLServerController implements Initializable {

    Controller controller = new Controller();

    @FXML
    private TextArea usersArea;

    @FXML
    private ComboBox<String> firstChooser;
    @FXML
    private ComboBox<String> secondChooser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        firstChooser.getItems().addAll("Online", "OffLine", "Available", "Busy", "three");
        secondChooser.getItems().addAll("Online", "OffLine", "Available", "Busy", "three");
    }

    @FXML
    private void onUserReportClicked() {

        ArrayList<User> AllUsers = new ArrayList<User>();

        AllUsers = controller.getAllUsers();

        for (User user : AllUsers) {
            System.out.println(user.getFirstName());
            usersArea.appendText(user.getFirstName() + "\n");
        }
    }
@FXML
    private void onUsergetPercentClicked() {

        String firstValue ="u.Online=1";
        
        String secondValue ="u.status=1";
        
      
                 
        switch (firstChooser.getValue())
            
        {
            case "Online": 
                firstValue ="u.Online=1";
                break;
                
            case "OffLine": 
                firstValue ="u.Online=0";
                break;
            case "Available": 
                firstValue ="u.Status=\"Available\"";
                break;
            case "Busy": 
                firstValue ="u.Status=\"Busy\"";
                break;
            case "three": 
                firstValue ="u.Status=\"three\"";
                break;
            
          
        }
        
               switch (secondChooser.getValue())
            
        {
            case "Online": 
                secondValue ="u.Online=1";
                break;
                
            case "OffLine": 
                secondValue ="u.Online=0";
                break;
            case "Available": 
                secondValue ="u.Status=\"Available\"";
                break;
            case "Busy": 
                secondValue ="u.Status=\"Busy\"";
                break;
            case "three": 
                secondValue ="u.Status=\"three\"";
                break;
            
          
        }
        
          String Query = "Select *  from user u where "+firstValue+" and "+secondValue;
          
        ArrayList<User> AllUsers = new ArrayList<User>();
        AllUsers = controller.selectAllUsersWhere(Query);
   

            

            for (User user : AllUsers) {
                System.out.println(user.getFirstName());
                usersArea.appendText(user.getFirstName() + "\n");
            }
        }
       
    }
