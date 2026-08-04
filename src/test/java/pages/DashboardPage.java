package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage {

    @FindBy(xpath = "//h1[contains(text(), 'Good')] | //*[contains(text(), 'DASHBOARD')] | //*[contains(text(), 'Patient')]")
    private WebElement greetingHeader;

    @FindBy(xpath = "//*[contains(@href, '/dashboard') or contains(text(), 'Home')]")
    private WebElement homeNavLink;

    @FindBy(xpath = "//*[contains(@href, '/log-symptoms') or contains(text(), 'Log Symptoms') or contains(text(), 'Log symptoms')]")
    private WebElement logSymptomsNavLink;

    @FindBy(xpath = "//*[contains(@href, '/medications') or contains(text(), 'Medications')]")
    private WebElement medicationsNavLink;

    @FindBy(xpath = "//*[contains(@href, '/lab-results') or contains(text(), 'Lab Results')]")
    private WebElement labResultsNavLink;

    @FindBy(xpath = "//*[contains(@href, '/history') or contains(text(), 'History')]")
    private WebElement historyNavLink;

    @FindBy(xpath = "//*[contains(@href, '/insights') or contains(text(), 'Insights')]")
    private WebElement insightsNavLink;

    @FindBy(xpath = "//button[contains(text(), 'Log Out')] | //a[contains(text(), 'Log Out')] | //*[contains(text(), 'Log Out')]")
    private WebElement logoutButton;

    @FindBy(xpath = "//a[contains(text(), 'Log symptoms')] | //button[contains(text(), 'Log symptoms')]")
    private WebElement logSymptomsHeaderButton;

    @FindBy(xpath = "//a[contains(text(), \"Log today's symptoms\")] | //button[contains(text(), \"Log today's symptoms\")]")
    private WebElement logTodaysSymptomsCardButton;

    @FindBy(xpath = "//a[contains(text(), 'Start Assessment')] | //button[contains(text(), 'Start Assessment')]")
    private WebElement startAssessmentButton;

    @FindBy(xpath = "//*[contains(text(), 'YOUR MONITORING') or contains(text(), 'On Track')]")
    private WebElement monitoringStatusCard;

    @FindBy(xpath = "//*[contains(text(), 'Flare Risk') or contains(text(), 'Predicted Risk')]")
    private WebElement flareRiskCard;

    public boolean isDashboardLoaded() {
        try {
            waitForVisibility(greetingHeader);
            return greetingHeader.isDisplayed() || driver.getCurrentUrl().contains("dashboard");
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("dashboard");
        }
    }

    public String getGreetingText() {
        try {
            return getText(greetingHeader);
        } catch (Exception e) {
            return "";
        }
    }

    public void clickHomeNav() {
        click(homeNavLink);
    }

    public void clickLogSymptomsNav() {
        click(logSymptomsNavLink);
    }

    public void clickMedicationsNav() {
        click(medicationsNavLink);
    }

    public void clickLabResultsNav() {
        click(labResultsNavLink);
    }

    public void clickHistoryNav() {
        click(historyNavLink);
    }

    public void clickInsightsNav() {
        click(insightsNavLink);
    }

    public void clickLogout() {
        click(logoutButton);
    }

    public void clickLogSymptomsHeaderButton() {
        click(logSymptomsHeaderButton);
    }

    public void clickLogTodaysSymptomsCardButton() {
        click(logTodaysSymptomsCardButton);
    }

    public void clickStartAssessment() {
        click(startAssessmentButton);
    }

    public boolean isMonitoringStatusDisplayed() {
        try {
            waitForVisibility(monitoringStatusCard);
            return monitoringStatusCard.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFlareRiskCardDisplayed() {
        try {
            waitForVisibility(flareRiskCard);
            return flareRiskCard.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getTodaysSymptomsSectionText() {
        try {
            WebElement section = driver.findElement(org.openqa.selenium.By.xpath("//*[contains(text(), \"Today's Symptoms\")]/ancestor::div[contains(@class, 'card') or contains(@class, 'rounded') or contains(@class, 'border') or contains(@class, 'bg-') or contains(@class, 'p-')][1]"));
            return section.getText();
        } catch (Exception e) {
            try {
                return driver.findElement(org.openqa.selenium.By.xpath("//*[contains(text(), \"Today's Symptoms\")]/ancestor::div[2]")).getText();
            } catch (Exception ex) {
                return "";
            }
        }
    }

    public boolean verifyTodaysSymptomsOnDashboard() {
        String text = getTodaysSymptomsSectionText();
        System.out.println("--------------------------------------------------");
        System.out.println("[DASHBOARD VERIFY] Today's Symptoms Card Content:\n" + text);
        System.out.println("--------------------------------------------------");
        boolean hasRespiratory = text.contains("Respiratory");
        boolean hasNasal = text.contains("Nasal");
        boolean hasSkin = text.contains("Skin");
        return (hasRespiratory && hasNasal && hasSkin);
    }

    public boolean verifyTodaysSymptomsAndRiskOnDashboard() {
        String text = getTodaysSymptomsSectionText();
        System.out.println("==================================================");
        System.out.println("[DASHBOARD VERIFY] Full Symptoms, Scores, Risk & Severity Card Breakdown:");
        System.out.println(text);
        System.out.println("==================================================");

        boolean hasRespiratory = text.contains("Respiratory");
        boolean hasNasal = text.contains("Nasal");
        boolean hasSkin = text.contains("Skin");
        boolean hasOverallRisk = text.contains("Overall Risk") || text.contains("/10");
        boolean hasSeverity = text.contains("Severity") || text.contains("Low") || text.contains("Moderate") || text.contains("High") || text.contains("controlled") || text.contains("Monitor");

        System.out.println("[CHECK] Respiratory section score present: " + hasRespiratory);
        System.out.println("[CHECK] Nasal section score present: " + hasNasal);
        System.out.println("[CHECK] Skin section score present: " + hasSkin);
        System.out.println("[CHECK] Overall Risk card present: " + hasOverallRisk);
        System.out.println("[CHECK] Severity card present: " + hasSeverity);

        return (hasRespiratory && hasNasal && hasSkin && hasOverallRisk && hasSeverity);
    }
}
