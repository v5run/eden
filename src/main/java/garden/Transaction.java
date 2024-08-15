package garden;
import java.security.*;
import java.util.ArrayList;

import garden.StringUtil;

public class Transaction {
    public String transactionID;
    public PublicKey sender; // need public addresses of sender and recieptient
    public PublicKey reciever;
    public float amount;
    public byte[] signature;

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
    private static int sequence = 0;

    public Transaction(PublicKey from, PublicKey to, float amount, ArrayList<TransactionInput> inputs){
        this.sender = from;
        this.reciever = to;
        this.amount = amount;
        this.inputs = inputs;
    }

    private String calculateHash(){
        sequence++;
        return StringUtil.sha256(
            StringUtil.getStringFromKey(sender) +
            StringUtil.getStringFromKey(reciever) +
            Float.toString(amount) + sequence
        );
    }

    public void generateSignature(PrivateKey privateKey){
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciever) + Float.toString(amount); // needs to account for sender/rec. keys & value sent
        this.signature = StringUtil.applyECDSASig(privateKey, data);
    }

    public boolean verifySignature(){
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciever) + Float.toString(amount);
        return StringUtil.verifyECDSASig(sender, data, signature);
    }



}
