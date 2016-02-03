package view;

import controller.Controller;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ServerFormController implements Initializable {

    @FXML
    Button startServiceButton;
    @FXML
    Button stopServiceButton;
    @FXML
    PieChart pieChart;
    @FXML
    PieChart pieChart2;
    @FXML
    private TextArea adTextArea;
    @FXML
    private Button sendAdButton;

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
        updateOnlinePieChart();
        updatePieChart();
    }

    @FXML
    void handleStartButton() {
        controller.startServer(registry);
        startServiceButton.setDisable(true);
        stopServiceButton.setDisable(false);
    }
    @FXML
    void handleStopButton() {
        controller.stopServer();
        startServiceButton.setDisable(false);
        stopServiceButton.setDisable(true);
    }
    @FXML
    public void updateOnlinePieChart() {
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Online Users", controller.getOnlineUsersCount()),
                        new PieChart.Data("Offline Users", controller.getOfflineUsersCount()));
        pieChart.setData(pieChartData);
    }
    
    @FXML
    public void updateCharts() {
        updateOnlinePieChart();
        updatePieChart();
    }
    
    @FXML
     public void updatePieChart() {
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        
                        new PieChart.Data("Female Users", controller.getFemaleUsersCount()),
                        new PieChart.Data("Male Users", controller.getMaleUsersCount()));
        pieChart2.setData(pieChartData);
    }
    
    public void handleSendAd(){
        controller.sendAddToOnlineUsers(adTextArea.getText());
    }
}
