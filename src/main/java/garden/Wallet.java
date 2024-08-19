package garden;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.chart.LineChart.SortingPolicy;


public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;
	public ArrayList<Transaction> transactionHistory = new ArrayList<Transaction>();
	public HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); //only UTXOs owned by this wallet.

	// wallet balance = sum of UTXOs
    public Wallet() {
        generateKeyPair();
    }

    public void generateKeyPair(){
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC"); // use elliptic curve alg w/ bounc
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1"); // 192 bits security
			
			keyGen.initialize(ecSpec, random);
        	KeyPair keyPair = keyGen.generateKeyPair();
	    	
	    	privateKey = keyPair.getPrivate();
	        publicKey = keyPair.getPublic();
			
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
    }

	// summation of all utxos owed to me = my balance
	public float getBalance(){
		float total = 0;

		for (Map.Entry<String, TransactionOutput> item: GardenOfEden.UTXOs.entrySet()){
			TransactionOutput UTXO = item.getValue();

			if (UTXO.isMine(publicKey)){ // if output belongs to me
				UTXOs.put(UTXO.id,UTXO); // then add it to list of unspent transactions.
            	total += UTXO.value ; 
            }
		}
		return total;
	}

	// send funds if the amount is less than or equal to balance. 
	public Transaction sendFunds(publicKey reciepient, float amount){

		if (getBalance() < amount){
			System.out.println("$ Insufficient funds in Wallet ...  Transaction Discarded.");
			return null;
		}

		ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
		float total = 0;

		// create a valid input chain by appending as many needed inputs as the amount required. break when total greater than amount
		for (Map.Entry<String, TransactionOutput> item: GardenOfEden.UTXOs.entrySet()){
			TransactionOutput UTXO = item.getValue();
            total += UTXO.value ; 
            inputs.add(TransactionInput(UTXO.ID));
			if (total>amount) break;
		}

		Transaction newTransaction = new Transaction(publicKey, publicKey, amount, inputs);
		newTransaction.generateSignature(privateKey);

		// inputs are now no longer valid UTXOs therefore need to remove from memory
		for (TransactionInput input : inputs){
			UTXOs.remove(input.transactionOutputID);
		}

		// maintain prior transactions from this wallet
		transactionHistory.add(newTransaction);
		return newTransaction;

	}


}
