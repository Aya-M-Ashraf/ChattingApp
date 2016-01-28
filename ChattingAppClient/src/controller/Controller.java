package controller;

import interfaces.AddFriendServerService;
import interfaces.SignInServerService;
import interfaces.SignUpServerService;
import interfaces.ChangeStatusService;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.pojo.User;

/**
 *
 * @author Amr
 */
public class Controller extends Application {

    private SignInServerService serverSignInRef;
    private SignUpServerService serverSignUpRef;
    private AddFriendServerService serverAddFriendRef;
    private ChangeStatusService changeStatusRef;

    public Controller() {
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 5000);

            serverSignInRef = (SignInServerService) registry.lookup("SignInService");
            serverSignUpRef = (SignUpServerService) registry.lookup("SignUpService");
            serverAddFriendRef = (AddFriendServerService) registry.lookup("AddFriendService");
            changeStatusRef = (ChangeStatusService) registry.lookup("ChangeStatusService");

        } catch (RemoteException | NotBoundException ex) {
            System.out.println("can't lookup from registry");
        }
    }

    public void sendUserToServer(User user) {

        if (serverSignUpRef.clientSignUp(user)) {
            System.out.println("Sign up is correct");
        } else {
            System.out.println("Sign up fails");
        }
    }

    public User signIn(String email, String password) {

        User user;
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
        Parent root = FXMLLoader.load(getClass().getResource("/view/SignInForm.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
