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

    @AndroidFindBy(id = "com.androidsample.generalstore:id/termsButton")
    private WebElement termsButton;

    public double sumOfProductPrices() {
        double sum = 0.0;
        for (WebElement p : productPrices) {
            String txt = p.getText().replaceAll("[^0-9.]", "");
            if (!txt.isEmpty()) sum += Double.parseDouble(txt);
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
}
