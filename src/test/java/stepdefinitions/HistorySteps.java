package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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
        if (driver.getCurrentUrl() != null && driver.getCurrentUrl().contains("login")) {
            utils.MonthlyAssessmentHandler.reLoginIfOnLoginPage(driver);
        }
        utils.MonthlyAssessmentHandler.handleMonthlyAssessmentIfPresent(driver);
        if (!driver.getCurrentUrl().contains("history")) {
            dashboardPage.clickHistoryNav();
        }
        utils.MonthlyAssessmentHandler.handleMonthlyAssessmentIfPresent(driver);
        Assertions.assertTrue(historyPage.isPageLoaded(), "History page failed to load.");
    }

    @Then("the user should see historical symptom logs")
    public void theUserShouldSeeHistoricalSymptomLogs() {
        Assertions.assertTrue(historyPage.isPageLoaded(), "User is not on History page.");
    }

    @Then("the user should see the Symptom History header, title, and breadcrumbs")
    public void theUserShouldSeeTheSymptomHistoryHeaderTitleAndBreadcrumbs() {
        Assertions.assertTrue(historyPage.verifyHeaderAndTitleText(),
                "Assertion Failed: Symptom History header, title, or breadcrumbs missing or invalid.");
    }

    @Then("the user should see the Filter by Date section with Today, Last 7 Days, Last 30 days, and Custom Range buttons")
    public void theUserShouldSeeTheFilterByDateSectionWithFilterButtons() {
        Assertions.assertTrue(historyPage.verifyFilterSectionAndButtons(),
                "Assertion Failed: Filter by Date section or filter buttons (Today, Last 7 Days, Last 30 days, Custom Range) are missing.");
    }

    @When("the user selects the {string} filter button")
    public void theUserSelectsTheFilterButton(String filterName) {
        historyPage.clickFilterButton(filterName);
    }

    @Then("the {string} filter button should be active")
    public void theFilterButtonShouldBeActive(String filterName) {
        Assertions.assertTrue(historyPage.verifyFilterIsActive(filterName),
                "Assertion Failed: Filter button '" + filterName + "' is not active.");
    }

    @Then("the history entries list should update displaying correct values for {string}")
    public void theHistoryEntriesListShouldUpdateDisplayingCorrectValuesFor(String filterName) {
        Assertions.assertTrue(historyPage.verifyFilteredResultsContent(filterName),
                "Assertion Failed: History entries counter or symptom card content is missing for filter '" + filterName + "'.");
    }

    @Then("if a daily log exists for today, the entry should display Nasal, Respiratory, and Skin score cards with clinical title badges")
    public void ifADailyLogExistsForTodayTheEntryShouldDisplayScoreCardsWithClinicalTitleBadges() {
        Assertions.assertTrue(historyPage.verifyTodayLoggedEntryValues(),
                "Assertion Failed: Today's logged entry card is missing Nasal, Respiratory, or Skin scores or clinical title badges.");
    }

    @Then("the Custom Range date input fields should be displayed")
    public void theCustomRangeDateInputFieldsShouldBeDisplayed() {
        Assertions.assertTrue(historyPage.verifyCustomRangeInputsVisible(),
                "Assertion Failed: Start Date or End Date custom range input fields are not visible.");
    }

    @When("the user enters start date {string} and end date {string}")
    public void theUserEntersStartDateAndEndDate(String startDate, String endDate) {
        historyPage.enterCustomDateRange(startDate, endDate);
    }
}
