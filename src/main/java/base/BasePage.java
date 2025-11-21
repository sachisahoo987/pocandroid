package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected AppiumDriver driver;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        if (this.driver == null) {
            throw new IllegalStateException("Driver is null. Did you initialize it in Hooks?");
        }
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    protected void waitForVisible(WebElement element, int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForVisible(WebElement element) {
        waitForVisible(element, 20);
    }

    protected String platformName() {
        Object o = driver.getCapabilities().getCapability("platformName");
        return o == null ? "" : o.toString().toLowerCase();
    }

    protected boolean isAndroid() {
        return platformName().contains("android");
    }

    protected boolean isIOS() {
        return platformName().contains("ios");
    }
}
