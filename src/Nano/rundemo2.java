package Nano;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class rundemo2 {

    public static void main(String[] args) throws SocketException, UnknownHostException {
        account b=new account();
        b.openTransaction();
        DatagramSocket ds=new DatagramSocket(10002);
        new Thread(new Send(ds,10001,b)).start();
        new Thread(new Receive(ds,b)).start();

    }
}
