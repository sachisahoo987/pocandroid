package utils;

import base.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumBy;

public class ScrollUtils {

    public static void scrollToText(String visibleText) {
        AppiumDriver driver = DriverFactory.getDriver();

        if (driver == null) {
            throw new RuntimeException("Driver not initialized");
        }

        if (!(driver instanceof AndroidDriver)) {
            throw new RuntimeException("scrollToText is only supported on Android");
        }

        String uiSelector =
                "new UiScrollable(new UiSelector().scrollable(true))"
                        + ".scrollIntoView(new UiSelector().textContains(\""
                        + visibleText + "\"));";

        driver.findElement(AppiumBy.androidUIAutomator(uiSelector));
    }
}
