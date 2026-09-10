package stepdefinitions;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import pages.NoticeOfPrivacyPracticesPage;

public class NoticeOfPrivacyPracticesSteps {

    private final NoticeOfPrivacyPracticesPage nppPage = new NoticeOfPrivacyPracticesPage();

    @Then("the user should be navigated to the Notice of Privacy Practices page")
    public void verify_npp_page_loaded() {
        Assertions.assertTrue(nppPage.isPageLoaded(), "Notice of Privacy Practices page did not load");
    }

    @Then("the current notice version and effective date should be displayed")
    public void verify_current_notice_version_displayed() {
        Assertions.assertTrue(nppPage.isCurrentVersionDisplayed(), "Current notice version/effective date not displayed");
    }

    @Then("the acknowledgement status should show {string}")
    public void verify_acknowledgement_status(String expectedStatus) {
        String actualStatus = nppPage.getAcknowledgementStatusText();
        Assertions.assertTrue(
                actualStatus.contains(expectedStatus),
                "Expected acknowledgement status to contain '" + expectedStatus + "' but was '" + actualStatus + "'"
        );
    }

   @When("the user clicks the Download a copy button on the privacy notice")
public void click_download_copy() {
    nppPage.clickDownloadCopy();
}
    @Then("the download action should be triggered")
    public void verify_download_action_triggered() {
        Assertions.assertTrue(nppPage.isDownloadButtonPresentAndClickable(), "Download button was not clickable");
    }

    @Then("the Previous versions section should be displayed")
    public void verify_previous_versions_section_displayed() {
        Assertions.assertTrue(nppPage.isPreviousVersionsSectionDisplayed(), "Previous versions section not displayed");
    }

    @When("the user navigates back to the profile page from the privacy notice")
    public void navigate_back_to_profile_from_npp() {
        nppPage.clickBackToProfile();
    }
}