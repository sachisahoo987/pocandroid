package pages;

import base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Map;

public class ProductPage extends BasePage {

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
    private List<WebElement> productNames;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productAddCart")
    private List<WebElement> addToCartButtons;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnCart")
    private WebElement cartBtn;

    public ProductPage() { super(); }

    public void addProductByName(String name) {
        for (int i = 0; i < productNames.size(); i++) {
            if (productNames.get(i).getText().trim().equalsIgnoreCase(name.trim())) {
                addToCartButtons.get(i).click();
                return;
            }
        }
        // fallback scroll (android)
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + name + "\"));"));
        // refresh elements
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        for (int i = 0; i < productNames.size(); i++) {
            if (productNames.get(i).getText().trim().equalsIgnoreCase(name.trim())) {
                addToCartButtons.get(i).click();
                return;
            }
        }
        throw new RuntimeException("Product not found: " + name);
    }

    public void goToCart() { cartBtn.click(); }
}
