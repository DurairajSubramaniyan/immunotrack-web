package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;

public class Hooks {

    private static final ThreadLocal<Scenario> currentScenario = new ThreadLocal<>();

    @Before
    public void setUp(Scenario scenario) {
        currentScenario.set(scenario);
        // Initialize WebDriver before each scenario
        DriverManager.getDriver();
    }

    public static void log(String message) {
        System.out.println(message);
        Scenario scenario = currentScenario.get();
        if (scenario != null) {
            scenario.log(message);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            // Take screenshot if the scenario fails
            if (scenario.isFailed()) {
                try {
                    final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", scenario.getName() + " - Failure Screenshot");
                } catch (Exception e) {
                    System.err.println("Failed to capture screenshot: " + e.getMessage());
                }
            }
            // Quit the driver and clean up ThreadLocal
            DriverManager.quitDriver();
        }
        currentScenario.remove();
    }
}
