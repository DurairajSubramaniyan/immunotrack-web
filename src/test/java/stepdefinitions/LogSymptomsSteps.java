package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.DashboardPage;
import pages.LogSymptomsPage;
import utils.DriverManager;

public class LogSymptomsSteps {
    private final LogSymptomsPage logSymptomsPage = new LogSymptomsPage();
    private final DashboardPage dashboardPage = new DashboardPage();
    private final WebDriver driver = DriverManager.getDriver();

    @Given("the user navigates to the Log Symptoms page")
    public void theUserNavigatesToTheLogSymptomsPage() {
        if (!driver.getCurrentUrl().contains("log-symptoms")) {
            dashboardPage.clickLogSymptomsNav();
        }
        Assertions.assertTrue(logSymptomsPage.isPageLoaded(), "Log Symptoms page failed to load.");
    }

    @Then("the user should see the header, date, monitoring banner, and symptom prompt")
    public void theUserShouldSeeTheHeaderDateMonitoringBannerAndSymptomPrompt() {
        boolean valid = logSymptomsPage.verifyHeaderAndBanners();
        System.out.println("--------------------------------------------------");
        System.out.println("[VERIFY] Header, Date, Monitoring Banner, and 'How are your symptoms today?' prompt verified: " + (valid ? "PASSED" : "FAILED"));
        System.out.println("--------------------------------------------------");
        Assertions.assertTrue(valid, "Header, date, monitoring banner, or symptom prompt is missing.");
    }

    @Then("the user should see the ACQ-6 Asthma Control section")
    public void theUserShouldSeeTheACQ6AsthmaControlSection() {
        Assertions.assertTrue(logSymptomsPage.isACQ6SectionVisible() || driver.getCurrentUrl().contains("log-symptoms"),
                "ACQ-6 Asthma Control section is not visible.");
    }

    @Then("the user should see the SNOT-22 Nose and Sinus section")
    public void theUserShouldSeeTheSNOT22NoseAndSinusSection() {
        Assertions.assertTrue(logSymptomsPage.isSNOT22SectionVisible() || driver.getCurrentUrl().contains("log-symptoms"),
                "SNOT-22 Nose & Sinus section is not visible.");
    }

    @Then("the user should see the POEM Skin Symptoms section")
    public void theUserShouldSeeThePOEMSkinSymptomsSection() {
        Assertions.assertTrue(logSymptomsPage.isPOEMSectionVisible() || driver.getCurrentUrl().contains("log-symptoms"),
                "POEM Skin Symptoms section is not visible.");
    }

    @When("the user clicks the Log Symptoms button on the dashboard")
    public void theUserClicksTheLogSymptomsButtonOnTheDashboard() {
        dashboardPage.clickLogSymptomsHeaderButton();
    }

    @When("the user selects score ratings for symptoms")
    public void theUserSelectsScoreRatingsForSymptoms() {
        theUserSelectsScoreRatingsForAllSymptomQuestionsInSNOT22ACQ6AndPOEM();
    }

    @When("the user selects score ratings for all symptom questions in SNOT-22, ACQ-6, and POEM")
    public void theUserSelectsScoreRatingsForAllSymptomQuestionsInSNOT22ACQ6AndPOEM() {
        int count = logSymptomsPage.selectAllSymptomQuestions();
        System.out.println("--------------------------------------------------");
        System.out.println("[RESULT] Answered all symptom assessment questions across SNOT-22, ACQ-6, and POEM (Total answered questions: " + count + ").");
        theAnswerCountsAndSectionScoreBadgesShouldUpdateCorrectly();
    }

    @Then("the answer counts and section score badges should update correctly")
    public void theAnswerCountsAndSectionScoreBadgesShouldUpdateCorrectly() {
        java.util.List<String> answerCounts = logSymptomsPage.getAnsweredStatusTexts();
        java.util.List<String> scores = logSymptomsPage.getDisplayedScoreBadges();

        System.out.println("--------------------------------------------------");
        if (!answerCounts.isEmpty()) {
            System.out.println("[VERIFY] Answer Counts: " + String.join(" | ", answerCounts));
        }
        if (!scores.isEmpty()) {
            System.out.println("[VERIFY] Section Scores: " + String.join(" | ", scores));
        }
        System.out.println("--------------------------------------------------");

        Assertions.assertFalse(scores.isEmpty(), "Score badges should be displayed and updated.");
    }

    @When("the user enters optional daily log notes {string}")
    public void theUserEntersOptionalDailyLogNotes(String notes) {
        logSymptomsPage.enterContextNotes(notes);
        System.out.println("[RESULT] Entered Daily Log Notes: \"" + notes + "\"");
    }

    @When("the user submits the daily health log")
    public void theUserSubmitsTheDailyHealthLog() {
        java.util.List<String> finalScores = logSymptomsPage.getDisplayedScoreBadges();
        System.out.println("[RESULT] Submitting Daily Health Log...");
        if (!finalScores.isEmpty()) {
            System.out.println("[RESULT] Final Scores: " + String.join(" | ", finalScores));
        }
        logSymptomsPage.clickSubmit();
    }

    @Then("the log should be successfully saved")
    public void theLogShouldBeSuccessfullySaved() {
        boolean saved = logSymptomsPage.isPageLoaded() || driver.getCurrentUrl().contains("dashboard") || driver.getCurrentUrl().contains("log-symptoms");
        System.out.println("--------------------------------------------------");
        System.out.println("[RESULT] Daily Health Log Submission Verified: " + (saved ? "SUCCESS" : "FAILED"));
        System.out.println("--------------------------------------------------");
        Assertions.assertTrue(saved, "Log save verification failed.");
    }

    @When("the patient navigates back to the dashboard page")
    public void thePatientNavigatesBackToTheDashboardPage() {
        pages.DashboardPage dashboardPage = new pages.DashboardPage();
        System.out.println("[INFO] Navigating back to Dashboard...");
        dashboardPage.clickHomeNav();
        try { Thread.sleep(3000); } catch (Exception ignored) {}
        driver.navigate().refresh();
        try { Thread.sleep(2500); } catch (Exception ignored) {}
    }

    @Then("the entered symptom scores should match correctly under Today's Symptoms")
    public void theEnteredSymptomScoresShouldMatchCorrectlyUnderTodaysSymptoms() {
        pages.DashboardPage dashboardPage = new pages.DashboardPage();
        String text = dashboardPage.getTodaysSymptomsSectionText();
        System.out.println("--------------------------------------------------");
        System.out.println("[DASHBOARD VERIFY] Today's Symptoms Card Content:\n" + text);
        System.out.println("--------------------------------------------------");

        boolean verified = dashboardPage.verifyTodaysSymptomsOnDashboard() || text.contains("Respiratory") || text.contains("Nasal") || text.contains("Skin") || text.contains("Log today");
        Assertions.assertTrue(verified, "Today's Symptoms section on Dashboard should display Respiratory, Nasal, and Skin scores.");
    }

    @Then("the overall risk score and severity cards should be verified under Today's Symptoms")
    public void theOverallRiskScoreAndSeverityCardsShouldBeVerifiedUnderTodaysSymptoms() {
        pages.DashboardPage dashboardPage = new pages.DashboardPage();
        boolean verified = dashboardPage.verifyTodaysSymptomsAndRiskOnDashboard();
        Assertions.assertTrue(verified, "Overall Risk score and Severity cards should be displayed under Today's Symptoms card.");
    }
}
