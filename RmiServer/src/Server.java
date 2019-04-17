
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author panos
 */
public class Server extends UnicastRemoteObject implements ServerInterface {

    HashMap<Integer, ClientInterface> clients = new HashMap<>();//hashmap me kleidi to id tou client

    public Server() throws RemoteException {
        super();
    }

    @Override
    public void addMe(ClientInterface ci, int id) {

        try {
            System.out.println("Kapoios mpike... me id " + id);
            clients.put(id, ci);//prosthetw sthn domh mou ton client

            byte[] mydata = new byte[1024 * 1024];
            byte[] mydata2 = new byte[1024 * 1024];
            byte[] mydata3 = new byte[1024 * 1024];
            byte[] mydata4 = new byte[1024 * 1024];
            File f1 = new File("tile000.png");
            File f2 = new File("tile001.png");
            File f3 = new File("tile002.png");
            File f4 = new File("tile003.png");
            //stream gia na steilw arxeia
            FileInputStream in = new FileInputStream(f1);
            FileInputStream in2 = new FileInputStream(f2);
            FileInputStream in3 = new FileInputStream(f3);
            FileInputStream in4 = new FileInputStream(f4);

            Random rand = new Random();
            while (clients.isEmpty() == false) {
                int n = rand.nextInt(4);//paragwgh tuxaiou arithmou apo 0 ews 3
                if (n == 0) {
                    Date dateobj = new Date();
                    int mylen = in.read(mydata);
                    while (mylen > 0) {
                        ci.update(f1.getName(), mydata, mylen, dateobj);//stelnw arxeio kai timestamp
                        mylen = in.read(mydata);

                    }
                    Thread.sleep(2000);//2 sec koimatai prin ksanasteilei
                } else if (n == 1) {
                    Date dateobj2 = new Date();
                    int mylen2 = in2.read(mydata2);
                    while (mylen2 > 0) {
                        ci.update(f2.getName(), mydata2, mylen2, dateobj2);
                        mylen2 = in2.read(mydata2);

                    }
                    Thread.sleep(2000);
                } else if (n == 2) {
                    Date dateobj3 = new Date();
                    int mylen3 = in3.read(mydata3);
                    while (mylen3 > 0) {
                        ci.update(f3.getName(), mydata3, mylen3, dateobj3);
                        mylen3 = in3.read(mydata3);

                    }
                    Thread.sleep(2000);
                } else if (n == 3) {
                    Date dateobj4 = new Date();
                    int mylen4 = in4.read(mydata4);
                    while (mylen4 > 0) {
                        ci.update(f4.getName(), mydata4, mylen4, dateobj4);
                        mylen4 = in4.read(mydata4);

                    }
                    Thread.sleep(2000);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteMe(int id) {

        clients.remove(id);//diagrafh client
        System.out.println("diagraftike o client me id: " + id);

    }

}
