package pages;

import base.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class FormPage extends BasePage {

    @AndroidFindBy(id = "com.androidsample.generalstore:id/spinnerCountry")
    private WebElement countryDropdown;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
    private WebElement nameField;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/radioMale")
    private WebElement maleRadio;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
    private WebElement femaleRadio;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
    private WebElement letsShopBtn;

    public void selectCountry(String country) {
        waitForVisible(countryDropdown);
        countryDropdown.click();
        // scroll to the country and click - using ScrollUtils
        try {
            utils.ScrollUtils.scrollToText(country);
            // after scroll, click the text directly (UiScrollable brings it to view)
            driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + country + "\")").click();
        } catch (Exception e) {
            // fallback: attempt xpath click
            driver.findElementByXPath("//android.widget.TextView[contains(@text, \"" + country + "\")]").click();
        }
    }

    public void enterName(String name) {
        waitForVisible(nameField);
        nameField.clear();
        nameField.sendKeys(name);
        try { driver.hideKeyboard(); } catch (Exception ignored) {}
    }

    public void selectGender(String gender) {
        if ("female".equalsIgnoreCase(gender)) femaleRadio.click();
        else maleRadio.click();
    }

    public void tapLetsShop() {
        letsShopBtn.click();
    }
}
