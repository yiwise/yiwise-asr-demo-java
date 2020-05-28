package com.yiwise.asr.demo.recognizer.file;

import com.yiwise.asr.AsrClientFactory;
import com.yiwise.asr.common.client.protocol.AsrParam;
import com.yiwise.asr.common.client.utils.JsonUtils;
import com.yiwise.asr.demo.util.PropertiesLoader;
import com.yiwise.asr.recognizer.file.FileRecognizerUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

public class FileRecognizerDemoBootstrap {
    private static Logger logger = LoggerFactory.getLogger(FileRecognizerDemoBootstrap.class);

    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesLoader.loadProperties("config.properties");
        String gatewayUrl = properties.getProperty("gatewayUrl", "http://127.0.0.1:6060");
        String accessKeyId = properties.getProperty("accessKeyId");
        String accessKeySecret = properties.getProperty("accessKeySecret");
        String audioFileName = properties.getProperty("audioFileName", "test.wav");

        Long hotWordId = StringUtils.isEmpty(properties.getProperty("hotWordId")) ? null : Long.valueOf(properties.getProperty("hotWordId"));
        Long selfLearningModelId = StringUtils.isEmpty(properties.getProperty("selfLearningModelId")) ? null : Long.valueOf(properties.getProperty("selfLearningModelId"));
        boolean enablePunctuation = Boolean.valueOf(properties.getProperty("enablePunctuation", "false"));

        // 初始化AsrClientFactory，AsrClientFactory中缓存了AsrClient的实例，每次识别的时候从AsrClientFactory中获取AsrClient的实例
        AsrClientFactory.init(gatewayUrl, accessKeyId, accessKeySecret);

        File file = new File(audioFileName);
        if (!file.exists()) {
            URL resource = Thread.currentThread().getContextClassLoader().getResource(audioFileName);
            String resourceFile = resource.getFile();
            file = new File(URLDecoder.decode(resourceFile, "UTF-8"));
        }

        AsrParam asrParam = new AsrParam();
        asrParam.setEnablePunctuation(enablePunctuation);
        asrParam.setHotWordId(hotWordId);
        asrParam.setSelfLearningModelId(selfLearningModelId);

        // 识别通道
        // [1]          表示单通道
        // [0, 1]       表示双通道文件，只识别右声道
        // [1, 0]       表示双通道文件，只识别左声道
        // [1, 1]       表示识别双声道
        // 如果是双声道文件，并且两个声道都要识别，计时时间按照2倍时长计算
        Integer[] recognizeAudioChannelArr;

        if (StringUtils.contains(file.getName(), "双声道")) {
            recognizeAudioChannelArr = new Integer[]{1, 1};
        } else {
            recognizeAudioChannelArr = new Integer[]{1};
        }

        String recognizeFileRequest = FileRecognizerUtils.sendRecognizeFileRequest(file, asrParam, recognizeAudioChannelArr);
        logger.info(JsonUtils.object2PrettyString(JsonUtils.string2JsonNode(recognizeFileRequest)));
    }

}
