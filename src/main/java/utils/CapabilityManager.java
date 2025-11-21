package utils;

import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public final class CapabilityManager {

    private static final String ANDROID_CAPS_PATH = "src/main/resources/android.json";

    private CapabilityManager() {
    }

    /**
     * Loads DesiredCapabilities only from android.json.
     */
    public static DesiredCapabilities getCapabilities() {
        try {
            return loadCapabilitiesFromJson(ANDROID_CAPS_PATH);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Android capabilities: " + e.getMessage(), e);
        }
    }

    /**
     * Reads JSON file and converts it to DesiredCapabilities.
     */
    private static DesiredCapabilities loadCapabilitiesFromJson(String jsonPath) throws IOException {
        String jsonContent = Files.readString(Path.of(jsonPath));

        JSONObject json = new JSONObject(jsonContent);
        DesiredCapabilities caps = new DesiredCapabilities();

        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            caps.setCapability(key, json.get(key));
        }

        return caps;
    }
}
