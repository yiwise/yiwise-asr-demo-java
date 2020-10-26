package com.yiwise.asr.demo.selfllearning;

import com.yiwise.asr.AsrClientFactory;
import com.yiwise.asr.common.client.utils.JsonUtils;
import com.yiwise.asr.demo.util.PropertiesLoader;
import com.yiwise.asr.selflearning.SelflearningModelTrainingUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * 添加自学习模型测试（测试账号没有配额，请联系商务申请正式账号）
 */
public class SelfLearningRequestByTextDemoBootstrap {
    private static Logger logger = LoggerFactory.getLogger(SelfLearningRequestByTextDemoBootstrap.class);

    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesLoader.loadProperties("config.properties");
        String gatewayUrl = properties.getProperty("gatewayUrl", "http://127.0.0.1:6060");
        String accessKeyId = properties.getProperty("accessKeyId");
        String accessKeySecret = properties.getProperty("accessKeySecret");

        // 初始化AsrClientFactory，AsrClientFactory中缓存了AsrClient的实例，每次识别的时候从AsrClientFactory中获取AsrClient的实例
        AsrClientFactory.init(gatewayUrl, accessKeyId, accessKeySecret);

        File file = new File(Thread.currentThread().getContextClassLoader().getResource("text.txt").getFile());
        String text = IOUtils.toString(new FileInputStream(file));

        String trainingRequest = SelflearningModelTrainingUtils.sendTrainingRequestByText(11L, "测试模型txt_" + System.currentTimeMillis(), text);
        logger.info(JsonUtils.object2PrettyString(JsonUtils.string2JsonNode(trainingRequest)));
    }

}
