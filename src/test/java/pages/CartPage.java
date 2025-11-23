package pages;

import base.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartPage extends BasePage {

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
    private List<WebElement> productPrices;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
    private WebElement totalAmount;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/termsButton")
    private WebElement termsBtn;

    public CartPage() { super(); }

    // --------------------------
    // PRICE SUM
    // --------------------------
    public double sumOfProductPrices() {
        return productPrices.stream()
                .mapToDouble(e -> Double.parseDouble(e.getText().replaceAll("[^0-9.]", "")))
                .sum();
    }

    // --------------------------
    // DISPLAYED TOTAL
    // --------------------------
    public double displayedTotal() {
        return Double.parseDouble(totalAmount.getText().replaceAll("[^0-9.]", ""));
    }

    // --------------------------
    // LONG-PRESS TERMS AND CONDITIONS
    // --------------------------
    public void openTerms() {
        // ensure the terms button is visible first
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(termsBtn));

        // use Appium's mobile longClickGesture - duration is ms
        Map<String, Object> args = new HashMap<>();
        args.put("elementId", ((RemoteWebElement) termsBtn).getId());
        args.put("duration", 2000); // 2 seconds long press

        driver.executeScript("mobile: longClickGesture", args);

        // Wait for modal dialog to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle")));
    }
}
