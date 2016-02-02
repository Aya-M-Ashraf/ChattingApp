package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Ahmed Ashraf
 */
public interface ChattingServerService extends Remote {
    public void sendMsg(String text, String reciever, String sender) throws RemoteException;
    public void createGroupChat(ArrayList<String> arrayList) throws RemoteException;
    public void sendMsgToGroupChat(int ID, String msg, String senderEmail) throws RemoteException;
    public void addFriendsToExistedChatRoom(ArrayList<String> list, int chatRoomID)throws RemoteException;
    public void removeMeFromRoom(String mail, int ID) throws RemoteException;
    public ClientServices sendFile(String fileName, String receiverEmail, String senderEmail) throws RemoteException;
}
