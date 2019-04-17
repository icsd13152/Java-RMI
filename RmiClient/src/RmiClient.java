


import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author panos
 */
public class RmiClient {

    
    public static void main(String[] args) {
        
            
        try {
            Client rc2 = new Client();
           
            

        } catch (RemoteException ex) {
            Logger.getLogger(RmiClient.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

}
