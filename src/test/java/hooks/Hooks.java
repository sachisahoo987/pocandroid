package hooks;

import base.DriverFactory;
import io.cucumber.java.*;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.ConfigReader;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Hooks {

    private boolean shouldRecordVideo;
    private boolean captureFailureScreenshot;

    @Before
    public void beforeScenario() {

        String platform = ConfigReader.getOrDefault("platform", "android");
        DriverFactory.initDriver(platform);

        // Only take screenshot on failure
        captureFailureScreenshot = Boolean.parseBoolean(
                System.getProperty(
                        "failureShots",
                        ConfigReader.getOrDefault("failure_screenshots", "true")
                )
        );

        shouldRecordVideo = Boolean.parseBoolean(
                System.getProperty(
                        "recordVideo",
                        ConfigReader.getOrDefault("record_video", "true")
                )
        );

        // -------- Start Screen Recording --------
        if (shouldRecordVideo) {
            try {
                Map<String, Object> params = new HashMap<>();
                params.put("maxDurationSec", 1800);
                params.put("bitRate", 800000);
                params.put("videoType", "mp4");
                params.put("resolution", "720x1280");

                DriverFactory.getDriver().executeScript(
                        "mobile: startMediaProjectionRecording",
                        params
                );

                System.out.println(">>> Video recording started");

            } catch (Exception e) {
                System.out.println(">>> Failed to start video recording: " + e.getMessage());
            }
        }
    }

    @After
    public void afterScenario(Scenario scenario) {

        // -------- Screenshot Only On Failure --------
        if (scenario.isFailed() && captureFailureScreenshot) {
            try {
                byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                        .getScreenshotAs(OutputType.BYTES);

                Allure.addAttachment(
                        "Failure Screenshot",
                        "image/png",
                        new ByteArrayInputStream(screenshot),
                        ".png"
                );

            } catch (Exception e) {
                System.out.println(">>> Failed to capture failure screenshot: " + e.getMessage());
            }
        }

        // -------- Stop Recording --------
        if (shouldRecordVideo) {
            String base64Video = null;

            for (int i = 1; i <= 5; i++) {
                try {
                    base64Video = (String) DriverFactory.getDriver()
                            .executeScript("mobile: stopMediaProjectionRecording");

                    if (base64Video != null && !base64Video.isEmpty()) break;

                    Thread.sleep(1200);

                } catch (Exception e) {
                    System.out.println(">>> Error stopping recording: " + e.getMessage());
                }
            }

            if (base64Video != null) {
                try {
                    byte[] videoBytes = Base64.getDecoder().decode(base64Video);

                    Files.createDirectories(Paths.get("test-output/videos"));

                    String filename = "test-output/videos/" +
                            scenario.getName().replaceAll("\\s+", "_") + ".mp4";

                    Files.write(Paths.get(filename), videoBytes);

                    Allure.addAttachment(
                            "Scenario Video",
                            "video/mp4",
                            new ByteArrayInputStream(videoBytes),
                            ".mp4"
                    );

                } catch (Exception ignored) {}
            }
        }

        // -------- Quit Driver --------
        try {
            DriverFactory.quitDriver();
        } catch (Exception ignored) {}
    }
}
