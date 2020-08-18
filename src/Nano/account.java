package Nano;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.LinkedList;
import java.lang.Exception;
import java.util.Random;
import com.google.gson.annotations.*;
public class account {

    public PrivateKey privateKey;

    public PublicKey publicKey;

    @Expose public  final String address;

    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            // Initialize the key generator and generate a KeyPair
            keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
            KeyPair keyPair = keyGen.generateKeyPair();
            // Set the public and private keys from the keyPair
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    String signature;
     public int balance;//余额

    //作为代表的权重，默认为0
    @Expose public int representNum=0;
    //代表的账户地址
    @Expose String representAddress;

    LinkedList<Block> ownBlockchain=new LinkedList<>();
    account(){
        generateKeyPair();
        //在构建器里对address进行初始化
        address=StringUtil.getStringFromKey(this.publicKey);
    }

    //开户函数,首个账户需要自己开户
    void openTransaction()  {
        if(!ownBlockchain.isEmpty())
            throw new RuntimeException();
        else{
        openBlock a=new openBlock();
        //随机给定初始的余额
        Random rand=new Random();
        int i=rand.nextInt(10)+1;
        a.work=100*i;
        this.balance=a.work;
        //创世账户减少相应数量
        genesis.genesisBalance-=a.work;
        a.address=this.address;
        a.representative=this.address;
        a.hash=a.calculateHash();
        this.ownBlockchain.add(a);
        this.representNum=1;
        this.representAddress=this.address;
        genesis.genesisAccount.add(this.address);
        }


    }
    //由别的账户发来的交易进行开户
    void openTransaction(account source,int amount){
        if(!ownBlockchain.isEmpty())
            throw new RuntimeException();
        else{
            openBlock a=new openBlock();
            a.work=amount;
            this.balance=a.work;
            a.address=this.address;
            a.representative=source.address;
            a.signature=source.generateSignature(this,amount);
            a.hash=a.calculateHash();
            this.ownBlockchain.add(a);
            this.representNum=0;
            //此账户的代表账户为发送交易方的账户
            this.representAddress=source.address;
            //发送交易方代表权重加1
            source.representNum++;
            genesis.genesisAccount.add(this.address);
        }
    }
    //发送交易函数
    void sendTransaction(account des,int amount){
        if (ownBlockchain.isEmpty())
            throw new RuntimeException();
        else{
            //将sendBlock添加到发送方的区块链中
            sendBlock a=new sendBlock();
            a.previous=this.ownBlockchain.getLast().hash;
            a.destination=des.address;
            a.balance=this.balance;
            a.work=amount;
            a.signature=generateSignature(des,amount);
            a.hash=a.calculateHash();
            this.ownBlockchain.add(a);
            this.balance-=amount;
           //如果目标账户未开户，对目标账户中使用此交易开户





        }


    }
    //将接受交易与发送交易分开，如果没开户，进行开户。
    void receiveTransaction(account source,int amount){
        if(ownBlockchain.isEmpty()){
            openTransaction(source,amount);

        }
        else {
            receiveBlock r = new receiveBlock();
            r.previous = this.ownBlockchain.getLast().hash;
            r.source = source.ownBlockchain.getLast().hash;
            r.work = amount;
            r.signature = source.ownBlockchain.getLast().signature;
            r.hash = r.calculateHash();
            this.ownBlockchain.add(r);
            this.balance += amount;
        }


    }

//更换代表交易
    void changeTransaction(account b){
        changeBlock c=new changeBlock();
        c.previous=this.ownBlockchain.getLast().hash;
        c.representative=b.address;
        c.signature=generateSignature(b,0);
        c.work=0;
        c.hash=c.calculateHash();
        this.ownBlockchain.add(c);
        //如果该账户之前的代表是自己，则代表权重减1
        if(this.representAddress==this.address)
            this.representNum--;
        //代表地址变为目标账户地址
        this.representAddress=b.address;
        b.representNum++;


    }
    //对目标账户的交易产生数字签名
    public String generateSignature(account b,int value){
        String data=this.address+b.address+String.valueOf(value);
        byte[] output=StringUtil.applyECDSASig(this.privateKey,data);
        return String.valueOf(output);


    }

    public static boolean verifySignature(account a,sendBlock send){
        String data=a.address+send.destination+String.valueOf(send.work);
        return StringUtil.verifyECDSASig(a.publicKey,data,send.signature.getBytes());




    }


}
