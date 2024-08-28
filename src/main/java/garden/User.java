package garden;
import garden.Wallet;

public class User {
    
    // user has a wallet, wallet has a user
    public Wallet wallet;

    public User(){
        this.wallet = new Wallet();
    }
}
