package garden;

import java.util.Date;
import java.util.ArrayList;

public class Block {

    public String hash;
    public String prevHash;
    private String merkleRoot;
    private Long time;
    private int nonce; // the "special number" needed to crack the code
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>();

    public Block(String prevHash){
        this.prevHash = prevHash;
        this.time = new Date().getTime();
        this.hash = calcHash();
    }

    public String calcHash(){
        String digest = prevHash + Long.toString(time) + Integer.toString(nonce) + merkleRoot;
        String newHash = StringUtil.sha256(digest); 
        return newHash;
    }

    @Override
    public String toString() {
        return "Block {" +
                "hash='" + hash + '\'' +
                ", previousHash='" + prevHash + '\'' +
                ", merkleroot='" + merkleRoot + '\'' +
                ", timeStamp=" + time +
                '}' + "\n";
    }

    public void mineBlock(int difficulty) {
        merkleRoot = StringUtil.getMerkleRoot(transactions);
		String target = new String(new char[difficulty]).replace('\0', '0');
		while(!hash.substring(0, difficulty).equals(target)) {
			this.nonce ++;
			hash = calcHash();
		}
		System.out.println("Block Mined! : " + hash + "\nN-once: " + Integer.toString(nonce));
	}

    public boolean addTransaction(Transaction transaction){

        if (transaction == null) return false;
        if ((prevHash!= "0")&&((transaction.canTransaction() != true))){ // genesis block (doesnt have a prev hash and cannot process transaction)
            System.out.println("$ Discarded. Transaction failed to process.");
            return false;
        }

        transactions.add(transaction);
        System.out.println("Transaction added to the Garden!");
        return true;
    }

}
