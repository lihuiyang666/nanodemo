package Nano;
//测试创世账户中会不会相应改变数据
public class exam4 {
    public static void main(String[] args){
        account a=new account();
        a.openTransaction();
        System.out.println(genesis.genesisAccount.get(genesis.genesisAccount.size()-1).ownBlockchain);
        account b=new account();
        sendBlock block=a.sendTransaction(b,10);
        b.receiveTransaction(a,block,10);
        System.out.println(genesis.genesisAccount.get(0).ownBlockchain);
        System.out.println(genesis.genesisAccount.get(genesis.genesisAccount.size()-1).ownBlockchain);
    }
}
