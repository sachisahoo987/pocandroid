package utils;

import base.DriverFactory;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public final class ScrollUtils {

    private ScrollUtils() {}

    /**
     * Scrolls until an element containing the visibleText is found (Android UiScrollable).
     * This uses UiAutomator2's UiScrollable which works reliably for list views.
     */
    public static void scrollToText(String visibleText) {
        AppiumDriver<?> driver = DriverFactory.getDriver();
        if (driver == null) throw new RuntimeException("Driver not initialized");
        String uiSelector = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\"" + visibleText + "\"));";
        driver.findElement(By.androidUIAutomator(uiSelector));
    }
}
