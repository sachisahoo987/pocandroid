package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected AppiumDriver driver;
    protected AllureLifecycle lifecycle = Allure.getLifecycle();

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        if (this.driver == null)
            throw new IllegalStateException("Driver is null. Did you initialize it in Hooks?");

        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    @Step("{action}")
    public void perform(String action, Runnable runnable) {
        runnable.run();
        attachScreenshot();
    }

    @Step("{action}")
    protected <T> T perform(String action, java.util.concurrent.Callable<T> callable) {
        try {
            T result = callable.call();   // Execute and capture return value
            attachScreenshot();           // Auto screenshot
            return result;                // Return the result to caller
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // ==========================
    //      WAIT WRAPPER
    // ==========================
    protected void waitForVisible(WebElement element, int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForVisible(WebElement element) {
        waitForVisible(element, 20);
    }



    // ==========================
    // AUTOMATIC SCREENSHOT
    // ==========================
    @Attachment(value = "{action} Screenshot", type = "image/png")
    protected byte[] attachScreenshot() {
        byte[] shot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        lifecycle.addAttachment("Screenshot", "image/png", "png", shot);
        return shot;
    }

    // ==========================
    //   COMMON ACTION HELPERS
    // ==========================
    protected void click(WebElement element, String msg) {
        perform(msg, () -> element.click());
    }

    protected void type(WebElement element, String text, String msg) {
        perform(msg, () -> element.sendKeys(text));
    }

    protected String get(WebElement element, String msg) {
        final String[] value = {""};
        perform(msg, () -> value[0] = element.getText());
        return value[0];
    }

    // ==========================
    //  PLATFORM HELPERS
    // ==========================
    protected String platformName() {
        Object o = driver.getCapabilities().getCapability("platformName");
        return o == null ? "" : o.toString().toLowerCase();
    }

    protected boolean isAndroid() { return platformName().contains("android"); }

    protected boolean isIOS() { return platformName().contains("ios"); }
}
