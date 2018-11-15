package com.gomo.ethchain.service;

import net.sf.json.JSONObject;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.Transaction;

import java.math.BigInteger;
import java.util.Optional;

public class EthSDK {
    private static Web3j web3;

    static {
        web3 = GomoCoinSDK.getWeb3();
    }

    public static BigInteger getBlockNumber() throws Exception {
        BigInteger blockNumber = web3.ethBlockNumber().send().getBlockNumber();
        return blockNumber;
    }

    public static Block getBlockByNumber(String blockNumber) throws Exception {
        Block block = web3.ethGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(Long.valueOf(blockNumber))), true).send().getBlock();
        return block;
    }

    public static Block getBlockByHash(String blockHash) throws Exception {
        Block block = web3.ethGetBlockByHash(blockHash, true).send().getBlock();
        return block;
    }

    public static Optional<Transaction> getTransactionByHash(String transactionHash) throws Exception {
        Optional<Transaction> transaction = web3.ethGetTransactionByHash(transactionHash).send().getTransaction();
        return transaction;
    }

    public static Optional<Transaction> getTransactionByBlockHashAndIndex(String blockHash, BigInteger index) throws Exception {
        Optional<Transaction> transaction = web3.ethGetTransactionByBlockHashAndIndex(blockHash, index).send().getTransaction();
        return transaction;
    }

    public static Optional<Transaction> getTransactionByBlockNumberAndIndex(String blockNumber, BigInteger index) throws Exception {
        Optional<Transaction> transaction = web3.ethGetTransactionByBlockNumberAndIndex(DefaultBlockParameter.valueOf(BigInteger.valueOf(Long.valueOf(blockNumber))), index).send().getTransaction();
        return transaction;
    }

}
