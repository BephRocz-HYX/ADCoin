package com.gomo.ethchain.client;

import com.gomo.ethchain.util.PropertiesUtil;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class Web3JClient {

    private static String ip;

    public static void setIp(String ip) {
        Web3JClient.ip = ip;
    }

    private Web3JClient() {
    }

    private volatile static Web3j web3j;

    public static Web3j getClient() {
        if (web3j == null) {
            synchronized (Web3JClient.class) {
                if (web3j == null) {
                    web3j = Web3j.build(new HttpService(ip));
                }
            }
        }
        return web3j;
    }
}