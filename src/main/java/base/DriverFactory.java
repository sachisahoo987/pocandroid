package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import utils.ConfigReader;

import java.net.URL;
import java.time.Duration;

public final class DriverFactory {

    private static final ThreadLocal<AppiumDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {}

    public static void initDriver(String platform) {
        try {
            if (DRIVER.get() != null) return;

            String appiumUrl = ConfigReader.getOrDefault("appium_url", "http://127.0.0.1:4723");

            /* ============================================================
               ANDROID 15 (API 36.0) – Pixel 8 Emulator
               ============================================================ */
            if (platform.equalsIgnoreCase("android")) {

                UiAutomator2Options options = new UiAutomator2Options();

                options.setPlatformName("Android");
                options.setDeviceName(ConfigReader.get("device_name"));
                options.setPlatformVersion(ConfigReader.get("platform_version"));
                options.setUdid(ConfigReader.get("udid"));
                options.setAutomationName("UiAutomator2");

                // === App specific ===
                options.setAppPackage(ConfigReader.get("app_package"));
                options.setAppActivity(ConfigReader.get("app_activity"));
                options.setAppWaitActivity(ConfigReader.get("app_wait_activity"));

                // Stable flags for new OS versions
                options.setAutoGrantPermissions(true);
                options.setDisableWindowAnimation(true);
                options.setNoReset(false);
                options.setNewCommandTimeout(Duration.ofSeconds(1200));

                DRIVER.set(new AndroidDriver(new URL(appiumUrl), options));
                Thread.sleep(30000);
            }


            /* ============================================================
                iOS SUPPORT (unchanged)
               ============================================================ */
            else if (platform.equalsIgnoreCase("ios")) {

                XCUITestOptions options = new XCUITestOptions()
                        .setDeviceName(ConfigReader.getOrDefault("ios_device_name", "iPhone 15"))
                        .setPlatformVersion(ConfigReader.getOrDefault("ios_platform_version", "17.0"))
                        .setBundleId(ConfigReader.getOrDefault("ios_bundle_id", "your.ios.app"));

                DRIVER.set(new IOSDriver(new URL(appiumUrl), options));
            }

            else {
                throw new IllegalArgumentException("Unsupported platform: " + platform);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize driver → " + e.getMessage(), e);
        }
    }

    public static AppiumDriver getDriver() {
        return DRIVER.get();
    }

    public static void quitDriver() {
        AppiumDriver d = DRIVER.get();
        if (d != null) {
            try { d.quit(); } catch (Exception ignored) {}
            DRIVER.remove();
        }
    }
}
