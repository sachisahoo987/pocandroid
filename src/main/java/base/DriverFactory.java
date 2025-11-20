package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.CapabilityManager;
import utils.ConfigReader;

import java.net.URL;

public final class DriverFactory {

    private static final ThreadLocal<AppiumDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {}

    public static void initDriver(String platform) {
        try {
            if (DRIVER.get() != null) return;  // Driver already initialized for this thread

            String appiumUrl = ConfigReader.getOrDefault("appium_url", "http://127.0.0.1:4723");
            DesiredCapabilities caps = CapabilityManager.getCapabilities(platform);

            if (platform.equalsIgnoreCase("android")) {
                DRIVER.set(new AndroidDriver(new URL(appiumUrl), caps));
            }
            else if (platform.equalsIgnoreCase("ios")) {
                DRIVER.set(new IOSDriver(new URL(appiumUrl), caps));
            }
            else {
                throw new IllegalArgumentException("Invalid platform: " + platform);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Appium driver: " + e.getMessage(), e);
        }
    }

    public static AppiumDriver getDriver() {
        return DRIVER.get();
    }

    public static void setDriver(AppiumDriver driver) {
        DRIVER.set(driver);
    }

    public static void quitDriver() {
        AppiumDriver driver = DRIVER.get();
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {
            }
            DRIVER.remove();
        }
    }
}
