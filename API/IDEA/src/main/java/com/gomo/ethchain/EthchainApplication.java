package com.gomo.ethchain;

import com.gomo.ethchain.service.GomoCoinSDK;
import com.gomo.ethchain.test.Test;
import com.gomo.ethchain.util.PropertiesUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EthchainApplication {

    static {
        //初始化环境（服务器内网ip）只需调用一次
        try {
            GomoCoinSDK.init("http://192.168.4.19:8545/", "0x8c1ab6622718e3fa29697bdbf7b82e0bcfb1d568");
        } catch (Exception e) {

        }
    }
    public static void main(String[] args) throws Exception{

        SpringApplication.run(EthchainApplication.class, args);
//        Test.testSDK();
//        System.out.println("合约地址："+Test.deployContract());
    }
}
