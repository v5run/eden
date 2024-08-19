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

    public boolean canTransaction(){
        if (!verifySignature()){
            System.out.println("$ Transaction Signature Failed Verification");
            return false;
        }

        for (TransactionInput i : inputs){
            i.UTXO = GardenOfEden.UTXOs.get(i.transactionOutputID); // demeter got violated bare icl
        }

        if(getInputsValue() < GardenOfEden.minimumTransaction) {
			System.out.println("$ Transaction Inputs too small: " + getInputsValue());
			return false;
		}

        float remaining = getInputsValue() - amount;
        transactionID = calculateHash();
        outputs.add(new TransactionOutput(this.reciever, amount , transactionID)); //send value to recipient
		outputs.add(new TransactionOutput(this.sender, remaining , transactionID)); //send the left over 'change' back to sender

        for(TransactionOutput o : outputs) {
			GardenOfEden.UTXOs.put(o.ID, o);
		}
		
		//remove transaction inputs from UTXO lists as spent:
		for(TransactionInput i : inputs) {
			if(i.UTXO == null) continue; //if Transaction can't be found skip it 
			GardenOfEden.UTXOs.remove(i.UTXO.ID);
		}
		
		return true;
    }

    //returns sum of inputs(UTXOs) values
	public float getInputsValue() {
		float total = 0;
		for(TransactionInput i : inputs) {
			if(i.UTXO == null) continue; //if Transaction can't be found skip it 
			total += i.UTXO.value;
		}
		return total;
	}

    //returns sum of outputs:
	public float getOutputsValue() {
		float total = 0;
		for(TransactionOutput o : outputs) {
			total += o.value;
		}
		return total;
	}
	

}
