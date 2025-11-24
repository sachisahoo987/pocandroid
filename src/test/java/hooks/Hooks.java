package hooks;

import base.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ConfigReader;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Hooks {

    @Before
    public void beforeScenario() {

        String platform = ConfigReader.getOrDefault("platform", "android");
        DriverFactory.initDriver(platform);

        // Optimized MediaProjection recording to prevent System UI freeze
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("maxDurationSec", 1800);       // 30 minutes max
            params.put("bitRate", 800000);            // Low bitrate = no freeze (0.8 Mbps)
            params.put("videoType", "mp4");
            params.put("resolution", "720x1280");     // Reduce load on emulator
            params.put("bgRecording", true);          // prevents System UI blocking

            DriverFactory.getDriver().executeScript(
                    "mobile: startMediaProjectionRecording",
                    params
            );

            System.out.println(">>> Optimized MediaProjection recording STARTED");

        } catch (Exception e) {
            System.out.println(">>> FAILED to start optimized recording: " + e.getMessage());
        }
    }

    @After
    public void afterScenario(Scenario scenario) {

        byte[] videoBytes = null;
        String base64Video = null;

        // Retry stopping recording because Android 14/15 is slow to finalize MP4
        for (int attempt = 1; attempt <= 5; attempt++) {
            try {
                base64Video = (String) DriverFactory.getDriver()
                        .executeScript("mobile: stopMediaProjectionRecording");

                if (base64Video != null && !base64Video.isEmpty()) {
                    System.out.println(">>> Recording STOPPED on attempt " + attempt);
                    break;
                } else {
                    System.out.println(">>> Video not ready (attempt " + attempt + "), waiting...");
                    Thread.sleep(1500);
                }

            } catch (Exception e) {
                System.out.println(">>> Error stopping recording (attempt " + attempt + "): " + e.getMessage());
                try { Thread.sleep(1500); } catch (Exception ignored) {}
            }
        }

        // Decode Base64 (if any)
        try {
            if (base64Video != null && !base64Video.isEmpty()) {
                videoBytes = Base64.getDecoder().decode(base64Video);
            }
        } catch (Exception ignored) {}

        // Save video + Attach to Allure
        try {
            if (videoBytes != null) {

                Files.createDirectories(Paths.get("test-output/videos"));

                String filename = "test-output/videos/" +
                        scenario.getName().replaceAll("\\s+", "_") + ".mp4";

                Files.write(Paths.get(filename), videoBytes);
                System.out.println(">>> Video saved: " + filename);

                // Allure video attachment
                io.qameta.allure.Allure.addAttachment(
                        "Screen Recording",
                        "video/mp4",
                        new ByteArrayInputStream(videoBytes),
                        ".mp4"
                );

            } else {
                System.out.println(">>> No video produced even after retry.");
            }

        } catch (Exception e) {
            System.out.println(">>> FAILED to save/attach video: " + e.getMessage());
        }

        // Quit driver
        try {
            DriverFactory.quitDriver();
        } catch (Exception e) {
            System.out.println(">>> FAILED to quit driver: " + e.getMessage());
        }
    }
}
