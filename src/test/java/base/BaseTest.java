package base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    @BeforeMethod
    public void setup() {
        DriverFactory.initDriver("android"); // or from config
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
