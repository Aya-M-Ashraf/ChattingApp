package view;

import controller.Controller;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class ServerFormController implements Initializable {

   @FXML 
   Button startServiceButton;
   @FXML
   Button stopServiceButton;
   

   Registry registry;
   Controller controller = new Controller();
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stopServiceButton.setDisable(true);
       try {
           registry = LocateRegistry.createRegistry(5000);
       } catch (RemoteException ex) {
           System.out.println("can't create registry");
           ex.printStackTrace();
       }

    }    
    @FXML
    void handleStartButton(){
        controller.startServer(registry);
        startServiceButton.setDisable(true);
        stopServiceButton.setDisable(false);
    }
    @FXML
    void handleStopButton(){
        controller.stopServer();
        startServiceButton.setDisable(false);
        stopServiceButton.setDisable(true);
    }
}
