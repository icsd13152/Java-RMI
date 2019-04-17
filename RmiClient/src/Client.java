
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author panos
 */
public class Client extends UnicastRemoteObject implements ClientInterface {
//dhlwseis
    private int id;
    JPanel p2, p1, p3;
    private int k;
    ServerInterface si;
    JFrame fr;
    String fname;
    ArrayList<Image> icons = new ArrayList<>();
    Date d2 = null;
    Image image = null;
    JTextArea ta;
    JScrollPane scrollPane;

    public Client() throws RemoteException {
        super();
        try {
            //pairnw anafora enos antikeimenou server gia na mporw na kalesw sunartiseis toy Server
            si = (ServerInterface) Naming.lookup("//localhost/server");
            //grafiko gia eggrafh tou xrhsth ston server stelnw id
            Container pane = new Container();
            JTextField uid = new JTextField(10);
            JOptionPane.showMessageDialog(pane, uid, "enter your id ", JOptionPane.QUESTION_MESSAGE);
            id = Integer.parseInt(uid.getText());
            drawFrame();//kalw methodo gia dhmiourgia tou grafikou

            si.addMe(this, id);//stelnw minima ston server na me prosthesei

        } catch (NotBoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(String filename, byte[] data, int len, Date d) {
        d2 = d;
        try {

            File f = new File(filename);//dhmiourgia arxeiou

            FileOutputStream out = new FileOutputStream(f);//stream gia na grapsw to arxeio ston xwro tou client
            fname = filename;
            out.write(data, 0, len);//grapsimo twn dedomenwn
            out.flush();//flush ton buffer
            out.close();//kleisimo stream
            image = ImageIO.read(f);//diavasma eikonas
            
            //dhmiourgia panel gia na topothetithei h eikona
            p1 = new JPanel() {
                @Override
                public void paintComponent(Graphics g) {

                    super.paintComponent(g);

                    g.drawImage(image, 150, 150, this);//sxediash eikonas

                }

            };

            SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            sf.format(d);

            ta.append("Movement event caught at " + d.toString() + "\n");//kollaw sto prohgoumeno text to neo

            fr.remove(p1);//diagrafh tou paliou panel ts eikonas

            fr.getContentPane().add(p1, BorderLayout.CENTER);//epanatopothtish
            //epanaparametropoihsh twn dedomenwn megethos klp.. kai epanasxediasmos
            fr.revalidate();
            fr.repaint();
            //gia thn eikona

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void drawFrame() {
        p1 = new JPanel() {//panel gia eikona
            @Override
            public void paintComponent(Graphics g) {

                super.paintComponent(g);

                g.drawImage(image, 150, 150, this);

            }

        };
//panel gia textarea
        p2 = new JPanel();
        ta = new JTextArea(5, 30);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        scrollPane = new JScrollPane(ta);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//scrollbar
        ta.setEditable(true);
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        d2 = new Date();
        sf.format(d2);

        ta.setText("Movement event caught at " + d2.toString() + "\n");
        p2.add(scrollPane);

        JButton b1 = new JButton("Exit");//button gia exit
        b1.setSize(5, 20);

        b1.addActionListener(new ActionListener() {//methdos actionlistener gia koumpi

            public void actionPerformed(ActionEvent evt) {
                try {
                    //grafiko gia na vazei id o client kai na ton diagrafei
                    Container pane = new Container();
                    JTextField uid = new JTextField(10);
                    JOptionPane.showMessageDialog(pane, uid, "enter your id for unsubscribe", JOptionPane.QUESTION_MESSAGE);
                    int idc = Integer.parseInt(uid.getText());
                    si.deleteMe(idc);
                    System.exit(1);//telos tou build
                } catch (RemoteException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
//dhmiourgia tou frame pou tha mpoun mesa tou ta panelakia
        fr = new JFrame();

        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setResizable(true);
        FlowLayout layout = new FlowLayout();

        fr.getContentPane().setLayout(new BorderLayout());
        fr.getContentPane().add(p2, BorderLayout.NORTH);

        fr.getContentPane().add(p1, BorderLayout.CENTER);

        fr.getContentPane().add(b1, BorderLayout.SOUTH);
        fr.setSize(600, 600);
        fr.setVisible(true);
    }

}
