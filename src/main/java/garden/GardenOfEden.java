package garden;
import java.util.*;

public class GardenOfEden {

	public static ArrayList<Block> garden = new ArrayList<Block>(); // blockchain -> "Garden of Eden"
	public static int req = 7; // requirement/difficulty of finding the correct hash

	public static void main(String[] args) {
		
		Block genesisBlock = new Block("Block1", "0");
		//System.out.println("Hash for block 1 : " + genesisBlock.hash);
		garden.add(genesisBlock);
		System.out.println("Trying to Mine block 1... ");
		garden.get(0).mineBlock(req);
		
		Block secondBlock = new Block("Block2",genesisBlock.hash);
		//System.out.println("Hash for block 2 : " + secondBlock.hash);
		garden.add(secondBlock);
		System.out.println("Trying to Mine block 2... ");
		garden.get(1).mineBlock(req);
		
		Block thirdBlock = new Block("Block3",secondBlock.hash);
		//System.out.println("Hash for block 3 : " + thirdBlock.hash);
		garden.add(thirdBlock);
		System.out.println("Trying to Mine block 3... ");
		garden.get(2).mineBlock(req);

		isChainValid();

		for (Block block : garden) {
            System.out.println(block);
        }
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