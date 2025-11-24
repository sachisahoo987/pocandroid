package utils;

import base.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.appium.java_client.AppiumDriver;

import java.io.File;
import java.nio.file.Paths;

public final class ScreenshotUtils {

    private ScreenshotUtils() {}

    public static byte[] capturePngBytes(String name) {
        try {
            AppiumDriver driver = DriverFactory.getDriver();
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String dir = "test-output/screenshots";
            FileUtils.forceMkdir(new File(dir));
            String path = Paths.get(dir, name + "_" + System.currentTimeMillis() + ".png").toString();
            FileUtils.copyFile(src, new File(path));
            return FileUtils.readFileToByteArray(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
