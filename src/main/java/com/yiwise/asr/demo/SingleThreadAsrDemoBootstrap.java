package com.yiwise.asr.demo;

import com.yiwise.asr.AsrClient;
import com.yiwise.asr.AsrClientFactory;
import com.yiwise.asr.demo.util.PropertiesLoader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * 单线程测试demo
 */
public class SingleThreadAsrDemoBootstrap {
    private static Logger logger = LoggerFactory.getLogger(SingleThreadAsrDemoBootstrap.class);

    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesLoader.loadProperties("config.properties");
        String gatewayUrl = properties.getProperty("gatewayUrl", "http://127.0.0.1:6060");
        String accessKeyId = properties.getProperty("accessKeyId");
        String accessKeySecret = properties.getProperty("accessKeySecret");
        String audioFileName = properties.getProperty("audioFileName", "test.wav");
        Long hotWordId = StringUtils.isEmpty(properties.getProperty("hotWordId")) ? null : Long.valueOf(properties.getProperty("hotWordId"));
        Float selfLearningRatio = StringUtils.isEmpty(properties.getProperty("selfLearningRatio")) ? null : Float.valueOf(properties.getProperty("selfLearningRatio"));
        Long selfLearningModelId = StringUtils.isEmpty(properties.getProperty("selfLearningModelId")) ? null : Long.valueOf(properties.getProperty("selfLearningModelId"));
        boolean enablePunctuation = Boolean.valueOf(properties.getProperty("enablePunctuation", "false"));
        boolean enableIntermediateResult = Boolean.valueOf(properties.getProperty("enableIntermediateResult", "false"));
        boolean enableInverseTextNormalization = Boolean.valueOf(properties.getProperty("enableInverseTextNormalization", "true"));

        // 初始化AsrClientFactory，AsrClientFactory中缓存了AsrClient的实例，每次识别的时候从AsrClientFactory中获取AsrClient的实例
        AsrClientFactory.init(gatewayUrl, accessKeyId, accessKeySecret);
        AsrClient asrClient = AsrClientFactory.getAsrClient();

        long currentTimeMillis = System.currentTimeMillis();
        AsrDemo.doTest(asrClient, audioFileName, hotWordId, enablePunctuation, enableIntermediateResult, enableInverseTextNormalization, selfLearningModelId, selfLearningRatio);

        long time = System.currentTimeMillis() - currentTimeMillis;
        logger.info("所有线程执行完毕, time=" + time);
    }
}
