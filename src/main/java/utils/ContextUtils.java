package utils;

import base.DriverManager;
import io.appium.java_client.android.AndroidDriver;

public class ContextUtils {

    public static void switchToWebView() {
        AndroidDriver driver = DriverManager.getDriver();
        for (String context : driver.getContextHandles()) {
            if (context.contains("WEBVIEW")) {
                driver.context(context);
                break;
            }
        }
    }

    public static void switchToNative() {
        DriverManager.getDriver().context("NATIVE_APP");
    }
}
