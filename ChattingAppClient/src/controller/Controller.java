package controller;

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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.pojo.User;
import services.ClientServicesImpl;
import view.ChatBoxController;
import view.FXMLControllersInterface;
import view.GroupChatBoxController;
import view.ReceiveFriendRequestFormController;

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
            chattingRef = (ChattingServerService) registry.lookup("ChattingService");
            receiveFriendRequestService = (ReceiveFriendRequestService) registry.lookup("ReceiveFriendRequestService");
            forgetPasswordService = (ForgetPasswordService) registry.lookup("ForgetPasswordService");

        } catch (RemoteException | NotBoundException ex) {
            System.out.println("can't lookup from registry");
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

    public User getUserByEmail(String email) {
        try {
            user = serverSignInRef.signIn(email, null);
        } catch (RemoteException ex) {
            System.out.println("can't use sign In from server");
            ex.printStackTrace();
        }
        return user;
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
                    System.out.println("Request is sent");
                    serverAddFriendRef.deliverFriendRequest(userEmail, emailToAdd);
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
                    System.out.println("my Frind list: " + friendlistUser.getEmail());
                    if (friendlistUser.getEmail().equals(friendEmail)) {
                        friendlistUser.setStatus(newStatus);
                    }
                }
                currentControllersMap.get("mainPageFormController").updateList(user);
                System.out.println("List updatedddddddd");
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
                System.out.println("I have :" + myFreindsRequests.size());
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
                System.out.println("add " + senderEmail + " to " + user.getEmail() + " = " + email);
                updateMyFriendsList();
                System.out.println("finished updatelist to" + user.getEmail());
                System.out.println("inside confirmFriendRequest) userFriendList is : ");
                for (User friend : user.getFriendsList()) {
                    System.out.println(friend.getEmail());
                }
                return true;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void updateMyFriendsList() {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> {
                currentControllersMap.get("mainPageFormController").passUser(user);
                currentControllersMap.get("mainPageFormController").updateListView(); 
            });
        }
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
                        homeStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                            public void handle(WindowEvent we) {
                                getCurrentChatControllersMap().remove(mail, chatBoxController);
                            }
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
                    homeStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            currentGroupChatControllersMap.remove(ID);
                            try {
                                chattingRef.removeMeFromRoom(mail, ID);
                            } catch (RemoteException ex) {
                                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                            }
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
            System.out.println("inside client controller sendMsgToGchat");
            chattingRef.sendMsgToGroupChat(ID, msg, email);
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveGroupChatMsg(int ID, String msg, String senderEmail) {
        System.out.println("inside client controller call back receiveGroupChatMsg");
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
            System.out.println("iam in the send file to user chating app client");
            clientServices = chattingRef.sendFile(fileName, receiverEmail, senderEmail);
        } catch (RemoteException ex) {
            System.out.println("");
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
        for(User friend : user.getFriendsList()){
            if(friend.getEmail().equals(friendMail))
                friend.setIsOnline(true);
        }
        updateMyFriendsList();
    }
}
