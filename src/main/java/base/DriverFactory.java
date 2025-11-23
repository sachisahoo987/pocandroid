package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.WebElement;
import utils.ConfigReader;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;
import java.util.Optional;

public final class DriverFactory {

    private static final ThreadLocal<AppiumDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {}

    public static void initDriver(String platform) {
        try {
            if (DRIVER.get() != null) return;

            // Ensure URL includes protocol + host + port
            String appiumUrl = ConfigReader.getOrDefault("appium_url", "http://127.0.0.1:4723/");
            if (!appiumUrl.endsWith("/")) appiumUrl = appiumUrl + "/";

            /* ============================================================
               ANDROID
               ============================================================ */
            if (platform.equalsIgnoreCase("android")) {

                UiAutomator2Options options = new UiAutomator2Options();

                options.setPlatformName("Android");
                options.setDeviceName(ConfigReader.get("device_name"));
                // platform_version may be missing; guard gracefully
                Optional<String> platformVersion = Optional.ofNullable(ConfigReader.get("platform_version"));
                platformVersion.ifPresent(options::setPlatformVersion);

                options.setUdid(ConfigReader.get("udid"));
                options.setAutomationName(ConfigReader.getOrDefault("automation_name", "UiAutomator2"));

                // App specifics
                options.setAppPackage(ConfigReader.get("app_package"));
                options.setAppActivity(ConfigReader.get("app_activity"));
                options.setAppWaitActivity(ConfigReader.getOrDefault("app_wait_activity", ""));

                // Stability flags
                options.setAutoGrantPermissions(true);
                // safe capability setting if helper not available
                options.setCapability("disableWindowAnimation", true);

                options.setNoReset(Boolean.parseBoolean(ConfigReader.getOrDefault("no_reset", "false")));

                // new_command_timeout from config (seconds), fallback to 1200
                int newCmdTimeout = Integer.parseInt(ConfigReader.getOrDefault("new_command_timeout", "1200"));
                options.setNewCommandTimeout(Duration.ofSeconds(newCmdTimeout));

                // Set adbExecTimeout to reduce instrumentation failures (if supported by client)
                try {
                    options.setAdbExecTimeout(Duration.ofSeconds(120));
                } catch (Exception ignored) {}

                AppiumDriver driver = new AndroidDriver(new URL(appiumUrl), options);

                // set short implicit wait and attach to ThreadLocal
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                DRIVER.set(driver);

                // don't sleep — instead wait for app to reach known activity in tests / hooks
            }

            /* ============================================================
               iOS
               ============================================================ */
            else if (platform.equalsIgnoreCase("ios")) {

                XCUITestOptions options = new XCUITestOptions()
                        .setDeviceName(ConfigReader.getOrDefault("ios_device_name", "iPhone 15"))
                        .setPlatformVersion(ConfigReader.getOrDefault("ios_platform_version", "17.0"))
                        .setBundleId(ConfigReader.getOrDefault("ios_bundle_id", "your.ios.app"));


                AppiumDriver driver = new IOSDriver(new URL(appiumUrl), options);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                DRIVER.set(driver);
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
