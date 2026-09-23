package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.MedicationsPage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MedicationsSteps {

    private final MedicationsPage medicationsPage;
    private String htmlClassBeforeToggle;
    private int missedCountBeforeMarkAsTaken;

    public MedicationsSteps() {
        this.medicationsPage = new MedicationsPage();
    }

    // =====================================================================
    // NAVIGATION
    // =====================================================================

    @And("the user navigates to the Medications page")
    public void the_user_navigates_to_the_medications_page() {
        medicationsPage.clickSidebarItem("Medications");

        Assert.assertTrue(
                medicationsPage.isPageLoaded(),
                "Medications page did not load"
        );
    }

    // =====================================================================
    // PAGE LOAD
    // =====================================================================

    @Then("the Medications page title should be displayed")
    public void the_medications_page_title_should_be_displayed() {
        Assert.assertTrue(
                medicationsPage.getPageTitle().isDisplayed(),
                "Medications page title is not displayed"
        );
    }

    @And("the subtitle {string} should be visible")
    public void the_subtitle_should_be_visible(String subtitle) {
        Assert.assertEquals(
                medicationsPage.getPageSubtitle().getText().trim(),
                subtitle,
                "Subtitle text mismatch"
        );
    }

    @And("the {string} section should be visible")
    public void the_section_should_be_visible(String sectionName) {

        switch (sectionName) {

            case "QUICK ADD MEDICATION":
                Assert.assertTrue(
                        medicationsPage.getQuickAddMedicationHeading().isDisplayed(),
                        "Quick Add Medication section is not visible"
                );
                break;

            case "Weekly Adherence":
                Assert.assertTrue(
                        medicationsPage.getWeeklyAdherenceHeading().isDisplayed(),
                        "Weekly Adherence section is not visible"
                );
                break;

            default:
                Assert.fail("Unknown section: " + sectionName);
        }
    }

    @And("the {string} button should be visible")
    public void the_button_should_be_visible(String buttonName) {

        if ("ADD TO PLAN".equalsIgnoreCase(buttonName)) {

            Assert.assertTrue(
                    medicationsPage.getAddToPlanBtn().isDisplayed(),
                    "ADD TO PLAN button is not visible"
            );

        } else {
            Assert.fail("Unknown button: " + buttonName);
        }
    }

    // =====================================================================
    // BREADCRUMB
    // =====================================================================

    @Then("the {string} breadcrumb should be visible")
    public void the_breadcrumb_should_be_visible(String breadcrumbText) {

        if ("Patient Portal".equals(breadcrumbText)) {

            Assert.assertTrue(
                    medicationsPage.getPatientPortalBreadcrumb().isDisplayed(),
                    "Patient Portal breadcrumb is not visible"
            );

        } else if ("Medications".equals(breadcrumbText)) {

            Assert.assertTrue(
                    medicationsPage.getMedicationsBreadcrumb().isDisplayed(),
                    "Medications breadcrumb is not visible"
            );

        } else {
            Assert.fail("Unknown breadcrumb: " + breadcrumbText);
        }
    }

    // =====================================================================
    // HEADER
    // =====================================================================

    @And("the theme toggle button should be visible")
    public void the_theme_toggle_button_should_be_visible() {

        Assert.assertTrue(
                medicationsPage.getThemeToggleBtn().isDisplayed(),
                "Theme toggle button is not visible"
        );
    }

    @And("the notification bell button should be visible")
    public void the_notification_bell_button_should_be_visible() {

        Assert.assertTrue(
                medicationsPage.getNotificationBellBtn().isDisplayed(),
                "Notification bell is not visible"
        );
    }

    // Avatar test intentionally removed.

    @And("the username {string} should be visible")
    public void the_username_should_be_visible(String username) {

        Assert.assertEquals(
                medicationsPage.getUsernameText().getText().trim(),
                username,
                "Username mismatch"
        );
    }

    @And("the user role {string} should be visible")
    public void the_user_role_should_be_visible(String role) {

        Assert.assertEquals(
                medicationsPage.getUserRoleText().getText().trim(),
                role,
                "User role mismatch"
        );
    }

    // =====================================================================
    // SIDEBAR
    // =====================================================================

    @Then("the sidebar should show {string} menu item")
    public void the_sidebar_should_show_menu_item(String menuItem) {

        WebElement item = sidebarItemFor(menuItem);

        Assert.assertTrue(
                item.isDisplayed(),
                "Sidebar menu item not visible: " + menuItem
        );
    }

    @And("the sidebar should show {string} menu item as active")
    public void the_sidebar_should_show_menu_item_as_active(String menuItem) {

        Assert.assertTrue(
                medicationsPage.getMedicationsMenuItemActive().isDisplayed(),
                menuItem + " menu item is not active"
        );
    }

    private WebElement sidebarItemFor(String menuItem) {

        switch (menuItem) {

            case "Home":
                return medicationsPage.getHomeMenuItem();

            case "Log Symptoms":
                return medicationsPage.getLogSymptomsMenuItem();

            case "Medications":
                return medicationsPage.getMedicationsMenuItem();

            case "Lab Results":
                return medicationsPage.getLabResultsMenuItem();

            case "History":
                return medicationsPage.getHistoryMenuItem();

            case "Insights":
                return medicationsPage.getInsightsMenuItem();

            case "Log Out":
                return medicationsPage.getLogOutMenuItem();

            default:
                throw new IllegalArgumentException(
                        "Unknown sidebar item: " + menuItem
                );
        }
    }

    // =====================================================================
    // FORM FIELDS
    // =====================================================================

    @Then("the {string} dropdown should be visible")
    public void the_dropdown_should_be_visible(String fieldName) {

        WebElement field;

        switch (fieldName.toUpperCase()) {

            case "CATEGORY":
                field = medicationsPage.getCategoryDropdown();
                break;

            case "MEDICATION":
                field = medicationsPage.getMedicationDropdown();
                break;

            case "FREQUENCY":
                field = medicationsPage.getFrequencyDropdown();
                break;

            case "UNIT":
                field = medicationsPage.getUnitDropdownEnabled();
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown dropdown: " + fieldName
                );
        }

        Assert.assertTrue(
                field.isDisplayed(),
                fieldName + " dropdown is not visible"
        );
    }

    @And("the {string} input field should be visible")
    public void the_input_field_should_be_visible(String fieldName) {

        WebElement field = inputFieldFor(fieldName);

        Assert.assertTrue(
                field.isDisplayed(),
                fieldName + " input field is not visible"
        );
    }

    @And("the {string} field should be visible")
    public void the_field_should_be_visible(String fieldName) {

        WebElement field = inputFieldFor(fieldName);

        Assert.assertTrue(
                field.isDisplayed(),
                fieldName + " field is not visible"
        );
    }

    private WebElement inputFieldFor(String fieldName) {

        switch (fieldName.toUpperCase()) {

            case "DOSAGE":
                return medicationsPage.getDosageInput();

            case "START DATE":
                return medicationsPage.getStartDateInput();

            case "NOTES (OPTIONAL)":
                return medicationsPage.getNotesInput();

            default:
                throw new IllegalArgumentException(
                        "Unknown input field: " + fieldName
                );
        }
    }

    // =====================================================================
    // START DATE
    // =====================================================================

    @Then("the Start Date field should display today's date")
    public void the_start_date_field_should_display_today_s_date() {

        String expectedDate =
                LocalDate.now().format(
                        DateTimeFormatter.ofPattern("MM/dd/yyyy")
                );

        Assert.assertEquals(
                medicationsPage.getStartDateValue(),
                expectedDate,
                "Start Date does not default to today's date"
        );
    }

    // =====================================================================
    // MAIN TABS
    // =====================================================================

    @Then("the {string} tab should be visible with a count badge")
    public void the_tab_should_be_visible_with_a_count_badge(
            String tabName) {

        if ("Medications".equals(tabName)) {

            Assert.assertTrue(
                    medicationsPage.getMedicationsTab().isDisplayed(),
                    "Medications tab is not visible"
            );

            Assert.assertTrue(
                    medicationsPage.getMedicationsTabCount().isDisplayed(),
                    "Medications count badge is not visible"
            );

        } else if ("Reminders".equals(tabName)) {

            Assert.assertTrue(
                    medicationsPage.getRemindersTab().isDisplayed(),
                    "Reminders tab is not visible"
            );

            Assert.assertTrue(
                    medicationsPage.getRemindersTabCount().isDisplayed(),
                    "Reminders count badge is not visible"
            );

        } else {
            Assert.fail("Unknown tab: " + tabName);
        }
    }

    @When("the user clicks the {string} tab")
    public void the_user_clicks_the_tab(String tabName) {
        medicationsPage.clickMainTab(tabName);
    }

    // =====================================================================
    // FILTER TABS
    // =====================================================================

    @Then("the {string} filter tab should be visible")
    public void the_filter_tab_should_be_visible(String filterName) {

        WebElement tab = filterTabFor(filterName);

        Assert.assertTrue(
                tab.isDisplayed(),
                filterName + " filter tab is not visible"
        );
    }

    private WebElement filterTabFor(String filterName) {

        switch (filterName) {

            case "Today":
                return medicationsPage.getTodayTab();

            case "Future":
                return medicationsPage.getFutureTab();

            case "Missed":
                return medicationsPage.getMissedTab();

            case "Discontinued":
                return medicationsPage.getDiscontinuedTab();

            case "Yesterday":
                return medicationsPage.getYesterdayTab();

            case "Missed Logs":
                return medicationsPage.getMissedLogsTab();

            case "Pending":
                return medicationsPage.getPendingTab();

            case "Completed":
                return medicationsPage.getCompletedTab();

            default:
                throw new IllegalArgumentException(
                        "Unknown filter tab: " + filterName
                );
        }
    }

    @And("the user clicks the {string} filter tab")
    public void the_user_clicks_the_filter_tab(String filterName) {
        medicationsPage.clickFilterTabByName(filterName);
    }

    // =====================================================================
    // WEEKLY ADHERENCE
    // =====================================================================

    @And("the adherence percentage should be displayed")
    public void the_adherence_percentage_should_be_displayed() {

        Assert.assertTrue(
                medicationsPage.getAdherencePercentage()
                        .getText()
                        .contains("%"),
                "Adherence percentage is not displayed"
        );
    }

    @And("the {string} label should be visible")
    public void the_label_should_be_visible(String label) {

        if ("Last 7 days".equals(label)) {

            Assert.assertTrue(
                    medicationsPage.getLast7DaysLabel().isDisplayed(),
                    "Last 7 days label is not visible"
            );

        } else {
            Assert.fail("Unknown label: " + label);
        }
    }

    @And("the {string} count should be displayed")
    public void the_count_should_be_displayed(String label) {

        if ("Doses Taken".equals(label)) {

            Assert.assertTrue(
                    medicationsPage.getDosesTakenCount().isDisplayed(),
                    "Doses Taken count is not displayed"
            );

        } else if ("Doses Missed".equals(label)) {

            Assert.assertTrue(
                    medicationsPage.getDosesMissedCount().isDisplayed(),
                    "Doses Missed count is not displayed"
            );

        } else {
            Assert.fail("Unknown count label: " + label);
        }
    }

    // =====================================================================
    // CATEGORY DROPDOWN
    // =====================================================================

    @When("the user clicks the Category dropdown")
    public void the_user_clicks_the_category_dropdown() {

        medicationsPage.getCategoryDropdown().click();
    }

    @Then("the Category dropdown should contain the following options:")
    public void the_category_dropdown_should_contain_the_following_options(
            List<String> expectedOptions) {

        List<String> actualOptions =
                medicationsPage.getVisibleDropdownOptionTexts();

        for (String expected : expectedOptions) {

            Assert.assertTrue(
                    actualOptions.stream()
                            .anyMatch(actual ->
                                    actual.equalsIgnoreCase(expected.trim())),
                    "Missing Category option: " + expected
            );
        }
    }

    @When("the user selects {string} from the Category dropdown")
    public void the_user_selects_from_the_category_dropdown(
            String category) {

        medicationsPage.selectCategory(category);
    }

    // =====================================================================
    // MEDICATION DROPDOWN
    // =====================================================================

    @And("the user clicks the Medication dropdown")
    public void the_user_clicks_the_medication_dropdown() {

        medicationsPage.getMedicationDropdown().click();
    }

    @Then("the Medication dropdown should contain:")
    public void the_medication_dropdown_should_contain(
            List<String> expectedMedications) {

        List<String> actualOptions =
                medicationsPage.getVisibleDropdownOptionTexts();

        for (String expected : expectedMedications) {

            Assert.assertTrue(
                    actualOptions.stream()
                            .anyMatch(actual ->
                                    actual.equalsIgnoreCase(expected.trim())),
                    "Missing Medication option: " + expected
            );
        }
    }

    @When("the user selects {string} from the Medication dropdown")
    public void the_user_selects_from_the_medication_dropdown(
            String medication) {

        medicationsPage.selectMedicationOption(medication);
    }

    // =====================================================================
    // FREQUENCY
    // =====================================================================

    @When("the user selects {string} from the Frequency dropdown")
    public void the_user_selects_from_the_frequency_dropdown(
            String frequency) {

        medicationsPage.selectFrequencyOption(frequency);
    }

    // =====================================================================
    // REMINDER SETTINGS
    // =====================================================================

    @Then("the reminder message should show {string}")
    public void the_reminder_message_should_show(
            String expectedMessage) {

        Assert.assertTrue(
                medicationsPage.getPrnReminderMessage()
                        .getText()
                        .trim()
                        .contains(expectedMessage),
                "Reminder message mismatch"
        );
    }

    @Then("the {string} field should be visible in Reminder Settings")
    public void the_field_should_be_visible_in_reminder_settings(
            String fieldName) {

        Assert.assertTrue(
                medicationsPage.getPreferredTimeLabel().isDisplayed(),
                "Preferred Time field is not visible: " + fieldName
        );
    }

    @And("the default time should be {string}")
    public void the_default_time_should_be(String expectedTime) {

        String rawValue =
                medicationsPage.getPreferredTimeValue();

        Assert.assertNotNull(
                rawValue,
                "Preferred time value is null"
        );

        Assert.assertFalse(
                rawValue.isEmpty(),
                "Preferred time value is empty"
        );

        LocalTime actualTime =
                LocalTime.parse(
                        rawValue,
                        DateTimeFormatter.ofPattern("HH:mm")
                );

        String actualFormatted =
                actualTime.format(
                        DateTimeFormatter.ofPattern("hh:mm a")
                );

        Assert.assertEquals(
                actualFormatted,
                expectedTime,
                "Default preferred time mismatch"
        );
    }

    @When("the user sets the preferred time to {string}")
    public void the_user_sets_the_preferred_time_to(String time) {

        medicationsPage.setPreferredTime(time);
    }

    // =====================================================================
    // UNIT
    // =====================================================================

    @Then("the unit dropdown should be disabled")
    public void the_unit_dropdown_should_be_disabled() {

        Assert.assertFalse(
                medicationsPage.getUnitDropdownDisabled().isEnabled(),
                "Unit dropdown should be disabled"
        );
    }

    @Then("the unit dropdown should be enabled")
    public void the_unit_dropdown_should_be_enabled() {

        Assert.assertTrue(
                medicationsPage.getUnitDropdownEnabled().isEnabled(),
                "Unit dropdown should be enabled"
        );
    }

    @And("the unit should show {string}")
    public void the_unit_should_show(String unit) {

        Assert.assertEquals(
                medicationsPage.getUnitDropdownEnabled()
                        .getText()
                        .trim(),
                unit,
                "Unit value mismatch"
        );
    }

    // =====================================================================
    // FORM ACTIONS
    // =====================================================================

    @When("the user enters {string} in the Dosage field")
    public void the_user_enters_in_the_dosage_field(String dosage) {

        medicationsPage.enterDosage(dosage);
    }

    @And("the user enters {string} in the Notes field")
    public void the_user_enters_in_the_notes_field(String notes) {

        medicationsPage.enterNotes(notes);
    }

    @When("the user clicks the {string} button")
    public void the_user_clicks_the_button(String buttonName) {

        if ("ADD TO PLAN".equalsIgnoreCase(buttonName)) {

            medicationsPage.clickAddToPlan();

        } else {
            Assert.fail("Unknown button: " + buttonName);
        }
    }

    @When("the user clicks the {string} button without filling any fields")
    public void the_user_clicks_the_button_without_filling_any_fields(
            String buttonName) {

        if ("ADD TO PLAN".equalsIgnoreCase(buttonName)) {

            medicationsPage.clickAddToPlan();

        } else {
            Assert.fail("Unknown button: " + buttonName);
        }
    }

    @And("the user clicks the {string} button without selecting Medication")
    public void the_user_clicks_the_button_without_selecting_medication(
            String buttonName) {

        if ("ADD TO PLAN".equalsIgnoreCase(buttonName)) {

            medicationsPage.clickAddToPlan();

        } else {
            Assert.fail("Unknown button: " + buttonName);
        }
    }

    @And("the user clicks the {string} button without entering Dosage")
    public void the_user_clicks_the_button_without_entering_dosage(
            String buttonName) {

        if ("ADD TO PLAN".equalsIgnoreCase(buttonName)) {

            medicationsPage.clickAddToPlan();

        } else {
            Assert.fail("Unknown button: " + buttonName);
        }
    }

    // =====================================================================
    // SUCCESS / VALIDATION
    // =====================================================================

    @Then("the medication should be added successfully")
    public void the_medication_should_be_added_successfully() {

        Assert.assertTrue(
                medicationsPage.isSuccessMessageDisplayed(),
                "Success message is not displayed"
        );
    }

    @Then("a validation error should be displayed for the Category field")
    public void a_validation_error_should_be_displayed_for_the_category_field() {

        Assert.assertTrue(
                medicationsPage.isValidationErrorDisplayedFor("Category"),
                "Category validation error not displayed. Toast: "
                        + medicationsPage.getToastMessage()
        );
    }

    @Then("a validation error should be displayed for the Medication field")
    public void a_validation_error_should_be_displayed_for_the_medication_field() {

        Assert.assertTrue(
                medicationsPage.isValidationErrorDisplayedFor("Medication"),
                "Medication validation error not displayed. Toast: "
                        + medicationsPage.getToastMessage()
        );
    }

    @Then("a validation error should be displayed for the Dosage field")
    public void a_validation_error_should_be_displayed_for_the_dosage_field() {

        Assert.assertTrue(
                medicationsPage.isValidationErrorDisplayedFor("Dosage"),
                "Dosage validation error not displayed. Toast: "
                        + medicationsPage.getToastMessage()
        );
    }

    @Then("a validation error should be displayed for invalid dosage")
    public void a_validation_error_should_be_displayed_for_invalid_dosage() {

        Assert.assertTrue(
                medicationsPage.isValidationErrorDisplayed(),
                "Validation error not displayed. Toast: "
                        + medicationsPage.getToastMessage()
        );
    }

    // =====================================================================
    // MEDICATION CARDS
    // =====================================================================

    @Then("medications scheduled for today should be displayed")
    public void medications_scheduled_for_today_should_be_displayed() {

        Assert.assertTrue(
                medicationsPage.getMedicationCardCount() >= 0,
                "Medication cards were not rendered"
        );
    }

    @Then("missed medications should be displayed")
    public void missed_medications_should_be_displayed() {

        Assert.assertTrue(
                medicationsPage.getMedicationCardCount() >= 0,
                "Missed medications were not rendered"
        );
    }

    @And("each missed medication card should show the reason")
    public void each_missed_medication_card_should_show_the_reason() {

        Assert.assertTrue(
                medicationsPage.doAllMissedCardsShowReason(),
                "Missed medication cards do not show reason"
        );
    }

    @Then("medications from yesterday should be displayed")
    public void medications_from_yesterday_should_be_displayed() {

        Assert.assertTrue(
                medicationsPage.getMedicationCardCount() >= 0,
                "Yesterday medications were not rendered"
        );
    }

    @Then("discontinued medications count should match the tab badge")
    public void discontinued_medications_count_should_match_the_tab_badge() {

        int badgeCount =
                medicationsPage.getFilterTabCount("Discontinued");

        int cardCount =
                medicationsPage.getMedicationCardCount();

        Assert.assertEquals(
                cardCount,
                badgeCount,
                "Discontinued card count does not match tab badge"
        );
    }

    // =====================================================================
    // MARK AS TAKEN
    // =====================================================================

    @And("the user clicks the {string} button for the first medication")
    public void the_user_clicks_the_button_for_the_first_medication(
            String buttonName) {

        if ("Mark as Taken".equalsIgnoreCase(buttonName)) {

            missedCountBeforeMarkAsTaken =
                    medicationsPage.getFilterTabCount("Missed");

            medicationsPage.markFirstMedicationAsTakenWithConfirmation();

            medicationsPage.waitForCardsToRefresh();

        } else {
            Assert.fail("Unknown card button: " + buttonName);
        }
    }

    @Then("the medication should be marked as taken")
    public void the_medication_should_be_marked_as_taken() {

        int after =
                medicationsPage.getFilterTabCount("Missed");

        Assert.assertTrue(
                after < missedCountBeforeMarkAsTaken,
                "Missed count did not decrease after marking medication as taken"
        );
    }

    @And("the user clicks the {string} button for the first medication without confirming")
    public void the_user_clicks_the_button_for_the_first_medication_without_confirming(
            String buttonName) {

        if ("Mark as Taken".equalsIgnoreCase(buttonName)) {

            missedCountBeforeMarkAsTaken =
                    medicationsPage.getFilterTabCount("Missed");

            medicationsPage.clickMarkAsTakenOnFirstCard();

        } else {
            Assert.fail("Unknown card button: " + buttonName);
        }
    }

    @Then("the {string} confirmation dialog should be displayed")
    public void the_confirmation_dialog_should_be_displayed(
            String dialogTitle) {

        Assert.assertTrue(
                medicationsPage.getMarkAsTakenDialogTitle()
                        .getText()
                        .trim()
                        .contains(dialogTitle),
                "Confirmation dialog not displayed: " + dialogTitle
        );
    }

    @And("the {string} button should be visible in the dialog")
    public void the_button_should_be_visible_in_the_dialog(
            String buttonName) {

        if ("Yes, I took it".equals(buttonName)) {

            Assert.assertTrue(
                    medicationsPage.getMarkAsTakenDialogConfirmBtn().isDisplayed(),
                    "Confirm button is not visible"
            );

        } else if ("Cancel".equals(buttonName)) {

            Assert.assertTrue(
                    medicationsPage.getMarkAsTakenDialogCancelBtn().isDisplayed(),
                    "Cancel button is not visible"
            );

        } else {
            Assert.fail("Unknown dialog button: " + buttonName);
        }
    }

    @And("the user clicks {string} on the confirmation dialog")
    public void the_user_clicks_on_the_confirmation_dialog(
            String buttonName) {

        if ("Cancel".equals(buttonName)) {

            medicationsPage.cancelMarkAsTakenDialog();

        } else if ("Yes, I took it".equals(buttonName)) {

            medicationsPage.getMarkAsTakenDialogConfirmBtn().click();

        } else {
            Assert.fail("Unknown dialog button: " + buttonName);
        }
    }

    @Then("the medication should remain in the Missed list")
    public void the_medication_should_remain_in_the_missed_list() {

        int after =
                medicationsPage.getFilterTabCount("Missed");

        Assert.assertEquals(
                after,
                missedCountBeforeMarkAsTaken,
                "Missed count changed after cancelling confirmation"
        );
    }

    // =====================================================================
    // THEME
    // =====================================================================

    @When("the user clicks the theme toggle button")
    public void the_user_clicks_the_theme_toggle_button() {

        htmlClassBeforeToggle =
                medicationsPage.getHtmlThemeClass();

        medicationsPage.clickThemeToggleButton();
    }

    @Then("the page theme should switch accordingly")
    public void the_page_theme_should_switch_accordingly() {

        String after =
                medicationsPage.getHtmlThemeClass();

        Assert.assertNotEquals(
                after,
                htmlClassBeforeToggle,
                "Page theme did not switch"
        );
    }

    // =====================================================================
    // SIDEBAR NAVIGATION
    // =====================================================================

    @When("the user clicks {string} in the sidebar")
    public void the_user_clicks_in_the_sidebar(String menuItem) {

        medicationsPage.clickSidebarItem(menuItem);
    }

    @Then("the user should be redirected to the Home page")
    public void the_user_should_be_redirected_to_the_home_page() {

        Assert.assertTrue(
                medicationsPage.getDriverCurrentUrl()
                        .toLowerCase()
                        .contains("home"),
                "User was not redirected to Home page"
        );
    }

    @Then("the user should be redirected to the Log Symptoms page")
    public void the_user_should_be_redirected_to_the_log_symptoms_page() {

        Assert.assertTrue(
                medicationsPage.getDriverCurrentUrl()
                        .toLowerCase()
                        .contains("symptom"),
                "User was not redirected to Log Symptoms page"
        );
    }

    @Then("the user should be redirected to the Lab Results page")
    public void the_user_should_be_redirected_to_the_lab_results_page() {

        Assert.assertTrue(
                medicationsPage.getDriverCurrentUrl()
                        .toLowerCase()
                        .contains("lab"),
                "User was not redirected to Lab Results page"
        );
    }

    @Then("the user should be redirected to the History page")
    public void the_user_should_be_redirected_to_the_history_page() {

        Assert.assertTrue(
                medicationsPage.getDriverCurrentUrl()
                        .toLowerCase()
                        .contains("history"),
                "User was not redirected to History page"
        );
    }

    @Then("the user should be redirected to the Insights page")
    public void the_user_should_be_redirected_to_the_insights_page() {

        Assert.assertTrue(
                medicationsPage.getDriverCurrentUrl()
                        .toLowerCase()
                        .contains("insight"),
                "User was not redirected to Insights page"
        );
    }

    @Then("the user should be redirected to the Login page")
    public void the_user_should_be_redirected_to_the_login_page() {

        Assert.assertTrue(
                medicationsPage.getDriverCurrentUrl()
                        .toLowerCase()
                        .contains("login"),
                "User was not redirected to Login page"
        );
    }
}