package garden;

import java.util.Date;

public class Block {

    public String hash;
    public String prevHash;
    private String data;
    private Long time;
    private int nonce; // the "special number" needed to crack the code

    public Block(String data, String prevHash){
        this.data = data;
        this.prevHash = prevHash;
        this.time = new Date().getTime();
        this.hash = calcHash();
    }

    public String calcHash(){
        String digest = prevHash + Long.toString(time) + Integer.toString(nonce) + data;
        String newHash = StringUtil.sha256(digest); 
        return newHash;
    }

    @Override
    public String toString() {
        return "Block {" +
                "hash='" + hash + '\'' +
                ", previousHash='" + prevHash + '\'' +
                ", data='" + data + '\'' +
                ", timeStamp=" + time +
                '}' + "\n";
    }

    public void mineBlock(int difficulty) {
        this.nonce = 0;
		String target = new String(new char[difficulty]).replace('\0', '0');
		while(!hash.substring(0, difficulty).equals(target)) {
			this.nonce ++;
			hash = calcHash();
		}
		System.out.println("Block Mined! : " + hash + "\nN-once: " + Integer.toString(nonce));
	}

}
