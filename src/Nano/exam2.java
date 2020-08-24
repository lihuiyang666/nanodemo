package Nano;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class exam2 {
    public static void main(String[] args) throws IOException {
        account a=new account();
        a.openTransaction();
        account b=new account();
        sendBlock block=a.sendTransaction(b,10);
        b.receiveTransaction(a,block,10);
        System.out.println(b.ownBlockchain.getLast().toString());

    }
}
