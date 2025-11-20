package base;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import listeners.ExtentListener;
import utils.ConfigReader;

public class Hooks {

    @Before(order = 1)
    public void beforeScenario() {
        String platform = System.getProperty("platform", ConfigReader.getOrDefault("platform", "android"));
        DriverFactory.initDriver(platform);
        ExtentListener.startTest(Thread.currentThread().getName());
    }

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            ExtentListener.captureScreenshot(DriverFactory.getDriver(), scenario.getName());
        }
        DriverFactory.quitDriver();
        ExtentListener.endTest();
    }
}

