package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ConfigReader {
    private static final Properties prop = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            prop.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage(), e);
        }
    }

    private ConfigReader() {}

    public static String get(String key) {
        return prop.getProperty(key);
    }

    public static String getOrDefault(String key, String def) {
        String v = prop.getProperty(key);
        return v == null ? def : v;
    }
}
