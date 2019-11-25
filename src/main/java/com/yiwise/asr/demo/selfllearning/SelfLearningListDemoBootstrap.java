package com.yiwise.asr.demo.selfllearning;

import com.yiwise.asr.AsrClientFactory;
import com.yiwise.asr.common.client.utils.JsonUtils;
import com.yiwise.asr.demo.util.PropertiesLoader;
import com.yiwise.asr.selflearning.SelflearningModelTrainingUtils;

import java.util.Properties;

/**
 * 获取自学习模型列表测试（测试账号没有配额，请联系商务申请正式账号）
 */
public class SelfLearningListDemoBootstrap {

    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesLoader.loadProperties("config.properties");
        String gatewayUrl = properties.getProperty("gatewayUrl", "http://127.0.0.1:6060");
        String accessKeyId = properties.getProperty("accessKeyId");
        String accessKeySecret = properties.getProperty("accessKeySecret");

        // 初始化AsrClientFactory，AsrClientFactory中缓存了AsrClient的实例，每次识别的时候从AsrClientFactory中获取AsrClient的实例
        AsrClientFactory.init(gatewayUrl, accessKeyId, accessKeySecret);

        String listTrainingRequest = SelflearningModelTrainingUtils.listTrainingRequest(1, 20, null, null);
        System.out.println(JsonUtils.object2PrettyString(JsonUtils.string2JsonNode(listTrainingRequest)));
    }

}
