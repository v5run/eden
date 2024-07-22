package garden;
import java.util.*;

public class GardenOfEden {

	public static ArrayList<Block> garden = new ArrayList<Block>(); // blockchain -> "Garden of Eden"

	public static void main(String[] args) {
		
		Block genesisBlock = new Block("Block1", "0");
		//System.out.println("Hash for block 1 : " + genesisBlock.hash);
		garden.add(genesisBlock);
		
		Block secondBlock = new Block("Block2",genesisBlock.hash);
		//System.out.println("Hash for block 2 : " + secondBlock.hash);
		garden.add(secondBlock);
		
		Block thirdBlock = new Block("Block3",secondBlock.hash);
		//System.out.println("Hash for block 3 : " + thirdBlock.hash);
		garden.add(thirdBlock);

		for (Block block : garden) {
            System.out.println(block);
        }
		isChainValid();
	}

	public static Boolean isChainValid(){
		Block previous;
		Block current;

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
		}
		System.out.println("Chain is valid!");
		return true;
	}
}