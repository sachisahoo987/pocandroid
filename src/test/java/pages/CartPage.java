package pages;

import base.BasePage;
import base.DriverFactory;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.cucumber.java.Before;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    @Before
    public void setup() {
        DriverFactory.initDriver("android");    // start driver
        CartPage cartPage = new CartPage();        // now driver is non-null
    }

    /* ====================== PRODUCT PRICES ====================== */
    @AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[contains(@name,'$')]")
    private List<WebElement> productPrices;

    /* ====================== TOTAL AMOUNT ====================== */
    @AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
    @iOSXCUITFindBy(accessibility = "total_amount")
    private WebElement totalAmount;

    /* ====================== TERMS BUTTON ====================== */
    @AndroidFindBy(id = "com.androidsample.generalstore:id/termsButton")
    @iOSXCUITFindBy(accessibility = "terms_button")
    private WebElement termsButton;


    /* ============================================================
       BUSINESS METHODS
       ============================================================ */

    public double sumOfProductPrices() {
        double sum = 0.0;
        for (WebElement p : productPrices) {
            String txt = p.getText().replaceAll("[^0-9.]", "");
            if (!txt.isEmpty()) {
                sum += Double.parseDouble(txt);
            }
        }
        return sum;
    }

    public double displayedTotal() {
        String txt = totalAmount.getText().replaceAll("[^0-9.]", "");
        return txt.isEmpty() ? 0.0 : Double.parseDouble(txt);
    }

    public void openTerms() {
        termsButton.click();
    }

    public boolean isCartPageLoaded() {
        try {
            return totalAmount.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getTotalAmount() {
        return totalAmount.getText();
    }

}
