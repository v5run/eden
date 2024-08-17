package garden;
import java.util.*;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class GardenOfEden {

	public static ArrayList<Block> garden = new ArrayList<Block>(); // blockchain -> "Garden of Eden"
	public static int req = 7; // requirement/difficulty of finding the correct hash\

	public static Wallet walletA;
	public static Wallet walletB;

	public static HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();

	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());

		walletA = new Wallet();
		walletB = new Wallet();

		System.out.println("Private and public keys:");
		System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
		System.out.println(StringUtil.getStringFromKey(walletA.publicKey));

		//Testing transactions between wallets

		Transaction testTrans = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
		testTrans.generateSignature(walletA.privateKey);

		//Verify the signature works and verify it from the public key
		System.out.println("Is signature verified?");
		System.out.println(testTrans.verifySignature());
	}

	public static Boolean isChainValid(){
		Block previous;
		Block current;
		String hashTarget = new String(new char[req]).replace('\0', '0');

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
		}
		System.out.println("The Garden of Eden is currently valid!");
		return true;
	}
}