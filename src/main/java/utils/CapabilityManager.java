package utils;


import browserstack.shaded.org.json.JSONObject;
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

                // Check for IPA inside resources/apps/
                String appFolder = System.getProperty("user.dir") + "/src/main/resources/apps/";
                String ipaPath = ApkFinder.findApkOrIpa(appFolder, "ipa");

                if (ipaPath != null) {
                    System.out.println("📱 Using IPA from folder: " + ipaPath);
                    return loadCapabilitiesFromJson(
                            "src/main/resources/ios.json",
                            "${ios_app_path}",
                            ipaPath
                    );
                }

                // Fallback to JSON (ios_app_path)
                String appPath = ConfigReader.get("ios_app_path");
                System.out.println("📱 Using IPA from JSON config: " + appPath);

                return loadCapabilitiesFromJson(
                        "src/main/resources/ios.json",
                        "${ios_app_path}",
                        appPath
                );

            } else {

                // ANDROID LOGIC
                String appFolder = System.getProperty("user.dir") + "/src/main/resources/apps/";
                String apkPath = ApkFinder.findApkOrIpa(appFolder, "apk");

                if (apkPath != null) {
                    System.out.println("🤖 Using APK from folder: " + apkPath);
                    return loadCapabilitiesFromJson(
                            "src/main/resources/android.json",
                            "${app_path}",
                            apkPath
                    );
                }

                // Fallback to JSON
                String appPath = ConfigReader.getOrDefault("app_path", "GeneralStore.apk");
                System.out.println("🤖 Using APK from JSON config: " + appPath);

                return loadCapabilitiesFromJson(
                        "src/main/resources/android.json",
                        "${app_path}",
                        appPath
                );
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