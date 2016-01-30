package controller;

import interfaces.AddFriendServerService;
import interfaces.SignInServerService;
import interfaces.SignUpServerService;
import interfaces.ChangeStatusService;
import interfaces.SignOutServerService;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.pojo.User;
import services.ClientServicesImpl;
import view.FXMLControllersInterface;
import view.MainPageFormController;
import view.SignInFormController;

public class Controller extends Application {

    ClientServicesImpl clientServicesImpl;
    private SignInServerService serverSignInRef;
    private SignOutServerService serverSignOutRef;
    private SignUpServerService serverSignUpRef;
    private AddFriendServerService serverAddFriendRef;
    private ChangeStatusService changeStatusRef;
    User user;
    Map<String, FXMLControllersInterface> currentControllersMap = new HashMap<>();

    public Controller() {
        try {

            clientServicesImpl = new ClientServicesImpl(this);

        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 5000);

            serverSignInRef = (SignInServerService) registry.lookup("SignInService");
            serverSignOutRef = (SignOutServerService) registry.lookup("SignOutService");
            serverSignUpRef = (SignUpServerService) registry.lookup("SignUpService");
            serverAddFriendRef = (AddFriendServerService) registry.lookup("AddFriendService");
            changeStatusRef = (ChangeStatusService) registry.lookup("ChangeStatusService");

        } catch (RemoteException | NotBoundException ex) {
            System.out.println("can't lookup from registry");
        }
    }

    public Map<String, FXMLControllersInterface> getCurrentControllers() {
        return currentControllersMap;
    }

    public void setCurrentControllers(Map<String, FXMLControllersInterface> currentControllers) {
        this.currentControllersMap = currentControllers;
    }

    public void sendUserToServer(User user) {

        if (serverSignUpRef.clientSignUp(user)) {
            System.out.println("Sign up is correct");
        } else {
            System.out.println("Sign up fails");
        }
    }

    public User signIn(String email, String password) {

        try {
            user = serverSignInRef.signIn(email, password);

            if (user == null) {   // user doesn't exist              
                System.out.println("User doesn't exisit!");
            } else {
                if (user.getPassword().equals(password)) {
                    //password is correct
                    System.out.println(user.getFirstName() + " u r logged in");
                    if (serverSignInRef.updateUserIsOnlineByEmail(email)) {
                        user.setIsOnline(true);
                        System.out.println("isonline updated" + user.getEmail() + " " + user.isIsOnline());

                    } else {
                        System.out.println("isonline doesn't updated.");
                    }
                    return user;
                } else {
                    // password isn't correct.
                    System.out.println("Password is incorrect!");
                }
            }
        } catch (RemoteException ex) {
            System.out.println("can't use sign In from server");
            ex.printStackTrace();
        }
        return null;
    }

    public boolean signOutOneUser(String eMail) {
        try {
            System.out.println("befor  user Signd out ");
            return serverSignOutRef.signOutOneUser(eMail);

        } catch (RemoteException ex) {

            System.out.println("user Signd out ");

            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public boolean updateUserStatus(String email, String status) {
        try {
            if (serverSignInRef.updateUserStatusByEmail(email, status) == true) {
                System.out.println("status updated by " + status);
            } else {
                System.out.println("status doesn't updated.");

            }
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void addFriendToUser(String userEmail, String emailToAdd) {

        try {
            if (serverAddFriendRef.checkIfUserExist(emailToAdd)) {
                if (serverAddFriendRef.sendFriendRequest(userEmail, emailToAdd)) {
                    System.out.println("Request is sent");
                }
            } else {
                System.out.println("this mail doesn't belong to anyone");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayChangeFrindStatus(User user, String newStatus) {
        try {
            if (changeStatusRef.tellFriendsMyStatus(user, newStatus)) {
                System.out.println("Status is send.");
            } else {
                System.out.println("Error in send status.");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignInForm.fxml"));
        Parent root = loader.load();
        FXMLControllersInterface signInFormController = loader.getController();
        currentControllersMap.put("SignInFormController", signInFormController);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void registerMe() {
        try {

            serverSignInRef.registerUser(clientServicesImpl);
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void unregisterMe() {
        try {
            serverSignInRef.unregisterUser(clientServicesImpl);
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getEmail() {
        return user.getEmail();
    }

    public void ReceiveAdd(String adMassege) {
        System.out.println("Server say: ------> " + adMassege);
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> {
                FXMLControllersInterface myController = currentControllersMap.get("mainPageFormController");
                myController.displayAdd(adMassege);
            });
        }
    }
}
