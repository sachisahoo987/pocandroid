package debug;

import io.cucumber.java.Before;
import org.openqa.selenium.remote.http.ClientConfig;

public class DebugHook {
    @Before(order = 0)
    public void showSeleniumJar() {
        System.out.println("====== SELENIUM JAR LOADED AT RUNTIME ======");
        System.out.println(ClientConfig.class.getProtectionDomain().getCodeSource().getLocation());
        System.out.println("============================================");
    }
}
