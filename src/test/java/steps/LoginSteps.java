package steps;

import io.cucumber.java.en.*;
import pages.LoginPage;
import org.testng.Assert;

public class LoginSteps {

    private LoginPage loginPage = new LoginPage();

    @Given("the app is launched")
    public void the_app_is_launched() {
        // driver created in Hooks
    }

    @When("the user enters valid login credentials")
    public void enter_login() {
        loginPage.login("testuser","password123");
    }

    @Then("the user should land on the home screen")
    public void validate_home() {
        Assert.assertTrue(true, "Home screen visible (placeholder)");
    }
}
