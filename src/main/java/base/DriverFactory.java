package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.CapabilityManager;
import utils.ConfigReader;

import java.net.URL;

public class DriverFactory {

    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public static void initDriver(String platform) {
        try {
            String appiumUrl = ConfigReader.getOrDefault("appium_url", "http://127.0.0.1:4723/wd/hub");
            DesiredCapabilities caps = CapabilityManager.getCapabilities(platform);
            if (platform.contains("android")) {
                driver.set(new AndroidDriver(new URL(appiumUrl), caps));
            } else {
                driver.set(new IOSDriver(new URL(appiumUrl), caps));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to init driver: " + e.getMessage());
        }
    }

    public static AppiumDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
