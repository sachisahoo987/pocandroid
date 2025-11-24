package pages;

import base.BasePage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.LocatorReader;

import java.util.List;

public class ProductPage extends BasePage {

    private final By productNameBy = By.xpath(LocatorReader.getLocator("ProductPage", "productName"));
    private final By addCartBtnBy = By.xpath(LocatorReader.getLocator("ProductPage", "addCartBtn"));
    private final By cartBtnBy = By.xpath(LocatorReader.getLocator("ProductPage", "cartBtn"));

    public ProductPage() { super(); }

    public void addProductByName(String name) {
        List<WebElement> names = driver.findElements(productNameBy);
        List<WebElement> adds = driver.findElements(addCartBtnBy);

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).getText().trim().equalsIgnoreCase(name.trim())) {
                adds.get(i).click();
                return;
            }
        }

        // scroll into view and re-query
        String uiScroll = String.format("new UiScrollable(new UiSelector().scrollable(true).instance(0))"
                + ".scrollIntoView(new UiSelector().text(\"%s\").instance(0));", name);
        driver.findElement(AppiumBy.androidUIAutomator(uiScroll));

        names = driver.findElements(productNameBy);
        adds = driver.findElements(addCartBtnBy);
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).getText().trim().equalsIgnoreCase(name.trim())) {
                adds.get(i).click();
                return;
            }
        }

        throw new RuntimeException("Product not found: " + name);
    }

    public void goToCart() {
        driver.findElement(cartBtnBy).click();
    }
}
