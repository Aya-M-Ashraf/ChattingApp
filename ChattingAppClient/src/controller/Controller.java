package controller;

import interfaces.SignInServerService;
import interfaces.SignUpServerService;
import java.io.IOException;
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
public class Controller extends Application{
    
    SignInServerService serverSignInRef;
    SignUpServerService serverSignUpRef;
    
    public Controller() {
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 5000);

            serverSignInRef = (SignInServerService) registry.lookup("SignInService");
            serverSignUpRef = (SignUpServerService) registry.lookup("SignUpService");
            
        } catch (RemoteException ex) {
            System.out.println("cant lookup");
        } catch (NotBoundException ex) {
            System.out.println("cant lookup");
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
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
            if (user == null) {
                // user doesn't exist
                System.out.println("User doesn't exisit!");
            } else {
                if (user.getPassword().equals(password)) {
                    //password is correct
                    System.out.println(user.getPassword() + " u r logged in");
                    return user;
                } else {
                    // password isn't correct.
                    System.out.println("Password is incorrect!");
                }
            }
        } catch (RemoteException ex) {
            System.out.println("can't use sign In from server");
            ex.printStackTrace();
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
      
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("SignUpForm.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);   
    }  

    
}
