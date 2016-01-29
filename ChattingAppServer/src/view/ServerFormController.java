package view;

import controller.Controller;
import interfaces.ClientServices;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;


public class ServerFormController implements Initializable {

   @FXML 
   Button startServiceButton;
   @FXML
   Button stopServiceButton;
   @FXML
   PieChart pieChart;

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
       updatePieChart();
  
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
    @FXML
    public void updatePieChart(){
       
           ObservableList<PieChart.Data> pieChartData =
                   FXCollections.observableArrayList(
                           new PieChart.Data("Online Users", controller.getOnlineUsersCount()),
                           new PieChart.Data("Offline Users", controller.getOfflineUsersCount()));
           
           pieChart.setData(pieChartData);
        try {   
           for( ClientServices client : controller.getUsersInterfacesVector())
               client.printEmail();
       } catch (RemoteException ex) {
           Logger.getLogger(ServerFormController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
}
