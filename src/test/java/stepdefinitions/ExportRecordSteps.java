package stepdefinitions;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import pages.ExportRecordPage;

public class ExportRecordSteps {

    private final ExportRecordPage exportRecordPage = new ExportRecordPage();

    @Then("the user should be navigated to the Export My Record page")
    public void user_should_be_on_export_page() {
        Assertions.assertTrue(exportRecordPage.isExportPageLoaded(), "Export My Record page did not load");
    }

    @When("the user enters the account password {string} for identity verification")
    public void user_enters_identity_password(String password) {
        Assertions.assertTrue(exportRecordPage.isIdentityModalDisplayed(), "Identity Verification modal not shown");
        exportRecordPage.enterIdentityPassword(password);
    }

    @When("the user confirms the password")
    public void user_confirms_password() {
        exportRecordPage.clickConfirmPassword();
    }

    @Then("the identity should be verified successfully")
    public void identity_verified_successfully() {
        Assertions.assertTrue(exportRecordPage.isIdentityVerifiedToastDisplayed(), "Identity verification success message not shown");
    }

    @When("the user selects the {string} export scope")
    public void user_selects_export_scope(String scope) {
        if (scope.equalsIgnoreCase("Full Record")) {
            exportRecordPage.selectFullRecordScope();
        }
    }

    @When("the user requests the export")
    public void user_requests_export() {
        exportRecordPage.clickRequestExport();
    }

    @Then("an export queued confirmation should be displayed")
    public void export_queued_confirmation_displayed() {
        Assertions.assertTrue(exportRecordPage.isExportQueuedModalDisplayed(), "Export queued confirmation not shown");
    }

    @When("the user clicks {string}")
    public void user_clicks_button(String buttonName) {
        if (buttonName.equalsIgnoreCase("Return to Profile")) {
            exportRecordPage.clickReturnToProfile();
        } else if (buttonName.equalsIgnoreCase("View Export History")) {
            exportRecordPage.clickViewExportHistory();
        }
    }

    @Then("the user should be navigated to the profile page")
    public void user_navigated_to_profile() {
        Assertions.assertTrue(exportRecordPage.isProfilePageLoaded(), "Profile page did not load");
    }
}