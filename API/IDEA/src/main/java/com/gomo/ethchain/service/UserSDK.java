package com.gomo.ethchain.service;

import net.sf.json.JSONObject;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;


import java.io.File;

public class UserSDK {

    /**
     * 创建账户
     *
     * @param password 账户密码
     * @return 账户地址、私钥
     * @throws Exception
     */
    public static JSONObject creatUser(String password) throws Exception {
        String filePath = "/home/huangyixuan/blockchain/eth/test/keystore/";
        String fileName = WalletUtils.generateNewWalletFile(password, new File(filePath), false);
        Credentials credentials = WalletUtils.loadCredentials(password, filePath + fileName);
        String privateKey = credentials.getEcKeyPair().getPrivateKey().toString();
        JSONObject data = new JSONObject();
        data.element("address", credentials.getAddress()).element("fileName", fileName)
                .element("privateKey", privateKey).element("password", password).element("filePath", filePath);
        return data;
    }
}
