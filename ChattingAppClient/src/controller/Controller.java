package controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientImpl;
import interfaces.SignUpServerService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.pojo.User;
//import view.ClientFrame;

/**
 *
 * @author Aya M. Ashraf
 */
public class Controller extends Application {

    ClientImpl clientImplRef;
    SignUpServerService serverRef;

    public Controller() {

        try {
            clientImplRef = new ClientImpl(this);
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 5000);
            serverRef = (SignUpServerService) registry.lookup("ChatService");
            //serverRef.register(clientImplRef);                          //add client to server Vector
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (NotBoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * take user object from the signUpFromCotroller
     */
    public void sendUserTOServer(User user) {
      
        if (serverRef.clientSignUp(user)) {
            System.out.println("sign up correct");
        } else {
            System.out.println("sign up fail");
        }
    }
 @Override
    public void start(Stage stage) throws Exception {
   
     
    }

    
    public static void main(String[] args) {
        
        new Controller();
        launch(args);
    }

}
