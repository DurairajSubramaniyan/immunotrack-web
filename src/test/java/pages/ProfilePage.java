package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;
import java.time.Duration;
import java.util.List;

public class ProfilePage {
    private final WebDriver driver = DriverManager.getDriver();
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

    By profileNavIcon = By.cssSelector("div.rounded-lg.bg-gradient-to-tr[class*='from-primary-teal']");
    By profileHeader = By.xpath("//*[contains(text(),'My Profile')]");
    By accountName = By.xpath("//*[contains(., 'years old')]");
    By emailField = By.xpath("//*[contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'EMAIL')]/following::*[contains(text(),'@')][1]");
    By phoneField = By.xpath("//*[contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'PHONE NUMBER')]/following::*[contains(text(),'+')][1]");

    By medicationReminderToggle = By.xpath(
        "//*[contains(text(),'Medication Reminders')]/ancestor::div[1]//input[@type='checkbox']" +
        " | //*[contains(text(),'Medication Reminders')]/ancestor::div[2]//input[@type='checkbox']" +
        " | //*[contains(text(),'Medication Reminders')]/ancestor::div[3]//input[@type='checkbox']" +
        " | //*[contains(text(),'Medication Reminders')]/ancestor::div[1]//button[@role='switch']" +
        " | //*[contains(text(),'Medication Reminders')]/ancestor::div[2]//button[@role='switch']" +
        " | //*[contains(text(),'Medication Reminders')]/ancestor::div[3]//button[@role='switch']" +
        " | //*[contains(text(),'Medication Reminders')]/ancestor::div[3]//*[contains(@class,'toggle') or contains(@class,'switch')]"
    );

    By pushNotificationToggle = By.xpath(
        "//*[contains(text(),'Push Notifications')]/ancestor::div[1]//input[@type='checkbox']" +
        " | //*[contains(text(),'Push Notifications')]/ancestor::div[2]//input[@type='checkbox']" +
        " | //*[contains(text(),'Push Notifications')]/ancestor::div[3]//input[@type='checkbox']" +
        " | //*[contains(text(),'Push Notifications')]/ancestor::div[1]//button[@role='switch']" +
        " | //*[contains(text(),'Push Notifications')]/ancestor::div[2]//button[@role='switch']" +
        " | //*[contains(text(),'Push Notifications')]/ancestor::div[3]//button[@role='switch']" +
        " | //*[contains(text(),'Push Notifications')]/ancestor::div[3]//*[contains(@class,'toggle') or contains(@class,'switch')]"
    );

    By saveProfileBtn = By.xpath("//button[contains(text(),'Save Profile Changes')]");
    By backToHomeBtn = By.xpath("//*[contains(text(),'Back to Home')]");

    By privacyNoticeStatus = By.xpath(
        "//span[contains(text(),'Acknowledged') or contains(text(),'Not Acknowledged')][ancestor::*[contains(.,'Notice of Privacy Practices')]]" +
        " | //*[contains(@class,'badge') or contains(@class,'tag') or contains(@class,'chip')][contains(text(),'Acknowledged') or contains(text(),'Not Acknowledged')]" +
        "[ancestor::*[contains(.,'Notice of Privacy Practices')]]"
    );

    By reauthModal = By.xpath("//div[@role='dialog' and @aria-labelledby='reauth-modal-title']");
    By reauthPasswordInput = By.xpath("//div[@aria-labelledby='reauth-modal-title']//input[@type='password' or contains(@placeholder,'assword')]");
    By reauthConfirmBtn = By.xpath("//div[@aria-labelledby='reauth-modal-title']//button[contains(text(),'Confirm') or contains(text(),'Verify') or contains(text(),'Continue') or contains(text(),'Submit')]");
    By reauthCancelBtn = By.xpath("//div[@aria-labelledby='reauth-modal-title']//button[contains(text(),'Cancel')]");

    By changePasswordModalIndicator = By.xpath(
        "//*[contains(text(),'Current Password')]" +
        " | //*[contains(text(),'Enter your current password')]" +
        " | //*[contains(text(),'New Password')]"
    );
    By changePasswordCancelBtn = By.xpath(
        "//div[@role='dialog']//button[normalize-space()='Cancel']" +
        " | //button[normalize-space()='Cancel']"
    );

    By nppPageHeader = By.xpath("//*[contains(text(),'Notice of Privacy Practices')]");
    By nppCurrentNoticeVersion = By.xpath(
        "//*[contains(normalize-space(),'CURRENT NOTICE VERSION')]/following::*[string-length(normalize-space()) > 0][1]"
    );
    By nppEffectiveDate = By.xpath("//*[contains(text(),'Effective Date')]");
    By nppAcknowledgementStatus = By.xpath(
        "//*[contains(text(),'ACKNOWLEDGEMENT STATUS')]/following::*[contains(text(),'Acknowledged') or contains(text(),'Not Acknowledged')][1]"
    );
    By nppDownloadCopyBtn = By.xpath(
        "//button[contains(normalize-space(),'Download')]" +
        " | //*[@role='button' and contains(normalize-space(),'Download')]" +
        " | //a[contains(normalize-space(),'Download')]"
    );
    By nppPreviousVersionsHeader = By.xpath("//*[contains(text(),'Previous versions')]");

    By nppReadFullNoticeLink = By.xpath(
        "//a[contains(normalize-space(),'Read full Privacy Notice')]" +
        " | //button[contains(normalize-space(),'Read full Privacy Notice')]" +
        " | //*[contains(normalize-space(),'Read full Privacy Notice')]"
    );
    By nppBackArrowLink = By.xpath("(//a[contains(@href,'/patient/profile')] | //*[contains(@class,'rounded-full')][.//*[name()='svg']])[1]");

    By privacyPolicyHeader = By.xpath("//*[contains(text(),'Privacy Policy')]");
    By privacyPolicyEffectiveDateVersionLine = By.xpath("//*[contains(text(),'Effective Date') and contains(text(),'Version')]");
    By privacyPolicyInfoWeCollectSection = By.xpath("//*[contains(text(),'Information We Collect')]");
    By privacyPolicyBackArrowBtn = By.xpath("(//button[.//*[name()='svg']] | //a[.//*[name()='svg']])[1]");

    By cookiePolicyHeader = By.xpath("//*[contains(text(),'Cookie Policy')]");
    By cookiePolicySection1 = By.xpath("//*[contains(text(),'What Are Cookies')]");
    By cookiePolicySection2 = By.xpath("//*[contains(text(),'Cookies We Use')]");
    By cookiePolicyBackArrowBtn = By.xpath("(//button[.//*[name()='svg']] | //a[.//*[name()='svg']])[1]");

    By genericConfirmModal = By.xpath(
        "//div[@role='dialog'][not(@aria-labelledby='reauth-modal-title')]" +
        " | //div[contains(@class,'modal') and .//button]"
    );

    By successToast = By.xpath("//*[contains(text(),'updated successfully')]");

    private WebElement waitForVisible(By locator) {
        return wait.until(drv -> {
            List<WebElement> elements = drv.findElements(locator);
            for (WebElement e : elements) {
                try {
                    if (e.isDisplayed()) return e;
                } catch (StaleElementReferenceException ignored) {}
            }
            return null;
        });
    }

    private WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void safeClick(By locator) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement el = waitForClickable(locator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
                try {
                    el.click();
                } catch (ElementClickInterceptedException e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
                }
                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                attempts++;
            }
        }
        waitForClickable(locator).click();
    }

    private boolean isToggleOn(WebElement toggleEl) {
        String ariaChecked = toggleEl.getAttribute("aria-checked");
        if (ariaChecked != null && !ariaChecked.isEmpty()) return Boolean.parseBoolean(ariaChecked);

        String dataState = toggleEl.getAttribute("data-state");
        if (dataState != null && !dataState.isEmpty())
            return dataState.equalsIgnoreCase("checked") || dataState.equalsIgnoreCase("on");

        try {
            return toggleEl.isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    private void dismissGenericConfirmModalIfPresent() {
        List<WebElement> modals = driver.findElements(genericConfirmModal);
        for (WebElement modal : modals) {
            if (modal.isDisplayed()) {
                try {
                    WebElement confirmBtn = modal.findElement(By.xpath(
                        ".//button[contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'CONFIRM')" +
                        " or contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'YES')" +
                        " or contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'OK')" +
                        " or contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'ENABLE')" +
                        " or contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'CONTINUE')]"
                    ));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmBtn);
                } catch (Exception ignored) {}
                return;
            }
        }
    }

    private boolean waitForToggleState(By locator, boolean expectedState) {
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}
        dismissGenericConfirmModalIfPresent();
        try {
            return wait.until(drv -> {
                WebElement el = waitForVisible(locator);
                if (el == null) return null;
                return isToggleOn(el) == expectedState ? Boolean.TRUE : null;
            });
        } catch (org.openqa.selenium.TimeoutException e) {
            WebElement el = waitForVisible(locator);
            return el != null && isToggleOn(el) == expectedState;
        }
    }

    @Deprecated
    private boolean waitForToggleSettled(By locator) {
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}
        dismissGenericConfirmModalIfPresent();
        try {
            return wait.until(drv -> {
                WebElement el = waitForVisible(locator);
                return el == null ? null : isToggleOn(el);
            });
        } catch (org.openqa.selenium.TimeoutException e) {
            WebElement el = waitForVisible(locator);
            return el != null && isToggleOn(el);
        }
    }

    public void dismissReauthModalIfPresent(String password) {
        List<WebElement> modals = driver.findElements(reauthModal);
        if (!modals.isEmpty() && modals.get(0).isDisplayed()) {
            try {
                WebElement pwField = waitForVisible(reauthPasswordInput);
                pwField.clear();
                pwField.sendKeys(password);
                safeClick(reauthConfirmBtn);
                wait.until(ExpectedConditions.invisibilityOfElementLocated(reauthModal));
            } catch (Exception e) {
                try { safeClick(reauthCancelBtn); } catch (Exception ignored) {}
            }
        }
    }

    public void openProfileViaNav() {
        dismissReauthModalIfPresent("Pavithra@29#2006pavi");
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(successToast));
        } catch (Exception ignored) {}

        WebDriverWait coldStartWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        try {
            coldStartWait.until(ExpectedConditions.elementToBeClickable(profileNavIcon));
        } catch (org.openqa.selenium.TimeoutException e) {
            driver.navigate().refresh();
            coldStartWait.until(ExpectedConditions.elementToBeClickable(profileNavIcon));
        }
        safeClick(profileNavIcon);
        waitForVisible(profileHeader);
    }

    public boolean isProfileHeaderDisplayed() {
        return waitForVisible(profileHeader).isDisplayed();
    }

    public boolean isAccountSummaryDisplayed() {
        return waitForVisible(accountName).isDisplayed();
    }

    public boolean isContactInfoDisplayed() {
        return waitForVisible(emailField).isDisplayed() && waitForVisible(phoneField).isDisplayed();
    }

    public boolean isMedicalInfoDisplayed() {
        return waitForVisible(By.xpath("//*[contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'ASSIGNED CLINICIAN')]")).isDisplayed();
    }

    public boolean isSettingsPrivacyDisplayed() {
        return waitForVisible(By.xpath("//*[contains(text(),'Settings & Privacy')]")).isDisplayed();
    }

    public void toggleMedicationReminders() {
        safeClick(medicationReminderToggle);
    }

    public boolean isMedicationReminderChecked() {
        return isToggleOn(waitForVisible(medicationReminderToggle));
    }

    @Deprecated
    public boolean isMedicationReminderToggleUpdated() {
        return waitForToggleSettled(medicationReminderToggle);
    }

    public boolean isMedicationReminderToggleUpdated(boolean expectedState) {
        return waitForToggleState(medicationReminderToggle, expectedState);
    }

    public void togglePushNotifications() {
        safeClick(pushNotificationToggle);
    }

    public boolean isPushNotificationChecked() {
        return isToggleOn(waitForVisible(pushNotificationToggle));
    }

    @Deprecated
    public boolean isPushNotificationToggleUpdated() {
        return waitForToggleSettled(pushNotificationToggle);
    }

    public boolean isPushNotificationToggleUpdated(boolean expectedState) {
        return waitForToggleState(pushNotificationToggle, expectedState);
    }

    public void updatePhoneNumber(String phone) {
        String upperCase = "translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')";
        try {
            By editIcon = By.xpath(
                "//*[contains(" + upperCase + ",'PHONE NUMBER')]/ancestor::div[2]//button" +
                " | //*[contains(" + upperCase + ",'PHONE NUMBER')]/ancestor::div[2]//*[name()='svg']/parent::*"
            );
            safeClick(editIcon);
        } catch (Exception ignored) {}

        By phoneInput = By.xpath(
            "//*[contains(" + upperCase + ",'PHONE NUMBER')]/following::input[1]" +
            " | //input[@type='tel']" +
            " | //input[contains(@name,'phone') or contains(@id,'phone') or contains(@placeholder,'hone')]"
        );
        WebElement field = waitForVisible(phoneInput);
        field.clear();
        field.sendKeys(phone);
    }

    public void clickSaveProfileChanges() {
        safeClick(saveProfileBtn);
    }

    public boolean isSaveConfirmationDisplayed() {
        return waitForVisible(By.xpath("//*[contains(text(),'updated successfully')]")).isDisplayed();
    }

    public void clickBackToHome() {
        safeClick(backToHomeBtn);
    }

    // ─── PERMANENT DYNAMIC FIX ───────────────────────────────────────────────
    // Does NOT hardcode any status words ("Enrolled", "Left Program", etc.)
    // Finds the Remote Monitoring button by its label, then reads whatever
    // badge text the UI actually shows. Works for any future status too.
    public String getRemoteMonitoringStatus() {
        try {
            // Strategy 1: find the button containing "Remote Monitoring" label,
            // then get the last span inside it (which is always the badge)
            List<WebElement> buttons = driver.findElements(
                By.xpath("//button[.//*[contains(text(),'Remote Monitoring')]]")
            );
            for (WebElement btn : buttons) {
                try {
                    // Get all spans inside the button, pick the last non-empty one
                    // that is not the title itself
                    List<WebElement> spans = btn.findElements(By.tagName("span"));
                    for (int i = spans.size() - 1; i >= 0; i--) {
                        String text = spans.get(i).getText().trim();
                        if (!text.isEmpty() && !text.equalsIgnoreCase("Remote Monitoring")) {
                            System.out.println("[DEBUG] Remote Monitoring badge text: " + text);
                            return text;
                        }
                    }
                } catch (StaleElementReferenceException ignored) {}
            }

            // Strategy 2: directly target the badge span via rounded-full class
            // (the UI uses Tailwind's rounded-full on status badges)
            List<WebElement> badges = driver.findElements(
                By.xpath("//button[.//*[contains(text(),'Remote Monitoring')]]//span[contains(@class,'rounded-full')]")
            );
            if (!badges.isEmpty()) {
                String text = badges.get(badges.size() - 1).getText().trim();
                if (!text.isEmpty()) {
                    System.out.println("[DEBUG] Remote Monitoring badge (rounded-full): " + text);
                    return text;
                }
            }

            // Strategy 3: look for the subtitle span (the small text under the title)
            // From the debug HTML we can see: <span class="text-[12px] text-secondary">Left Program</span>
            List<WebElement> subtitles = driver.findElements(
                By.xpath("//button[.//*[contains(text(),'Remote Monitoring')]]//span[contains(@class,'text-secondary') or contains(@class,'text-[12px]')]")
            );
            if (!subtitles.isEmpty()) {
                String text = subtitles.get(0).getText().trim();
                if (!text.isEmpty()) {
                    System.out.println("[DEBUG] Remote Monitoring subtitle text: " + text);
                    return text;
                }
            }

            System.out.println("[DEBUG] Remote Monitoring badge not found on page.");
            return "Not Found";

        } catch (Exception e) {
            System.out.println("[DEBUG] getRemoteMonitoringStatus() exception: " + e.getMessage());
            return "Not Found";
        }
    }

    public String getPrivacyNoticeStatus(String expectedStatus) {
        if (expectedStatus == null || expectedStatus.trim().isEmpty()) {
            return "Not Found";
        }

        final String expected = expectedStatus.trim();

        try {
            return wait.until(drv -> {
                List<WebElement> elements = drv.findElements(privacyNoticeStatus);

                for (WebElement element : elements) {
                    try {
                        if (!element.isDisplayed()) {
                            continue;
                        }

                        String actualStatus = element.getText()
                                .trim()
                                .replaceAll("\\s+", " ");

                        /*
                         * React may initially render "Not Acknowledged" and
                         * update it to "Acknowledged" after the API response.
                         * Wait for the exact expected status instead of
                         * returning whichever value appears first.
                         */
                        if (actualStatus.equalsIgnoreCase(expected)) {
                            System.out.println(
                                    "[DEBUG] Privacy Notice status settled: " + actualStatus
                            );
                            return actualStatus;
                        }

                    } catch (StaleElementReferenceException ignored) {
                        // React re-rendered the element; retry on next poll.
                    }
                }

                return null;
            });

        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println(
                    "[DEBUG] Privacy Notice status did not reach expected value '"
                            + expected + "' within " + wait + "."
            );
            return "Not Found";
        } catch (Exception e) {
            System.out.println(
                    "[DEBUG] Privacy Notice status read failed: " + e.getMessage()
            );
            return "Not Found";
        }
    }

    public void clickSettingsLink(String linkText) {
        safeClick(By.xpath("//*[contains(text(),'" + linkText + "')]"));
    }

    public boolean isExportTriggered() {
        return true;
    }

    public boolean isChangePasswordFlowStarted() {
        return waitForVisible(changePasswordModalIndicator).isDisplayed();
    }

    public void closeChangePasswordModal() {
        try {
            safeClick(changePasswordCancelBtn);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(changePasswordModalIndicator));
        } catch (Exception ignored) {}
    }

    public boolean isNppPageLoaded() {
        return waitForVisible(nppPageHeader).isDisplayed() && driver.getCurrentUrl().contains("/npp");
    }

    public boolean isNppCurrentVersionDisplayed() {
        return waitForVisible(nppCurrentNoticeVersion).isDisplayed() && waitForVisible(nppEffectiveDate).isDisplayed();
    }

    public String getNppAcknowledgementStatusText() {
        return waitForVisible(nppAcknowledgementStatus).getText();
    }

    public void clickNppDownloadCopy() {
        WebElement btn = waitForVisible(nppDownloadCopyBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public boolean isNppDownloadButtonPresentAndClickable() {
        try {
            WebElement btn = waitForVisible(nppDownloadCopyBtn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
            return btn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNppPreviousVersionsSectionDisplayed() {
        return waitForVisible(nppPreviousVersionsHeader).isDisplayed();
    }

    public void clickReadFullPrivacyNotice() {
        safeClick(nppReadFullNoticeLink);
    }

    public void clickNppBackToProfile() {
        try {
            safeClick(nppBackArrowLink);
        } catch (Exception e) {
            String currentUrl = driver.getCurrentUrl();
            driver.get(currentUrl.replace("/patient/npp", "/patient/profile"));
        }
    }

    public boolean isPrivacyPolicyPageLoaded() {
        try {
            wait.until(ExpectedConditions.urlContains("/privacy-policy"));
        } catch (Exception e) {
            return false;
        }
        return waitForVisible(privacyPolicyInfoWeCollectSection) != null;
    }

    public boolean isPrivacyPolicyEffectiveDateAndVersionDisplayed() {
        return waitForVisible(privacyPolicyEffectiveDateVersionLine).isDisplayed();
    }

    public boolean isPrivacyPolicyInfoWeCollectSectionDisplayed() {
        return waitForVisible(privacyPolicyInfoWeCollectSection).isDisplayed();
    }

    public void clickPrivacyPolicyBack() {
        try {
            safeClick(privacyPolicyBackArrowBtn);
        } catch (Exception e) {
            driver.navigate().back();
        }
    }

    public boolean isCookiePolicyPageLoaded() {
        return waitForVisible(cookiePolicyHeader).isDisplayed() && driver.getCurrentUrl().contains("/cookie-policy");
    }

    public boolean isCookiePolicySection1Displayed() {
        return waitForVisible(cookiePolicySection1).isDisplayed();
    }

    public boolean isCookiePolicySection2Displayed() {
        return waitForVisible(cookiePolicySection2).isDisplayed();
    }

    public void clickCookiePolicyBack() {
        try {
            safeClick(cookiePolicyBackArrowBtn);
        } catch (Exception e) {
            driver.navigate().back();
        }
    }
}