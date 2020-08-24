package Nano;

import com.google.gson.annotations.Expose;

abstract class Block {
    public String hash;

    static enum type{
        open, send, receive, change
    }
    int work;
    String signature;


}
class openBlock extends Block{
    String address;
    String representative;
    final type own=type.open;
    String calculateHash(){
        String s=StringUtil.applySha256(this.address+this.representative
                +Integer.toString(work) +this.own.toString()+this.signature);
        return s;

    }


    @Override
    public String toString() {
        return "openBlock{" +
                "hash='" + hash + '\'' +
                ", work=" + work +
                ", own=" + own +
                ", signature='" + signature + '\'' +
                ", address='" + address + '\'' +
                ", representative='" + representative + '\'' +
                '}';
    }
}

class sendBlock extends Block{
    String previous;
    int balance;
    String destination;
    final type own=type.send;
    String calculateHash(){
        String s=StringUtil.applySha256(this.previous+Integer.toString(balance)
                +this.destination
                +Integer.toString(work) +this.own.toString()+this.signature);
        return s;
    }

    @Override
    public String toString() {
        return "sendBlock{" +
                "hash='" + hash + '\'' +
                ", work=" + work +
                ", own=" + own +
                ", signature='" + signature + '\'' +
                ", previous='" + previous + '\'' +
                ", balance=" + balance +
                ", destination='" + destination + '\'' +
                ", own=" + own +
                '}';
    }
}
class receiveBlock extends Block{
    String previous;
    String source;
    final type own=type.receive;
    String calculateHash(){
        String s=StringUtil.applySha256(this.previous+this.source
                +Integer.toString(work) +this.own.toString()+this.signature);
        return s;
    }

    @Override
    public String toString() {
        return "receiveBlock{" +
                "hash='" + hash + '\'' +
                ", work=" + work +
                ", own=" + own +
                ", signature='" + signature + '\'' +
                ", previous='" + previous + '\'' +
                ", source='" + source + '\'' +
                ", own=" + own +
                '}';
    }
}
class changeBlock extends Block{
    String previous;
    String representative;
    final type own=type.change;
    String calculateHash(){
        String s=StringUtil.applySha256(this.previous+this.representative
                +Integer.toString(work) +this.own.toString()+this.signature);
        return s;
    }

    @Override
    public String toString() {
        return "changeBlock{" +
                "hash='" + hash + '\'' +
                ", work=" + work +
                ", own=" + own +
                ", signature='" + signature + '\'' +
                ", previous='" + previous + '\'' +
                ", representative='" + representative + '\'' +
                ", own=" + own +
                '}';
    }
}
public class blockdemo {
}
