package hooks;

import base.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void init() {
        DriverFactory.initDriver("android");
    }

    @After
    public void quit() {
        DriverFactory.quitDriver();
    }
}
