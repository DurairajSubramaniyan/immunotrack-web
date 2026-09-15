package stepdefinitions;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.ProfilePage;
import utils.DriverManager;

public class ProfileSteps {

    private final ProfilePage profilePage = new ProfilePage();
    private final WebDriver driver = DriverManager.getDriver();

    private boolean medicationReminderStateBeforeToggle;
    private boolean pushNotificationStateBeforeToggle;

    @Given("the user is on the Profile page")
    public void user_is_on_profile_page() {
        profilePage.openProfileViaNav();
    }

    @When("the patient navigates to the Profile page from the top navigation")
    public void navigate_to_profile_page() {
        profilePage.openProfileViaNav();
    }

    @Then("the user should see the My Profile header and description")
    public void verify_profile_header() {
        Assertions.assertTrue(profilePage.isProfileHeaderDisplayed(), "My Profile header not visible");
    }

    @Then("the user should see the account name, condition tag, and age")
    public void verify_account_summary() {
        Assertions.assertTrue(profilePage.isAccountSummaryDisplayed(), "Account summary not visible");
    }

    @Then("the user should see the Contact Information section with email, phone number, birthday, and gender")
    public void verify_contact_info_section() {
        Assertions.assertTrue(profilePage.isContactInfoDisplayed(), "Contact Information section missing fields");
    }

    @Then("the user should see the Medical Information section with assigned clinician and practice name")
    public void verify_medical_info_section() {
        Assertions.assertTrue(profilePage.isMedicalInfoDisplayed(), "Medical Information section missing fields");
    }

    @Then("the user should see the Settings and Privacy section")
    public void verify_settings_privacy_section() {
        Assertions.assertTrue(profilePage.isSettingsPrivacyDisplayed(), "Settings & Privacy section not visible");
    }

    @When("the user toggles the Medication Reminders switch")
    public void toggle_medication_reminders() {
        medicationReminderStateBeforeToggle = profilePage.isMedicationReminderChecked();
        profilePage.toggleMedicationReminders();
    }

    @Then("the Medication Reminders status should update accordingly")
    public void verify_medication_reminders_status() {
        boolean expected = !medicationReminderStateBeforeToggle;
        Assertions.assertTrue(
            profilePage.isMedicationReminderToggleUpdated(expected),
            "Medication Reminder toggle did not update: was "
                + medicationReminderStateBeforeToggle
                + ", expected it to flip to " + expected
        );
    }

    @When("the user toggles the Push Notifications switch")
    public void toggle_push_notifications() {
        pushNotificationStateBeforeToggle = profilePage.isPushNotificationChecked();
        profilePage.togglePushNotifications();
    }

    @Then("the Push Notifications status should update accordingly")
    public void verify_push_notifications_status() {
        boolean expected = !pushNotificationStateBeforeToggle;
        Assertions.assertTrue(
            profilePage.isPushNotificationToggleUpdated(expected),
            "Push Notification toggle did not update: was "
                + pushNotificationStateBeforeToggle
                + ", expected it to flip to " + expected
        );
    }

    @When("the user updates the phone number to {string}")
    public void update_phone_number(String phone) {
        profilePage.updatePhoneNumber(phone);
    }

    @When("the user clicks the Save Profile Changes button")
    public void click_save_profile_changes() {
        profilePage.clickSaveProfileChanges();
    }

    @Then("a profile update confirmation should be displayed")
    public void verify_profile_update_confirmation() {
        Assertions.assertTrue(profilePage.isSaveConfirmationDisplayed(), "Save confirmation not shown");
    }

    @When("the patient navigates back to the dashboard using Back to Home")
    public void navigate_back_to_home() {
        profilePage.clickBackToHome();
    }

    @Then("the Remote Monitoring section should show {string} status")
    public void verify_remote_monitoring_status(String expectedStatus) {
        String actual = normalizeStatus(profilePage.getRemoteMonitoringStatus());

        Assertions.assertFalse(
            isMissing(actual),
            "Remote Monitoring status badge could not be found on the page."
        );

        System.out.println(
            "[TEST] Remote Monitoring - Expected: '" + expectedStatus
                + "', Actual: '" + actual + "'"
        );

        Assertions.assertEquals(
            normalizeStatus(expectedStatus),
            actual,
            "Remote Monitoring status mismatch. Expected: '"
                + expectedStatus + "', but UI shows: '" + actual + "'"
        );
    }

    @Then("the Notice of Privacy Practices should show {string} status")
    public void verify_privacy_notice_status(String expectedStatus) {
        // ✅ FIX: expectedStatus argument pass பண்றோம்
        String actual = normalizeStatus(profilePage.getPrivacyNoticeStatus(expectedStatus));

        Assertions.assertFalse(
            isMissing(actual),
            "Privacy Notice status badge could not be found on the page."
        );

        System.out.println(
            "[TEST] Privacy Notice - Expected: '" + expectedStatus
                + "', Actual: '" + actual + "'"
        );

        Assertions.assertEquals(
            normalizeStatus(expectedStatus),
            actual,
            "Privacy Notice status mismatch. Expected: '"
                + expectedStatus + "', but UI shows: '" + actual + "'"
        );
    }

    private String normalizeStatus(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().replaceAll("\\s+", " ").toLowerCase();
    }

    private boolean isMissing(String value) {
        return value.isEmpty() || "not found".equals(value);
    }

    @When("the user clicks on {string}")
    public void click_settings_link(String linkText) {
        profilePage.clickSettingsLink(linkText);
    }

    @Then("the export record action should be triggered")
    public void verify_export_action() {
        Assertions.assertTrue(profilePage.isExportTriggered(), "Export action did not trigger");
    }

    @Then("the change password flow should be initiated")
    public void verify_change_password_flow() {
        Assertions.assertTrue(profilePage.isChangePasswordFlowStarted(), "Change password flow not started");
    }

    @When("the user closes the Change Password dialog")
    public void close_change_password_dialog() {
        profilePage.closeChangePasswordModal();
    }

    @Then("the user should be navigated to the Privacy Policy page")
    public void verify_privacy_policy_page_loaded() {
        Assertions.assertTrue(profilePage.isPrivacyPolicyPageLoaded(), "Privacy Policy page did not load");
    }

    @Then("the privacy policy effective date and version should be displayed")
    public void verify_privacy_policy_effective_date_displayed() {
        Assertions.assertTrue(
            profilePage.isPrivacyPolicyEffectiveDateAndVersionDisplayed(),
            "Effective date/version line not displayed on Privacy Policy page"
        );
    }

    @Then("the Information We Collect section should be displayed")
    public void verify_information_we_collect_section_displayed() {
        Assertions.assertTrue(
            profilePage.isPrivacyPolicyInfoWeCollectSectionDisplayed(),
            "'Information We Collect' section not displayed"
        );
    }

    @When("the user clicks back from the Privacy Policy page")
    public void click_back_from_privacy_policy_page() {
        profilePage.clickPrivacyPolicyBack();
    }

    @Then("the user should be navigated to the Cookie Policy page")
    public void verify_cookie_policy_page_loaded() {
        Assertions.assertTrue(profilePage.isCookiePolicyPageLoaded(), "Cookie Policy page did not load");
    }

    @Then("the What Are Cookies section should be displayed")
    public void verify_what_are_cookies_section_displayed() {
        Assertions.assertTrue(
            profilePage.isCookiePolicySection1Displayed(),
            "'What Are Cookies' section not displayed"
        );
    }

    @Then("the Cookies We Use section should be displayed")
    public void verify_cookies_we_use_section_displayed() {
        Assertions.assertTrue(
            profilePage.isCookiePolicySection2Displayed(),
            "'Cookies We Use' section not displayed"
        );
    }

    @When("the user clicks back from the Cookie Policy page")
    public void click_back_from_cookie_policy_page() {
        profilePage.clickCookiePolicyBack();
    }
}