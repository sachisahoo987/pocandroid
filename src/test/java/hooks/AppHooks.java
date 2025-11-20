package hooks;

import base.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class AppHooks {

    @Before
    public void startApp() throws Exception {
        DriverManager.initDriver();
    }

    @After
    public void tearDown() {
        DriverManager.quit();
    }
}
