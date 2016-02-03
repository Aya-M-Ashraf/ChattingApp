package view;

import controller.Controller;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.pojo.User;

public class ServerFormController implements Initializable {

    @FXML
    Button startServiceButton;
    @FXML
    Button stopServiceButton;
    @FXML
    PieChart pieChart;
    @FXML
    private TextArea adTextArea;
    @FXML
    private Button sendAdButton;

    //sign up tab
    @FXML
    private Button imgChooser;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField eMail;
    @FXML
    private TextField question;
    @FXML
    private TextField answer;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private TextField country;
    @FXML
    private TextField city;

    //table view columns
    @FXML
    private TableView userTableView;

    @FXML
    private TableColumn fnameCol;
    @FXML
    private TableColumn lnameCol;
    @FXML
    private TableColumn countryCol;
    @FXML
    private TableColumn cityCol;
    @FXML
    private TableColumn onlineCol;
    @FXML
    private TableColumn statusCol;
    @FXML
    private TableColumn genderCol;
    //table view observable list
    private ObservableList<User> usersList = FXCollections.observableArrayList();

    User user;

    Registry registry;
    Controller controller = new Controller();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gender.getItems().addAll("Female", "Male");
        stopServiceButton.setDisable(true);
        try {
            registry = LocateRegistry.createRegistry(5000);
        } catch (RemoteException ex) {
            System.out.println("can't create registry");
            ex.printStackTrace();
        }
        updatePieChart();

        ArrayList<User> AllUsers = new ArrayList<User>();

        AllUsers = controller.getAllUserInfo();

        usersList.addAll(AllUsers);

        fnameCol = new TableColumn("fname");
        fnameCol.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));

        lnameCol = new TableColumn("lname");
        lnameCol.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));

        countryCol = new TableColumn("country");
        countryCol.setCellValueFactory(new PropertyValueFactory<User, String>("country"));

        cityCol = new TableColumn("city");
        cityCol.setCellValueFactory(new PropertyValueFactory<User, String>("city"));

        onlineCol = new TableColumn("online");
        onlineCol.setCellValueFactory(new PropertyValueFactory<User, String>("isOnline"));

        statusCol = new TableColumn("status");
        statusCol.setCellValueFactory(new PropertyValueFactory<User, String>("status"));

        genderCol = new TableColumn("gender");
        genderCol.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));

        userTableView.getColumns().setAll(fnameCol, lnameCol, countryCol, cityCol, onlineCol, statusCol, genderCol);

        userTableView.setItems(usersList);
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
    public void updatePieChart() {

        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Online Users", controller.getOnlineUsersCount()),
                        new PieChart.Data("Offline Users", controller.getOfflineUsersCount()));

        pieChart.setData(pieChartData);
    }

    @FXML
    public void onSubmit() {
        if (Validation.nameValidation(firstName.getText())) {
            if (Validation.nameValidation(lastName.getText())) {
                if (Validation.eMailValidation(eMail.getText())) {
                    if (Validation.passwordValidation(password.getText())) {
                        user = new User(eMail.getText(), firstName.getText(), lastName.getText(), password.getText(), country.getText(), city.getText(), question.getText(), answer.getText(), "Available", gender.getValue(), true);
                        if (!controller.searchForUserByEMail(user.getEmail())) {
                            controller.insertUser(user);
                        };
                    } else {
                        //System.out.println("password is invalid");
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("WRARING");
                        alert.setHeaderText(null);
                        alert.setContentText("password should contain capital letter , small letter , special character , numbers and at least 8 digits");
                        alert.showAndWait();
                    }
                } else {
                    //System.out.println("e_mail is invalid");
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("WRARING");
                    alert.setHeaderText(null);
                    alert.setContentText("E-Mail is invalid");
                    alert.showAndWait();
                }
            } else {
                //System.out.println("last name is false");
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("WRARING");
                alert.setHeaderText(null);
                alert.setContentText("Last name is invalid");
                alert.showAndWait();
            }
        } else {
            //System.out.println("first name is incorrect");
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("WRARING");
            alert.setHeaderText(null);
            alert.setContentText("First name is invalid");
            alert.showAndWait();
        }
    }

    public void handleSendAd() {
        controller.sendAddToOnlineUsers(adTextArea.getText());
    }
}
