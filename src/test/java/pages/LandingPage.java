package pages;

import base.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class LandingPage {

    private final AndroidDriver driver;

    public LandingPage() {
        this.driver = DriverManager.getDriver();
    }

    private final By countryDropdown = By.id("android:id/text1");
    private final By nameField = By.id("com.androidsample.generalstore:id/nameField");
    private final By maleButton = By.id("com.androidsample.generalstore:id/radioMale");
    private final By femaleButton = By.id("com.androidsample.generalstore:id/radioFemale");
    private final By shopButton = By.id("com.androidsample.generalstore:id/btnLetsShop");

    public void selectCountry(String country) {
        driver.findElement(countryDropdown).click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='" + country + "']")).click();
    }

    public void enterName(String name) {
        driver.findElement(nameField).sendKeys(name);
        driver.hideKeyboard();
    }

    public void selectGender(String gender) {
        if (gender.equalsIgnoreCase("male"))
            driver.findElement(maleButton).click();
        else
            driver.findElement(femaleButton).click();
    }

    public void clickShop() {
        driver.findElement(shopButton).click();
    }
}
