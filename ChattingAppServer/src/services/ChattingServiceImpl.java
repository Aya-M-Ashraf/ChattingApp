package services;

import controller.Controller;
import interfaces.ChattingServerService;
import interfaces.ClientServices;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author Ahmed Ashraf
 */
public class ChattingServiceImpl extends UnicastRemoteObject implements ChattingServerService {

    Controller controller;

    public ChattingServiceImpl(Controller controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public void sendMsg(String text, String reciever, String sender) throws RemoteException {
        controller.sendMsgToUser(text, reciever, sender);
    }

    @Override
    public void createGroupChat(ArrayList<String> arrayList) throws RemoteException {
        controller.createGroupChat(arrayList);
    }

    @Override
    public void sendMsgToGroupChat(int ID, String msg, String senderEmail) throws RemoteException {
        System.out.println("inside chattingImpl Server sendMsgToGChat");
        controller.sendMsgToGroupChat(ID, msg, senderEmail);
    }

    @Override
    public void addFriendsToExistedChatRoom(ArrayList<String> list, int chatRoomID) throws RemoteException {
        controller.addFriendstoExistedChatRoom(list, chatRoomID);
    }

    @Override
    public void removeMeFromRoom(String mail, int ID) throws RemoteException {
        controller.removeMeFromRoom(mail, ID);
    }

    @Override
    public ClientServices sendFile(String fileName, String reciever, String senderEmail) throws RemoteException {

        return controller.sendFileToUser(fileName, reciever, senderEmail);

    }
}
