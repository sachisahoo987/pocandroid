package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import base.DriverFactory;

public final class AllureUtils {

    public static void takeScreenshot(String name) {
        try {
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BYTES);

            Allure.getLifecycle().addAttachment(
                    name,
                    "image/png",
                    "png",
                    screenshot
            );

        } catch (Exception ignored) {}
    }
}
