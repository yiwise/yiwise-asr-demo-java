package com.yiwise.asr.demo;

import com.yiwise.asr.AsrClient;

public class MultiThreadAsrDemoBootstrap {

    public static void main(String[] args) throws Exception {
        // 初始化AsrClient，client可复用，无需每次初始化
        AsrClient asrClient = new AsrClient("default");

        String audioFileName = "test.wav";

        int threadCount = 8;  // 开启线程个数
        int loopCount = 10;     // 循环次数

        for (int index = 0; index < threadCount; index++) {
            Thread thread = new Thread(() -> {
                for (int i = 0; i < loopCount; i++) {
                    try {
                        AsrDemo.doTest(asrClient, audioFileName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        Thread.currentThread().join();
    }
}
