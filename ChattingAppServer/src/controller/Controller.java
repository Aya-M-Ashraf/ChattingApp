package controller;

import interfaces.ClientServices;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
//import javafx.scene.control.Alert;
import model.dao.ManipulateDB;
import model.dao.QueryDB;
import model.pojo.User;
import services.AddFriendServiceImpl;
import services.SignInServiceImpl;
import services.SignUpServiceImpl;
import services.ChangeStatusServiceImpl;
import services.ChattingServiceImpl;
import services.ForgetPasswordServiceImpl;
import services.ReceiveFriendRequestServiceImpl;
import services.SignOutServiceImpl;

public class Controller {

    ManipulateDB manipulateDBObj;
    QueryDB queryDB;

    Vector<User> usersVector = new Vector<User>();
    Vector<ClientServices> usersInterfacesVector = new Vector<>();
    Vector<Integer> roomsIDs = new Vector<>();

    Map<Integer, ArrayList<String>> currentGroupsMap = new HashMap<>();

    public Controller() {
        try {
            manipulateDBObj = new ManipulateDB();
            queryDB = new QueryDB();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Vector<ClientServices> getUsersInterfacesVector() {
        return usersInterfacesVector;
    }

    public void setUsersInterfacesVector(Vector<ClientServices> usersInterfacesVector) {
        this.usersInterfacesVector = usersInterfacesVector;
    }

    public boolean searchForUserByEMail(String eMail) {
        User user;
        user = manipulateDBObj.selectAllfromUserWhereEmail(eMail);
        if (user == null) {
            //System.out.println("no user with this mail is found");
            return false;
        } else {
            //System.out.println("user with this mail is found");
            return true;
        }
    }

    public boolean removeUserFromVector(String eMail) {
        User user = manipulateDBObj.selectAllfromUserWhereEmail(eMail);
        if (user != null) {
            System.out.println("user removed");
            return usersVector.remove(user);
        } else {
            System.out.println("user is equal to null in selectAllfromUserWhereEmail(eMail) in removeUserFromVector(String eMail)");
            return false;
        }
    }

    public boolean setUserOff(String eMail) {
        return manipulateDBObj.setUserOff(eMail);
    }

    public User signIn(String email, String password) {
        return manipulateDBObj.selectAllfromUserWhereEmail(email);
    }

    public boolean insertUser(User user) {
        return manipulateDBObj.insertUser(user);
    }

    public boolean updateUserIsOnlineByEmail(String email) {

        return manipulateDBObj.updateUserIsOnlineByEmail(email);
    }

    public boolean updateUserStatusByEmail(String email, String status) {
        return manipulateDBObj.updateUserStatusByEmail(email, status);
    }

    public void startServer(Registry registry) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            SignInServiceImpl SignInServiceImplRef = new SignInServiceImpl(this);
            registry.rebind("SignInService", SignInServiceImplRef);

            SignOutServiceImpl SignOutServiceImplRef = new SignOutServiceImpl(this);
            registry.rebind("SignOutService", SignOutServiceImplRef);

            SignUpServiceImpl SignUpServiceRef = new SignUpServiceImpl(this);
            registry.rebind("SignUpService", SignUpServiceRef);

            AddFriendServiceImpl AddFriendServiceRef = new AddFriendServiceImpl(this);
            registry.rebind("AddFriendService", AddFriendServiceRef);

            ChangeStatusServiceImpl changeStatusServiceImplRef = new ChangeStatusServiceImpl(this);
            registry.rebind("ChangeStatusService", changeStatusServiceImplRef);

            ChattingServiceImpl chattingServiceImplRef = new ChattingServiceImpl(this);
            registry.rebind("ChattingService", chattingServiceImplRef);

            ReceiveFriendRequestServiceImpl receiveFriendRequestServiceImplRef = new ReceiveFriendRequestServiceImpl(this);
            registry.rebind("ReceiveFriendRequestService", receiveFriendRequestServiceImplRef);

            ForgetPasswordServiceImpl forgetPasswordServiceImplRef = new ForgetPasswordServiceImpl(this);
            registry.rebind("ForgetPasswordService", forgetPasswordServiceImplRef);

        } catch (RemoteException ex) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("WRARING");
//            alert.setHeaderText(null);
//            alert.setContentText("The Port is already in used.");
//            alert.showAndWait();
        }
    }

    public void stopServer() {
        try {
            if (!usersInterfacesVector.isEmpty()) {
                for (ClientServices onlineUser : usersInterfacesVector) {
                    try {
                        onlineUser.serverisDown();
                    } catch (RemoteException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 5000);
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");

            registry.unbind("SignInService");
            registry.unbind("SignOutService");
            registry.unbind("SignUpService");
            registry.unbind("AddFriendService");
            registry.unbind("ChangeStatusService");
            registry.unbind("ChattingService");
            registry.unbind("ReceiveFriendRequestService");
            registry.unbind("ForgetPasswordService");

        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (NotBoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean addFriendRequest(String userEmail, String emailToAdd) {
        if (manipulateDBObj.selectAllFromUserWhereEmailwithoutFriendList(userEmail) != null) {
            if (manipulateDBObj.selectAllFromFriendRequest(userEmail, emailToAdd) == false) {
                return manipulateDBObj.insertFriendRequest(userEmail, emailToAdd);
            } else {
                System.out.println("You already sent friend request to " + emailToAdd);
            }
        } else {
            System.out.println("user not found.");
        }
        return false;
    }

    public ArrayList<User> getAllUserInfo() {
        return queryDB.selectAllUsersInfo();
    }

    public ArrayList<User> getAllUsers() {
        return queryDB.selectAllUsers();
    }

    public ArrayList<User> selectAllUsersWhere(String query) {
        return queryDB.selectAllUsersWhere(query);
    }

    public int getOnlineUsersCount() {
        return manipulateDBObj.selectAllOnlineUsers().size();
    }

    public int getFemaleUsersCount() {
        return manipulateDBObj.selectAllFemaleUsers().size();
    }

    public int getMaleUsersCount() {
        return manipulateDBObj.selectAllMaleUsers().size();
    }

    public int getOfflineUsersCount() {
        return manipulateDBObj.selectAllOfflineUsers().size();
    }

    public void sendAddToOnlineUsers(String text) {
        try {
            if (usersInterfacesVector.size() > 0) {
                for (ClientServices clientServices : usersInterfacesVector) {
                    clientServices.recieveAd(text);
                }
            } else {
                //System.out.println("There is no online users.");
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle("WRARING");
//                alert.setHeaderText(null);
//                alert.setContentText("There is no online users");
//                alert.showAndWait();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tellMyFriendsMyStatus(User user, String newStatus) {
        for (ClientServices clientService : usersInterfacesVector) {
            try {
                clientService.changeFriendStatus(user.getEmail(), newStatus);
            } catch (RemoteException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<String> getMyFriendsReuests(String email) {
        ArrayList<String> friendsRequest = new ArrayList<>();
        friendsRequest = manipulateDBObj.selectMyFriendsRequest(email);
        return friendsRequest;
    }

    public boolean addFriendToMyList(String email, String friendEmail) {
        if (manipulateDBObj.insertFriendToUser(email, friendEmail)) {
            manipulateDBObj.insertFriendToUser(friendEmail, email);
            manipulateDBObj.deleteFriendRequest(friendEmail, email);
            return true;
        }
        return false;
    }

    public boolean deleteFriendRequest(String friendEmail, String email) {
        return manipulateDBObj.deleteFriendRequest(friendEmail, email);
    }

    public void sendMsgToUser(String text, String reciever, String sender) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> {
                try {
                    for (ClientServices client : usersInterfacesVector) {
                        if (client.getEmail().equals(reciever)) {
                            client.recieveMsg(text, sender, reciever);
                        }
                        if (client.getEmail().equals(sender)) {
                            client.recieveMsg(text, sender, reciever);
                        }
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    public void createGroupChat(ArrayList<String> arrayList) {
        roomsIDs.add(1);
        try {
            for (ClientServices client : usersInterfacesVector) {
                if (arrayList.contains(client.getEmail())) {
                    client.recieveGroupChat(roomsIDs.size(), arrayList);
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentGroupsMap.put(roomsIDs.size(), arrayList);
    }

    public void sendMsgToGroupChat(int ID, String msg, String senderEmail) {
        try {
            ArrayList<String> friends = currentGroupsMap.get(ID);
            for (String mail : friends) {
                for (ClientServices client : usersInterfacesVector) {
                    if (mail.equals(client.getEmail())) {
                        client.recieveGroupChatMsg(ID, msg, senderEmail);
                    }
                }
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public void addFriendstoExistedChatRoom(ArrayList<String> list, int chatRoomID) {
        try {
            for (String friend : list) {
                currentGroupsMap.get(chatRoomID).add(friend);
            }
            for (String friend : list) {
                for (ClientServices client : usersInterfacesVector) {
                    if (client.getEmail().equals(friend)) {
                        client.recieveGroupChat(chatRoomID, currentGroupsMap.get(chatRoomID));
                    }
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removeMeFromRoom(String mail, int ID) {
        try {
            ArrayList<String> friends = new ArrayList<>();
            friends = currentGroupsMap.get(ID);
            friends.remove(mail);
            currentGroupsMap.put(ID, friends);
            for (String str : friends) {
                for (ClientServices client : usersInterfacesVector) {
                    if (str.equals(client.getEmail())) {
                        client.updateRoomList(ID, friends);
                    }
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ClientServices sendFileToUser(String fileName, String reciever, String senderEmail) {
        ClientServices receiverclient = null;
        for (ClientServices client : usersInterfacesVector) {
            try {
                if (client.getEmail().equals(reciever)) {
                    if (client.askUserToReceiveFile(fileName, reciever, senderEmail)) {
                        receiverclient = client;
                    }
                }
            } catch (RemoteException ex) {
                System.out.println(" iam in  sendFileToUser  server controller");
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return receiverclient;
    }

    public void deliverFriendRequest(String sender, String reciever) {
        try {
            for (ClientServices client : usersInterfacesVector) {
                if (client.getEmail().equals(reciever)) {
                    client.getFriendRequest(sender);
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User selectUserWhereEmail(String email) {
        return manipulateDBObj.selectAllFromUserWhereEmailwithoutFriendList(email);
    }

    public void tellMyFriendsIamOnline(ClientServices userInterface) {
        try {
            ArrayList<User> myfriends = userInterface.getAllMyFriends();
            for (User myUser : myfriends) {
                for (ClientServices onlineUser : usersInterfacesVector) {
                    if (myUser.getEmail().equals(onlineUser.getEmail())) {
                        onlineUser.setFriendOnline(userInterface.getEmail());
                        onlineUser.popUpOnlineNotification(userInterface.getEmail()); //pop up to online that i'm online
                    }
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tellMyFriendsIamOffline(ClientServices userInterface) {
        try {
            ArrayList<User> myfriends = userInterface.getAllMyFriends();
            for (User myUser : myfriends) {
                for (ClientServices onlineUser : usersInterfacesVector) {
                    if (myUser.getEmail().equals(onlineUser.getEmail())) {
                        onlineUser.setFriendOffline(userInterface.getEmail());
                        onlineUser.popUpOfflineNotification(userInterface.getEmail());

                    }
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void confirmToSender(String senderEmail, String mail) {
        try {
            for (ClientServices onlineUser : usersInterfacesVector) {
                if (senderEmail.equals(onlineUser.getEmail())) {
                    onlineUser.addMyRequest(mail);
                }
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
}
