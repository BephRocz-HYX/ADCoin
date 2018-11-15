package com.gomo.ethchain.util;

import java.io.*;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties prop = new Properties();

//    static {
//        readProperties("src/config.properties");
//    }

    /**
     * 读取配置文件
     *
     * @param filePath
     */
    public static void readProperties(String filePath) {
        try {
            InputStream in = new FileInputStream(filePath);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            prop.load(bf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据key读取对应的value
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}