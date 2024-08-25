package garden;
import java.util.*;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class GardenOfEden {

	public static ArrayList<Block> garden = new ArrayList<Block>(); // blockchain -> "Garden of Eden"
	public static int req = 3; // requirement/difficulty of finding the correct hash\

	public static Wallet walletA;
	public static Wallet walletB;

	public static HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();

	public static float minimumTransaction = 0.1f;
	public static Transaction genesisTransaction;

	public static String uri = "mongodb+srv://eden:garden777@gardenblocks.hht9x.mongodb.net/";
	public static MongoClient mongoClient = MongoClients.create(uri);


	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());

		walletA = new Wallet();
		walletB = new Wallet();
		Wallet coinbase = new Wallet();

		genesisTransaction = new Transaction(coinbase.publicKey, walletA.publicKey, 1000f, null); // create a starting transaction
		genesisTransaction.generateSignature(coinbase.privateKey);
		genesisTransaction.transactionID = "0";
		genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.reciever, genesisTransaction.amount, genesisTransaction.transactionID));
		UTXOs.put(genesisTransaction.outputs.get(0).ID, genesisTransaction.outputs.get(0));

		Block genesis = new Block("0");
		genesis.addTransaction(genesisTransaction); // adding initial transaction to block
		System.out.println("Mining Genesis Block...");
		addBlock(genesis);
		

		// testing
		Block block1 = new Block(genesis.hash);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("\nWalletA is Attempting to send Eden (40) to WalletB...");
		block1.addTransaction(walletA.sendFunds(walletB.publicKey, 250f));
		addBlock(block1);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());
		
		Block block2 = new Block(block1.hash);
		System.out.println("\nWalletA Attempting to send more Eden (1000) than it has...");
		block2.addTransaction(walletA.sendFunds(walletB.publicKey, 2000f));
		addBlock(block2);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());
		
		Block block3 = new Block(block2.hash);
		System.out.println("\nWalletB is Attempting to send Eden (20) to WalletA...");
		block3.addTransaction(walletB.sendFunds(walletA.publicKey, 400));
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		isChainValid();

		System.out.println("Current Blocks in Chain; \n");
		for (Block block : garden) {
            System.out.println(block);
        }

		walletA.viewPastTransactions();
		walletB.viewPastTransactions();
	}

	public static Boolean isChainValid(){
		Block previous;
		Block current;
		String hashTarget = new String(new char[req]).replace('\0', '0');
		HashMap<String,TransactionOutput> tempUTXOs = new HashMap<String,TransactionOutput>(); 
		tempUTXOs.put(genesisTransaction.outputs.get(0).ID, genesisTransaction.outputs.get(0));

		for (int i = 1; i < garden.size() ; i++){
			previous = garden.get(i-1);
			current = garden.get(i);

			if (!((current.prevHash).equals(previous.hash))){
				return false;
			}
			if (!((current.hash).equals(current.calcHash()))){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			if(!current.hash.substring(0, req).equals(hashTarget)){ // confirm the 
				System.out.println("This block hasn't been mined");
				return false;
			}

			TransactionOutput tempOut;
			for(int t=0; t <current.transactions.size(); t++) {
				Transaction currentTransaction = current.transactions.get(t);
				
				if(!currentTransaction.verifySignature()) {
					System.out.println("$ Signature on Transaction(" + t + ") is Invalid");
					return false; 
				}
				if(currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
					System.out.println("$ Inputs are note equal to outputs on Transaction(" + t + ")");
					return false; 
				}
				
				for(TransactionInput input: currentTransaction.inputs) {	
					tempOut = tempUTXOs.get(input.transactionOutputID);
					
					if(tempOut == null) {
						System.out.println("$ Referenced input on Transaction(" + t + ") is Missing");
						return false;
					}
					
					if(input.UTXO.balance != tempOut.balance) {
						System.out.println("$ Referenced input Transaction(" + t + ") value is InvalID");
						return false;
					}
					
					tempUTXOs.remove(input.transactionOutputID);
				}
				
				for(TransactionOutput output: currentTransaction.outputs) {
					tempUTXOs.put(output.ID, output);
				}
				
				if( currentTransaction.outputs.get(0).reciever != currentTransaction.reciever) {
					System.out.println("#Transaction(" + t + ") output reciepient is not who it should be");
					return false;
				}
				if( currentTransaction.outputs.get(1).reciever != currentTransaction.sender) {
					System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
					return false;
				}
				
			}
		}
		System.out.println("The Garden of Eden is currently valid!");
		return true;
	}

	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(req);
		garden.add(newBlock);

		MongoDatabase mongodb = mongoClient.getDatabase("Garden");
		MongoCollection collection = mongodb.getCollection("blocks");
		Document document = new Document(newBlock.prevHash, newBlock.getMerk());

		collection.insertOne(document);
	}
}