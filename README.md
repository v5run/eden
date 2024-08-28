# Garden of Eden - Blockchain Simulation with Secure Transactions

This project, **Garden of Eden**, is a blockchain simulation built in Java that showcases a secure transaction system using ECDSA for digital signatures and SHA-256 for hashing. The project integrates MongoDB for storing blockchain data and demonstrates the core functionalities of blockchain technology, such as transaction creation, validation, mining, and decentralized ledger maintenance.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## Overview

The **Garden of Eden** project is a Java-based blockchain simulation that implements core blockchain functionalities, including secure transactions, wallet management, and block mining. This project aims to provide an educational tool for understanding how blockchain works, with a focus on cryptographic security, decentralized transaction management, and data integrity.

## Features

- **Secure Transaction System**: Utilizes ECDSA for digital signatures and SHA-256 for hashing, ensuring data integrity and authenticity in blockchain transactions.
- **Wallet Functionality**: Implements public/private key pairs for users to securely send and receive funds while maintaining a decentralized ledger of transactions.
- **Decentralized Ledger**: Manages a blockchain ledger that validates, mines, and stores blocks in a distributed fashion.
- **Integration with MongoDB**: Efficiently stores and manages transaction data, blocks, and other blockchain-related information using MongoDB.
- **Block Mining and Validation**: Implements Proof-of-Work (PoW) consensus mechanism for mining new blocks and validating transactions.

## Technology Stack

- **Java**: Core programming language for implementing blockchain logic and features.
- **MongoDB**: NoSQL database for storing blockchain data.
- **BouncyCastle**: Cryptographic library for ECDSA digital signatures.
- **SHA-256**: Secure Hash Algorithm for hashing data.
- **Maven**: Build automation tool for managing dependencies and project build lifecycle.

## Getting Started

### Prerequisites

To run this project, you'll need to have the following installed:

- **Java 21** or later
- **Maven 3.6.0** or later
- **MongoDB** (local or cloud-based instance)
- **Git** for version control

### Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/v5run/eden.git
   cd eden/
2. Set Up MongoDB
Make sure you have a running instance of MongoDB. If using MongoDB Atlas, create a cluster and get the connection URI.

3. **Configure MongoDB Connection**
   
Update the MongoDB connection URI in GardenOfEden.java: (Feel Free to use your own User/Pass or eden:garden777)
 ```bash
   public static String uri = "mongodb+srv://<username>:<password>@<cluster-url>/";
 ```
4. Build the Project with Maven
```bash
   mvn clean package
 ```
5. Run the Application
```bash
   java -jar target/eden-1.0.jar
 ```

## Usage
Once the application is running, it will simulate the creation of wallets, transactions, and blocks within a blockchain network. The following functionalities are demonstrated:

- **Creating Wallets**: Two wallets (A and B) are created and initialized with a balance.
- **Sending Funds**: Wallets can send funds to each other securely, with transactions being added to new blocks.
- **Mining Blocks**: New blocks are mined using a Proof-of-Work algorithm and added to the blockchain.
- **Viewing Blockchain Integrity**: The system checks and displays the integrity and validity of the entire blockchain.

## Project Structure
The project is structured as follows:
```bash
   /src/main/java/garden/
|-- Block.java
|-- GardenOfEden.java
|-- Transaction.java
|-- Wallet.java
|-- TransactionInput.java
|-- TransactionOutput.java
|-- ...
 ```
- **Block.java**: Represents a block in the blockchain.
- **GardenOfEden.java**: Main class to run the blockchain simulation.
- **Transaction.java**: Represents a transaction with inputs and outputs.
- **Wallet.java**: Handles wallet functionalities, such as sending and receiving funds.
- **TransactionInput.java and TransactionOutput.java**: Handle transaction details and outputs.

## Contributing
Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a feature branch: git checkout -b feature/new-feature.
3. Commit your changes: git commit -m 'Add new feature'.
4. Push to the branch: git push origin feature/new-feature.
5. Open a Pull Request.

## License
This project is licensed under the MIT License. See the `LICENSE` file for more details.
