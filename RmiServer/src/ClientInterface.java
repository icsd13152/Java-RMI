

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;


/**
 *
 * @author panos
 */
public interface ClientInterface extends Remote{
    public void update (String filename, byte[] data, int len,Date d) throws RemoteException;
    
    
}
