package server;

import interfaces.ClientInterface;
import interfaces.SignUpServerService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DBConnection.DataBaseConnection;
import model.dao.ManipulateDB;
import model.pojo.User;

/**
 *
 * @author Aya M. Ashraf
 */
public class ServerImpl extends UnicastRemoteObject implements SignUpServerService {

      
    ManipulateDB dbManipulator;

    public ServerImpl() throws RemoteException {
        try {
            dbManipulator = new ManipulateDB();
        } catch (ClassNotFoundException ex) {
            System.out.println("i am manipulate database server imp");
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
     /*
     *the implementaion of the sgn up method in server interface
     */
     public boolean clientSignUp(User user){
         if(!dbManipulator.searchForUserByEMail(user.getEmail()))
             if(dbManipulator.insertUser(user))
                 return true;

        return false;
     }

}
