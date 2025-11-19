package utils;

import org.openqa.selenium.remote.DesiredCapabilities;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;
import java.util.Iterator;

public class CapabilityManager {

    public static DesiredCapabilities getCapabilities(String platform) {
        try {
            String filename = platform.contains("browserstack") ? "browserstack.json" : platform + ".json";
            String path = "src/main/resources/" + filename;
            String raw = new String(Files.readAllBytes(Paths.get(path)));
            JSONObject json = new JSONObject(raw);
            DesiredCapabilities caps = new DesiredCapabilities();
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String k = keys.next();
                caps.setCapability(k, json.get(k));
            }
            return caps;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load caps: " + e.getMessage());
        }
    }
}
