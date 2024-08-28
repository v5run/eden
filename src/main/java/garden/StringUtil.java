package garden;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.ArrayList;

public class StringUtil {

    public static String sha256(String input){

        try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        
			
			byte[] hash = digest.digest(input.getBytes("UTF-8"));	        
			StringBuffer hexString = new StringBuffer(); // contain hash as hexi
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
    }

	public static byte[] applyECDSASig(PrivateKey privateKey, String input) {
		Signature dsa;
		byte[] output = new byte[0];
		try {
			dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey); //sign signature
			byte[] strByte = input.getBytes();
			dsa.update(strByte);
			byte[] realSig = dsa.sign();
			output = realSig;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return output;
	}

	public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
		try {
			Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
			ecdsaVerify.initVerify(publicKey);
			ecdsaVerify.update(data.getBytes());
			return ecdsaVerify.verify(signature);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	public static String getMerkleRoot(ArrayList<Transaction> transactions) { // merkle hash needs to go into block header to allow for tamper-proof chains : credit to Keifer Kif
		int count = transactions.size();
		ArrayList<String> previousTreeLayer = new ArrayList<>();
	
		for (Transaction t : transactions) { // add leafs of tree (single transactions) to initiate base layer
			previousTreeLayer.add(t.transactionID);
		}
	
		// While there is more than one element, continue hashing pairs
		while (count > 1) {
			ArrayList<String> currentTreeLayer = new ArrayList<>();
	
			for (int i = 0; i < previousTreeLayer.size(); i += 2) {
				// If this is the last element and the list size is odd, duplicate it
				String left = previousTreeLayer.get(i);
				String right = (i + 1 < previousTreeLayer.size()) ? previousTreeLayer.get(i + 1) : left; 
	
				// Combine the pair and hash it
				currentTreeLayer.add(sha256(left + right)); // merge pairs of previous layer nodes together
			}
	
			count = currentTreeLayer.size(); // loop condition, count will approach 1, which is the merkle root
			previousTreeLayer = currentTreeLayer; // move up to the next layer
		}
	
		// If the last checked layer size is 1, set merkleRoot = first item, else ""
		String merkleRoot = (previousTreeLayer.size() == 1) ? previousTreeLayer.get(0) : "";
		return merkleRoot;
	}
	

	public static String getDifficultyString(int difficulty){
		return new String(new char[difficulty]).replace('\0', '0'); // setting up difficulty req (x amount of 0's to start hash); if diff=5, return='00000'
	}

}