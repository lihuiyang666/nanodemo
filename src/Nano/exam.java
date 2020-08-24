package Nano;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class exam {
    public static void main(String[] args) throws IOException {
        String s=new String();
        BufferedReader a=new BufferedReader(new InputStreamReader(System.in));
        s=a.readLine();
        String[] ss=s.split(" ");
        for(int i=0;i<ss.length;i++){
            System.out.println(ss[i]);
        }
        account account=new account();
        account.openTransaction();
        Gson json=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String str=json.toJson(account);
        System.out.println(str);

    }
}
