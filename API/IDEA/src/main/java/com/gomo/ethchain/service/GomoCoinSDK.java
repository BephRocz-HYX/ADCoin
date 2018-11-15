package com.gomo.ethchain.service;

import com.gomo.ethchain.contract.__GomoCoin_sol_GomoToken;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigInteger;

public class GomoCoinSDK {
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(10000);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(100000);
    private static String GomoTokenAddress;
    private static Web3j web3;

    public static String getGomoTokenAddress() {
        return GomoTokenAddress;
    }

    public static Web3j getWeb3() {
        return web3;

    }

    /**
     * 初始化环境

     *
     * @param ip               以太坊节点
     * @param gomoTokenAddress 智能合约地址
     * @throws Exception
     */
    public static void init(String ip, String gomoTokenAddress) throws Exception {
        GomoTokenAddress = gomoTokenAddress;
        web3 = Web3j.build(new HttpService(ip));
    }

    /**
     * 管理员转账gomo币给用户
     *
     * @param accountTo 用户钱包地址
     * @param number    gomo币数量
     * @param password  管理员账户密码
     * @param keyPath   管理员账户私钥文件全路径
     * @return 交易Hash值
     * @throws Exception
     */
    public static String transfer(String accountTo, String number, String password, String keyPath) throws Exception {
        String transactionHash = "";
        BigInteger value = Convert.toWei(number, Convert.Unit.ETHER).toBigInteger();
        String account = web3.ethAccounts().send().getAccounts().get(0);
        Credentials credentials = WalletUtils.loadCredentials(password, keyPath);
        __GomoCoin_sol_GomoToken contract = __GomoCoin_sol_GomoToken
                .load(GomoTokenAddress, web3, credentials, GAS_PRICE, GAS_LIMIT);
        TransactionReceipt receipt = contract.transfer(accountTo, value).send();
        while (true) {
            transactionHash = receipt.getTransactionHash();
            Thread.sleep(100);
            if (!transactionHash.isEmpty())
                break;
        }
        return transactionHash;
    }

    /**
     * 用户账户之间转账gomo币
     *
     * @param accountFrom 转出方钱包地址
     * @param accountTo   接收方钱包地址
     * @param number      gomo币数量
     * @param password    转出方账户密码
     * @param keyPath     转出方账户私钥文件全路径
     * @return 交易Hash值
     * @throws Exception
     */
    public static String transferFrom(String accountFrom, String accountTo, String number, String password, String keyPath) throws Exception {
        String transactionHash = "";
        Credentials credentials = WalletUtils.loadCredentials(password, keyPath);//解锁账户
        __GomoCoin_sol_GomoToken ct = __GomoCoin_sol_GomoToken
                .load(GomoTokenAddress, web3, credentials, GAS_PRICE, GAS_LIMIT);
        BigInteger value = Convert.toWei(number, Convert.Unit.ETHER).toBigInteger();
        TransactionReceipt receipt = ct.transferFrom(accountFrom, accountTo, value).send();
        while (true) {
            transactionHash = receipt.getTransactionHash();
            Thread.sleep(100);
            if (!transactionHash.isEmpty())
                break;
        }
        return transactionHash;
    }

    /**
     * 账户余额
     *
     * @param account 账户地址
     * @return gomo币数量
     * @throws Exception
     */
    public static BigInteger balanceOf(String account) throws Exception {
        Credentials credentials = Credentials.create(account);//解锁账户
        __GomoCoin_sol_GomoToken ct = __GomoCoin_sol_GomoToken
                .load(GomoTokenAddress, web3, credentials, GAS_PRICE, GAS_LIMIT);
        BigInteger num = ct.balanceOf(account).send();
        BigInteger coinNum = Convert.fromWei(String.valueOf(num), Convert.Unit.ETHER).toBigInteger();
        return coinNum;
    }

    /**
     * 暂停合约
     *
     * @param password 管理员账户密码
     * @param keyPath  管理员账户私钥文件全路径
     * @throws Exception
     */
    public static void pause(String password, String keyPath) throws Exception {
        String account = web3.ethAccounts().send().getAccounts().get(0);
        Credentials credentials = WalletUtils.loadCredentials(password, keyPath);//解锁账户
        __GomoCoin_sol_GomoToken contract = __GomoCoin_sol_GomoToken
                .load(GomoTokenAddress, web3, credentials, GAS_PRICE, GAS_LIMIT);
        TransactionReceipt receipt = contract.pause().send();
        System.out.println("交易Hash:" + receipt.getTransactionHash());
    }

    /**
     * 重启合约
     *
     * @param password 管理员账户密码
     * @param keyPath  管理员账户私钥文件全路径
     * @throws Exception
     */
    public static void unpause(String password, String keyPath) throws Exception {
        String account = web3.ethAccounts().send().getAccounts().get(0);
        Credentials credentials = WalletUtils.loadCredentials(password, keyPath);//解锁账户
        __GomoCoin_sol_GomoToken contract = __GomoCoin_sol_GomoToken
                .load(GomoTokenAddress, web3, credentials, GAS_PRICE, GAS_LIMIT);
        TransactionReceipt receipt = contract.unpause().send();
        System.out.println("交易Hash:" + receipt.getTransactionHash());
    }

    /**
     * 转让管理权限
     *
     * @param newOwner 新管理员账户地址
     * @param password 原管理员账户密码
     * @param keyPath  原管理员账户私钥文件全路径
     * @throws Exception
     */
    public static void transferOwnership(String newOwner, String password, String keyPath) throws Exception {
        String account = web3.ethAccounts().send().getAccounts().get(0);
        Credentials credentials = WalletUtils.loadCredentials(password, keyPath);//解锁账户
        __GomoCoin_sol_GomoToken contract = __GomoCoin_sol_GomoToken
                .load(GomoTokenAddress, web3, credentials, GAS_PRICE, GAS_LIMIT);
        TransactionReceipt receipt = contract.transferOwnership(newOwner).send();
        System.out.println("交易Hash:" + receipt.getTransactionHash());
    }
}
