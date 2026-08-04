package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.DashboardPage;
import pages.HistoryPage;
import utils.DriverManager;

public class HistorySteps {
    private final HistoryPage historyPage = new HistoryPage();
    private final DashboardPage dashboardPage = new DashboardPage();
    private final WebDriver driver = DriverManager.getDriver();

    @Given("the user navigates to the History page")
    public void theUserNavigatesToTheHistoryPage() {
        if (!driver.getCurrentUrl().contains("history")) {
            dashboardPage.clickHistoryNav();
        }
        Assertions.assertTrue(historyPage.isPageLoaded(), "History page failed to load.");
    }

    @Then("the user should see historical symptom logs")
    public void theUserShouldSeeHistoricalSymptomLogs() {
        Assertions.assertTrue(historyPage.isPageLoaded(), "User is not on History page.");
    }
}
