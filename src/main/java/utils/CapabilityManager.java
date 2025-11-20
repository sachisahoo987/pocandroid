package utils;

import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * Utility class to manage and retrieve DesiredCapabilities for different platforms
 * by loading configuration from JSON files and applying path overrides.
 */
public final class CapabilityManager {

    private CapabilityManager() {
        // Private constructor to prevent instantiation
    }

    /**
     * Loads DesiredCapabilities based on the platform key.
     *
     * @param platformKey A string indicating the target platform (e.g., "ios" or "android").
     * @return DesiredCapabilities object.
     */
    public static DesiredCapabilities getCapabilities(String platformKey) {
        String lowerKey = platformKey.toLowerCase();

        try {
            if (lowerKey.contains("ios")) {
                String appPath = ConfigReader.get("ios_app_path");
                return loadCapabilitiesFromJson("src/main/resources/ios.json", "${ios_app_path}", appPath);
            } else {
                // Default to Android logic
                String appPath = ConfigReader.getOrDefault("app_path", "app/app-debug.apk");
                return loadCapabilitiesFromJson("src/main/resources/android.json", "${app_path}", appPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load capabilities for platform: " + platformKey +
                    ". Error: " + e.getMessage(), e);
        }
    }

    /**
     * Reads a JSON file, replaces a placeholder with a value, and converts
     * the resulting JSON into DesiredCapabilities.
     *
     * @param jsonPath The path to the capabilities JSON file.
     * @param placeholder The string placeholder to look for in the JSON content.
     * @param replacement The value to substitute the placeholder with.
     * @return DesiredCapabilities object populated from the JSON.
     * @throws IOException If the JSON file cannot be read.
     */
    private static DesiredCapabilities loadCapabilitiesFromJson(String jsonPath, String placeholder, String replacement) throws IOException {
        Path path = Paths.get(jsonPath);
        // Use Files.readString for simple, modern file reading (Java 11+)
        String rawJson = Files.readString(path);

        // Replace the placeholder
        rawJson = rawJson.replace(placeholder, replacement);

        JSONObject json = new JSONObject(rawJson);
        DesiredCapabilities caps = new DesiredCapabilities();

        // Iterate over JSON keys and set them as capabilities
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = json.get(key);
            caps.setCapability(key, value);
        }

        return caps;
    }
}