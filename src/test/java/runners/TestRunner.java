package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"hooks", "steps", "listeners", "utils","base"},
        plugin = {
                "pretty",
                "json:target/cucumber.json",
                "html:target/cucumber-html-report",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        },
        monochrome = true,
        tags = "not @ignore"   // will be overridden by runtime tag
)
public class TestRunner extends AbstractTestNGCucumberTests {

    static {
        // ✔ get tag from maven command
        String runtimeTag = System.getProperty("cucumber.filter.tags");

        if (runtimeTag != null && !runtimeTag.trim().isEmpty()) {
            // ✔ override the tag at runtime
            System.setProperty("cucumber.filter.tags", runtimeTag);
        }
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
