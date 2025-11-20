package pages;

import base.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductPage extends BasePage {

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
    private List<WebElement> productNames;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productAddCart")
    private List<WebElement> addToCartButtons;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnCart")
    private WebElement cartBtn;

    public void addProductByName(String name) {
        for (int i = 0; i < productNames.size(); i++) {
            if (productNames.get(i).getText().trim().equalsIgnoreCase(name.trim())) {
                addToCartButtons.get(i).click();
                return;
            }
        }
        // fallback: scroll to name and click - use ScrollUtils
        utils.ScrollUtils.scrollToText(name);
        // attempt again (assume visible now)
        for (int i = 0; i < productNames.size(); i++) {
            if (productNames.get(i).getText().trim().equalsIgnoreCase(name.trim())) {
                addToCartButtons.get(i).click();
                return;
            }
        }
        throw new RuntimeException("Product not found: " + name);
    }

    public void addFirstProduct() {
        if (!addToCartButtons.isEmpty()) addToCartButtons.get(0).click();
    }

    public void goToCart() {
        cartBtn.click();
    }
}
