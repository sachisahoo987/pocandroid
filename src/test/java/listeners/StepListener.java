package listeners;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import utils.AllureUtils;

public class StepListener {

    @AfterStep
    public void attachScreenshot(Scenario scenario) {
        boolean takeAll = Boolean.getBoolean("takeAllScreens");

        if (takeAll || scenario.isFailed()) {
            AllureUtils.takeScreenshot(
                    scenario.getName().replaceAll("\\s+", "_") + "_step"
            );
        }
    }
}
