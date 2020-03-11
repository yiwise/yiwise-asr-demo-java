package com.yiwise.asr.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesLoader {
    private static Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);

    public static Properties loadProperties(String path) {
        try (InputStream stream = loadAsInputStream(path)) {
            return loadProperties(stream);
        } catch (IOException e) {
            throw new RuntimeException("配置文件解析失败。", e);
        }
    }

    public static Properties loadProperties(InputStream stream) {
        Properties properties = new Properties();
        try (InputStreamReader isr = new InputStreamReader(stream, StandardCharsets.UTF_8);
             BufferedReader bf = new BufferedReader(isr)) {
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
        logger.debug("加载配置文件, path=" + path);
        return stream;
    }
}
