package garden;
import java.security.PublicKey;

// show final balance, acts as proof of balance
public class TransactionOutput {
    public String ID;
    public PublicKey reciever; // new owner of coins
    public float balance; // amount of coins they own -> outputs specify where the money goes (the whole balance since partitions of utxo's cannot be done)
    public String parentTransactionID;

    public TransactionOutput(PublicKey reciepient, float value, String parentTransactionId){
        this.reciever = reciepient;
        this.balance = value;
        this.parentTransactionID = parentTransactionId;
        this.ID = StringUtil.sha256(StringUtil.getStringFromKey(reciepient) + Float.toString(value) + parentTransactionId);
    }

    public boolean isMine(PublicKey pk){
        return (pk == reciever);
    }
}
