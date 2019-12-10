package com.yiwise.asr.demo.recognizer.file;

import com.yiwise.asr.AsrClientFactory;
import com.yiwise.asr.common.client.protocol.AsrParam;
import com.yiwise.asr.common.client.utils.JsonUtils;
import com.yiwise.asr.demo.util.PropertiesLoader;
import com.yiwise.asr.recognizer.file.FileRecognizerUtils;

import java.io.File;
import java.util.Properties;

public class FileRecognizerDemoBootstrap {

    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesLoader.loadProperties("config.properties");
        String gatewayUrl = properties.getProperty("gatewayUrl", "http://127.0.0.1:6060");
        String accessKeyId = properties.getProperty("accessKeyId");
        String accessKeySecret = properties.getProperty("accessKeySecret");
        String audioFileName = properties.getProperty("audioFileName", "test.wav");

        // 初始化AsrClientFactory，AsrClientFactory中缓存了AsrClient的实例，每次识别的时候从AsrClientFactory中获取AsrClient的实例
        AsrClientFactory.init(gatewayUrl, accessKeyId, accessKeySecret);

        File file = new File(audioFileName);
        AsrParam asrParam = new AsrParam();

        // 识别通道
        // [1]          表示单通道
        // [0, 1]       表示双通道文件，只识别右声道
        // [1, 0]       表示双通道文件，只识别左声道
        // [1, 1]       表示识别双声道
        // 如果是双声道文件，并且两个声道都要识别，计时时间按照2倍时长计算
        Integer[] recognizeAudioChannelArr = new Integer[]{1};

        String recognizeFileRequest = FileRecognizerUtils.sendRecognizeFileRequest(null, file, asrParam, recognizeAudioChannelArr);
        System.out.println(JsonUtils.object2PrettyString(JsonUtils.string2JsonNode(recognizeFileRequest)));
    }

}
