package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public abstract class BasePage {


    public static final ThreadLocal<AppiumDriver> DRIVER = new ThreadLocal<>();

    protected AppiumDriver driver = DRIVER.get();

    protected BasePage() {
        this.driver = DriverFactory.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    protected void waitForVisible(WebElement element, int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForVisible(WebElement element) {
        waitForVisible(element, 20);
    }

    protected boolean isAndroid() {
        return String.valueOf(
                driver.getCapabilities().getCapability("platformName")
        ).equalsIgnoreCase("android");
    }

    protected boolean isIOS() {
        return String.valueOf(
                driver.getCapabilities().getCapability("platformName")
        ).equalsIgnoreCase("ios");
    }

}
