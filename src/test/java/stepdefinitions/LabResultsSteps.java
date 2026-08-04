package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.DashboardPage;
import pages.LabResultsPage;
import utils.DriverManager;

public class LabResultsSteps {
    private final LabResultsPage labResultsPage = new LabResultsPage();
    private final DashboardPage dashboardPage = new DashboardPage();
    private final WebDriver driver = DriverManager.getDriver();

    @Given("the user navigates to the Lab Results page")
    public void theUserNavigatesToTheLabResultsPage() {
        if (!driver.getCurrentUrl().contains("lab-results")) {
            dashboardPage.clickLabResultsNav();
        }
        Assertions.assertTrue(labResultsPage.isPageLoaded(), "Lab Results page failed to load.");
    }

    @Then("the user should see lab results and biomarker readings")
    public void theUserShouldSeeLabResultsAndBiomarkerReadings() {
        Assertions.assertTrue(labResultsPage.isPageLoaded(), "User is not on Lab Results page.");
    }
}
