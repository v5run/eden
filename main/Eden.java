package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class Eden {

    @SpringBootApplication
	public static void main(String[] args) {
		
		Block genesisBlock = new Block("Block1", "0");
		System.out.println("Hash for block 1 : " + genesisBlock.hash);
		
		Block secondBlock = new Block("Block2",genesisBlock.hash);
		System.out.println("Hash for block 2 : " + secondBlock.hash);
		
		Block thirdBlock = new Block("Block3",secondBlock.hash);
		System.out.println("Hash for block 3 : " + thirdBlock.hash);
		
	}
}