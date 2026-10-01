package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class LeftProgramPage extends BasePage {

    // =========================================================================
    // DASHBOARD / HOME PAGE - SUSPENDED MONITORING BANNER LOCATORS
    // =========================================================================

    @FindBy(xpath = "//*[contains(text(), 'MONITORING SUSPENDED')]")
    private WebElement monitoringSuspendedBadge;

    @FindBy(xpath = "//*[contains(text(), 'You’ve left your monitoring') or contains(text(), \"You've left your monitoring\")]")
    private WebElement youveLeftMonitoringHeadline;

    @FindBy(xpath = "//*[contains(text(), 'Your daily symptom data is no longer shared with your care team')]")
    private WebElement monitoringSuspendedDescription;

    @FindBy(xpath = "//button[contains(., 'Re-join Monitoring') or contains(., 'Re-join monitoring')] | //a[contains(., 'Re-join Monitoring') or contains(., 'Re-join monitoring')]")
    private WebElement rejoinMonitoringBannerBtn;

    // =========================================================================
    // PROFILE PAGE - SETTINGS & PRIVACY LOCATORS
    // =========================================================================

    @FindBy(xpath = "//*[contains(text(), 'Settings & Privacy') or contains(text(), 'Settings and Privacy')]")
    private WebElement settingsAndPrivacyHeader;

    @FindBy(xpath = "//button[.//*[contains(text(), 'Remote Monitoring')]] | //*[contains(text(), 'Remote Monitoring')]/ancestor::button[1] | //*[contains(text(), 'Remote Monitoring')]/ancestor::div[contains(@class,'cursor-pointer')][1]")
    private WebElement remoteMonitoringRow;

    // =========================================================================
    // REMOTE MONITORING OVERVIEW PAGE (/patient/remote-monitoring) LOCATORS
    // =========================================================================

    @FindBy(xpath = "//h1[contains(text(), 'Remote Monitoring')] | //h2[contains(text(), 'Remote Monitoring')] | //*[contains(@class, 'text-') and contains(text(), 'Remote Monitoring')]")
    private WebElement remoteMonitoringPageHeader;

    @FindBy(xpath = "//*[contains(text(), 'Remote Therapeutic Monitoring program')]")
    private WebElement remoteMonitoringSubtitle;

    @FindBy(xpath = "//*[contains(text(), 'Left Monitoring')]")
    private WebElement leftMonitoringCardTitle;

    @FindBy(xpath = "//*[contains(text(), 'You left the monitoring. Your daily symptom data is no longer shared with your care team')]")
    private WebElement leftMonitoringCardDescription;

    @FindBy(xpath = "//button[contains(., 'Re-join monitoring') or contains(., 'Re-join Monitoring')]")
    private WebElement rejoinMonitoringDetailBtn;

    @FindBy(xpath = "//*[contains(text(), 'ABOUT RTM')]")
    private WebElement aboutRtmHeader;

    @FindBy(xpath = "//*[contains(text(), 'How does it work?')]")
    private WebElement howDoesItWorkTitle;

    @FindBy(xpath = "//*[contains(text(), 'No Extra Hardware')]")
    private WebElement noExtraHardwareTitle;

    @FindBy(xpath = "//*[contains(text(), 'Cost and Coverage')]")
    private WebElement costAndCoverageTitle;

    @FindBy(xpath = "(//button[.//*[name()='svg']] | //a[.//*[name()='svg']] | //*[contains(@class,'cursor-pointer')][.//*[name()='svg']])[1]")
    private WebElement backArrowButton;

    // =========================================================================
    // DASHBOARD METHODS
    // =========================================================================

    public boolean isMonitoringSuspendedBannerDisplayed() {
        try {
            waitForPageReady();
            String pageText = driver.findElement(By.tagName("body")).getText();
            String upperPageText = pageText.toUpperCase();

            boolean badge = upperPageText.contains("MONITORING SUSPENDED")
                    || upperPageText.contains("SUSPENDED")
                    || !driver.findElements(By.xpath("//*[contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'MONITORING SUSPENDED') or contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'SUSPENDED')]")).isEmpty();

            boolean headline = upperPageText.contains("YOU’VE LEFT YOUR MONITORING")
                    || upperPageText.contains("YOU'VE LEFT YOUR MONITORING")
                    || !driver.findElements(By.xpath("//*[contains(text(), 'You’ve left your monitoring') or contains(text(), \"You've left your monitoring\")]")).isEmpty();

            boolean desc = pageText.contains("Your daily symptom data is no longer shared with your care team")
                    || upperPageText.contains("YOUR DAILY SYMPTOM DATA IS NO LONGER SHARED")
                    || !driver.findElements(By.xpath("//*[contains(text(), 'Your daily symptom data is no longer shared with your care team')]")).isEmpty();

            boolean btn = pageText.contains("Re-join Monitoring")
                    || pageText.contains("Re-join monitoring")
                    || upperPageText.contains("RE-JOIN MONITORING")
                    || !driver.findElements(By.xpath("//button[contains(., 'Re-join Monitoring') or contains(., 'Re-join monitoring')] | //a[contains(., 'Re-join Monitoring') or contains(., 'Re-join monitoring')]")).isEmpty();

            System.out.println("[LEFT PROGRAM VERIFY] Monitoring Suspended Badge: " + badge);
            System.out.println("[LEFT PROGRAM VERIFY] You've Left Monitoring Headline: " + headline);
            System.out.println("[LEFT PROGRAM VERIFY] Suspension Description: " + desc);
            System.out.println("[LEFT PROGRAM VERIFY] Re-join Monitoring Button: " + btn);

            return headline && desc && btn;
        } catch (Exception e) {
            System.err.println("[LEFT PROGRAM VERIFY] Exception checking suspended banner: " + e.getMessage());
            return false;
        }
    }

    public String getSuspendedBannerHeadline() {
        try {
            return getText(youveLeftMonitoringHeadline);
        } catch (Exception e) {
            return "";
        }
    }

    public String getSuspendedBannerDescription() {
        try {
            return getText(monitoringSuspendedDescription);
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isRejoinMonitoringBannerButtonDisplayed() {
        try {
            return rejoinMonitoringBannerBtn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if the 16-day progress bar or progress indicators exist on the page.
     * For a user who left the program, this MUST return false.
     */
    public boolean is16DaysProgressBarDisplayed() {
        try {
            waitForPageReady();
            String pageSource = driver.getPageSource();
            String bodyText = driver.findElement(By.tagName("body")).getText();

            // Look for "16 days logged", "/ 16 days", "16-day bar", progress tracker
            boolean has16DaysPattern = bodyText.matches("(?i).*[0-9]+\\s*/\\s*16\\s*days\\s*logged.*")
                    || bodyText.matches("(?i).*16\\s*days\\s*logged.*")
                    || bodyText.matches("(?i).*[0-9]+\\s*days\\s*left.*")
                    || bodyText.contains("YOUR MONITORING");

            List<WebElement> progressBars = driver.findElements(By.xpath(
                "//*[contains(text(), '16 days') or contains(text(), '/ 16')] | " +
                "//*[contains(@class, 'progress') and contains(., '16')] | " +
                "//div[@role='progressbar' and contains(., '16')]"
            ));

            boolean displayed = has16DaysPattern || (!progressBars.isEmpty() && progressBars.get(0).isDisplayed());
            System.out.println("[LEFT PROGRAM VERIFY] Is 16 Days Bar Displayed: " + displayed);
            return displayed;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickRejoinMonitoringOnBanner() {
        try {
            waitForPageReady();
            List<WebElement> btns = driver.findElements(By.xpath(
                "//button[contains(., 'Re-join Monitoring') or contains(., 'Re-join monitoring')] | " +
                "//a[contains(., 'Re-join Monitoring') or contains(., 'Re-join monitoring')] | " +
                "//*[contains(@class, 'cursor-pointer') and (contains(., 'Re-join Monitoring') or contains(., 'Re-join monitoring'))]"
            ));
            if (!btns.isEmpty()) {
                WebElement btn = btns.get(0);
                try {
                    click(btn);
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                }
            } else {
                click(rejoinMonitoringBannerBtn);
            }
            Thread.sleep(1500);
            waitForPageReady();
        } catch (Exception e) {
            System.err.println("Could not click Re-join Monitoring button on banner: " + e.getMessage());
        }
    }

    // =========================================================================
    // DAILY LOG / LOG SYMPTOMS METHODS
    // =========================================================================

    public boolean isDailyHealthLogHeaderDisplayed() {
        try {
            waitForPageReady();
            List<WebElement> headers = driver.findElements(By.xpath("//h1[contains(text(), 'Daily Health Log')] | //h2[contains(text(), 'Daily Health Log')] | //*[contains(text(), 'Daily Health Log')]"));
            return !headers.isEmpty() && headers.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // =========================================================================
    // PROFILE - SETTINGS & PRIVACY METHODS
    // =========================================================================

    public boolean isSettingsAndPrivacyHeaderDisplayed() {
        try {
            waitForPageReady();
            List<WebElement> headers = driver.findElements(By.xpath("//*[contains(text(), 'Settings & Privacy') or contains(text(), 'Settings and Privacy')]"));
            return !headers.isEmpty() && headers.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifySettingsAndPrivacyAllOptionsDisplayed() {
        try {
            waitForPageReady();
            String pageText = driver.findElement(By.tagName("body")).getText();

            boolean medRem = pageText.contains("Medication Reminders");
            boolean pushNotif = pageText.contains("Push Notifications");
            boolean changePw = pageText.contains("Change Password");
            boolean remoteMon = pageText.contains("Remote Monitoring");
            boolean npp = pageText.contains("Notice of Privacy Practices");
            boolean exportRec = pageText.contains("Export My Record");
            boolean privSec = pageText.contains("Privacy & Security");
            boolean cookie = pageText.contains("Cookie Policy");

            System.out.println("--------------------------------------------------");
            System.out.println("[PROFILE SETTINGS VERIFY] Medication Reminders: " + medRem);
            System.out.println("[PROFILE SETTINGS VERIFY] Push Notifications: " + pushNotif);
            System.out.println("[PROFILE SETTINGS VERIFY] Change Password: " + changePw);
            System.out.println("[PROFILE SETTINGS VERIFY] Remote Monitoring: " + remoteMon);
            System.out.println("[PROFILE SETTINGS VERIFY] Notice of Privacy Practices: " + npp);
            System.out.println("[PROFILE SETTINGS VERIFY] Export My Record: " + exportRec);
            System.out.println("[PROFILE SETTINGS VERIFY] Privacy & Security: " + privSec);
            System.out.println("[PROFILE SETTINGS VERIFY] Cookie Policy: " + cookie);
            System.out.println("--------------------------------------------------");

            return medRem && pushNotif && changePw && remoteMon && npp && exportRec && privSec && cookie;
        } catch (Exception e) {
            return false;
        }
    }

    public String getRemoteMonitoringBadgeInProfile() {
        try {
            waitForPageReady();
            List<WebElement> badges = driver.findElements(By.xpath(
                "//button[.//*[contains(text(),'Remote Monitoring')]]//span[contains(text(), 'Left Program') or contains(@class,'rounded-full') or contains(@class,'text-secondary')]" +
                " | //*[contains(text(),'Remote Monitoring')]/following::*[contains(text(),'Left Program')][1]"
            ));
            for (WebElement badge : badges) {
                String text = badge.getText().trim();
                if (!text.isEmpty() && !text.equalsIgnoreCase("Remote Monitoring")) {
                    return text;
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public void clickRemoteMonitoringInProfile() {
        try {
            waitForPageReady();
            WebElement elem = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//*[contains(text(),'Remote Monitoring')]] | //*[contains(text(),'Remote Monitoring')]/ancestor::button[1] | //*[contains(text(),'Remote Monitoring')]")
            ));
            try {
                click(elem);
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem);
            }
            Thread.sleep(1500);
        } catch (Exception e) {
            System.err.println("Could not click Remote Monitoring in Profile: " + e.getMessage());
        }
    }

    // =========================================================================
    // REMOTE MONITORING OVERVIEW SCREEN (/patient/remote-monitoring) METHODS
    // =========================================================================

    public boolean isRemoteMonitoringPageLoaded() {
        try {
            waitForPageReady();
            return safeUrlContains("remote-monitoring") ||
                    !driver.findElements(By.xpath("//*[contains(text(), 'Remote Monitoring') and contains(text(), 'Program')] | //*[contains(text(), 'Left Monitoring')]")).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyRemoteMonitoringHeaders() {
        try {
            waitForPageReady();
            String pageText = driver.findElement(By.tagName("body")).getText();
            String upperPageText = pageText.toUpperCase();

            boolean titlePresent = upperPageText.contains("REMOTE MONITORING")
                    || !driver.findElements(By.xpath("//h1[contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'REMOTE MONITORING')] | //*[contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'REMOTE MONITORING')]")).isEmpty();
            boolean subtitlePresent = pageText.contains("Remote Therapeutic Monitoring program")
                    || upperPageText.contains("REMOTE THERAPEUTIC MONITORING PROGRAM")
                    || !driver.findElements(By.xpath("//*[contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'REMOTE THERAPEUTIC MONITORING')]")).isEmpty();
            boolean breadcrumbPresent = upperPageText.contains("OVERVIEW")
                    || upperPageText.contains("PATIENT PORTAL")
                    || !driver.findElements(By.xpath("//*[contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'OVERVIEW')]")).isEmpty();

            System.out.println("[RTM OVERVIEW VERIFY] Page Title Present: " + titlePresent);
            System.out.println("[RTM OVERVIEW VERIFY] Subtitle Present: " + subtitlePresent);
            System.out.println("[RTM OVERVIEW VERIFY] Breadcrumb Present: " + breadcrumbPresent);

            return titlePresent && subtitlePresent && breadcrumbPresent;
        } catch (Exception e) {
            return false;
        }
    }

    public String getInnermostText(By locator) {
        try {
            waitForPageReady();
            List<WebElement> elements = driver.findElements(locator);
            if (elements.isEmpty()) return "";
            WebElement best = null;
            int minLen = Integer.MAX_VALUE;
            for (WebElement el : elements) {
                try {
                    if (el.isDisplayed()) {
                        String t = el.getText().trim();
                        if (!t.isEmpty() && t.length() < minLen) {
                            minLen = t.length();
                            best = el;
                        }
                    }
                } catch (Exception ignored) {}
            }
            if (best == null) {
                for (WebElement el : elements) {
                    try {
                        String t = el.getText().trim();
                        if (!t.isEmpty() && t.length() < minLen) {
                            minLen = t.length();
                            best = el;
                        }
                    } catch (Exception ignored) {}
                }
            }
            return best != null ? best.getText().trim() : "";
        } catch (Exception e) {
            return "";
        }
    }

    public boolean verifyLeftMonitoringMainCard() {
        try {
            waitForPageReady();
            String title = getLeftMonitoringCardTitle();
            String desc = getLeftMonitoringCardDescription();
            String btn = getRejoinMonitoringButtonText();

            boolean titleValid = "Left Monitoring".equalsIgnoreCase(title);
            boolean descValid = desc.contains("You left the monitoring");
            boolean btnValid = btn.toLowerCase().contains("re-join monitoring");

            System.out.println("[RTM OVERVIEW VERIFY] Left Monitoring Card Title ('" + title + "'): " + titleValid);
            System.out.println("[RTM OVERVIEW VERIFY] Left Monitoring Description: " + descValid);
            System.out.println("[RTM OVERVIEW VERIFY] Re-join Monitoring Button ('" + btn + "'): " + btnValid);

            return titleValid && descValid && btnValid;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyAboutRtmCardAndSections() {
        try {
            waitForPageReady();
            String header = getAboutRtmHeaderText();
            String howItWorks = getHowItWorksTitle();
            String howItWorksContent = getHowItWorksContent();
            String noHardware = getNoExtraHardwareTitle();
            String noHardwareContent = getNoExtraHardwareContent();
            String costCoverage = getCostAndCoverageTitle();
            String costCoverageContent = getCostAndCoverageContent();

            boolean headerValid = "ABOUT RTM".equalsIgnoreCase(header);
            boolean howValid = "How does it work?".equalsIgnoreCase(howItWorks) && !howItWorksContent.isEmpty();
            boolean noHardValid = "No Extra Hardware".equalsIgnoreCase(noHardware) && !noHardwareContent.isEmpty();
            boolean costValid = "Cost and Coverage".equalsIgnoreCase(costCoverage) && !costCoverageContent.isEmpty();

            System.out.println("--------------------------------------------------");
            System.out.println("[RTM ABOUT VERIFY] Header 'ABOUT RTM': " + headerValid);
            System.out.println("[RTM ABOUT VERIFY] 'How does it work?': " + howValid);
            System.out.println("[RTM ABOUT VERIFY] 'No Extra Hardware': " + noHardValid);
            System.out.println("[RTM ABOUT VERIFY] 'Cost and Coverage': " + costValid);
            System.out.println("--------------------------------------------------");

            return headerValid && howValid && noHardValid && costValid;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickBackArrow() {
        try {
            waitForPageReady();
            By specificBackArrow = By.xpath(
                "//h1[contains(., 'Remote Monitoring')]/preceding::button[1] | " +
                "//h1[contains(., 'Remote Monitoring')]/..//button | " +
                "//*[contains(text(), 'Remote Monitoring')]/preceding-sibling::button | " +
                "//*[contains(text(), 'Remote Monitoring')]/..//button[.//*[name()='svg']] | " +
                "//*[contains(text(), 'Remote Monitoring')]/preceding::*[contains(@class, 'cursor-pointer') and .//*[name()='svg']][1] | " +
                "//button[contains(@aria-label, 'back') or contains(@aria-label, 'Back')] | " +
                "//a[contains(@href, '/profile')]"
            );

            List<WebElement> backButtons = driver.findElements(specificBackArrow);
            if (!backButtons.isEmpty()) {
                WebElement btn = backButtons.get(0);
                try {
                    click(btn);
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                }
            } else {
                driver.navigate().back();
            }
            Thread.sleep(1500);
            waitForPageReady();

            if (!driver.getCurrentUrl().contains("profile")) {
                driver.navigate().back();
                Thread.sleep(1500);
            }
        } catch (Exception e) {
            driver.navigate().back();
        }
    }

    public String getMonitoringSuspendedBadgeText() {
        return getInnermostText(By.xpath(
            "//*[contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'MONITORING SUSPENDED') or contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'SUSPENDED')]"
        ));
    }

    public String getRejoinBannerButtonText() {
        return getInnermostText(By.xpath(
            "//button[contains(., 'Re-join Monitoring') or contains(., 'Re-join monitoring')] | " +
            "//a[contains(., 'Re-join Monitoring') or contains(., 'Re-join monitoring')]"
        ));
    }

    public String getSettingsAndPrivacyHeaderText() {
        return getInnermostText(By.xpath("//h1[contains(., 'Settings & Privacy')] | //h2[contains(., 'Settings & Privacy')] | //*[contains(text(), 'Settings & Privacy')]"));
    }

    public String getSettingsAndPrivacyOptionText(String optionName) {
        return getInnermostText(By.xpath("//*[contains(text(), '" + optionName + "')]"));
    }

    public String getRemoteMonitoringPageTitle() {
        return getInnermostText(By.xpath(
            "//h1[contains(., 'Remote Monitoring')] | //h2[contains(., 'Remote Monitoring')] | //*[contains(@class, 'text-') and contains(text(), 'Remote Monitoring') and not(contains(text(), 'Left'))]"
        ));
    }

    public String getRemoteMonitoringSubtitle() {
        return getInnermostText(By.xpath("//*[contains(text(), 'Remote Therapeutic Monitoring program')]"));
    }

    public String getBreadcrumbText() {
        try {
            waitForPageReady();
            String text = getInnermostText(By.xpath(
                "//*[contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'PATIENT PORTAL') and contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'OVERVIEW')]"
            ));
            if (!text.isEmpty()) {
                return text.replaceAll("\\s+", " ").trim();
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public String getLeftMonitoringCardTitle() {
        return getInnermostText(By.xpath("//h2[contains(., 'Left Monitoring')] | //h3[contains(., 'Left Monitoring')] | //*[contains(text(), 'Left Monitoring')]"));
    }

    public String getLeftMonitoringCardDescription() {
        return getInnermostText(By.xpath(
            "//*[contains(text(), 'You left the monitoring') or contains(text(), 'Your daily symptom data is no longer shared')]"
        ));
    }

    public String getRejoinMonitoringButtonText() {
        return getInnermostText(By.xpath(
            "//button[contains(., 'Re-join monitoring') or contains(., 'Re-join Monitoring')]"
        ));
    }

    public String getAboutRtmHeaderText() {
        return getInnermostText(By.xpath(
            "//*[self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::p or self::span or self::div][contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'ABOUT RTM')]"
        ));
    }

    public String getHowItWorksTitle() {
        return getInnermostText(By.xpath(
            "//*[contains(text(), 'work?') or contains(text(), 'Work?') or contains(text(), 'How does it work')]"
        ));
    }

    public String getHowItWorksContent() {
        return getInnermostText(By.xpath(
            "//*[contains(text(), 'By simply logging your nasal') or contains(text(), 'detailed clinical tracking')]"
        ));
    }

    public String getNoExtraHardwareTitle() {
        return getInnermostText(By.xpath("//*[contains(text(), 'No Extra Hardware')]"));
    }

    public String getNoExtraHardwareContent() {
        return getInnermostText(By.xpath(
            "//*[contains(text(), 'No smartwatch or tracker required') or contains(text(), 'RTM relies entirely')]"
        ));
    }

    public String getCostAndCoverageTitle() {
        return getInnermostText(By.xpath("//*[contains(text(), 'Cost and Coverage')]"));
    }

    public String getCostAndCoverageContent() {
        return getInnermostText(By.xpath(
            "//*[contains(text(), 'Most insurance plans cover RTM') or contains(text(), 'copays and deductibles')]"
        ));
    }
}
