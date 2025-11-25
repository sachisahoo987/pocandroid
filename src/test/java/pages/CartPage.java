package pages;

import base.BasePage;
import base.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import utils.LocatorReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartPage extends BasePage {

    private final By productPriceBy = By.xpath(LocatorReader.getLocator("CartPage", "productPrice"));
    private final By totalAmountBy = By.xpath(LocatorReader.getLocator("CartPage", "totalAmount"));
    private final By termsBy = By.xpath(LocatorReader.getLocator("CartPage", "termsBtn"));

    public CartPage() { super(); }

    public double sumOfProductPrices() {
        return perform("Calculate Sum of Product Prices", () -> {
            List<WebElement> prices = driver.findElements(productPriceBy);
            return prices.stream()
                    .mapToDouble(e -> Double.parseDouble(e.getText().replaceAll("[^0-9.]", "")))
                    .sum();
        });
    }

    public double displayedTotal() {
        return perform("Get Displayed Cart Total", () -> {
            WebElement el = driver.findElement(totalAmountBy);
            return Double.parseDouble(el.getText().replaceAll("[^0-9.]", ""));
        });
    }

    public void openTerms() {
        perform("Open Terms & Conditions", () -> {
            WebElement termsBtn = driver.findElement(termsBy);

            try {
                Map<String, Object> args = new HashMap<>();
                args.put("elementId", ((RemoteWebElement) termsBtn).getId());
                args.put("duration", 2000);
                driver.executeScript("mobile: longClickGesture", args);
            } catch (Exception e) {
                termsBtn.click();
            }
        });
    }

}
