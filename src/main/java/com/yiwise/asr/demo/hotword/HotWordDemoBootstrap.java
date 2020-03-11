package com.yiwise.asr.demo.hotword;

import com.yiwise.asr.AsrClientFactory;
import com.yiwise.asr.common.client.utils.JsonUtils;
import com.yiwise.asr.demo.recognizer.file.FileRecognizerDemoBootstrap;
import com.yiwise.asr.demo.util.PropertiesLoader;
import com.yiwise.asr.hotword.HotwordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 添加热词测试（测试账号没有配额，请联系商务申请正式账号）
 */
public class HotWordDemoBootstrap {

    private static Logger logger = LoggerFactory.getLogger(HotWordDemoBootstrap.class);

    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesLoader.loadProperties("config.properties");
        String gatewayUrl = properties.getProperty("gatewayUrl", "http://127.0.0.1:6060");
        String accessKeyId = properties.getProperty("accessKeyId");
        String accessKeySecret = properties.getProperty("accessKeySecret");

        // 初始化AsrClientFactory，AsrClientFactory中缓存了AsrClient的实例，每次识别的时候从AsrClientFactory中获取AsrClient的实例
        AsrClientFactory.init(gatewayUrl, accessKeyId, accessKeySecret);

        Map<String, Integer> map = new HashMap<>();
        String hotWord = HotwordUtils.addOrUpdateHotWordRequest(null, "热词名称_" + +System.currentTimeMillis(), map);
        logger.info(JsonUtils.object2PrettyString(JsonUtils.string2JsonNode(hotWord)));
    }

}
