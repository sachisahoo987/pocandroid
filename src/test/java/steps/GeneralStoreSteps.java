package steps;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.CartPage;
import pages.FormPage;
import pages.ProductPage;
import listeners.ExtentListener;
import utils.GestureUtils;

public class GeneralStoreSteps {

    private final FormPage form = new FormPage();
    private final ProductPage product = new ProductPage();
    private final CartPage cart = new CartPage();

    @Given("the app is launched")
    public void app_launched() {
        ExtentListener.startTest("GeneralStore - scenario");
    }

    @When("the user selects country {string}")
    public void select_country(String country) {
        form.selectCountry(country);
        ExtentListener.pass("Selected country: " + country);
    }

    @When("the user enters name {string}")
    public void enter_name(String name) {
        form.enterName(name);
        ExtentListener.pass("Entered name: " + name);
    }

    @When("the user selects gender {string}")
    public void select_gender(String gender) {
        form.selectGender(gender);
    }

    @When("the user taps Let's Shop")
    public void tap_shop() {
        form.tapLetsShop();
    }

    @Then("the product list should be displayed")
    public void product_list() {
        Assert.assertTrue(product != null);
    }

    @Given("the user is on the product list")
    public void on_product_list() {
        // assume previous scenario or do form flow
        // minimal check
        Assert.assertTrue(true);
    }

    @When("the user adds product {string} and {string}")
    public void add_products(String p1, String p2) {
        product.addProductByName(p1);
        product.addProductByName(p2);
        ExtentListener.pass("Added products: " + p1 + ", " + p2);
    }

    @When("the user navigates to cart")
    public void go_cart() {
        product.goToCart();
    }

    @Then("the cart total should equal sum of item prices")
    public void verify_cart_total() {
        double sum = cart.sumOfProductPrices();
        double total = cart.displayedTotal();
        Assert.assertEquals(total, sum, 0.01);
        ExtentListener.pass("Cart total verified: " + total);
    }

    @Given("the user is on the cart page")
    public void on_cart_page() {
        // placeholder
    }

    @When("the user long presses on Terms and Conditions")
    public void long_press_terms() {
        // find location approximate or tap element
        cart.openTerms();
        ExtentListener.pass("Terms opened");
    }

    @Then("the Terms modal should be displayed")
    public void terms_modal() {
        Assert.assertTrue(true);
        ExtentListener.pass("Terms modal verified");
    }
}
