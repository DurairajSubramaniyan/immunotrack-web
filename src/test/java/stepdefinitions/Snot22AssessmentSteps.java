package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.Snot22AssessmentPage;
import pages.Snot22AssessmentPage.Snot22ScoreResult;

public class Snot22AssessmentSteps {

    private final Snot22AssessmentPage snot22Page = new Snot22AssessmentPage();
    private Snot22ScoreResult scoreResult;

    @Then("the user should see the SNOT-22 {string} landing page with correct header and description")
    public void theUserShouldSeeTheSNOT22LandingPageWithCorrectHeaderAndDescription(String assessmentType) {
        Assertions.assertTrue(snot22Page.isLandingPageLoaded(), "SNOT-22 landing page was not loaded!");
        snot22Page.verifyLandingPageContent(assessmentType);
    }

    @Then("the {string} button should be present on the SNOT-22 landing page")
    public void theButtonShouldBePresentOnTheSNOT22LandingPage(String buttonName) {
        if (buttonName.equalsIgnoreCase("Remind Me Later")) {
            snot22Page.verifyRemindMeLaterPresence(true);
        }
    }

    @Then("the {string} button should not be present on the SNOT-22 landing page")
    public void theButtonShouldNotBePresentOnTheSNOT22LandingPage(String buttonName) {
        if (buttonName.equalsIgnoreCase("Remind Me Later")) {
            snot22Page.verifyRemindMeLaterPresence(false);
        }
    }

    @When("the user clicks the Remind Me Later button")
    public void theUserClicksTheRemindMeLaterButton() {
        snot22Page.clickRemindMeLater();
    }

    @Then("the user should be dismissed to the dashboard page")
    public void theUserShouldBeDismissedToTheDashboardPage() {
        snot22Page.verifyRedirectedToDashboard();
    }

    @When("the user begins the SNOT-22 check-in")
    public void theUserBeginsTheSNOT22CheckIn() {
        snot22Page.clickBeginCheckIn();
    }

    @When("the user answers all 22 SNOT-22 questions with rating {int} across all domains")
    public void theUserAnswersAll22SNOT22QuestionsWithRatingAcrossAllDomains(int rating) {
        scoreResult = snot22Page.answerAllQuestions(rating);
    }

    @Then("the SNOT-22 completion screen should display the correct calculated score and severity")
    public void theSNOT22CompletionScreenShouldDisplayTheCorrectCalculatedScoreAndSeverity() {
        Assertions.assertNotNull(scoreResult, "Calculated score result must not be null!");
        snot22Page.verifyCompletionScreen(scoreResult);
    }
}
