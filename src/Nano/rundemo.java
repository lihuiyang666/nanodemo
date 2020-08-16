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
        Gson json=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String s=json.toJson(a);
        System.out.println(s);




    }
}
