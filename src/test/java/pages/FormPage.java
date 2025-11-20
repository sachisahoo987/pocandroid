package pages;

import base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import utils.ScrollUtils;

import static com.sun.jna.Platform.isAndroid;

public class FormPage extends BasePage {

    /* ====================== COUNTRY DROPDOWN ====================== */
    @AndroidFindBy(id = "com.androidsample.generalstore:id/spinnerCountry")
    @iOSXCUITFindBy(accessibility = "country_dropdown")
    private WebElement countryDropdown;

    /* ====================== NAME FIELD ====================== */
    @AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeTextField[contains(@value,'name')]")
    private WebElement nameField;

    /* ====================== GENDER RADIO ====================== */
    @AndroidFindBy(id = "com.androidsample.generalstore:id/radioMale")
    @iOSXCUITFindBy(accessibility = "gender_male")
    private WebElement maleRadio;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
    @iOSXCUITFindBy(accessibility = "gender_female")
    private WebElement femaleRadio;

    /* ====================== LET’S SHOP BUTTON ====================== */
    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
    @iOSXCUITFindBy(accessibility = "lets_shop_btn")
    private WebElement letsShopBtn;


    /* =======================================================================
       ACTION METHODS
       ======================================================================= */

    public void selectCountry(String country) {
        waitForVisible(countryDropdown);
        countryDropdown.click();

        try {
            ScrollUtils.scrollToText(country);

            // Android: AppiumBy.androidUIAutomator
            if (isAndroid()) {
                driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().textContains(\"" + country + "\")"
                )).click();
            }

            // iOS: XCUI text element by name
            else {
                driver.findElement(AppiumBy.iOSClassChain(
                        "**/XCUIElementTypeStaticText[`name CONTAINS[c] \"" + country + "\"`]"
                )).click();
            }

        } catch (Exception e) {
            // fallback for Android + iOS
            if (isAndroid()) {
                driver.findElement(AppiumBy.xpath(
                        "//android.widget.TextView[contains(@text,'" + country + "')]"
                )).click();
            } else {
                driver.findElement(AppiumBy.iOSClassChain(
                        "**/XCUIElementTypeStaticText[`label CONTAINS[c] \"" + country + "\"`]"
                )).click();
            }
        }
    }


    public void enterName(String name) {
        waitForVisible(nameField);
        nameField.clear();
        nameField.sendKeys(name);
    }


    public void selectGender(String gender) {
        if ("female".equalsIgnoreCase(gender)) {
            femaleRadio.click();
        } else {
            maleRadio.click();
        }
    }


    public void tapLetsShop() {
        letsShopBtn.click();
    }
}
