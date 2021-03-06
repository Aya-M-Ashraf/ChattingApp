package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author Aya M. Ashraf
 */
public class Server extends Application{

    public Server()  {     
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ServerForm.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e->{Platform.exit();System.exit(0);});
        primaryStage.show();
        primaryStage.setResizable(false);

    }

    public static void main(String[] args) {
           launch(args);     
    }

}
