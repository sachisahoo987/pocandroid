package pages;

import base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
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
        // First try currently visible product list
        List<WebElement> names = driver.findElements(By.id("com.androidsample.generalstore:id/productName"));
        List<WebElement> adds = driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart"));

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).getText().trim().equalsIgnoreCase(name.trim())) {
                adds.get(i).click();
                return;
            }
        }

        // Not visible — scroll into view and re-query elements
        String uiScrollable = String.format(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
                        ".scrollIntoView(new UiSelector().text(\"%s\").instance(0));", name);
        try {
            driver.findElement(AppiumBy.androidUIAutomator(uiScrollable));
        } catch (Exception e) {
            // fallback: try partial match by description/textContains (safer)
            String altScroll = String.format(
                    "new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
                            ".scrollIntoView(new UiSelector().textContains(\"%s\").instance(0));", name);
            driver.findElement(AppiumBy.androidUIAutomator(altScroll));
        }

        // Re-fetch elements (fresh references)
        names = driver.findElements(By.id("com.androidsample.generalstore:id/productName"));
        adds = driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart"));

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).getText().trim().equalsIgnoreCase(name.trim())) {
                adds.get(i).click();
                return;
            }
        }

        throw new RuntimeException("Product not found after scroll: " + name);
    }

    public void goToCart() { cartBtn.click(); }
}
