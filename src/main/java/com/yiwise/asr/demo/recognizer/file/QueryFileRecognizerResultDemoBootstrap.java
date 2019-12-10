package com.yiwise.asr.demo.recognizer.file;

import com.yiwise.asr.AsrClientFactory;
import com.yiwise.asr.common.client.utils.JsonUtils;
import com.yiwise.asr.demo.util.PropertiesLoader;
import com.yiwise.asr.recognizer.file.FileRecognizerUtils;

import java.util.Properties;

public class QueryFileRecognizerResultDemoBootstrap {

    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesLoader.loadProperties("config.properties");
        String gatewayUrl = properties.getProperty("gatewayUrl", "http://127.0.0.1:6060");
        String accessKeyId = properties.getProperty("accessKeyId");
        String accessKeySecret = properties.getProperty("accessKeySecret");

        // 初始化AsrClientFactory，AsrClientFactory中缓存了AsrClient的实例，每次识别的时候从AsrClientFactory中获取AsrClient的实例
        AsrClientFactory.init(gatewayUrl, accessKeyId, accessKeySecret);

        Long fileRecognizerTaskId = 1L;

        String recognizeFileResult = FileRecognizerUtils.queryRecognizeFileResult(null, fileRecognizerTaskId);
        System.out.println(JsonUtils.object2PrettyString(JsonUtils.string2JsonNode(recognizeFileResult)));
    }

}
