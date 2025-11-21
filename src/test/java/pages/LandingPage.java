package pages;


import base.BasePage;
import base.DriverFactory;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.cucumber.java.Before;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends BasePage {

    public LandingPage() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    /* ===================== LOCATORS ===================== */

    @AndroidFindBy(id = "android:id/text1")
    @iOSXCUITFindBy(accessibility = "country_dropdown")
    private WebElement countryDropdown;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeTextField")
    private WebElement nameField;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/radioMale")
    @iOSXCUITFindBy(accessibility = "gender_male")
    private WebElement maleButton;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
    @iOSXCUITFindBy(accessibility = "gender_female")
    private WebElement femaleButton;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
    @iOSXCUITFindBy(accessibility = "lets_shop_btn")
    private WebElement shopButton;


    /* ===================== ACTIONS ===================== */

    public void selectCountry(String country) {
        countryDropdown.click();

        if (isAndroid()) {
            driver.findElement(
                            AppiumBy.androidUIAutomator(
                                    "new UiSelector().text(\"" + country + "\")"))
                    .click();
        } else { // iOS
            driver.findElement(
                    AppiumBy.iOSClassChain(
                            "**/XCUIElementTypeStaticText[`label == \"" + country + "\"`]"
                    )).click();
        }
    }

    public void enterName(String name) {
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void selectGender(String gender) {
        if (gender.equalsIgnoreCase("male"))
            maleButton.click();
        else
            femaleButton.click();
    }

    public void clickShop() {
        shopButton.click();
    }
}
