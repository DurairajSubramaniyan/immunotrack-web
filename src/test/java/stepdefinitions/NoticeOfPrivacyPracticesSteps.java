package stepdefinitions;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import pages.NoticeOfPrivacyPracticesPage;
import pages.ProfilePage;

public class NoticeOfPrivacyPracticesSteps {

    private final NoticeOfPrivacyPracticesPage nppPage = new NoticeOfPrivacyPracticesPage();
    private final ProfilePage profilePage = new ProfilePage();

    @Then("the user should be navigated to the Notice of Privacy Practices page")
    public void verify_npp_page_loaded() {
        Assertions.assertTrue(
            nppPage.isPageLoaded(),
            "Notice of Privacy Practices page did not load"
        );
    }

    @Then("the current notice version and effective date should be displayed")
    public void verify_current_notice_version_displayed() {
        Assertions.assertTrue(
            nppPage.isCurrentVersionDisplayed(),
            "Current notice version/effective date not displayed"
        );
    }

    @Then("the acknowledgement status should show {string}")
    public void verify_acknowledgement_status(String expectedStatus) {
        String actualStatus = normalizeStatus(nppPage.getAcknowledgementStatusText());

        Assertions.assertFalse(
            actualStatus.isEmpty() || "not found".equals(actualStatus),
            "Acknowledgement status could not be found on the page."
        );

        System.out.println(
            "[TEST] NPP Acknowledgement - Expected: '" + expectedStatus
                + "', Actual: '" + actualStatus + "'"
        );

        Assertions.assertEquals(
            normalizeStatus(expectedStatus),
            actualStatus,
            "Acknowledgement status mismatch. Expected: '"
                + expectedStatus + "', but UI shows: '" + actualStatus + "'"
        );
    }

    private String normalizeStatus(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().replaceAll("\\s+", " ").toLowerCase();
    }

    @When("the user clicks the Download a copy button on the privacy notice")
    public void click_download_copy() {
        nppPage.clickDownloadCopy();
    }

    @Then("the download action should be triggered")
    public void verify_download_action_triggered() {
        Assertions.assertTrue(
            nppPage.isDownloadButtonPresentAndClickable(),
            "Download button was not clickable"
        );
    }

    @Then("the Previous versions section should be displayed")
    public void verify_previous_versions_section_displayed() {
        Assertions.assertTrue(
            nppPage.isPreviousVersionsSectionDisplayed(),
            "Previous versions section not displayed"
        );
    }

    @When("the user navigates back to the profile page from the privacy notice")
    public void navigate_back_to_profile_from_npp() {
        nppPage.clickBackToProfile();
    }

    @When("the user clicks on the Read full Privacy Notice link")
    public void click_read_full_privacy_notice() {
        nppPage.clickReadFullPrivacyNotice();
    }

    @Then("the full privacy notice document should be displayed")
    public void verify_full_document_modal_displayed() {
        Assertions.assertTrue(
            nppPage.isFullDocumentModalDisplayed(),
            "Full privacy notice document modal did not open"
        );
    }

    @Then("the Download PDF button should be displayed on the document viewer")
    public void verify_download_pdf_button_displayed() {
        Assertions.assertTrue(
            nppPage.isFullDocumentDownloadPdfButtonDisplayed(),
            "Download PDF button not displayed on document viewer"
        );
    }

    @When("the user closes the full privacy notice document")
    public void close_full_document_modal() {
        nppPage.closeFullDocumentModal();
    }
}
