package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;
import model.pojo.User;

/**
 *
 * @author Aya M. Ashraf
 */
public interface ClientServices extends Remote {
    public String getEmail() throws RemoteException;
    public void recieveAd(String adMessege) throws RemoteException;
    public void recieveMsg(String text,String sender,String reciever) throws RemoteException;
    public void recieveGroupChat(int ID,ArrayList<String> arrayList) throws RemoteException;
    public void recieveGroupChatMsg(int ID, String msg, String senderEmail)throws RemoteException;
    public void updateRoomList(int ID, ArrayList<String> friends)throws RemoteException;
    public ClientServices receiveMyFriendchangeStatus(ClientServices clientService) throws RemoteException;
    public void changeFriendStatus(String friendEmail, String newStatus) throws RemoteException;
    public boolean askUserToReceiveFile(String fileName,String receiver,String senderEmail)throws RemoteException;
    public boolean receiveFileFromUser(String receiver ,String senderEmail ,byte[] file, String fileName) throws RemoteException;
    public void getFriendRequest(String userEmail) throws RemoteException;
    public ArrayList<User> getAllMyFriends()throws RemoteException;
    public void setFriendOnline(String email)throws RemoteException;
    public void setFriendOffline(String email)throws RemoteException;
    public void upDateMainList()throws RemoteException;
    public void popUpOnlineNotification(String email)throws RemoteException;
    public void popUpOfflineNotification(String email)throws RemoteException;
    public void serverisDown() throws RemoteException;
}
