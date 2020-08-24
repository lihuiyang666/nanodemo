package Nano;
import com.google.gson.*;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class rundemo {


    public static void main(String[] args) throws SocketException, UnknownHostException {

        account a=new account();
        a.openTransaction();
        DatagramSocket ds=new DatagramSocket(10001);
        new Thread(new Send(ds,10002,a)).start();
        new Thread(new Receive(ds,a)).start();




    }
}
