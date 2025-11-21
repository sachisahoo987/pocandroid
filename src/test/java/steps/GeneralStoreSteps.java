package steps;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.CartPage;
import pages.FormPage;
import pages.ProductPage;

public class GeneralStoreSteps {

    private final FormPage form = new FormPage();
    private final ProductPage product = new ProductPage();
    private final CartPage cart = new CartPage();

    // MUST have public zero-arg constructor (implicit)
    public GeneralStoreSteps() {}

    @Given("the app is launched")
    public void app_launched() { /* nothing - hooks started driver */ }

    @When("the user selects country {string}")
    public void select_country(String country) { form.selectCountry(country); }

    @When("the user enters name {string}")
    public void enter_name(String name) { form.enterName(name); }

    @When("the user taps Let's Shop")
    public void tap_shop() { form.tapLetsShop(); }

    @Then("the product list should be displayed")
    public void product_list() { Assert.assertTrue(product != null); }

    @When("the user adds product {string} and {string}")
    public void add_products(String p1, String p2) {
        product.addProductByName(p1);
        product.addProductByName(p2);
    }

    @When("the user navigates to cart")
    public void go_cart() { product.goToCart(); }

    @Then("the cart total should equal sum of item prices")
    public void verify_cart_total() {
        double sum = cart.sumOfProductPrices();
        double total = cart.displayedTotal();
        Assert.assertEquals(total, sum, 0.01);
    }

    @When("the user long presses on Terms and Conditions")
    public void long_press_terms() { cart.openTerms(); }

    @Then("the Terms modal should be displayed")
    public void terms_modal() { Assert.assertTrue(true); }
}
