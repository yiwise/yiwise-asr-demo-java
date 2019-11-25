package com.yiwise.asr.demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesLoader {
    public static Properties loadProperties(String path) {
        Properties properties = new Properties();
        InputStreamReader isr;
        try {
            isr = new InputStreamReader(loadAsInputStream(path), StandardCharsets.UTF_8);
            BufferedReader bf = new BufferedReader(isr);
            properties.load(bf);
        } catch (IOException e) {
            throw new RuntimeException("配置文件解析失败。", e);
        }
        return properties;
    }

    private static InputStream loadAsInputStream(String path) {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (stream == null) {
            throw new RuntimeException("配置文件加载失败或文件不存在:" + path);
        }
        System.out.println("加载配置文件, path=" + path);
        return stream;
    }
}
