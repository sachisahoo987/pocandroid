package pages;

import base.BasePage;
import base.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.LocatorReader;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartPage extends BasePage {

    private final By productPriceBy = By.xpath(LocatorReader.getLocator("CartPage", "productPrice"));
    private final By totalAmountBy = By.xpath(LocatorReader.getLocator("CartPage", "totalAmount"));
    private final By termsBy = By.xpath(LocatorReader.getLocator("CartPage", "termsBtn"));

    public CartPage() { super(); }

    public double sumOfProductPrices() {
        List<WebElement> prices = driver.findElements(productPriceBy);
        return prices.stream()
                .mapToDouble(e -> Double.parseDouble(e.getText().replaceAll("[^0-9.]", "")))
                .sum();
    }

    public double displayedTotal() {
        WebElement el = driver.findElement(totalAmountBy);
        return Double.parseDouble(el.getText().replaceAll("[^0-9.]", ""));
    }

    public void openTerms() {
        WebElement termsBtn = driver.findElement(termsBy);
        try {
            Map<String, Object> args = new HashMap<>();
            args.put("elementId", ((org.openqa.selenium.remote.RemoteWebElement) termsBtn).getId());
            args.put("duration", 2000);
            DriverFactory.getDriver().executeScript("mobile: longClickGesture", args);
        } catch (Exception e) {
            // fallback: click
            termsBtn.click();
        }
    }
}
