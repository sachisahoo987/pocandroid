import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.net.URL;
import java.time.Duration;

public class GeneralStoreAutomationPixel8aClean {

    public static void main(String[] args) {

        AndroidDriver driver = null;

        try {
            // -----------------------------
            // 1️⃣ Setup Appium Options
            // -----------------------------
            UiAutomator2Options options = new UiAutomator2Options();
            options.setPlatformName("Android");
            options.setDeviceName("Pixel_8a_API_36");
            options.setUdid("emulator-5554");
            options.setAutomationName("UiAutomator2");
            options.setAppPackage("com.androidsample.generalstore");
            options.setAppActivity("com.androidsample.generalstore.SplashActivity");
            // Set AppWaitActivity to the known next activity for better stability
            options.setAppWaitActivity("com.androidsample.generalstore.MainActivity");
            options.setAutoGrantPermissions(true);
            options.setNoReset(false);
            options.setNewCommandTimeout(Duration.ofSeconds(1200));

            System.out.println("Connecting to Appium server...");

            // ⚠️ FIX: Updated URL for Appium 2.x endpoint
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);

            // Set a maximum explicit wait time
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // -----------------------------
            // 2️⃣ Select Country
            // -----------------------------
            System.out.println("Waiting for Country dropdown...");

            // ➡️ FIX: Use Explicit Wait for the element to be present
            WebElement countryDropdown = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.id("com.androidsample.generalstore:id/spinnerCountry")
                    )
            );

            countryDropdown.click();
            System.out.println("Opened country dropdown.");

            // Scroll to "India" and click
            // Modern way using the driver directly
            WebElement countryIndia = driver.findElement(
                    AppiumBy.androidUIAutomator( // Note: You would also need to import AppiumBy
                            "new UiScrollable(new UiSelector().scrollable(true))" +
                                    ".scrollIntoView(new UiSelector().text(\"India\"))"
                    )
            );
            countryIndia.click();
            System.out.println("Selected country: India");

            // -----------------------------
            // 3️⃣ Enter Name
            // -----------------------------
            WebElement nameField = driver.findElement(By.id("com.androidsample.generalstore:id/nameField"));
            nameField.sendKeys("Sachidananda");
            System.out.println("Entered name.");

            // Hide keyboard safely
            try { driver.hideKeyboard(); } catch (Exception ignored) {}

            // -----------------------------
            // 4️⃣ Click Let's Shop
            // -----------------------------
            driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
            System.out.println("Clicked 'Let's Shop' button.");

            // ➡️ Best practice: Use an explicit wait for the next screen's elements
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.androidsample.generalstore:id/toolbar_title")));
            System.out.println("Landed on the Product screen.");

            System.out.println("Scenario completed successfully on Pixel 8a!");

        } catch (Exception e) {
            System.out.println("Test failed:");
            e.printStackTrace();
        } finally {
            if (driver != null) {
                System.out.println("Closing session.");
                driver.quit();
            }
        }
    }
}