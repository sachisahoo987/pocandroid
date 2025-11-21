package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties props = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);
        } catch (Exception ignored) {}
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static String getOrDefault(String key, String def) {
        return props.getProperty(key, def);
    }
}
