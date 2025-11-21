package utils;

import base.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public final class GestureUtils {

    private GestureUtils() {}

    private static JavascriptExecutor js() {
        return DriverFactory.getDriver();
    }

    /* =========================================================================
       TAP / CLICK
       ========================================================================= */

    public static void tap(int x, int y) {
        Map<String, Object> params = new HashMap<>();
        params.put("x", x);
        params.put("y", y);
        js().executeScript("mobile: tap", params);
    }

    public static void tap(WebElement element) {
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((org.openqa.selenium.remote.RemoteWebElement) element).getId());
        js().executeScript("mobile: tap", params);
    }

    /* =========================================================================
       LONG PRESS
       ========================================================================= */

    public static void longPress(int x, int y, int durationMs) {
        Map<String, Object> params = new HashMap<>();
        params.put("x", x);
        params.put("y", y);
        params.put("duration", durationMs);
        js().executeScript("mobile: longClick", params);
    }

    public static void longPress(WebElement element, int durationMs) {
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((org.openqa.selenium.remote.RemoteWebElement) element).getId());
        params.put("duration", durationMs);
        js().executeScript("mobile: longClick", params);
    }

    /* =========================================================================
       SWIPE / FLICK
       ========================================================================= */

    public static void swipe(int startX, int startY, int endX, int endY, int durationMs) {
        Map<String, Object> params = new HashMap<>();
        params.put("startX", startX);
        params.put("startY", startY);
        params.put("endX", endX);
        params.put("endY", endY);
        params.put("duration", durationMs);
        js().executeScript("mobile: swipe", params);
    }

    public static void flick(int startX, int startY, int endX, int endY) {
        // Flick is just swipe with very short duration
        swipe(startX, startY, endX, endY, 100);
    }

    /* =========================================================================
       SCROLL
       ========================================================================= */

    public static void scrollDown(int distance) {
        Map<String, Object> params = new HashMap<>();
        params.put("direction", "down");
        params.put("percent", distance);
        js().executeScript("mobile: scroll", params);
    }

    public static void scrollUp(int distance) {
        Map<String, Object> params = new HashMap<>();
        params.put("direction", "up");
        params.put("percent", distance);
        js().executeScript("mobile: scroll", params);
    }

    public static void scrollToElement(WebElement element) {
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((org.openqa.selenium.remote.RemoteWebElement) element).getId());
        js().executeScript("mobile: scroll", params);
    }

    /* =========================================================================
       DRAG & DROP
       ========================================================================= */

    public static void dragAndDrop(WebElement source, WebElement target) {
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((org.openqa.selenium.remote.RemoteWebElement) source).getId());
        params.put("destinationElementId", ((org.openqa.selenium.remote.RemoteWebElement) target).getId());
        js().executeScript("mobile: dragGesture", params);
    }

    public static void drag(int startX, int startY, int endX, int endY, int durationMs) {
        Map<String, Object> params = new HashMap<>();
        params.put("startX", startX);
        params.put("startY", startY);
        params.put("endX", endX);
        params.put("endY", endY);
        params.put("duration", durationMs);
        js().executeScript("mobile: dragGesture", params);
    }

    /* =========================================================================
       PINCH & ZOOM
       ========================================================================= */

    public static void pinchOpen(WebElement element, double percent) {
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((org.openqa.selenium.remote.RemoteWebElement) element).getId());
        params.put("scale", 1 + percent);
        params.put("velocity", 1.0);
        js().executeScript("mobile: pinchOpen", params);
    }

    public static void pinchClose(WebElement element, double percent) {
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((org.openqa.selenium.remote.RemoteWebElement) element).getId());
        params.put("scale", 1 - percent);
        params.put("velocity", 1.0);
        js().executeScript("mobile: pinchClose", params);
    }

    /* =========================================================================
       DOUBLE TAP
       ========================================================================= */

    public static void doubleTap(WebElement element) {
        Map<String, Object> params = new HashMap<>();
        params.put("elementId", ((org.openqa.selenium.remote.RemoteWebElement) element).getId());
        js().executeScript("mobile: doubleClick", params);
    }
}
