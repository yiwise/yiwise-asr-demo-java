package com.yiwise.asr.demo;

import com.yiwise.asr.AsrClient;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public class MultiThreadAsrDemoBootstrap {

    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesLoader.loadProperties("config.properties");
        String gatewayUrl = properties.getProperty("gatewayUrl", "http://127.0.0.1:6060");
        String audioFileName = properties.getProperty("audioFileName", "test.wav");
        Long hotWordId = StringUtils.isEmpty(properties.getProperty("hotWordId"))? null: Long.valueOf(properties.getProperty("hotWordId"));
        boolean enablePunctuation = Boolean.valueOf(properties.getProperty("enablePunctuation", "false")) ;
        boolean enableIntermediateResult = Boolean.valueOf(properties.getProperty("enableIntermediateResult", "false")) ;

        // 初始化AsrClient，client可复用，无需每次初始化
        AsrClient asrClient = new AsrClient(gatewayUrl, "default");

        int threadCount = Integer.valueOf(properties.getProperty("threadCount", "10"));  // 开启线程个数
        int loopCount = Integer.valueOf(properties.getProperty("loopCount", "3"));     // 循环次数

        for (int index = 0; index < threadCount; index++) {
            Thread thread = new Thread(() -> {
                for (int i = 0; i < loopCount; i++) {
                    try {
                        AsrDemo.doTest(asrClient, audioFileName, hotWordId, enablePunctuation, enableIntermediateResult);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        Thread.currentThread().join();
    }
}
