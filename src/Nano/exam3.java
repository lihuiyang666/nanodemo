package Nano;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class exam3 {
    public static void main(String[] args){
        account a=new account();
        account b=new account();
        a.openTransaction();
        b.openTransaction();
        sendBlock block=a.sendTransaction(b,10);
        Gson json=new Gson();
        String bl=json.toJson(block);
        sendBlock block1=json.fromJson(bl,sendBlock.class);
        System.out.println(block1);
        b.receiveTransaction(a,block1,10);
        System.out.println(b.ownBlockchain);
       
    }
}
