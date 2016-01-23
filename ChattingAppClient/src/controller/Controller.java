package controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientImpl;
import interfaces.ServerInterface;
import interfaces.SignInServerService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.pojo.User;
import view.ClientFrame;

/**
 *
 * @author Aya M. Ashraf
 */
public class Controller extends Application {

    ClientImpl clientImplRef;
    ClientFrame viewFrame;
    ServerInterface serverRef;
    SignInServerService serverSignInRef;
    User user;
    
    public Controller() {

        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 5000);
           
            clientImplRef = new ClientImpl(this);
            
            serverRef = (ServerInterface) registry.lookup("ChatService");
            serverRef.register(clientImplRef);                          //add client to server Vector
            
            serverSignInRef = (SignInServerService) registry.lookup("SignInService"); 
            
            
        } catch (RemoteException ex) {
            System.out.println("cant lookup");
            //ex.printStackTrace();
        } catch (NotBoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        //viewFrame = new ClientFrame(this);
        
        
    }

    public void sendMsg(String msg) {
        try {
            serverRef.tellOthers(msg);
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void unregister() throws RemoteException {
        serverRef.unregister(clientImplRef);
    }

    public void displayMsg(String msg) {
        viewFrame.display(msg);
    }
    
    public User signIn(String email,String password){
        try {
            
            user = serverSignInRef.signIn(email, password);
            
            if(user == null){
                // user doesn't exist
                System.out.println("User doesn't exisit!"); 
            }else
            {   
                if (user.getPassword().equals(password) ){
                    //password is correct
                    System.out.println(user.getPassword()+" u r logged in");
                     return user;
                }else{
                    // password isn't correct.
                    System.out.println("Password is incorrect!"); 
                }
            }
            
        } catch (RemoteException ex) {
             System.out.println("cant use signIn from server");
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
    }
        
    @Override
        public void start(Stage stage) throws Exception {
            
        Parent root = FXMLLoader.load(getClass().getResource("SignInForm.fxml"));
        //root.lookup("#emailLabel");
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();     
    }
        
    public static void main(String[] args) {
        new Controller();
        launch(args);
    }

}
