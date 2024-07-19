package main;
import java.util.Date;

public class Block {

    public String hash;
    public String prevHash;
    private String message;
    private Long time;

    public Block(String data, String prevHash){
        this.data = data;
        this.prevHash = prevHash;
        this.time = new Date().getTime();
        this.hash = calcHash();
    }

    private String calcHash(){

        String digest = prevHash + long.toString(time) + data;
        String newHash = StringUtil.sha256(digest);
        return newHash;
    }

}