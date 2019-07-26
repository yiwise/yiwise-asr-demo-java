package com.yiwise.asr.demo;

import com.yiwise.asr.AsrClient;
import com.yiwise.asr.AsrRecognizer;
import com.yiwise.asr.AsrRecognizerListener;
import com.yiwise.asr.common.client.protocol.AsrRecognizerResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class AsrDemo {

    public static void doTest(AsrClient asrClient, String audioFileName, Long hotWordId,
                              boolean enablePunctuation, boolean enableIntermediateResult) throws Exception {
        InputStream fileInputStream;

        File file = new File(audioFileName);
        if (file.exists()) {
            fileInputStream = new FileInputStream(file);
        } else {
            fileInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(audioFileName);
        }

        if (fileInputStream == null) {
            throw new RuntimeException("没有找到该文件，" + audioFileName);
        }

        try {
            // 丢弃wav的头文件
            if (audioFileName.startsWith(".wav")) {
                byte[] bytes = new byte[44];
                fileInputStream.read(bytes);
            }

            // 初始化AsrRecognizer，AsrRecognizer需要在每个识别会话中单独创建
            AsrRecognizer asrRecognizer = new AsrRecognizer(asrClient, new AsrRecognizerListener() {

                // 一句话的开始事件（如果一句话过短，可能没有开始事件，所有结果都在onSentenceEnd中返回）
                public void onSentenceBegin(AsrRecognizerResult result) {
                    // 请不要再此进行耗时操作，进行耗时操作可能引发一些不可预知问题；
                    // 如需进行耗时操作，请另外开辟线程执行
                    System.out.println("SentenceBegin----" + result.toString());
                }

                // 一句话的中间结果
                public void onSentenceBeginChanged(AsrRecognizerResult result) {
                    // 请不要再此进行耗时操作，进行耗时操作可能引发一些不可预知问题；
                    // 如需进行耗时操作，请另外开辟线程执行
                    System.out.println("SentenceChanged--" + result.toString());
                }

                // 一句话的结束事件
                public void onSentenceEnd(AsrRecognizerResult result) {
                    // 请不要再此进行耗时操作，进行耗时操作可能引发一些不可预知问题；
                    // 如需进行耗时操作，请另外开辟线程执行
                    System.out.println("SentenceEnd-----" + result.toString());
                }
            });


            // 设置参数
            // 热词id
            asrRecognizer.setHotWordId(hotWordId);
            // 是否打标点
            asrRecognizer.setEnablePunctuation(enablePunctuation);
            // 是否返回中间结果
            asrRecognizer.setEnableIntermediateResult(enableIntermediateResult);

            // 开启asr识别
            asrRecognizer.startAsr();

            // 发送音频流（模拟真实说话音频速率）
            // demo使用了文件来模拟音频流的发送，真实条件下，根据采样率发送音频数据即可
            // 对于8k pcm 编码数据，建议每发送4800字节 sleep 300 ms
            // 对于16k pcm 编码数据，建议每发送9600字节 sleep 300 ms
            asrRecognizer.sendAudio(fileInputStream, 4800, 300);

            // 停止ASR识别（发送停止识别后，最后的识别结果返回可能有一定延迟）
            asrRecognizer.stopAsr();
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }
}
