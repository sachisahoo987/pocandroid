package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ExtentListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    static {
        ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    public static void startTest(String name) {
        test.set(extent.createTest(name));
    }

    public static void captureScreenshot(AppiumDriver driver, String name) {
        try {
            String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.get().addScreenCaptureFromBase64String(base64, name);
        } catch (Exception e) {
            test.get().warning("Failed to capture screenshot: " + e.getMessage());
        }
    }

    public static void endTest() {
        extent.flush();
    }
}
