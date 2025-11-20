package steps;

import io.cucumber.java.en.*;
import pages.CartPage;
import static org.testng.Assert.assertTrue;

public class CartSteps {

    CartPage cart = new CartPage();

    @Given("user is on cart page")
    public void on_cart() {
        assertTrue(true);
    }

    @Then("total amount should be visible")
    public void total_amount_visible() {
        assertTrue(cart.getTotalAmount().contains("$"));
    }
}
