package Nano;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Send implements Runnable{
    //将账户作为发送消息类的成员
    final account a;

    public DatagramSocket ds;
    int port;

    Send(DatagramSocket ds,int port,account a){
        this.a=a;
        this.ds=ds;
        this.port=port;
    }
    @Override
    public void run() {
        try{
            BufferedReader bufr =new BufferedReader(new InputStreamReader(System.in));
            String line =null;
            //输入要交易的数量
            while ((line=bufr.readLine())!=null){
                if ("stop".equals(line))
                    break;
                //此账户发送交易
                sendBlock block=a.sendTransaction(new account(),Integer.parseInt(line));

                //将账户信息一起发送给对方
                Gson json=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                Gson json2=new Gson();
                String s=json.toJson(a);
                String bl=json2.toJson(block);
                line=line+" "+s+" "+bl;
                byte[] buf =line.getBytes();

                DatagramPacket dp=new DatagramPacket(buf,buf.length, InetAddress.getLocalHost(),port);
                ds.send(dp);
            }
        }
        catch (Exception e){
            throw new RuntimeException("发送失败");
        }


    }
}
class Receive implements Runnable{
    final account a;
    public DatagramSocket ds;
    Receive(DatagramSocket ds,account a){
        this.a=a;
        this.ds=ds;

    }

    @Override
    public void run() {
        try{
            while (true){
                byte[] buf =new byte[1024];
                DatagramPacket dp=new DatagramPacket(buf,buf.length);
                ds.receive(dp);

                String ip=dp.getAddress().getHostAddress();
                String data=new String(dp.getData(),0,dp.getLength());
                String[] ss=data.split(" ");
                Gson json=new Gson();
                int amount=Integer.parseInt(ss[0]);
                account sou=json.fromJson(ss[1],account.class);
                sendBlock block=json.fromJson(ss[2],sendBlock.class);
                this.a.receiveTransaction(sou,block,amount);
                System.out.println(ip+":"+data+ss[0]+" "+ss[1]);

            }

        }
        catch (Exception e){
            throw new RuntimeException("接受失败");
        }

    }
}

