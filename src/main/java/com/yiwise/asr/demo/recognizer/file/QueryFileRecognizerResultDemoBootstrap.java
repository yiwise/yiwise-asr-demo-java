package com.yiwise.asr.demo.recognizer.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.yiwise.asr.AsrClientFactory;
import com.yiwise.asr.common.client.protocol.AsrRecognizerResult;
import com.yiwise.asr.common.client.utils.JsonUtils;
import com.yiwise.asr.demo.util.PropertiesLoader;
import com.yiwise.asr.recognizer.file.FileRecognizerUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class QueryFileRecognizerResultDemoBootstrap {
    private static Logger logger = LoggerFactory.getLogger(QueryFileRecognizerResultDemoBootstrap.class);

    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesLoader.loadProperties("config.properties");
        String gatewayUrl = properties.getProperty("gatewayUrl", "http://127.0.0.1:6060");
        String accessKeyId = properties.getProperty("accessKeyId");
        String accessKeySecret = properties.getProperty("accessKeySecret");

        // 初始化AsrClientFactory，AsrClientFactory中缓存了AsrClient的实例，每次识别的时候从AsrClientFactory中获取AsrClient的实例
        AsrClientFactory.init(gatewayUrl, accessKeyId, accessKeySecret);

        Long fileRecognizerTaskId = 123L;

        String recognizeFileResult = FileRecognizerUtils.queryRecognizeFileResult(null, fileRecognizerTaskId);

        JsonNode jsonNode = JsonUtils.string2JsonNode(recognizeFileResult);
        JsonNode dataNode = jsonNode.get("data");
        String data = dataNode.get("result").toString();
        String recognizerStatus = dataNode.get("recognizerStatus").asText();
        if (!"FINISHED".equals(recognizerStatus)) {
            throw new RuntimeException("识别出错，recognizeFileResult=" + recognizeFileResult);
        }

        List<List<AsrRecognizerResult>> list = JsonUtils.string2Object(data, new TypeReference<List<List<AsrRecognizerResult>>>() {
        });

        List<Pair<Integer, AsrRecognizerResult>> listRes = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            final int finalI = i;
            final List<AsrRecognizerResult> item = list.get(i);
            item.forEach(temp -> {
                listRes.add(Pair.of(finalI, temp));
            });
        }

        List<Pair<Integer, AsrRecognizerResult>> collect = listRes.stream().sorted((item1, item2) -> (int) (item1.getRight().getBeginTime() - item2.getRight().getBeginTime())).collect(Collectors.toList());

        File file = new File("file.text");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (Pair<Integer, AsrRecognizerResult> pair : collect) {
            logger.debug("{}: {}", pair.getLeft(), pair.getRight().getResultText());
            bufferedWriter.write(pair.getLeft() + " : " + pair.getRight().getResultText());
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

}
