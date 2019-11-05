package com.yiwise.asr.demo;

import com.yiwise.asr.AsrClientFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadAsrDemoBootstrap {

    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesLoader.loadProperties("config.properties");
        String gatewayUrl = properties.getProperty("gatewayUrl", "http://127.0.0.1:6060");
        String accessKeyId = properties.getProperty("accessKeyId");
        String accessKeySecret = properties.getProperty("accessKeySecret");
        String audioFileName = properties.getProperty("audioFileName", "test.wav");
        Long hotWordId = StringUtils.isEmpty(properties.getProperty("hotWordId")) ? null : Long.valueOf(properties.getProperty("hotWordId"));
        Long selfLearningModelId = StringUtils.isEmpty(properties.getProperty("selfLearningModelId")) ? null : Long.valueOf(properties.getProperty("selfLearningModelId"));
        boolean enablePunctuation = Boolean.valueOf(properties.getProperty("enablePunctuation", "false"));
        boolean enableIntermediateResult = Boolean.valueOf(properties.getProperty("enableIntermediateResult", "false"));

        // 初始化AsrClientFactory，AsrClientFactory中缓存了AsrClient的实例，每次识别的时候从AsrClientFactory中获取AsrClient的实例
        AsrClientFactory.init(gatewayUrl, accessKeyId, accessKeySecret);

        int threadCount = Integer.valueOf(properties.getProperty("threadCount", "10"));  // 开启线程个数
        int loopCount = Integer.valueOf(properties.getProperty("loopCount", "3"));     // 循环次数

        AtomicInteger count = new AtomicInteger(threadCount * loopCount);
        long currentTimeMillis = System.currentTimeMillis();

        for (int index = 0; index < threadCount; index++) {
            Thread thread = new Thread(() -> {
                for (int i = 0; i < loopCount; i++) {

                    try {
                        AsrDemo.doTest(AsrClientFactory.getAsrClient(), audioFileName, hotWordId, enablePunctuation, enableIntermediateResult, selfLearningModelId);

                        int remain = count.decrementAndGet();
                        if (remain == 0) {
                            long time = System.currentTimeMillis() - currentTimeMillis;
                            System.out.println("所有线程执行完毕, time=" + time + ", average=" + time / loopCount);
                            System.exit(0);
                        }
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
