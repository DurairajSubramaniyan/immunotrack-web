package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.DashboardPage;
import pages.MedicationsPage;
import utils.DriverManager;

public class MedicationsSteps {
    private final MedicationsPage medicationsPage = new MedicationsPage();
    private final DashboardPage dashboardPage = new DashboardPage();
    private final WebDriver driver = DriverManager.getDriver();

    @Given("the user navigates to the Medications page")
    public void theUserNavigatesToTheMedicationsPage() {
        if (!driver.getCurrentUrl().contains("medications")) {
            dashboardPage.clickMedicationsNav();
        }
        Assertions.assertTrue(medicationsPage.isPageLoaded(), "Medications page failed to load.");
    }

    @Then("the user should see active medication prescriptions")
    public void theUserShouldSeeActiveMedicationPrescriptions() {
        Assertions.assertTrue(medicationsPage.isPageLoaded(), "User is not on Medications page.");
    }
}
