package controller;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.pojo.User;
import static controller.Validation.nameValidation;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;

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
    @FXML
    private TableColumn emailCol;
    @FXML
    private Label validatFirestName;
    @FXML
    private Label validatLastName;
    @FXML
    private Label validateEmail;
    @FXML
    private Label validateGender;
    @FXML
    private Label validatePassword;
    @FXML
    private Label validateCountry;
    @FXML
    private Label validateCity;
    @FXML
    private Label validateQuestion;
    @FXML
    private Label validateAnswer;

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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WRARING");
            alert.setHeaderText(null);
            alert.setContentText("The Port is already in use");
            alert.showAndWait();
        }

        ArrayList<User> AllUsers = new ArrayList<User>();

        AllUsers = controller.getAllUserInfo();

        usersList.addAll(AllUsers);
        
        emailCol = new TableColumn("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<User, String>("email"));

        fnameCol = new TableColumn("fname");
        fnameCol.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        fnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        fnameCol.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
            @Override
            public void handle(CellEditEvent<User, String> event) {
                ((User) event.getTableView().getItems().get(event.getTablePosition().getRow())).setFirstName(event.getNewValue());
                String mail = ((User) event.getTableView().getItems().get(event.getTablePosition().getRow())).getEmail();
                controller.updateUserInfo(mail, event.getNewValue(), "Fname");
            }
        });

        lnameCol = new TableColumn("lname");
        lnameCol.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        lnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lnameCol.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
            @Override
            public void handle(CellEditEvent<User, String> event) {
                ((User) event.getTableView().getItems().get(event.getTablePosition().getRow())).setLastName(event.getNewValue());
                String mail = ((User) event.getTableView().getItems().get(event.getTablePosition().getRow())).getEmail();
                controller.updateUserInfo(mail, event.getNewValue(), "Lname");
            }
        });

        countryCol = new TableColumn("country");
        countryCol.setCellValueFactory(new PropertyValueFactory<User, String>("country"));
        countryCol.setCellFactory(TextFieldTableCell.forTableColumn());
        countryCol.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
            @Override
            public void handle(CellEditEvent<User, String> event) {
                ((User) event.getTableView().getItems().get(event.getTablePosition().getRow())).setCountry(event.getNewValue());
                String mail = ((User) event.getTableView().getItems().get(event.getTablePosition().getRow())).getEmail();
                controller.updateUserInfo(mail, event.getNewValue(), "Country");
            }
        });

        cityCol = new TableColumn("city");
        cityCol.setCellValueFactory(new PropertyValueFactory<User, String>("city"));
        cityCol.setCellFactory(TextFieldTableCell.forTableColumn());
        cityCol.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
            @Override
            public void handle(CellEditEvent<User, String> event) {
                ((User) event.getTableView().getItems().get(event.getTablePosition().getRow())).setCity(event.getNewValue());
                String mail = ((User) event.getTableView().getItems().get(event.getTablePosition().getRow())).getEmail();
                controller.updateUserInfo(mail, event.getNewValue(), "City");
            }
        });

        onlineCol = new TableColumn("online");
        onlineCol.setCellValueFactory(new PropertyValueFactory<User, String>("isOnline"));

        statusCol = new TableColumn("status");
        statusCol.setCellValueFactory(new PropertyValueFactory<User, String>("status"));
        statusCol.setCellFactory(TextFieldTableCell.forTableColumn());
        statusCol.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
            @Override
            public void handle(CellEditEvent<User, String> event) {
                ((User) event.getTableView().getItems().get(event.getTablePosition().getRow())).setStatus(event.getNewValue());
                String mail = ((User) event.getTableView().getItems().get(event.getTablePosition().getRow())).getEmail();
                controller.updateUserInfo(mail, event.getNewValue(), "Status");
            }
        });

        genderCol = new TableColumn("gender");
        genderCol.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
        genderCol.setCellFactory(TextFieldTableCell.forTableColumn());
        genderCol.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
            @Override
            public void handle(CellEditEvent<User, String> event) {
                ((User) event.getTableView().getItems().get(event.getTablePosition().getRow())).setGender(event.getNewValue());
                String mail = ((User) event.getTableView().getItems().get(event.getTablePosition().getRow())).getEmail();
                controller.updateUserInfo(mail, event.getNewValue(), "Gender");
            }
        });

        userTableView.getColumns().setAll(fnameCol, emailCol, lnameCol, countryCol, cityCol, onlineCol, statusCol, genderCol);

        userTableView.setItems(usersList);
        updateCharts();
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
    public void onSubmit() {
        if (Validation.nameValidation(firstName.getText())) {
            if (Validation.nameValidation(lastName.getText())) {
                if (Validation.eMailValidation(eMail.getText())) {
                    if (Validation.passwordValidation(password.getText())) {

                        if (nameValidation(country.getText())) {

                            if (nameValidation(city.getText())) {

                                if (nameValidation(question.getText())) {

                                    if (nameValidation(answer.getText())) {

                                        user = new User(eMail.getText(), firstName.getText(), lastName.getText(), password.getText(), country.getText(), city.getText(), question.getText(), answer.getText(), "Available", gender.getValue(), true);
                                        if (!controller.searchForUserByEMail(user.getEmail())) {
                                            controller.insertUser(user);
//                                            Alert alert = new Alert(Alert.AlertType.WARNING);
//                                            alert.setTitle("WRARING");
//                                            alert.setHeaderText(null);
//                                            alert.setContentText("Account has been created.");
//                                            alert.showAndWait();
                                        }

                                    } else {
                                        validateAnswer.setText("You must answer the question.");
                                    }
                                } else {
                                    validateQuestion.setText("You must enter question.");
                                }
                            } else {
                                validateCity.setText("You must enter your city.");
                            }
                        } else {
                            validateCountry.setText("You must enter your country.");
                        }
                    } else {
                        validatePassword.setText("password should contain capital letter, small letter, special character, numbers and at least 8 digits");
                    }
                } else {
                    validateEmail.setText("E-Mail is invalid");
                }
            } else {
                validatLastName.setText("Last name is invalid");
            }
        } else {
            //System.out.println("first name is incorrect");
            validatFirestName.setText("First name is invalid");
        }

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

    @FXML
    public void updateOnlinePieChart() {
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Online Users", controller.getOnlineUsersCount()),
                        new PieChart.Data("Offline Users", controller.getOfflineUsersCount()));
        pieChart.setData(pieChartData);
    }

    public void handleSendAd() {
        controller.sendAddToOnlineUsers(adTextArea.getText());
    }
}
