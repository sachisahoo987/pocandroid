package pages;

import base.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    @AndroidFindBy(id = "com.example:id/username")
    @iOSXCUITFindBy(accessibility = "username")
    public WebElement username;

    @AndroidFindBy(id = "com.example:id/password")
    @iOSXCUITFindBy(accessibility = "password")
    public WebElement password;

    @AndroidFindBy(id = "com.example:id/loginBtn")
    @iOSXCUITFindBy(accessibility = "loginBtn")
    public WebElement loginButton;

    public void login(String u, String p) {
        waitForVisible(username);
        username.sendKeys(u);
        password.sendKeys(p);
        loginButton.click();
    }
}
