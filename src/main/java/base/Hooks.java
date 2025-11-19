package base;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ConfigReader;
import listeners.ExtentListener;

public class Hooks {

    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {
        ConfigReader.init();
        String platform = System.getProperty("platform", ConfigReader.get("platform"));
        DriverFactory.initDriver(platform);
        ExtentListener.startTest(scenario.getName());
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
