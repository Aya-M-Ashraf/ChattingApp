package services;

import controller.ChatBoxController;
import controller.Controller;
import interfaces.ClientServices;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
import model.pojo.User;
import controller.YesNoBoxController;
import javafx.stage.WindowEvent;

public class ClientServicesImpl extends UnicastRemoteObject implements ClientServices {

    Controller controller;

    public ClientServicesImpl(Controller controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public String getEmail() throws RemoteException {
        return controller.getEmail();
    }

    @Override
    public void recieveAd(String adMessege) throws RemoteException {
        controller.ReceiveAdd(adMessege);
    }

    @Override
    public ClientServices receiveMyFriendchangeStatus(ClientServices clientService) {
        return clientService;
    }

    @Override
    public void changeFriendStatus(String friendEmail, String newStatus) throws RemoteException {
        controller.changeFriendStatus(friendEmail, newStatus);
    }

    @Override
    public void recieveMsg(String text, String sender, String receiever) throws RemoteException {
        controller.receiveMsgFromUser(text, sender, receiever);
    }

    @Override
    public void recieveGroupChat(int ID, ArrayList<String> arrayList) throws RemoteException {
        controller.participateGroupChat(ID, arrayList);
    }

    @Override
    public void recieveGroupChatMsg(int ID, String msg, String senderEmail) throws RemoteException {
        controller.receiveGroupChatMsg(ID, msg, senderEmail);
    }

    @Override
    public void updateRoomList(int ID, ArrayList<String> friends) throws RemoteException {
        controller.updateRooomList(ID, friends);
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
                            ///
                            if (controller.getCurrentChatControllersMap().get(senderEmail) == null) {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChatBox.fxml"));
                                    Parent chatPageParent = loader.load();
                                    ChatBoxController chatBoxController = loader.getController();
                                    try {
                                        chatBoxController.passUser(controller.getUserByEmail(senderEmail));
                                    } catch (RemoteException ex) {
                                        Logger.getLogger(ClientServicesImpl.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    chatBoxController.passController(controller);
                                    controller.getCurrentChatControllersMap().put(senderEmail, chatBoxController);
                                    
                                    Scene chatPageScene = new Scene(chatPageParent);
                                    Stage chatStage = new Stage();
                                    chatStage.setResizable(false);
                                    chatStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                        public void handle(WindowEvent we) {
                                            controller.getCurrentChatControllersMap().remove(senderEmail, chatBoxController);
                                        }
                                    });
                                    
                                    chatStage.setScene(chatPageScene);
                                    chatStage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(ClientServicesImpl.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            //

                            FileChooser fileChooser = new FileChooser();
                            File myFile = fileChooser.showSaveDialog(controller.getCurrentChatControllersMap().get(senderEmail).getLabel().getScene().getWindow());
                            if (myFile != null) {
                                try {
                                    FileOutputStream fos = new FileOutputStream(myFile.getAbsolutePath() + fileName);
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
    public void getFriendRequest(String sender) throws RemoteException {
        controller.getRequest(sender);
    }

    @Override
    public ArrayList<User> getAllMyFriends() throws RemoteException {
        return controller.getAllMyFriends();
    }

    @Override
    public void setFriendOnline(String friendMail) throws RemoteException {
        controller.setFriendOnline(friendMail);
    }

    @Override
    public void upDateMainList() throws RemoteException {
        controller.updateMyFriendsList();
    }

    @Override
    public void addMyRequest(String friendMail) throws RemoteException {
        controller.addMyRequest(friendMail);
    }

    @Override
    public void setFriendOffline(String friendMail) throws RemoteException {
        controller.setFriendOffline(friendMail);
    }

    @Override
    public void popUpOnlineNotification(String email) throws RemoteException {
        controller.putNotification("Online", email);
    }

    @Override
    public void popUpOfflineNotification(String email) throws RemoteException {
        controller.putNotification("Offline", email);
    }

    @Override
    public void serverisDown() throws RemoteException {
        controller.serverisDown();
    }
}
