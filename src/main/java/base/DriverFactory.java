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
            if (platform.equalsIgnoreCase("android")) {

                UiAutomator2Options options = new UiAutomator2Options()
                        .setPlatformName("Android")
                        .setDeviceName("Pixel_2_API_27")
                        .setUdid("emulator-5554")
                        .setAutomationName("UiAutomator2")
                        .setAppPackage("com.androidsample.generalstore")
                        .setAppActivity("com.androidsample.generalstore.SplashActivity")
                        .setAppWaitActivity("com.androidsample.generalstore.*")
                        .setAutoGrantPermissions(true)
                        .setDisableWindowAnimation(true)
                        .setSkipDeviceInitialization(true)
                        .setSkipServerInstallation(true)
                        .setIgnoreHiddenApiPolicyError(true)
                        .setNewCommandTimeout(Duration.ofSeconds(120));

/*
                UiAutomator2Options options = new UiAutomator2Options()
                        .setPlatformName("Android")
                        .setDeviceName(ConfigReader.getOrDefault("device_name", "emulator-5554"))
                        .setAutomationName("UiAutomator2")
                        .setUdid(ConfigReader.getOrDefault("udid", "emulator-5554"))
                        .setAppPackage(ConfigReader.getOrDefault("app_package", "com.androidsample.generalstore"))
                        .setAppActivity(ConfigReader.getOrDefault("app_activity", "com.androidsample.generalstore.SplashActivity"))
                        .setAppWaitActivity(ConfigReader.getOrDefault("app_wait_activity", "com.androidsample.generalstore.*"))
                        .setAutoGrantPermissions(true)
                        // helpful options for older APKs
                        .setDisableWindowAnimation(true)
                        .setSkipDeviceInitialization(true)
                        .setSkipServerInstallation(true)
                        .setNewCommandTimeout(Duration.ofSeconds(120));*/

                // optional: set explicit platform version for Android 9 emulators
                String platformVersion = ConfigReader.get("platform_version");
                if (platformVersion != null && !platformVersion.isBlank()) {
                    options.setPlatformVersion(platformVersion);
                }

                DRIVER.set(new AndroidDriver(new URL(appiumUrl), options));
            } else if (platform.equalsIgnoreCase("ios")) {

                XCUITestOptions options = new XCUITestOptions()
                        .setDeviceName(ConfigReader.getOrDefault("ios_device_name", "iPhone 15"))
                        .setPlatformVersion(ConfigReader.getOrDefault("ios_platform_version", "17.0"))
                        .setBundleId(ConfigReader.getOrDefault("ios_bundle_id", "your.ios.app"));

                DRIVER.set(new IOSDriver(new URL(appiumUrl), options));
            } else {
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
