import java.util.*;
import java.security.MessageDigest;

class Block{
    public int index;
    public long timeStamp;
    public String data;
    public String prevHash;
    public String hash;
    public int nonce;

    public Block(int index, long timeStamp, String data, String prevHash){
        this.index=index;
        this.timeStamp=timeStamp;
        this.data=data;
        this.prevHash=prevHash;
        this.nonce=0;
        this.hash=calHash();
    }
    public String calHash(){
        String h = index + Long.toString(timeStamp) + data + prevHash +nonce;
        return applySha256(h);
    }
    public static String applySha256(String h){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(h.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for(byte b: hash){
                String hex = Integer.toHexString(0xff & b);
                if(hex.length()==1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public String toString(){
        return "\nBlock " + index +
               "\nTimestamp     : " + timeStamp +
               "\nData          : " + data +
               "\nNonce         : " + nonce +
               "\nPrevious Hash : " + prevHash +
               "\nHash          : " + hash + "/n";
    }

    public static boolean isChainValid(List<Block> chain) {
    for (int i = 1; i < chain.size(); i++) {
        Block current = chain.get(i);
        Block previous = chain.get(i - 1);

        if (!current.prevHash.equals(previous.hash)) {
            return false;
        }
        if (!current.hash.equals(current.calHash())) {
            return false;
        }
    }
    return true;
}

}

public class App{
    public static void main(String[] args){
        List<Block> blockChain= new ArrayList<>();

        Block genesisBlock = new Block(0, System.currentTimeMillis(), "Genesis Block", "0");
        blockChain.add(genesisBlock);

        Block block1 = new Block(1, System.currentTimeMillis(), "Second Block", genesisBlock.hash);
        blockChain.add(block1);

        Block block2 = new Block(2, System.currentTimeMillis(), "Third Block", block1.hash);
        blockChain.add(block2);

        System.out.println("Initial Blockchain: ");
        for(Block block : blockChain){
            System.out.println(block);
        }
        
        System.out.println("Tampering Block 1's data....");
        block1.data = "Hack Data";
        block1.hash = block1.calHash();

        System.out.println("\n Blockchain After Tampering:");
        for(Block block : blockChain){
            System.out.println(block);
        }

        System.out.println("\n✅ Is Blockchain Valid? " + Block.isChainValid(blockChain));

    }
}
