package com.yiwise.asr.demo;

import com.yiwise.asr.AsrClient;
import com.yiwise.asr.AsrRecognizer;
import com.yiwise.asr.AsrRecognizerListener;
import com.yiwise.asr.common.client.protocol.AsrRecognizerResult;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.io.InputStream;

public class SingleThreadAsrDemoBootstrap {

    public static void main(String[] args) throws Exception {
        // 初始化AsrClient，client可复用，无需每次初始化
        AsrClient asrClient = new AsrClient("http://192.168.110.63:6060", "default");

        String audioFileName = "test.wav";

        AsrDemo.doTest(asrClient, audioFileName);

        Thread.currentThread().join();
    }
}
