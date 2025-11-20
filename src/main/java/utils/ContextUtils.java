/*
package utils;

import base.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.*;
import io.appium.java_client.remote.SupportsContextSwitching;

import java.util.Set;

public final class ContextUtils {

    private ContextUtils() {}

    */
/**
     * Switches driver context to WEBVIEW for Android or iOS.
     * Throws RuntimeException if no WebView context is found.
     *//*

    public static void switchToWebView() {
        AppiumDriver driver = DriverFactory.getDriver();
        SupportsContextSwitching ctx = (SupportsContextSwitching) driver;

        Set<String> contexts = ctx.getContextHandles();
        System.out.println("Available Contexts: " + contexts);

        for (String context : contexts) {
            if (context.toLowerCase().contains("webview") || context.toLowerCase().contains("browser")) {
                ctx.context(context);
                System.out.println("Switched to WebView context: " + context);
                return;
            }
        }
        throw new RuntimeException("No WEBVIEW context found");
    }

    */
/**
     * Switches back to native app context.
     *//*

    public static void switchToNative() {
        AppiumDriver driver = DriverFactory.getDriver();
        SupportsContextSwitching ctx = (SupportsContextSwitching) driver;

        ctx.context("NATIVE_APP");
        System.out.println("Switched to Native context");
    }

    */
/**
     * Returns the current context.
     *//*

    public static String getCurrentContext() {
        AppiumDriver driver = DriverFactory.getDriver();
        SupportsContextSwitching ctx = (SupportsContextSwitching) driver;
        return ctx.getContext();
    }

    */
/**
     * Prints all available contexts (useful for debugging).
     *//*

    public static void printAvailableContexts() {
        AppiumDriver driver = DriverFactory.getDriver();
        SupportsContextSwitching ctx = (SupportsContextSwitching) driver;

        Set<String> contexts = ctx.getContextHandles();
        System.out.println("Available Contexts: " + contexts);
    }
}
*/
