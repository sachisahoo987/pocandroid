package base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

  /*  @BeforeMethod
    public void setup() {
        String platform = utils.ConfigReader.getOrDefault("platform", "android");
        DriverFactory.initDriver(platform);
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }*/
}
