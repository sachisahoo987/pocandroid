package pages;

import base.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
    private List<WebElement> productPrices;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
    private WebElement totalAmount;

    public CartPage() { super(); }

    public double sumOfProductPrices() {
        return productPrices.stream()
                .mapToDouble(e -> Double.parseDouble(e.getText().replaceAll("[^0-9.]", "")))
                .sum();
    }

    public double displayedTotal() {
        return Double.parseDouble(totalAmount.getText().replaceAll("[^0-9.]", ""));
    }

    public void openTerms() {
        // implement long press or open terms
    }
}
