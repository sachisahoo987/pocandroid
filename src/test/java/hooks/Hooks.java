package hooks;

import base.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.ConfigReader;

public class Hooks {

    @Before
    public void beforeScenario() {
        // platform read from config or default to android
        String platform = ConfigReader.getOrDefault("platform", "android");
        DriverFactory.initDriver(platform);
    }

    @After
    public void afterScenario() {
        DriverFactory.quitDriver();
    }
}
