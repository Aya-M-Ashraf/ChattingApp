package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author Aya M. Ashraf
 */
public interface ClientServices extends Remote {
    public String getEmail() throws RemoteException;
    public void recieveAd(String adMessege) throws RemoteException;
    public void recieveMsg(String text,String sender) throws RemoteException;
    public void recieveGroupChat(int ID,ArrayList<String> arrayList) throws RemoteException;
    public void recieveGroupChatMsg(int ID, String msg, String senderEmail)throws RemoteException;
    public void updateRoomList(int ID, ArrayList<String> friends)throws RemoteException;
    public ClientServices receiveMyFriendchangeStatus(ClientServices clientService) throws RemoteException;
    public Vector<ClientServices> getAllMyFriends(ClientServices clientService) throws RemoteException;
    public void changeFriendStatus(String friendEmail, String newStatus) throws RemoteException;
    public boolean askUserToReceiveFile(String fileName,String receiver,String senderEmail)throws RemoteException;
    public boolean receiveFileFromUser(String receiver ,String senderEmail ,byte[] file) throws RemoteException;
    public void getFriendRequest(String userEmail) throws RemoteException;
}
