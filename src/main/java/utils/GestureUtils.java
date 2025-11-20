package utils;

import base.DriverFactory;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;
import java.util.HashMap;
import java.util.Map;

public final class GestureUtils {

    private GestureUtils() {}

    public static void longPress(int x, int y, int durationMs) {
        AppiumDriver<?> driver = DriverFactory.getDriver();
        Map<String, Object> params = new HashMap<>();
        params.put("x", x);
        params.put("y", y);
        params.put("duration", durationMs);
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", params);
    }

    public static void swipe(int startX, int startY, int endX, int endY, int durationMs) {
        AppiumDriver<?> driver = DriverFactory.getDriver();
        Map<String, Object> params = new HashMap<>();
        params.put("startX", startX);
        params.put("startY", startY);
        params.put("endX", endX);
        params.put("endY", endY);
        params.put("speed", durationMs); // some drivers use speed/time
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", params);
    }
}
