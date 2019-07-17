package com.yiwise.asr.demo;

import com.yiwise.asr.AsrClient;

public class MultiThreadAsrDemoBootstrap {

    public static void main(String[] args) throws Exception {
        // 初始化AsrClient，client可复用，无需每次初始化
        AsrClient asrClient = new AsrClient("http://192.168.110.63:6060", "default");

        String audioFileName = "test.wav";

        int threadCount = 10;  // 开启线程个数
        int loopCount = 10;     // 循环次数

        for (int index = 0; index < loopCount; index++) {
            for (int i = 0; i < threadCount; i++) {
                Thread thread = new Thread(() -> {
                    try {
                        AsrDemo.doTest(asrClient, audioFileName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
            }
        }

        Thread.currentThread().join();
    }
}
