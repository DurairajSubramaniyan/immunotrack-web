package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class MedicationsPage extends BasePage {

    public MedicationsPage() {
        super();
    }

    // =====================================================================
    // 1. HEADER ELEMENTS
    // =====================================================================

    @FindBy(xpath = "//span[text()='Patient Portal']")
    private WebElement patientPortalBreadcrumb;

    @FindBy(xpath = "//span[contains(@class,'text-primary-teal') and text()='Medications']")
    private WebElement medicationsBreadcrumb;

    @FindBy(xpath = "//button[@aria-label='Toggle theme']")
    private WebElement themeToggleBtn;

    @FindBy(xpath = "//button[contains(@aria-label,'notifications')]")
    private WebElement notificationBellBtn;

    @FindBy(xpath = "//button[contains(@aria-label,'notifications')]//span[contains(@class,'font-mono')]")
    private WebElement notificationCountBadge;

    // Kept in Page Object, but avatar is no longer tested in feature/steps.
    @FindBy(xpath = "//div[text()='PS']")
    private WebElement userAvatarPS;

    @FindBy(xpath = "//span[text()='pavithra']")
    private WebElement usernameText;

    @FindBy(xpath = "//span[text()='Patient' and contains(@class,'text-')]")
    private WebElement userRoleText;

    // =====================================================================
    // 2. PAGE TITLE SECTION
    // =====================================================================

    @FindBy(xpath = "//button[.//svg[contains(@class,'lucide-arrow-left')]]")
    private WebElement backArrowBtn;

    @FindBy(xpath = "//h2[text()='Medications']")
    private WebElement pageTitle;

    @FindBy(xpath = "//p[text()='Track your daily medication adherence']")
    private WebElement pageSubtitle;

    // =====================================================================
    // 3. QUICK ADD MEDICATION - FORM ELEMENTS
    // =====================================================================

    @FindBy(xpath = "//h3[contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'QUICK ADD MEDICATION')]")
    private WebElement quickAddMedicationHeading;

    // FIXED: Category dropdown
    @FindBy(xpath = "(//button[@role='combobox' and not(@disabled) and not(@data-placeholder)])[1]")
    private WebElement categoryDropdown;

    @FindBy(xpath = "(//button[@role='combobox' and not(@disabled) and not(@data-placeholder)])[1]//span/span")
    private WebElement categorySelectedText;

    // FIXED: Medication dropdown
    @FindBy(xpath = "//button[@role='combobox' and @data-placeholder]")
    private WebElement medicationDropdown;

    @FindBy(xpath = "//button[@role='combobox' and @data-placeholder]//span/span")
    private WebElement medicationSelectedText;

    // FIXED: Dosage input
    @FindBy(xpath = "//input[@placeholder='e.g. 10, 2.5']")
    private WebElement dosageInput;

    // FIXED: Unit dropdown - enabled
    @FindBy(xpath = "(//button[@role='combobox' and not(@disabled) and not(@data-placeholder)])[2]")
    private WebElement unitDropdownEnabled;

    // Unit dropdown - disabled
    @FindBy(xpath = "//button[@role='combobox' and @disabled]")
    private WebElement unitDropdownDisabled;

    // FIXED: Frequency dropdown
    @FindBy(xpath = "(//button[@role='combobox' and not(@disabled) and not(@data-placeholder)])[3]")
    private WebElement frequencyDropdown;

    @FindBy(xpath = "(//button[@role='combobox' and not(@disabled) and not(@data-placeholder)])[3]//span/span")
    private WebElement frequencySelectedText;

    @FindBy(xpath = "//input[@placeholder='MM/DD/YYYY']")
    private WebElement startDateInput;

    @FindBy(xpath = "//input[@placeholder='e.g. Take with food']")
    private WebElement notesInput;

    @FindBy(xpath = "//div[@role='option'] | //div[contains(@class,'SelectItem')]")
    private List<WebElement> dropdownOptions;

    // =====================================================================
    // 4. REMINDER SETTINGS SECTION
    // =====================================================================

    @FindBy(xpath = "//h4[contains(text(),'Reminder Settings')]")
    private WebElement reminderSettingsHeading;

    @FindBy(xpath = "//div[contains(@class,'bg-card/30') and contains(text(),'This medication is taken as needed')]")
    private WebElement prnReminderMessage;

    @FindBy(xpath = "//label[contains(text(),'Preferred Time')] | //*[contains(text(),'Preferred Time 1')]")
    private WebElement preferredTimeLabel;

    @FindBy(xpath = "//input[@type='time']")
    private WebElement preferredTimeInput;

    // =====================================================================
    // 5. ADD TO PLAN BUTTON
    // =====================================================================

    @FindBy(xpath = "//button[@data-slot='button']//span[text()='ADD TO PLAN']")
    private WebElement addToPlanBtn;

    @FindBy(xpath = "//section[@aria-label='Notifications']")
    private WebElement toastNotificationSection;

    // =====================================================================
    // 6. MAIN TABS
    // =====================================================================

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-medications')]]")
    private WebElement medicationsTab;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-medications')]]//span")
    private WebElement medicationsTabCount;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-reminders')]]")
    private WebElement remindersTab;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-reminders')]]//span")
    private WebElement remindersTabCount;

    // =====================================================================
    // 7. MEDICATION STATUS FILTER TABS
    // =====================================================================

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-today')]]")
    private WebElement todayTab;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-today')]]//span")
    private WebElement todayTabCount;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-future')]]")
    private WebElement futureTab;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-future')]]//span")
    private WebElement futureTabCount;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-missed')]]")
    private WebElement missedTab;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-missed')]]//span")
    private WebElement missedTabCount;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-discontinued')]]")
    private WebElement discontinuedTab;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-discontinued')]]//span")
    private WebElement discontinuedTabCount;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-yesterday')]]")
    private WebElement yesterdayTab;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-yesterday')]]//span")
    private WebElement yesterdayTabCount;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-missedlogs')]]")
    private WebElement missedLogsTab;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-missedlogs')]]//span")
    private WebElement missedLogsTabCount;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-pending')]]")
    private WebElement pendingTab;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-pending')]]//span")
    private WebElement pendingTabCount;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-completed')]]")
    private WebElement completedTab;

    @FindBy(xpath = "//button[@role='tab' and @id[contains(.,'trigger-completed')]]//span")
    private WebElement completedTabCount;

    // =====================================================================
    // 8. MEDICATION CARDS
    // =====================================================================

    @FindBy(xpath = "//h4[contains(@class,'font-bold') and contains(@class,'text-foreground')]/ancestor::div[contains(@class,'min-w-0')]")
    private List<WebElement> medicationCards;

    @FindBy(xpath = "//h4[contains(@class,'font-bold') and contains(@class,'text-foreground')]")
    private List<WebElement> medicationCardNames;

    @FindBy(xpath = "//span[text()='Auto-detected']")
    private List<WebElement> autoDetectedBadges;

    @FindBy(xpath = "//span[contains(@class,'text-secondary') and contains(@class,'text-xs') and contains(text(),'•')]")
    private List<WebElement> medicationDosageFrequencyText;

    @FindBy(xpath = "//span[text()='Reason: ']")
    private List<WebElement> reasonLabels;

    @FindBy(xpath = "//span[contains(@class,'italic') and contains(@class,'text-amber')]")
    private List<WebElement> reasonValues;

    @FindBy(xpath = "//span[contains(@class,'lucide-clock')]/following-sibling::* | //span[.//svg[contains(@class,'lucide-clock')]]")
    private List<WebElement> medicationCardDates;

    @FindBy(xpath = "//button[@data-slot='button' and contains(.,'Mark as Taken')]")
    private List<WebElement> markAsTakenBtns;

    @FindBy(xpath = "//*[contains(text(),'Did you take this?')]")
    private WebElement markAsTakenDialogTitle;

    @FindBy(xpath = "//button[normalize-space(text())='Cancel']")
    private WebElement markAsTakenDialogCancelBtn;

    @FindBy(xpath = "//button[contains(.,'Yes, I took it')]")
    private WebElement markAsTakenDialogConfirmBtn;

    // =====================================================================
    // 9. WEEKLY ADHERENCE SECTION
    // =====================================================================

    @FindBy(xpath = "//h4[text()='Weekly Adherence']")
    private WebElement weeklyAdherenceHeading;

    @FindBy(xpath = "//div[contains(@class,'text-4xl') and contains(text(),'%')]")
    private WebElement adherencePercentage;

    @FindBy(xpath = "//p[text()='Last 7 days']")
    private WebElement last7DaysLabel;

    @FindBy(xpath = "//p[text()='Doses Taken']/preceding-sibling::div")
    private WebElement dosesTakenCount;

    @FindBy(xpath = "//p[text()='Doses Taken']")
    private WebElement dosesTakenLabel;

    @FindBy(xpath = "//p[text()='Doses Missed']/preceding-sibling::div")
    private WebElement dosesMissedCount;

    @FindBy(xpath = "//p[text()='Doses Missed']")
    private WebElement dosesMissedLabel;

    // =====================================================================
    // 10. SIDEBAR NAVIGATION
    // =====================================================================

    @FindBy(xpath = "//button[.//span[text()='Home'] and .//svg[contains(@class,'lucide-house')]]")
    private WebElement homeMenuItem;

    @FindBy(xpath = "//button[.//span[text()='Log Symptoms']]")
    private WebElement logSymptomsMenuItem;

    @FindBy(xpath = "//button[.//span[text()='Medications'] and contains(@class,'bg-primary-teal')]")
    private WebElement medicationsMenuItemActive;

    @FindBy(xpath = "//button[.//span[text()='Medications']]")
    private WebElement medicationsMenuItem;

    @FindBy(xpath = "//button[.//span[text()='Lab Results']]")
    private WebElement labResultsMenuItem;

    @FindBy(xpath = "//button[.//span[text()='History']]")
    private WebElement historyMenuItem;

    @FindBy(xpath = "//button[.//span[text()='Insights']]")
    private WebElement insightsMenuItem;

    @FindBy(xpath = "//button[.//span[text()='Log Out']]")
    private WebElement logOutMenuItem;

    // =====================================================================
    // GETTERS
    // =====================================================================

    public WebElement getPatientPortalBreadcrumb() { return patientPortalBreadcrumb; }
    public WebElement getMedicationsBreadcrumb() { return medicationsBreadcrumb; }
    public WebElement getThemeToggleBtn() { return themeToggleBtn; }
    public WebElement getNotificationBellBtn() { return notificationBellBtn; }
    public WebElement getNotificationCountBadge() { return notificationCountBadge; }
    public WebElement getUserAvatarPS() { return userAvatarPS; }
    public WebElement getUsernameText() { return usernameText; }
    public WebElement getUserRoleText() { return userRoleText; }

    public WebElement getBackArrowBtn() { return backArrowBtn; }
    public WebElement getPageTitle() { return pageTitle; }
    public WebElement getPageSubtitle() { return pageSubtitle; }

    public WebElement getQuickAddMedicationHeading() { return quickAddMedicationHeading; }
    public WebElement getCategoryDropdown() { return categoryDropdown; }
    public WebElement getCategorySelectedText() { return categorySelectedText; }
    public WebElement getMedicationDropdown() { return medicationDropdown; }
    public WebElement getMedicationSelectedText() { return medicationSelectedText; }
    public WebElement getDosageInput() { return dosageInput; }
    public WebElement getUnitDropdownEnabled() { return unitDropdownEnabled; }
    public WebElement getUnitDropdownDisabled() { return unitDropdownDisabled; }
    public WebElement getFrequencyDropdown() { return frequencyDropdown; }
    public WebElement getFrequencySelectedText() { return frequencySelectedText; }
    public WebElement getStartDateInput() { return startDateInput; }
    public WebElement getNotesInput() { return notesInput; }
    public List<WebElement> getDropdownOptions() { return dropdownOptions; }

    public WebElement getReminderSettingsHeading() { return reminderSettingsHeading; }
    public WebElement getPrnReminderMessage() { return prnReminderMessage; }
    public WebElement getPreferredTimeLabel() { return preferredTimeLabel; }
    public WebElement getPreferredTimeInput() { return preferredTimeInput; }

    public WebElement getAddToPlanBtn() { return addToPlanBtn; }
    public WebElement getToastNotificationSection() { return toastNotificationSection; }

    public WebElement getMedicationsTab() { return medicationsTab; }
    public WebElement getMedicationsTabCount() { return medicationsTabCount; }
    public WebElement getRemindersTab() { return remindersTab; }
    public WebElement getRemindersTabCount() { return remindersTabCount; }

    public WebElement getTodayTab() { return todayTab; }
    public WebElement getTodayTabCount() { return todayTabCount; }
    public WebElement getFutureTab() { return futureTab; }
    public WebElement getFutureTabCount() { return futureTabCount; }
    public WebElement getMissedTab() { return missedTab; }
    public WebElement getMissedTabCount() { return missedTabCount; }
    public WebElement getDiscontinuedTab() { return discontinuedTab; }
    public WebElement getDiscontinuedTabCount() { return discontinuedTabCount; }
    public WebElement getYesterdayTab() { return yesterdayTab; }
    public WebElement getYesterdayTabCount() { return yesterdayTabCount; }
    public WebElement getMissedLogsTab() { return missedLogsTab; }
    public WebElement getMissedLogsTabCount() { return missedLogsTabCount; }
    public WebElement getPendingTab() { return pendingTab; }
    public WebElement getPendingTabCount() { return pendingTabCount; }
    public WebElement getCompletedTab() { return completedTab; }
    public WebElement getCompletedTabCount() { return completedTabCount; }

    public List<WebElement> getMedicationCards() { return medicationCards; }
    public List<WebElement> getMedicationCardNames() { return medicationCardNames; }
    public List<WebElement> getAutoDetectedBadges() { return autoDetectedBadges; }
    public List<WebElement> getMedicationDosageFrequencyText() { return medicationDosageFrequencyText; }
    public List<WebElement> getReasonLabels() { return reasonLabels; }
    public List<WebElement> getReasonValues() { return reasonValues; }
    public List<WebElement> getMedicationCardDates() { return medicationCardDates; }
    public List<WebElement> getMarkAsTakenBtns() { return markAsTakenBtns; }
    public WebElement getMarkAsTakenDialogTitle() { return markAsTakenDialogTitle; }
    public WebElement getMarkAsTakenDialogCancelBtn() { return markAsTakenDialogCancelBtn; }
    public WebElement getMarkAsTakenDialogConfirmBtn() { return markAsTakenDialogConfirmBtn; }

    public WebElement getWeeklyAdherenceHeading() { return weeklyAdherenceHeading; }
    public WebElement getAdherencePercentage() { return adherencePercentage; }
    public WebElement getLast7DaysLabel() { return last7DaysLabel; }
    public WebElement getDosesTakenCount() { return dosesTakenCount; }
    public WebElement getDosesTakenLabel() { return dosesTakenLabel; }
    public WebElement getDosesMissedCount() { return dosesMissedCount; }
    public WebElement getDosesMissedLabel() { return dosesMissedLabel; }

    public WebElement getHomeMenuItem() { return homeMenuItem; }
    public WebElement getLogSymptomsMenuItem() { return logSymptomsMenuItem; }
    public WebElement getMedicationsMenuItemActive() { return medicationsMenuItemActive; }
    public WebElement getMedicationsMenuItem() { return medicationsMenuItem; }
    public WebElement getLabResultsMenuItem() { return labResultsMenuItem; }
    public WebElement getHistoryMenuItem() { return historyMenuItem; }
    public WebElement getInsightsMenuItem() { return insightsMenuItem; }
    public WebElement getLogOutMenuItem() { return logOutMenuItem; }

    // =====================================================================
    // UTILITY METHODS
    // =====================================================================

    public boolean isPageLoaded() {
        try {
            waitForVisibility(pageTitle);
            return pageTitle.isDisplayed();
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("medications");
        }
    }

    public int getMedicationCardCount() {
        return medicationCards.size();
    }

    public void clickMarkAsTaken(int index) {
        if (index < markAsTakenBtns.size()) {
            click(markAsTakenBtns.get(index));
        }
    }

    // =====================================================================
    // DROPDOWN HELPERS
    // =====================================================================

    private void selectFromDropdown(WebElement dropdownButton, String optionText) {
        click(dropdownButton);

        if (!dropdownOptions.isEmpty()) {
            waitForVisibility(dropdownOptions.get(0));
        }

        for (WebElement option : dropdownOptions) {
            if (option.getText().trim().equalsIgnoreCase(optionText.trim())) {
                click(option);
                return;
            }
        }

        throw new NoSuchElementException("Dropdown option not found: " + optionText);
    }

    public void selectCategory(String category) {
        selectFromDropdown(categoryDropdown, category);
    }

    public void selectMedicationOption(String medication) {
        selectFromDropdown(medicationDropdown, medication);
    }

    public void selectFrequencyOption(String frequency) {
        selectFromDropdown(frequencyDropdown, frequency);
    }

    public List<String> getVisibleDropdownOptionTexts() {
        List<String> texts = new ArrayList<>();

        for (WebElement option : dropdownOptions) {
            texts.add(option.getText().trim());
        }

        return texts;
    }

    public void enterDosage(String dosage) {
        dosageInput.clear();
        dosageInput.sendKeys(dosage);
    }

    public void enterNotes(String notes) {
        notesInput.clear();
        notesInput.sendKeys(notes);
    }

    // =====================================================================
    // PREFERRED TIME
    // =====================================================================

    public void setPreferredTime(String time12hr) {

        java.time.LocalTime parsed =
                java.time.LocalTime.parse(
                        time12hr.trim(),
                        java.time.format.DateTimeFormatter.ofPattern("hh:mm a")
                );

        String time24hr =
                parsed.format(
                        java.time.format.DateTimeFormatter.ofPattern("HH:mm")
                );

        org.openqa.selenium.JavascriptExecutor js =
                (org.openqa.selenium.JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].value = arguments[1];" +
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                preferredTimeInput,
                time24hr
        );
    }

    public String getPreferredTimeValue() {
        return preferredTimeInput.getAttribute("value");
    }

    public String getStartDateValue() {
        return startDateInput.getAttribute("value");
    }

    // =====================================================================
    // ADD TO PLAN / TOAST
    // =====================================================================

    public void clickAddToPlan() {
        click(addToPlanBtn);
    }

    public String getToastMessage() {
        try {
            waitForVisibility(toastNotificationSection);
            return toastNotificationSection.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isSuccessMessageDisplayed() {
        String text = getToastMessage().toLowerCase();
        return text.contains("added") || text.contains("success");
    }

    public boolean isValidationErrorDisplayed() {
        String text = getToastMessage().toLowerCase();

        return text.contains("required")
                || text.contains("fill all required fields")
                || text.contains("please fill");
    }

    public boolean isValidationErrorDisplayedFor(String fieldName) {
        String text = getToastMessage();

        return isValidationErrorDisplayed()
                && text.toLowerCase().contains(fieldName.toLowerCase());
    }

    // =====================================================================
    // TABS
    // =====================================================================

    public void clickMainTab(String tabName) {
        click(
                tabName.equalsIgnoreCase("Medications")
                        ? medicationsTab
                        : remindersTab
        );
    }

    public void clickFilterTabByName(String filterName) {

        WebElement tab;

        switch (filterName) {
            case "Today":
                tab = todayTab;
                break;

            case "Future":
                tab = futureTab;
                break;

            case "Missed":
                tab = missedTab;
                break;

            case "Discontinued":
                tab = discontinuedTab;
                break;

            case "Yesterday":
                tab = yesterdayTab;
                break;

            case "Missed Logs":
                tab = missedLogsTab;
                break;

            case "Pending":
                tab = pendingTab;
                break;

            case "Completed":
                tab = completedTab;
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown filter tab: " + filterName
                );
        }

        click(tab);
    }

    public int getFilterTabCount(String filterName) {

        WebElement countEl;

        switch (filterName) {
            case "Today":
                countEl = todayTabCount;
                break;

            case "Future":
                countEl = futureTabCount;
                break;

            case "Missed":
                countEl = missedTabCount;
                break;

            case "Discontinued":
                countEl = discontinuedTabCount;
                break;

            case "Yesterday":
                countEl = yesterdayTabCount;
                break;

            case "Missed Logs":
                countEl = missedLogsTabCount;
                break;

            case "Pending":
                countEl = pendingTabCount;
                break;

            case "Completed":
                countEl = completedTabCount;
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown filter tab: " + filterName
                );
        }

        String digits =
                countEl.getText().replaceAll("[^0-9]", "");

        return digits.isEmpty()
                ? 0
                : Integer.parseInt(digits);
    }

    // =====================================================================
    // SIDEBAR
    // =====================================================================

    public void clickSidebarItem(String menuItem) {

        WebElement item;

        switch (menuItem) {
            case "Home":
                item = homeMenuItem;
                break;

            case "Log Symptoms":
                item = logSymptomsMenuItem;
                break;

            case "Medications":
                item = medicationsMenuItem;
                break;

            case "Lab Results":
                item = labResultsMenuItem;
                break;

            case "History":
                item = historyMenuItem;
                break;

            case "Insights":
                item = insightsMenuItem;
                break;

            case "Log Out":
                item = logOutMenuItem;
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown sidebar item: " + menuItem
                );
        }

        click(item);
    }

    // =====================================================================
    // THEME
    // =====================================================================

    public void clickThemeToggleButton() {
        click(themeToggleBtn);
    }

    public String getHtmlThemeClass() {
        return driver
                .findElement(By.tagName("html"))
                .getAttribute("class");
    }

    // =====================================================================
    // MISSED MEDICATIONS
    // =====================================================================

    public boolean doAllMissedCardsShowReason() {

        if (medicationCards.isEmpty()) {
            return true;
        }

        return !reasonLabels.isEmpty()
                && reasonLabels.size() >= medicationCards.size();
    }

    // =====================================================================
    // MARK AS TAKEN
    // =====================================================================

    public void clickMarkAsTakenOnFirstCard() {

        if (!markAsTakenBtns.isEmpty()) {
            click(markAsTakenBtns.get(0));
        }
    }

    public void markFirstMedicationAsTakenWithConfirmation() {

        if (markAsTakenBtns.isEmpty()) {
            throw new NoSuchElementException(
                    "No 'Mark as Taken' buttons found on the page"
            );
        }

        click(markAsTakenBtns.get(0));

        waitForVisibility(markAsTakenDialogTitle);

        click(markAsTakenDialogConfirmBtn);
    }

    public void cancelMarkAsTakenDialog() {
        click(markAsTakenDialogCancelBtn);
    }

    public void waitForCardsToRefresh() {

        try {
            Thread.sleep(1500);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    public String getDriverCurrentUrl() {
        return driver.getCurrentUrl();
    }
}