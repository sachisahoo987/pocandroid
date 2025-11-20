package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.CapabilityManager;
import utils.ConfigReader;

import java.net.URL;

public final class DriverFactory {

    private static final ThreadLocal<AppiumDriver<?>> DRIVER = new ThreadLocal<>();

    private DriverFactory() {}

    public static void initDriver(String platform) {
        try {
            if (DRIVER.get() != null) return; // already initialized for this thread

            String appiumUrl = ConfigReader.getOrDefault("appium_url", "http://127.0.0.1:4723/wd/hub");
            DesiredCapabilities caps = CapabilityManager.getCapabilities(platform);

           if (platform.toLowerCase().contains("android")) {
				DRIVER.set(new AndroidDriver<>(new URL(appiumUrl), caps));
			} 
			else if (platform.toLowerCase().contains("ios")) {
				DRIVER.set(new io.appium.java_client.ios.IOSDriver<>(new URL(appiumUrl), caps));
			} 
		else {
			throw new RuntimeException("Invalid platform: " + platform);
			}
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Appium driver: " + e.getMessage(), e);
        }
    }

    public static AppiumDriver<?> getDriver() {
        return DRIVER.get();
    }

    public static void quitDriver() {
        AppiumDriver<?> driver = DRIVER.get();
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {}
            DRIVER.remove();
        }
    }
}
