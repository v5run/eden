package garden;

public class TransactionInput {
    // inputs specify which outputs to spend
    public String transactionOutputID; // reference last output, meaning current balance of utxo
    public TransactionOutput UTXO;

    public TransactionInput(String transactionOutputId){ // need outputs from prev transactions to specify what to spend (since return of prev is spending of current)
        this.transactionOutputID = transactionOutputId;
    }
    
}
