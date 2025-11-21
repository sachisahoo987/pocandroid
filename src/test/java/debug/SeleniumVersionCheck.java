package debug;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;


import io.cucumber.java.Before;
import org.openqa.selenium.remote.http.ClientConfig;

public class SeleniumVersionCheck {
    @org.testng.annotations.Test
    public void showRemoteDriverJar() {
        System.out.println("====== SELENIUM REMOTE DRIVER JAR ======");
        System.out.println(RemoteWebDriver.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation());
        System.out.println("========================================");
    }
}
