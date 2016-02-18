package controller;

import eu.hansolo.enzo.notification.Notification;
import eu.hansolo.enzo.notification.Notification.Notifier;
import interfaces.AddFriendServerService;
import interfaces.SignInServerService;
import interfaces.SignUpServerService;
import interfaces.ChangeStatusService;
import interfaces.ChattingServerService;
import interfaces.ClientServices;
import interfaces.ForgetPasswordService;
import interfaces.ReceiveFriendRequestService;
import interfaces.SignOutServerService;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.pojo.User;
import services.ClientServicesImpl;

public class Controller extends Application {

    ClientServicesImpl clientServicesImpl;
    private SignInServerService serverSignInRef;
    private SignOutServerService serverSignOutRef;
    private SignUpServerService serverSignUpRef;
    private AddFriendServerService serverAddFriendRef;
    private ChangeStatusService changeStatusRef;
    private ChattingServerService chattingRef;
    private ReceiveFriendRequestService receiveFriendRequestService;
    private ForgetPasswordService forgetPasswordService;

    User user;
    Map<String, FXMLControllersInterface> currentControllersMap = new HashMap<>();
    Map<String, ChatBoxController> currentChatControllersMap = new HashMap<>();
    Map<Integer, GroupChatBoxController> currentGroupChatControllersMap = new HashMap<>();

    public Controller() {
        try {
            clientServicesImpl = new ClientServicesImpl(this);
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 5000);

            serverSignInRef = (SignInServerService) registry.lookup("SignInService");
            serverSignOutRef = (SignOutServerService) registry.lookup("SignOutService");
            serverSignUpRef = (SignUpServerService) registry.lookup("SignUpService");
            serverAddFriendRef = (AddFriendServerService) registry.lookup("AddFriendService");
            changeStatusRef = (ChangeStatusService) registry.lookup("ChangeStatusService");
            chattingRef = (ChattingServerService) registry.lookup("ChattingService");
            receiveFriendRequestService = (ReceiveFriendRequestService) registry.lookup("ReceiveFriendRequestService");
            forgetPasswordService = (ForgetPasswordService) registry.lookup("ForgetPasswordService");

        } catch (RemoteException | NotBoundException ex) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("WRARING");
//            alert.setHeaderText(null);
//            alert.setContentText("Serve is down, can't lookup from registry");
//            alert.showAndWait();
//            System.exit(0);
        }
    }

    public Map<String, ChatBoxController> getCurrentChatControllersMap() {
        return currentChatControllersMap;
    }

    public void setCurrentChatControllersMap(Map<String, ChatBoxController> currentChatControllersMap) {
        this.currentChatControllersMap = currentChatControllersMap;
    }

    public Map<String, FXMLControllersInterface> getCurrentControllers() {
        return currentControllersMap;
    }

    public void setCurrentControllers(Map<String, FXMLControllersInterface> currentControllers) {
        this.currentControllersMap = currentControllers;
    }

    public void sendUserToServer(User user) {
        if (serverSignUpRef.clientSignUp(user)) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("WRARING");
//            alert.setHeaderText(null);
//            alert.setContentText("Your account has been created.");
//            alert.showAndWait();
        } else {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("WRARING");
//            alert.setHeaderText(null);
//            alert.setContentText("There is a problem in account creation.");
//            alert.showAndWait();
        }
    }

    public User signIn(String email, String password) {
        try {
            user = serverSignInRef.signIn(email, password);
            if (user == null) {   // user doesn't exist              
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle("WRARING");
//                alert.setHeaderText(null);
//                alert.setContentText("User doesn't exisit!");
//                alert.showAndWait();
            } else {
                if (user.getPassword().equals(password)) {
                    //password is correct
                    if (serverSignInRef.updateUserIsOnlineByEmail(email)) {
                        user.setIsOnline(true);
                    }
                    return user;
                } else {
                    // password isn't correct.
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("WRARING");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Password is incorrect!");
//                    alert.showAndWait();
                }
            }
        } catch (RemoteException ex) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("WRARING");
//            alert.setHeaderText(null);
//            alert.setContentText("Server down, can't sign in.");
//            alert.showAndWait();
        }
        return null;
    }

    public User getUserByEmail(String email) throws RemoteException {
        User retrievedUser;
        retrievedUser = serverSignInRef.signIn(email, null);
        return retrievedUser;
    }

    public boolean signOutOneUser(String eMail) {
        try {
            System.out.println("before user Signd out ");
            return serverSignOutRef.signOutOneUser(eMail);
        } catch (RemoteException ex) {
            System.out.println("user Signd out ");
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateUserStatus(String email, String status) {
        try {
            if (serverSignInRef.updateUserStatusByEmail(user.getEmail(), status)) {
                System.out.println("status updated by " + status);
                changeStatusRef.tellFriendsMyStatus(user, status);
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
                    serverAddFriendRef.deliverFriendRequest(userEmail, emailToAdd);
////                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
////                    alert.setTitle("Confirmation");
////                    alert.setHeaderText(null);
////                    alert.setContentText("Your rwquest has been sent");
////                    alert.showAndWait();
                }
            } else {
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle("WRARING");
//                alert.setHeaderText(null);
//                alert.setContentText("this mail doesn't belong to anyone");
//                alert.showAndWait();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayChangeFrindStatus(User user, String newStatus) {
        try {
            changeStatusRef.tellFriendsMyStatus(user, newStatus);
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
        primaryStage.setResizable(false);
        primaryStage.show();
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
            try {
                if (!currentGroupChatControllersMap.isEmpty()) {
                    Iterator it = currentGroupChatControllersMap.entrySet().iterator();
                    while (it.hasNext()) {
                        HashMap.Entry pair = (HashMap.Entry) it.next();
                        chattingRef.removeMeFromRoom(user.getEmail(), (int) pair.getKey());
                    }
                }
            } catch (RemoteException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getEmail() {
        return user.getEmail();
    }

    public ArrayList<User> getOnlineFriendList() {
        return user.getOnlineFriendsList();
    }

    public void ReceiveAdd(String adMassege) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> {
                FXMLControllersInterface myController = currentControllersMap.get("mainPageFormController");
                myController.displayAdd(adMassege);
            });
        }
    }

    public void changeFriendStatus(String friendEmail, String newStatus) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> {
                for (User friendlistUser : user.getFriendsList()) {
                    if (friendlistUser.getEmail().equals(friendEmail)) {
                        friendlistUser.setStatus(newStatus);
                    }
                }
                currentControllersMap.get("mainPageFormController").updateList(user);
            });
        }
    }
    
    public void addMyRequest(String friendMail){
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> {  
                try {
                    user.getFriendsList().add(getUserByEmail(friendMail));
                    currentControllersMap.get("mainPageFormController").updateList(user);
                } catch (RemoteException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    public void getOfflineFriendRequest(String email) {
        ArrayList<String> myFreindsRequests = new ArrayList<>();
        try {
            myFreindsRequests = receiveFriendRequestService.getFriendRequests(email);
            if (myFreindsRequests != null) {
                for (String friendRequest : myFreindsRequests) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReceiveFriendRequestForm.fxml"));
                    Parent recieveRquestPageParent = loader.load();
                    ReceiveFriendRequestFormController controller = loader.getController();
                    controller.initData(friendRequest, email, this);
                    Scene receiveRequestPageScene = new Scene(recieveRquestPageParent);
                    Stage errorStage = new Stage();
                    errorStage.setScene(receiveRequestPageScene);
                    errorStage.show();
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean deleteReceivedFriendRequest(String friendEmail, String email) {
        try {
            return receiveFriendRequestService.cancelFriendRequest(friendEmail, email);
        } catch (RemoteException ex) {
            System.out.println("Entered catch");
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean confirmReceivedFriendRequest(String senderEmail, String email) {
        try {
            if (receiveFriendRequestService.confirmFriendReuest(senderEmail, email)) {
                user.getFriendsList().add(getUserByEmail(senderEmail));
                updateMyFriendsList();
                receiveFriendRequestService.confirmToSender(senderEmail,email);
                return true;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void updateMyFriendsList() {
       currentControllersMap.get("mainPageFormController").updateList(user);
    }

    public void sendMsg(String text, String reciever, String sender) {
        try {
            chattingRef.sendMsg(text, reciever, sender);
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createGroupChat(ArrayList<String> arrayList) {
        try {
            chattingRef.createGroupChat(arrayList);
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveMsgFromUser(String text, String sender, String reciever) {

        if (currentChatControllersMap.containsKey(sender)) {
            if (!Platform.isFxApplicationThread()) {
                Platform.runLater(() -> currentChatControllersMap.get(sender).recieveMsg(text, sender));
            }
        } else if (sender.equals(user.getEmail())) {
            if (!Platform.isFxApplicationThread()) {
                Platform.runLater(() -> currentChatControllersMap.get(reciever).recieveMsg(text, sender));
            }
        } else {
            if (!Platform.isFxApplicationThread()) {
                Platform.runLater(() -> {
                    User myfriend = new User();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChatBox.fxml"));
                        Parent homePageParent;
                        homePageParent = loader.load();
                        ChatBoxController chatBoxController = loader.getController();
                        for (User friend : user.getFriendsList()) {
                            if (friend.getEmail().equals(sender)) {
                                myfriend = friend;
                                break;
                            }
                        }
                        chatBoxController.passUser(myfriend);
                        chatBoxController.passController(this);
                        chatBoxController.recieveMsg(text, sender);
                        currentChatControllersMap.put(myfriend.getEmail(), chatBoxController);
                        final String mail = myfriend.getEmail();
                        Scene homePageScene = new Scene(homePageParent);
                        Stage homeStage = new Stage();
                        homeStage.setScene(homePageScene);
                        homeStage.setOnCloseRequest((WindowEvent we) -> {
                            getCurrentChatControllersMap().remove(mail, chatBoxController);
                        });
                        homeStage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        }
    }

    public void participateGroupChat(int ID, ArrayList<String> arrayList) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GroupChatBox.fxml"));
                    Parent root = loader.load();
                    GroupChatBoxController groupChatBoxController = loader.getController();
                    currentGroupChatControllersMap.put(ID, groupChatBoxController);
                    groupChatBoxController.setData(ID, arrayList);
                    groupChatBoxController.passController(this);
                    Scene scene = new Scene(root);
                    Stage homeStage = new Stage();
                    homeStage.setScene(scene);
                    final String mail = user.getEmail();
                    homeStage.setOnCloseRequest((WindowEvent we) -> {
                        currentGroupChatControllersMap.remove(ID);
                        try {
                            chattingRef.removeMeFromRoom(mail, ID);
                        } catch (RemoteException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    homeStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    public void sendMsgToGroupChat(int ID, String msg, String email) {
        try {
            chattingRef.sendMsgToGroupChat(ID, msg, email);
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveGroupChatMsg(int ID, String msg, String senderEmail) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> currentGroupChatControllersMap.get(ID).recieveMsg(msg, senderEmail));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void addFriendsToChatRoom(ArrayList<String> list, int chatRoomID) {
        try {
            chattingRef.addFriendsToExistedChatRoom(list, chatRoomID);
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateRooomList(int ID, ArrayList<String> friends) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> currentGroupChatControllersMap.get(ID).updateList(friends));
        }
    }

    public ClientServices sendFileToUser(String fileName, String receiverEmail, String senderEmail) {
        ClientServices clientServices = null;
        try {
            clientServices = chattingRef.sendFile(fileName, receiverEmail, senderEmail);
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientServices;
    }

    public User forgetPasswordHandle(String email) {
        try {
            return forgetPasswordService.selectUserWhereEmail(email);
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<User> getAllMyFriends() {
        return user.getFriendsList();
    }

    public void setFriendOnline(String friendMail) {
        for (User friend : user.getFriendsList()) {
            if (friend.getEmail().equals(friendMail)) {
                friend.setIsOnline(true);
            }
        }
        updateMyFriendsList();
    }

    public void setFriendOffline(String friendMail) {
        for (User friend : user.getFriendsList()) {
            if (friend.getEmail().equals(friendMail)) {
                friend.setIsOnline(false);
            }
        }
        updateMyFriendsList();
    }

    public void putNotification(String onStatus, String email) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> {
                Notification notify = new Notification(email, " is " + onStatus + " now");
                Notifier.INSTANCE.notify(notify);
            });
        }
    }

    public void serverisDown() {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> {
                try {
                    currentControllersMap.get("mainPageFormController").getNameLabel().getScene().getWindow().hide();
                    currentControllersMap.remove("mainPageFormController");
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("WRARING");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Sorry, Server is down. Try again later.");
//                    alert.showAndWait();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignInForm.fxml"));
                    Parent root = loader.load();
                    FXMLControllersInterface signInFormController = loader.getController();
                    currentControllersMap.put("SignInFormController", signInFormController);
                    Scene scene = new Scene(root);
                    Stage primaryStage = new Stage();
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }
}
