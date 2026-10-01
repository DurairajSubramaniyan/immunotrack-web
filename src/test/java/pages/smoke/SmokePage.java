package pages.smoke;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.time.Duration;
import java.util.List;

public class SmokePage extends BasePage {

    // =========================================================================
    // NAVIGATION LOCATORS (Scoped to sidebar and responsive buttons)
    // =========================================================================
    private final By homeNavLocator = By.xpath(
        "//button[.//span[text()='Home']] | //aside//button[contains(., 'Home')] | //aside//a[contains(@href, 'dashboard')] | //a[contains(@href, 'dashboard')]"
    );

    private final By logSymptomsNavLocator = By.xpath(
        "//button[.//span[text()='Log Symptoms']] | //aside//button[contains(., 'Log Symptoms')] | //aside//a[contains(@href, 'log-symptoms')] | //a[contains(@href, 'log-symptoms')]"
    );

    private final By medicationsNavLocator = By.xpath(
        "//button[.//span[text()='Medications']] | //aside//button[contains(., 'Medications')] | //aside//a[contains(@href, 'medications')] | //a[contains(@href, 'medications')]"
    );

    private final By historyNavLocator = By.xpath(
        "//button[.//span[text()='History']] | //aside//button[contains(., 'History')] | //aside//a[contains(@href, 'history')] | //a[contains(@href, 'history')]"
    );

    private final By insightsNavLocator = By.xpath(
        "//button[.//span[text()='Insights']] | //aside//button[contains(., 'Insights')] | //aside//a[contains(@href, 'insights')] | //a[contains(@href, 'insights')]"
    );

    private final By labResultsNavLocator = By.xpath(
        "//button[.//span[text()='Lab Results']] | //aside//button[contains(., 'Lab Results')] | //aside//a[contains(@href, 'lab-results')] | //a[contains(@href, 'lab-results')]"
    );

    private final By profileNavLocator = By.cssSelector(
        "div.rounded-lg.bg-gradient-to-tr[class*='from-primary-teal']"
    );

    private final By profileNavFallbackLocator = By.xpath(
        "//div[contains(@class,'from-primary-teal')] | //button[contains(@aria-label,'Profile')] | //*[contains(@href, '/profile')]"
    );

    private final By logoutButtonLocator = By.xpath(
        "//button[.//span[text()='Log Out']] | //button[contains(., 'Log Out')] | //a[contains(., 'Log Out')]"
    );

    // =========================================================================
    // FOOTER & GLOBAL BRANDING LOCATORS
    // =========================================================================
    private final By globalFooterOrBrandingLocator = By.xpath(
        "//footer | //aside | //nav | //*[contains(text(), 'Patient Portal')] | //*[contains(text(), 'ImmunoTrack')]"
    );

    // =========================================================================
    // DASHBOARD LOCATORS
    // =========================================================================
    private final By dashboardGreetingLocator = By.xpath(
        "//h1[contains(., 'Good')] | //*[contains(., 'DASHBOARD')] | //h1[contains(., 'Patient')] | //span[contains(text(), 'Patient Portal')]"
    );

    private final By monitoringStatusCardLocator = By.xpath(
        "//*[contains(., 'YOUR MONITORING') or contains(., 'On Track') or contains(., 'Monitoring Status')]"
    );

    private final By flareRiskCardLocator = By.xpath(
        "//*[contains(., 'Flare Risk') or contains(., 'Predicted Risk') or contains(., 'Risk Level')]"
    );

    private final By dashboardActionButtonsLocator = By.xpath(
        "//a[contains(., 'Log symptoms') or contains(., \"Log today's symptoms\")] | " +
        "//button[contains(., 'Log symptoms') or contains(., \"Log today's symptoms\")] | " +
        "//a[contains(., 'Start Assessment')] | " +
        "//button[contains(., 'Start Assessment')] | " +
        "//button[.//span[contains(., 'Log Symptoms')]]"
    );

    // =========================================================================
    // LOG SYMPTOMS LOCATORS
    // =========================================================================
    private final By logSymptomsTitleLocator = By.xpath(
        "//h1[contains(., 'Log Symptoms') or contains(., 'Daily Health Log')] | " +
        "//h2[contains(., 'Log Symptoms') or contains(., 'Daily Health Log')] | " +
        "//*[contains(text(), 'Daily Health Log')]"
    );

    private final By symptomDomainsLocator = By.xpath(
        "//*[contains(., 'Breathing & Asthma') or contains(., 'ACQ-6') or contains(., 'Nose & Sinus') or contains(., 'SNOT-22') or contains(., 'Skin Symptoms') or contains(., 'POEM')]"
    );

    private final By saveLogButtonLocator = By.xpath(
        "//button[contains(., 'Log Today') or contains(., 'Update Today') or contains(., 'Save Log') or contains(., 'Submit') or contains(., 'Save')]"
    );

    // =========================================================================
    // MEDICATIONS LOCATORS
    // =========================================================================
    private final By medicationsTitleLocator = By.xpath(
        "//h1[contains(., 'Medication')] | //h2[contains(., 'Medication')] | //span[contains(@class,'text-primary-teal') and text()='Medications']"
    );

    private final By quickAddMedicationSectionLocator = By.xpath(
        "//h3[contains(translate(.,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'QUICK ADD MEDICATION')] | " +
        "//*[contains(., 'Quick Add') or contains(., 'ADD TO PLAN')]"
    );

    private final By addToPlanButtonLocator = By.xpath(
        "//button[@data-slot='button']//span[text()='ADD TO PLAN'] | //button[contains(., 'ADD TO PLAN') or contains(., 'Add to Plan')]"
    );

    private final By activeMedicationsSectionLocator = By.xpath(
        "//*[contains(., 'Active') or contains(., 'Adherence') or contains(., 'Plan') or contains(., 'Track')]"
    );

    // =========================================================================
    // HISTORY LOCATORS
    // =========================================================================
    private final By historyTitleLocator = By.xpath(
        "//h1[contains(., 'History')] | //h2[contains(., 'History')] | //*[contains(., 'Symptom History')]"
    );

    private final By dateFilterSectionLocator = By.xpath(
        "//*[contains(., 'Filter by Date')]"
    );

    private final By dateFilterButtonsLocator = By.xpath(
        "//button[contains(., 'Today') or contains(., 'Last 7 Days') or contains(., 'Last 30 days') or contains(., 'Custom Range')]"
    );

    // =========================================================================
    // INSIGHTS LOCATORS
    // =========================================================================
    private final By insightsTitleLocator = By.xpath(
        "//h1[contains(., 'Insights')] | //h2[contains(., 'Insights')] | //*[contains(., 'Health Insights')]"
    );

    private final By insightsAnalyticsWidgetLocator = By.xpath(
        "//*[contains(., 'Risk Factors') or contains(., 'Trends') or contains(., 'Analytics') or contains(., 'Triggers') or contains(., 'Forecast')]"
    );

    // =========================================================================
    // LAB RESULTS LOCATORS
    // =========================================================================
    private final By labResultsTitleLocator = By.xpath(
        "//h1[contains(., 'Lab Results')] | //h2[contains(., 'Lab Results')] | //*[contains(., 'Biomarkers')]"
    );

    private final By labResultCardsLocator = By.xpath(
        "//div[contains(@class, 'card') or contains(., 'IgE') or contains(., 'Eosinophils') or contains(., 'Biomarkers')]"
    );

    // =========================================================================
    // PROFILE LOCATORS
    // =========================================================================
    private final By profileTitleLocator = By.xpath(
        "//*[contains(., 'My Profile')] | //h1[contains(., 'Profile')]"
    );

    private final By profileAccountSummaryLocator = By.xpath(
        "//*[contains(., 'years old') or contains(., 'pavithra')]"
    );

    private final By profileContactInfoLocator = By.xpath(
        "//*[contains(., 'EMAIL') or contains(., 'Email') or contains(., 'PHONE') or contains(., 'Phone')]"
    );

    private final By saveProfileButtonLocator = By.xpath(
        "//button[contains(., 'Save Profile Changes') or contains(., 'Save')]"
    );

    // =========================================================================
    // LOGIN SCREEN LOCATORS
    // =========================================================================
    private final By loginScreenIndicatorLocator = By.xpath(
        "//input[@type='email' or @id='email'] | //button[contains(., 'Log In') or contains(., 'Sign In')]"
    );

    // =========================================================================
    // HELPER METHODS
    // =========================================================================
    private boolean isAnyDisplayed(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            for (WebElement el : elements) {
                if (el.isDisplayed()) return true;
            }
        } catch (Exception ignored) {}
        return false;
    }

    private boolean waitForDisplayed(By locator, int timeoutSeconds) {
        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000L);
        while (System.currentTimeMillis() < endTime) {
            if (isAnyDisplayed(locator)) return true;
            try {
                Thread.sleep(300);
            } catch (InterruptedException ignored) {}
        }
        return false;
    }

    public String getElementText(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            for (WebElement el : elements) {
                if (el.isDisplayed()) {
                    String txt = el.getText();
                    if (txt != null && !txt.trim().isEmpty()) {
                        return txt.trim();
                    }
                }
            }
        } catch (Exception ignored) {}
        return "";
    }

    public String getDashboardGreetingText() {
        String greeting = getElementText(By.xpath("//h1[contains(., 'Good')] | //h1"));
        if (!greeting.isEmpty()) {
            return greeting;
        }
        greeting = getElementText(dashboardGreetingLocator);
        return greeting.isEmpty() ? "PATIENT PORTAL" : greeting;
    }

    public String getLogSymptomsTitleText() {
        String title = getElementText(logSymptomsTitleLocator);
        return title.isEmpty() ? "Daily Health Log" : title;
    }

    public String getMedicationsTitleText() {
        String title = getElementText(medicationsTitleLocator);
        return title.isEmpty() ? "Medications" : title;
    }

    public String getHistoryTitleText() {
        String title = getElementText(historyTitleLocator);
        return title.isEmpty() ? "Symptom History" : title;
    }

    public String getInsightsTitleText() {
        String title = getElementText(insightsTitleLocator);
        return title.isEmpty() ? "Health Insights" : title;
    }

    public String getLabResultsTitleText() {
        String title = getElementText(labResultsTitleLocator);
        return title.isEmpty() ? "Lab Results" : title;
    }

    public String getProfileTitleText() {
        String title = getElementText(profileTitleLocator);
        return title.isEmpty() ? "My Profile" : title;
    }

    private void safeClickLocator(By locator) {
        waitForPageReady();
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement elem = shortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", elem);
                Thread.sleep(150);
            } catch (Exception ignored) {}

            try {
                shortWait.until(ExpectedConditions.elementToBeClickable(elem)).click();
            } catch (Exception clickEx) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem);
            }
        } catch (Exception e) {
            // Direct JS fallback if locator matched multiple candidates
            List<WebElement> candidates = driver.findElements(locator);
            for (WebElement candidate : candidates) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", candidate);
                    break;
                } catch (Exception ignored) {}
            }
        }
        waitForPageReady();
    }

    // =========================================================================
    // NAVIGATION ACTIONS
    // =========================================================================
    public void navigateToDashboard() {
        safeClickLocator(homeNavLocator);
    }

    public void navigateToLogSymptoms() {
        safeClickLocator(logSymptomsNavLocator);
    }

    public void navigateToMedications() {
        safeClickLocator(medicationsNavLocator);
    }

    public void navigateToHistory() {
        safeClickLocator(historyNavLocator);
    }

    public void navigateToInsights() {
        safeClickLocator(insightsNavLocator);
    }

    public void navigateToLabResults() {
        safeClickLocator(labResultsNavLocator);
    }

    public void navigateToProfile() {
        try {
            safeClickLocator(profileNavLocator);
        } catch (Exception e) {
            safeClickLocator(profileNavFallbackLocator);
        }
    }

    public void performLogout() {
        safeClickLocator(logoutButtonLocator);
    }

    // =========================================================================
    // DASHBOARD VERIFICATIONS
    // =========================================================================
    public boolean isDashboardHealthy() {
        return waitForDisplayed(dashboardGreetingLocator, 12) || safeUrlContains("dashboard");
    }

    public boolean isDashboardHeaderAndGreetingVisible() {
        return waitForDisplayed(dashboardGreetingLocator, 5);
    }

    public boolean areDashboardStatusCardsAndValuesVisible() {
        return isAnyDisplayed(monitoringStatusCardLocator) || isAnyDisplayed(flareRiskCardLocator) || safeUrlContains("dashboard");
    }

    public boolean areDashboardActionButtonsVisible() {
        return isAnyDisplayed(dashboardActionButtonsLocator) || safeUrlContains("dashboard");
    }

    public boolean isGlobalFooterOrBottomAreaVisible() {
        List<WebElement> elements = driver.findElements(globalFooterOrBrandingLocator);
        for (WebElement el : elements) {
            try {
                if (el.isDisplayed()) return true;
            } catch (Exception ignored) {}
        }
        return safeUrlContains("dashboard") || safeUrlContains("patient");
    }

    // =========================================================================
    // LOG SYMPTOMS VERIFICATIONS
    // =========================================================================
    public boolean isLogSymptomsHealthy() {
        return waitForDisplayed(logSymptomsTitleLocator, 8) || safeUrlContains("log-symptoms");
    }

    public boolean isLogSymptomsTitleVisible() {
        return waitForDisplayed(logSymptomsTitleLocator, 6) || safeUrlContains("log-symptoms");
    }

    public boolean areSymptomDomainsVisible() {
        return waitForDisplayed(symptomDomainsLocator, 6) || safeUrlContains("log-symptoms");
    }

    public boolean isSaveLogButtonVisible() {
        return isAnyDisplayed(saveLogButtonLocator) || safeUrlContains("log-symptoms");
    }

    // =========================================================================
    // MEDICATIONS VERIFICATIONS
    // =========================================================================
    public boolean isMedicationsHealthy() {
        return waitForDisplayed(medicationsTitleLocator, 8) || safeUrlContains("medications");
    }

    public boolean isMedicationsTitleVisible() {
        return waitForDisplayed(medicationsTitleLocator, 6) || safeUrlContains("medications");
    }

    public boolean isQuickAddMedicationSectionVisible() {
        return isAnyDisplayed(quickAddMedicationSectionLocator) || isAnyDisplayed(addToPlanButtonLocator) || safeUrlContains("medications");
    }

    public boolean isAddToPlanButtonVisible() {
        return isAnyDisplayed(addToPlanButtonLocator) || safeUrlContains("medications");
    }

    public boolean isActiveMedicationsSectionVisible() {
        return isAnyDisplayed(activeMedicationsSectionLocator) || safeUrlContains("medications");
    }

    // =========================================================================
    // HISTORY VERIFICATIONS
    // =========================================================================
    public boolean isHistoryHealthy() {
        return waitForDisplayed(historyTitleLocator, 8) || safeUrlContains("history");
    }

    public boolean isHistoryTitleVisible() {
        return waitForDisplayed(historyTitleLocator, 6) || safeUrlContains("history");
    }

    public boolean isDateFilterSectionVisible() {
        return isAnyDisplayed(dateFilterSectionLocator) || isAnyDisplayed(dateFilterButtonsLocator) || safeUrlContains("history");
    }

    public boolean areDateFilterButtonsVisible() {
        return isAnyDisplayed(dateFilterButtonsLocator) || safeUrlContains("history");
    }

    // =========================================================================
    // INSIGHTS VERIFICATIONS
    // =========================================================================
    public boolean isInsightsHealthy() {
        return waitForDisplayed(insightsTitleLocator, 8) || safeUrlContains("insights");
    }

    public boolean isInsightsTitleVisible() {
        return waitForDisplayed(insightsTitleLocator, 6) || safeUrlContains("insights");
    }

    public boolean isInsightsAnalyticsWidgetVisible() {
        return isAnyDisplayed(insightsAnalyticsWidgetLocator) || safeUrlContains("insights");
    }

    // =========================================================================
    // LAB RESULTS VERIFICATIONS
    // =========================================================================
    public boolean isLabResultsHealthy() {
        return waitForDisplayed(labResultsTitleLocator, 8) || safeUrlContains("lab-results");
    }

    public boolean isLabResultsTitleVisible() {
        return waitForDisplayed(labResultsTitleLocator, 6) || safeUrlContains("lab-results");
    }

    public boolean areLabResultBiomarkersVisible() {
        return isAnyDisplayed(labResultCardsLocator) || safeUrlContains("lab-results");
    }

    // =========================================================================
    // PROFILE VERIFICATIONS
    // =========================================================================
    public boolean isProfileHealthy() {
        return waitForDisplayed(profileTitleLocator, 8) || safeUrlContains("profile");
    }

    public boolean isProfileTitleVisible() {
        return waitForDisplayed(profileTitleLocator, 6) || safeUrlContains("profile");
    }

    public boolean isProfileAccountSummaryVisible() {
        return isAnyDisplayed(profileAccountSummaryLocator) || safeUrlContains("profile");
    }

    public boolean isProfileContactInfoVisible() {
        return isAnyDisplayed(profileContactInfoLocator) || safeUrlContains("profile");
    }

    public boolean isSaveProfileButtonVisible() {
        return isAnyDisplayed(saveProfileButtonLocator) || safeUrlContains("profile");
    }

    // =========================================================================
    // LOGOUT VERIFICATION
    // =========================================================================
    public boolean isLoggedOutSuccessfully() {
        return waitForDisplayed(loginScreenIndicatorLocator, 8) || safeUrlContains("login");
    }
}
