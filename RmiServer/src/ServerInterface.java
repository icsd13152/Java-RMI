
import java.rmi.Remote;
import java.rmi.RemoteException;



/**
 *
 * @author panos
 */
public interface ServerInterface extends Remote  {
     public void addMe(ClientInterface ci,int id) throws RemoteException;
      public void deleteMe(int msg) throws RemoteException;
}
