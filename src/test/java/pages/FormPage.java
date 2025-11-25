package pages;

import base.BasePage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LocatorReader;

import java.time.Duration;

public class FormPage extends BasePage {

    private final By nameField = By.xpath(LocatorReader.getLocator("FormPage", "nameField"));
    private final By countrySpinner = By.xpath(LocatorReader.getLocator("FormPage", "countrySpinner"));
    private final By letsShopBtn = By.xpath(LocatorReader.getLocator("FormPage", "letsShopBtn"));

    public FormPage() { super(); }

    public void waitForFormReady() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(nameField));
    }

    public void enterName(String name) {
        perform("Enter Name: " + name, () -> {
            waitForFormReady();
            WebElement nf = driver.findElement(nameField);
            nf.clear();
            nf.sendKeys(name);
            try { driver.executeScript("mobile: hideKeyboard"); } catch (Exception ignored) {}
        });
    }

    public void selectCountry(String country) {
        perform("Select Country: " + country, () -> {
            driver.findElement(countrySpinner).click();

            String uiScroll = String.format(
                    "new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
                            ".scrollIntoView(new UiSelector().text(\"%s\").instance(0));",
                    country
            );

            driver.findElement(AppiumBy.androidUIAutomator(uiScroll)).click();
        });
    }

    public void tapLetsShop() {
        perform("Tap Let's Shop", () -> {
            driver.findElement(letsShopBtn).click();
        });
    }
}
