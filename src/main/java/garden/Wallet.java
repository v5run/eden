package garden;
import java.security.*;

public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;

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
}
