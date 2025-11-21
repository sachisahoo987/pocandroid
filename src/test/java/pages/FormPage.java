package pages;

import base.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class FormPage extends BasePage {

    @AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
    private WebElement nameField;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
    private WebElement letsShopBtn;

    public FormPage() {
        super();
    }

    public void enterName(String name) {
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void tapLetsShop() {
        letsShopBtn.click();
    }

    public void selectCountry(String country) {
        // implement scrolling/select logic for country selector - placeholder
    }

    public void selectGender(String gender) {
        // implement select male/female radio
    }
}
