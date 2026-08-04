package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.DashboardPage;
import pages.InsightsPage;
import utils.DriverManager;

public class InsightsSteps {
    private final InsightsPage insightsPage = new InsightsPage();
    private final DashboardPage dashboardPage = new DashboardPage();
    private final WebDriver driver = DriverManager.getDriver();

    @Given("the user navigates to the Insights page")
    public void theUserNavigatesToTheInsightsPage() {
        if (!driver.getCurrentUrl().contains("insights")) {
            dashboardPage.clickInsightsNav();
        }
        Assertions.assertTrue(insightsPage.isPageLoaded(), "Insights page failed to load.");
    }

    @Then("the user should see health insights analytics")
    public void theUserShouldSeeHealthInsightsAnalytics() {
        Assertions.assertTrue(insightsPage.isPageLoaded(), "User is not on Insights page.");
    }
}
