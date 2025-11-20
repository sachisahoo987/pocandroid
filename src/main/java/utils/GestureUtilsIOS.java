package utils;

import base.DriverFactory;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.JavascriptExecutor;

import java.util.HashMap;
import java.util.Map;

public final class GestureUtilsIOS {

    private GestureUtilsIOS() {}

    public static void longPress(int x, int y, int durationMs) {
        IOSDriver driver = (IOSDriver) DriverFactory.getDriver();
        Map<String, Object> params = new HashMap<>();
        params.put("x", x);
        params.put("y", y);
        params.put("duration", durationMs);
        ((JavascriptExecutor) driver).executeScript("mobile: touchAndHold", params);
    }

    public static void swipe(String direction) {
        IOSDriver driver = (IOSDriver) DriverFactory.getDriver();
        Map<String, Object> params = new HashMap<>();
        params.put("direction", direction);
        ((JavascriptExecutor) driver).executeScript("mobile: swipe", params);
    }
}
