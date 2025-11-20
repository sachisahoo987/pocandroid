package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int attempts = 0;
    private final int maxAttempts = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (attempts < maxAttempts) {
            attempts++;
            System.out.println("Retrying failed test: " + result.getName() +
                    " | Attempt " + attempts + " of " + maxAttempts);
            return true;
        }
        return false;
    }
}
