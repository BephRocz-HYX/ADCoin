package com.gomo.ethchain.test;

import com.gomo.ethchain.client.Web3JClient;
import com.gomo.ethchain.contract.__GomoCoin_sol_GomoToken;
import com.gomo.ethchain.service.GomoCoinSDK;
import com.gomo.ethchain.util.PropertiesUtil;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

public class Test {
    private static Web3j web3 = Web3j.build(new HttpService("http://192.168.4.19:8546/"));
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(10000000);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(100000000);


    public static String deployContract() throws Exception {
        String address = "";
        Credentials credentials = WalletUtils.loadCredentials(
                "1234",
                "/home/huangyixuan/blockchain/eth/testnet/keystore/UTC--2018-06-27T06-32-25.133524783Z--f4f8ca53fb8397e4d0fd901b3e6dc38de279e8be");
        __GomoCoin_sol_GomoToken contract = __GomoCoin_sol_GomoToken.deploy
                (web3, credentials, GAS_PRICE, GAS_LIMIT)
                .send();
        web3.ethMining().send();
        while (true) {
            address = contract.getContractAddress();
            Thread.sleep(100);
            if (!address.isEmpty())
                break;
        }

        return address;
    }

    public static void testSDK() throws Exception {
        String password = "1234";
        String keyPath = "/home/huangyixuan/blockchain/eth/test/keystore/UTC--2018-05-03T08-52-28.407217173Z--2a385d78f43bb41f819b8ae76e25ba4523f9f123";
//        String keyPath="H:\\UTC--2018-05-03T08-52-28.407217173Z--2a385d78f43bb41f819b8ae76e25ba4523f9f123";
        //初始化环境（服务器内网ip）只需调用一次
        GomoCoinSDK.init("http://192.168.4.19:8545/", "0xd96112606453068480c4683405d3d4541e3555a5");
        //开始调用GomoCoinSDK的方法，具体用途写在注释里了
        System.out.println("转账前管理员账户gomo币数量：" + GomoCoinSDK.balanceOf("0x2a385d78f43bb41f819b8ae76e25ba4523f9f123"));
        System.out.println("转账前用户账户gomo币数量：" + GomoCoinSDK.balanceOf("0x548ade47ea6708d31044cd30e7335116febfa989"));
        System.out.println("转账10个gomo币");
        System.out.println("转账交易Hash:" + GomoCoinSDK.transfer("0x548ade47ea6708d31044cd30e7335116febfa989", "10", password, keyPath));//管理员转账给用户
        System.out.println("转账后用户账户gomo币数量：" + GomoCoinSDK.balanceOf("0x548ade47ea6708d31044cd30e7335116febfa989"));
        System.out.println("转账后管理员账户gomo币数量：" + GomoCoinSDK.balanceOf("0x2a385d78f43bb41f819b8ae76e25ba4523f9f123"));
        //...其他函数调用

    }

    public static void main(String[] args) throws Exception {
        testSDK();

    }
}
