package Nano;

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
            while ((line=bufr.readLine())!=null){
                if ("stop".equals(line))
                    break;
                a.sendTransaction(new account(),100);
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
                System.out.println(ip+":"+data);

            }

        }
        catch (Exception e){
            throw new RuntimeException("接受失败");
        }

    }
}

