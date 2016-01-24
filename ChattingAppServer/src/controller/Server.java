package controller;

import java.rmi.RemoteException;
import java.rmi.registry.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.SignInServiceImpl;
import services.SignUpServiceImpl;

/**
 *
 * @author Aya M. Ashraf
 */
public class Server extends Application{

    public Server()  {

            
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ServerForm.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
           launch(args);
            
    }

}
