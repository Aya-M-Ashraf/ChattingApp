package services;

import controller.Controller;
import interfaces.ClientServices;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.YesNoBoxController;

/**
 *
 * @author Ahmed Ashraf
 */
public class ClientServicesImpl extends UnicastRemoteObject implements ClientServices{

    Controller controller;
    public ClientServicesImpl(Controller controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public String getEmail() throws RemoteException {
       return controller.getEmail();
    }
    
    @Override
    public void recieveAd(String adMessege) throws RemoteException{
        controller.ReceiveAdd(adMessege);
    }
    
    @Override
    public ClientServices receiveMyFriendchangeStatus(ClientServices clientService) {
        return clientService;
    }


    @Override
    public Vector<ClientServices> getAllMyFriends(ClientServices clientService) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeFriendStatus(String friendEmail, String newStatus) throws RemoteException {
        controller.changeFriendStatus(friendEmail,newStatus);
    }


    @Override
    public void recieveMsg(String text, String sender) throws RemoteException {
        controller.receiveMsgFromUser(text,sender);
    }

    @Override
    public void recieveGroupChat(int ID,ArrayList<String> arrayList) throws RemoteException {
        controller.participateGroupChat(ID,arrayList);
    }

    @Override
    public void recieveGroupChatMsg(int ID, String msg, String senderEmail) throws RemoteException {
        controller.receiveGroupChatMsg(ID,msg,senderEmail);
    }

    @Override
    public void updateRoomList(int ID, ArrayList<String> friends) throws RemoteException {
        controller.updateRooomList(ID,friends);
    }
    
    @Override
    public boolean receiveFileFromUser(String receiver, String senderEmail, byte[] file, String fileName) throws RemoteException {

        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> {

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/yesNoBox.fxml"));
                    Parent homePageParent = loader.load();

                    YesNoBoxController yesNoBoxController = loader.getController();

                    Scene homePageScene = new Scene(homePageParent);
                    Stage homeStage = new Stage();

                    homeStage.setScene(homePageScene);

                    homeStage.show();

                    yesNoBoxController.getYesButton().setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            homeStage.close();
                            FileChooser fileChooser = new FileChooser();
                            File myFile = fileChooser.showSaveDialog(controller.getCurrentChatControllersMap().get(senderEmail).getLabel().getScene().getWindow());
                            if (myFile != null) {
                                try {
                                    FileOutputStream fos = new FileOutputStream(myFile.getAbsolutePath()+fileName);
                                    //int size = fis.available();
                                    fos.write(file);
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {

                                System.out.println("you didnt choose file");
                            }

                        }
                    });

                    yesNoBoxController.getNoButton().setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            homeStage.close();


                        }
                    });

                } catch (IOException ex) {
                    Logger.getLogger(ClientServicesImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        return true;
    }

    public void writeFile(String path, byte[] b) {

        try {
            FileOutputStream fos = new FileOutputStream(path);
            //int size = fis.available();

            fos.write(b);
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean askUserToReceiveFile(String fileName, String receiver, String senderEmail) throws RemoteException {
        return true;
    }

    @Override
    public void getFriendRequest(String userEmail) throws RemoteException {
        controller.getOfflineFriendRequest(userEmail);
    }
    
}
