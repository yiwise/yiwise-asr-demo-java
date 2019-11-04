package com.yiwise.asr.demo;

import com.yiwise.asr.AsrClientFactory;
import com.yiwise.asr.selflearning.SelflearningModelTrainingUtils;

import java.io.File;
import java.util.Properties;

public class SelfLearningRequestDemoBootstrap {

    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesLoader.loadProperties("config.properties");
        String gatewayUrl = properties.getProperty("gatewayUrl", "http://127.0.0.1:6060");
        String accessKeyId = properties.getProperty("accessKeyId");
        String accessKeySecret = properties.getProperty("accessKeySecret");

        // 初始化AsrClientFactory，AsrClientFactory中缓存了AsrClient的实例，每次识别的时候从AsrClientFactory中获取AsrClient的实例
        AsrClientFactory.init(gatewayUrl, accessKeyId, accessKeySecret);

        File file = new File(Thread.currentThread().getContextClassLoader().getResource("text.txt").getFile());
        String trainingRequest = SelflearningModelTrainingUtils.sendTrainingRequest(null, "测试模型_" + System.currentTimeMillis(), file);
        System.out.println(trainingRequest);
    }

}
