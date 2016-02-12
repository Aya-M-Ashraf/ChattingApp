package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author KHoloud
 */
public interface ReceiveFriendRequestService extends Remote{
    public ArrayList<String> getFriendRequests(String email) throws RemoteException;
    public boolean confirmFriendReuest(String friendEmail, String email) throws RemoteException;
    public boolean cancelFriendRequest(String friendEmail, String eamil) throws RemoteException;
    public void confirmToSender(String senderEmail,String mail) throws RemoteException;
}
