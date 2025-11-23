package pages;

import base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FormPage extends BasePage {

    @AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
    private WebElement nameField;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
    private WebElement letsShopBtn;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/spinnerCountry")
    private WebElement countrySpinner;

    @AndroidFindBy(xpath = "//android.widget.RadioButton[@text='Male']")
    private WebElement maleRadio;

    @AndroidFindBy(xpath = "//android.widget.RadioButton[@text='Female']")
    private WebElement femaleRadio;

    public FormPage() { super(); }

    // --------------------------
    // WAIT FOR SCREEN LOAD
    // --------------------------
    public void waitForFormReady() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(nameField));
    }

    // --------------------------
    // ENTER NAME
    // --------------------------
    public void enterName(String name) {
        waitForFormReady();
        nameField.clear();
        nameField.sendKeys(name);

        try {
            driver.executeScript("mobile: hideKeyboard");
        } catch (Exception ignored) {}
    }

    // --------------------------
    // SELECT COUNTRY (fully working scroll + click)
    // --------------------------
    public void selectCountry(String country) {
        countrySpinner.click();

        String scrollToText = String.format(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
                        ".scrollIntoView(new UiSelector().text(\"%s\").instance(0));", country);

        WebElement countryToSelect =
                driver.findElement(AppiumBy.androidUIAutomator(scrollToText));

        countryToSelect.click();
    }

    // --------------------------
    // SELECT GENDER RADIO BUTTON
    // --------------------------
    public void selectGender(String gender) {
        gender = gender.trim().toLowerCase();

        if (gender.contains("m")) maleRadio.click();
        else if (gender.contains("f")) femaleRadio.click();
        else throw new RuntimeException("Invalid gender: " + gender);
    }

    // --------------------------
    // TAP LET’S SHOP
    // --------------------------
    public void tapLetsShop() {
        letsShopBtn.click();
    }
}
