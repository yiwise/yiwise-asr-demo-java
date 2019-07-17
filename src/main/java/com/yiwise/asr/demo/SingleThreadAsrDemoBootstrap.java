package com.yiwise.asr.demo;

import com.yiwise.asr.AsrClient;


public class SingleThreadAsrDemoBootstrap {

    public static void main(String[] args) throws Exception {
        // 初始化AsrClient，client可复用，无需每次初始化
        AsrClient asrClient = new AsrClient("default");

        String audioFileName = "test.wav";

        AsrDemo.doTest(asrClient, audioFileName);

        Thread.currentThread().join();
    }
}
