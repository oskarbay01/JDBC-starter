package com.jdbc.starter.util;

import java.io.IOException;

import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {
    // Класс Properties – это подкласс Hashtable. Он используется для хранения списков значений,
    // в которых ключ является String, а значение также является String.
    public static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PropertiesUtil() {
    }
}
