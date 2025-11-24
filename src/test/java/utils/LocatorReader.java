package utils;

import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class LocatorReader {

    private static JSONObject androidLocators;
    private static JSONObject iosLocators;

    static {
        try {
            System.out.println("[DEBUG] Loading JSON locators using ContextClassLoader...");

            androidLocators = loadJson("locators/android.json");
            iosLocators = loadJson("locators/ios.json");

            System.out.println("[DEBUG] Loaded JSON successfully.");

        } catch (Exception e) {
            System.out.println("[DEBUG] ERROR loading JSON: " + e.getMessage());
            throw new RuntimeException("Failed to load locator JSONs", e);
        }
    }

    private static JSONObject loadJson(String resourcePath) {
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();

            System.out.println("[DEBUG] Attempting to load: " + resourcePath);
            InputStream is = cl.getResourceAsStream(resourcePath);

            System.out.println("[DEBUG] InputStream = " + is);

            if (is == null) {
                throw new RuntimeException("Resource not found on classpath: " + resourcePath);
            }

            byte[] bytes = is.readAllBytes();
            return new JSONObject(new String(bytes, StandardCharsets.UTF_8));

        } catch (Exception e) {
            throw new RuntimeException("Failed to load: " + resourcePath, e);
        }
    }

    public static String getLocator(String page, String key) {
        String platform = ConfigReader.getOrDefault("platform", "android").toLowerCase();
        if (platform.contains("ios")) {
            return iosLocators.getJSONObject(page).getString(key);
        } else {
            return androidLocators.getJSONObject(page).getString(key);
        }
    }
}
