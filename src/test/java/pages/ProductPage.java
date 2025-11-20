package pages;

import base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.ScrollUtils;

import java.util.List;
import java.util.Map;

public class ProductPage extends BasePage {

    /* ======================== PRODUCT NAME LIST ======================== */
    @AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText")
    private List<WebElement> productNames;

    /* ======================== ADD TO CART BUTTON LIST ======================== */
    @AndroidFindBy(id = "com.androidsample.generalstore:id/productAddCart")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeButton[`label CONTAINS[c] 'Add'`]")
    private List<WebElement> addToCartButtons;

    /* ======================== CART BUTTON ======================== */
    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnCart")
    @iOSXCUITFindBy(accessibility = "cart_button")
    private WebElement cartBtn;


    /* ========================================================================
       CONSTRUCTOR
       ======================================================================== */
    public ProductPage() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    /* ========================================================================
       ADD PRODUCT BY NAME (CROSS PLATFORM)
       ======================================================================== */

    public void addProductByName(String name) {

        // 1️⃣ Try visible list first
        if (clickProductIfVisible(name)) return;

        // 2️⃣ Scroll depending on platform
        if (isAndroid()) {
            ScrollUtils.scrollToText(name);
        } else if (isIOS()) {
            scrollIOS(name);
        }

        // Refresh elements after scroll
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);

        // 3️⃣ Try again after scrolling
        if (clickProductIfVisible(name)) return;

        // 4️⃣ Platform fallback (search UI element directly)

        if (isAndroid()) {
            try {
                driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().textContains(\"" + name + "\")"
                )).click();
                return;
            } catch (Exception ex) {ex.printStackTrace();}
        }

        if (isIOS()) {
            try {
                driver.findElement(AppiumBy.iOSClassChain(
                        "**/XCUIElementTypeStaticText[`label CONTAINS[c] \"" + name + "\"`]"
                )).click();
                return;
            } catch (Exception ex) {ex.printStackTrace();}
        }

        throw new RuntimeException("Product not found: " + name);
    }


    /* ========================================================================
       CLICK PRODUCT IF CURRENTLY VISIBLE
       ======================================================================== */
    private boolean clickProductIfVisible(String name) {
        for (int i = 0; i < productNames.size(); i++) {
            if (productNames.get(i).getText().trim().equalsIgnoreCase(name.trim())) {
                addToCartButtons.get(i).click();
                return true;
            }
        }
        return false;
    }


    /* ========================================================================
       iOS SCROLLING
       ======================================================================== */
    private void scrollIOS(String name) {
        try {
            driver.executeScript("mobile: scroll", Map.of(
                    "direction", "down",
                    "predicateString", "label CONTAINS[c] '" + name + "'"
            ));
        } catch (Exception ex) {ex.printStackTrace();}
    }


    /* ========================================================================
       NAVIGATE TO CART
       ======================================================================== */
    public void goToCart() {
        cartBtn.click();
    }

    public void addFirstProductToCart() {
        if (!addToCartButtons.isEmpty()) {
            addToCartButtons.get(0).click();
        } else {
            throw new RuntimeException("No products found to add!");
        }
    }

    public void openCart() {
        cartBtn.click();
    }
}
