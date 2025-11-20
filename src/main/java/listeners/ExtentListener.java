package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public final class ExtentListener {

    private static final ExtentReports extent = new ExtentReports();
    private static final ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();

    static {
        ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-report.html");
        spark.config().setDocumentTitle("Automation Report");
        spark.config().setReportName("Mobile POC Report");
        spark.config().setTheme(Theme.STANDARD);
        extent.attachReporter(spark);
    }

    private ExtentListener() {}

    public static void startTest(String name) {
        currentTest.set(extent.createTest(name));
    }

    public static void pass(String msg) {
        if (currentTest.get() != null) currentTest.get().pass(msg);
    }

    public static void fail(String msg) {
        if (currentTest.get() != null) currentTest.get().fail(msg);
    }

    public static void captureScreenshot(AppiumDriver driver, String title) {
        try {
            if (driver == null) {
                return;
            }

            String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

            if (currentTest.get() != null) {
                currentTest.get().addScreenCaptureFromBase64String(base64, title);
            }

        } catch (Exception e) {
            if (currentTest.get() != null) {
                currentTest.get().warning("Screenshot failed: " + e.getMessage());
            }
        }
    }


    public static void endTest() {
        extent.flush();
        currentTest.remove();
    }
}
