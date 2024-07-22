package garden;

import java.util.Date;

public class Block {

    public String hash;
    public String prevHash;
    private String data;
    private Long time;

    public Block(String data, String prevHash){
        this.data = data;
        this.prevHash = prevHash;
        this.time = new Date().getTime();
        this.hash = calcHash();
    }

    public String calcHash(){
        String digest = prevHash + Long.toString(time) + data;
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

}
