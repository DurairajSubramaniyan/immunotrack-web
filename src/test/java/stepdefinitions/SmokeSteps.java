package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.smoke.SmokePage;
import utils.ConfigReader;
import utils.DriverManager;

public class SmokeSteps {

    private final SmokePage smokePage = new SmokePage();
    private final LoginPage loginPage = new LoginPage();
    private final WebDriver driver = DriverManager.getDriver();

    // =========================================================================
    // BACKGROUND / AUTHENTICATION
    // =========================================================================
    @Given("the patient logs in with valid credentials")
    public void patient_logs_in_with_valid_credentials() {
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("dashboard")) {
            String patientUrl = ConfigReader.getProperty("patient.url");
            if (patientUrl == null || patientUrl.isEmpty()) {
                patientUrl = ConfigReader.getProperty("url");
            }
            if (patientUrl == null || patientUrl.isEmpty()) {
                patientUrl = "https://immunotrack-frontend-y93m.onrender.com/patient/login";
            }
            driver.get(patientUrl);

            if (loginPage.isLoginPageLoaded()) {
                String email = ConfigReader.getProperty("patient.email");
                String password = ConfigReader.getProperty("patient.password");
                loginPage.enterEmail(email != null ? email : "immunotrack123@gmail.com");
                loginPage.enterPassword(password != null ? password : "Immunotrack@123");
                loginPage.clickLogin();
            }
        }
        utils.MonthlyAssessmentHandler.handleMonthlyAssessmentIfPresent(driver);
        Assertions.assertTrue(smokePage.isDashboardHealthy(), "Smoke Check Failed: Dashboard did not load healthy after login");
    }

    // =========================================================================
    // 1. DASHBOARD PAGE STEPS
    // =========================================================================
    @Then("the patient should see the dashboard greeting header and portal title")
    public void patient_should_see_dashboard_greeting_and_title() {
        String greeting = smokePage.getDashboardGreetingText();
        Hooks.log(">>> [DASHBOARD] Actual Greeting/Header: \"" + greeting + "\"");
        Assertions.assertTrue(smokePage.isDashboardHeaderAndGreetingVisible(), "Smoke Check Failed: Dashboard greeting or title is missing");
    }

    @And("the patient should see the monitoring status and flare risk cards with values")
    public void patient_should_see_monitoring_and_flare_risk_cards() {
        Assertions.assertTrue(smokePage.areDashboardStatusCardsAndValuesVisible(), "Smoke Check Failed: Monitoring status or flare risk cards are missing");
    }

    @And("the patient should see the dashboard action buttons to log symptoms or start assessment")
    public void patient_should_see_dashboard_action_buttons() {
        Assertions.assertTrue(smokePage.areDashboardActionButtonsVisible(), "Smoke Check Failed: Dashboard action buttons are missing");
    }

    @And("the page footer or bottom branding should be visible")
    public void page_footer_or_bottom_branding_should_be_visible() {
        Assertions.assertTrue(smokePage.isGlobalFooterOrBottomAreaVisible(), "Smoke Check Failed: Page footer or bottom branding not visible");
    }

    // =========================================================================
    // 2. LOG SYMPTOMS PAGE STEPS
    // =========================================================================
    @When("the patient navigates to the Log Symptoms module")
    public void patient_navigates_to_log_symptoms() {
        smokePage.navigateToLogSymptoms();
    }

    @Then("the Log Symptoms page title and header should be visible")
    public void log_symptoms_page_title_and_header_visible() {
        String title = smokePage.getLogSymptomsTitleText();
        Hooks.log(">>> [LOG SYMPTOMS] Actual Title/Header: \"" + title + "\"");
        Assertions.assertTrue(smokePage.isLogSymptomsTitleVisible(), "Smoke Check Failed: Log Symptoms title or header missing");
    }

    @And("the clinical symptom assessment domains should be displayed")
    public void clinical_symptom_assessment_domains_displayed() {
        Assertions.assertTrue(smokePage.areSymptomDomainsVisible(), "Smoke Check Failed: Symptom assessment domains missing");
    }

    @And("the save daily log action button should be visible")
    public void save_daily_log_button_visible() {
        Assertions.assertTrue(smokePage.isSaveLogButtonVisible(), "Smoke Check Failed: Save daily log action button missing");
    }

    @Then("the Log Symptoms module should load successfully")
    public void log_symptoms_should_load_successfully() {
        Assertions.assertTrue(smokePage.isLogSymptomsHealthy(), "Smoke Check Failed: Log Symptoms module did not load successfully");
    }

    // =========================================================================
    // 3. MEDICATIONS PAGE STEPS
    // =========================================================================
    @When("the patient navigates to the Medications module")
    public void patient_navigates_to_medications() {
        smokePage.navigateToMedications();
    }

    @Then("the Medications page title and header should be visible")
    public void medications_page_title_and_header_visible() {
        String title = smokePage.getMedicationsTitleText();
        Hooks.log(">>> [MEDICATIONS] Actual Title/Header: \"" + title + "\"");
        Assertions.assertTrue(smokePage.isMedicationsTitleVisible(), "Smoke Check Failed: Medications page title or header missing");
    }

    @And("the Quick Add medication section and form fields should be displayed")
    public void quick_add_medication_section_displayed() {
        Assertions.assertTrue(smokePage.isQuickAddMedicationSectionVisible(), "Smoke Check Failed: Quick Add medication section missing");
    }

    @And("the Add to Plan action button should be visible")
    public void add_to_plan_button_visible() {
        Assertions.assertTrue(smokePage.isAddToPlanButtonVisible(), "Smoke Check Failed: Add to Plan button missing");
    }

    @And("the active medications tracking section should be displayed")
    public void active_medications_tracking_section_displayed() {
        Assertions.assertTrue(smokePage.isActiveMedicationsSectionVisible(), "Smoke Check Failed: Active medications section missing");
    }

    @Then("the Medications module should load successfully")
    public void medications_should_load_successfully() {
        Assertions.assertTrue(smokePage.isMedicationsHealthy(), "Smoke Check Failed: Medications module did not load successfully");
    }

    // =========================================================================
    // 4. HISTORY PAGE STEPS
    // =========================================================================
    @When("the patient navigates to the History module")
    public void patient_navigates_to_history() {
        smokePage.navigateToHistory();
    }

    @Then("the Symptom History page title and header should be visible")
    public void history_page_title_and_header_visible() {
        String title = smokePage.getHistoryTitleText();
        Hooks.log(">>> [HISTORY] Actual Title/Header: \"" + title + "\"");
        Assertions.assertTrue(smokePage.isHistoryTitleVisible(), "Smoke Check Failed: History page title or header missing");
    }

    @And("the date filter section with quick range buttons should be displayed")
    public void date_filter_section_displayed() {
        Assertions.assertTrue(smokePage.isDateFilterSectionVisible(), "Smoke Check Failed: Date filter section missing");
    }

    @Then("the History module should load successfully")
    public void history_should_load_successfully() {
        Assertions.assertTrue(smokePage.isHistoryHealthy(), "Smoke Check Failed: History module did not load successfully");
    }

    // =========================================================================
    // 5. INSIGHTS PAGE STEPS
    // =========================================================================
    @When("the patient navigates to the Insights module")
    public void patient_navigates_to_insights() {
        smokePage.navigateToInsights();
    }

    @Then("the Health Insights page title and header should be visible")
    public void insights_page_title_and_header_visible() {
        String title = smokePage.getInsightsTitleText();
        Hooks.log(">>> [INSIGHTS] Actual Title/Header: \"" + title + "\"");
        Assertions.assertTrue(smokePage.isInsightsTitleVisible(), "Smoke Check Failed: Insights page title or header missing");
    }

    @And("the predictive insights and analytics widgets should be displayed")
    public void predictive_insights_widgets_displayed() {
        Assertions.assertTrue(smokePage.isInsightsAnalyticsWidgetVisible(), "Smoke Check Failed: Insights analytics widget missing");
    }

    @Then("the Insights module should load successfully")
    public void insights_should_load_successfully() {
        Assertions.assertTrue(smokePage.isInsightsHealthy(), "Smoke Check Failed: Insights module did not load successfully");
    }

    // =========================================================================
    // 6. LAB RESULTS PAGE STEPS
    // =========================================================================
    @When("the patient navigates to the Lab Results module")
    public void patient_navigates_to_lab_results() {
        smokePage.navigateToLabResults();
    }

    @Then("the Lab Results page title and header should be visible")
    public void lab_results_page_title_and_header_visible() {
        String title = smokePage.getLabResultsTitleText();
        Hooks.log(">>> [LAB RESULTS] Actual Title/Header: \"" + title + "\"");
        Assertions.assertTrue(smokePage.isLabResultsTitleVisible(), "Smoke Check Failed: Lab Results page title or header missing");
    }

    @And("the laboratory biomarker cards and readings should be displayed")
    public void biomarker_cards_displayed() {
        Assertions.assertTrue(smokePage.areLabResultBiomarkersVisible(), "Smoke Check Failed: Lab result biomarker cards missing");
    }

    @Then("the Lab Results module should load successfully")
    public void lab_results_should_load_successfully() {
        Assertions.assertTrue(smokePage.isLabResultsHealthy(), "Smoke Check Failed: Lab Results module did not load successfully");
    }

    // =========================================================================
    // 7. PROFILE PAGE STEPS
    // =========================================================================
    @When("the patient navigates to the Profile module")
    public void patient_navigates_to_profile() {
        smokePage.navigateToProfile();
    }

    @Then("the Profile page title and header should be visible")
    public void profile_page_title_and_header_visible() {
        String title = smokePage.getProfileTitleText();
        Hooks.log(">>> [PROFILE] Actual Title/Header: \"" + title + "\"");
        Assertions.assertTrue(smokePage.isProfileTitleVisible(), "Smoke Check Failed: Profile page title or header missing");
    }

    @And("the patient account summary with condition and age should be displayed")
    public void patient_account_summary_displayed() {
        Assertions.assertTrue(smokePage.isProfileAccountSummaryVisible(), "Smoke Check Failed: Profile account summary missing");
    }

    @And("the contact information section with email and phone should be displayed")
    public void contact_information_section_displayed() {
        Assertions.assertTrue(smokePage.isProfileContactInfoVisible(), "Smoke Check Failed: Contact information section missing");
    }

    @And("the Save Profile Changes action button should be visible")
    public void save_profile_changes_button_visible() {
        Assertions.assertTrue(smokePage.isSaveProfileButtonVisible(), "Smoke Check Failed: Save Profile Changes button missing");
    }

    @Then("the Profile module should load successfully")
    public void profile_should_load_successfully() {
        Assertions.assertTrue(smokePage.isProfileHealthy(), "Smoke Check Failed: Profile module did not load successfully");
    }

    // =========================================================================
    // DASHBOARD RETURN & LOGOUT
    // =========================================================================
    @When("the patient navigates back to the Dashboard module")
    public void patient_navigates_back_to_dashboard() {
        smokePage.navigateToDashboard();
    }

    @Then("the patient should be on the healthy Dashboard module")
    public void patient_should_be_on_healthy_dashboard() {
        Assertions.assertTrue(smokePage.isDashboardHealthy(), "Smoke Check Failed: Dashboard module is unhealthy or not loaded");
    }

    @When("the patient clicks the logout button")
    public void patient_clicks_logout_button() {
        smokePage.performLogout();
    }

    @Then("the patient should be redirected to the Login page")
    public void patient_should_be_redirected_to_login_page() {
        Assertions.assertTrue(smokePage.isLoggedOutSuccessfully(), "Smoke Check Failed: Not redirected to Login page after logout");
    }
}
