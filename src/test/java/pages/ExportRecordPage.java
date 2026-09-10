package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ExportRecordPage extends BasePage {

    By identityPasswordInput = By.xpath("//input[contains(@placeholder,'password') or contains(@placeholder,'Password')]");
    By confirmPasswordBtn = By.xpath("//button[contains(.,'Confirm Password')]");
    By cancelReturnBtn = By.xpath("//button[contains(.,'Cancel and Return')]");
    By identityVerifiedToast = By.xpath("//*[contains(.,'Identity verified successfully')]");

    By exportPageHeader = By.xpath("//*[contains(.,'Export My Record')]");
    By fullRecordScopeCard = By.xpath("//*[contains(.,'Full Record')]");
    By symptomLogsScopeCard = By.xpath("//*[contains(.,'Symptom Logs')]");
    By allTimeFilterBtn = By.xpath("//button[contains(.,'All Time')]");

    // FIXED: try multiple likely texts, scoped to <button> tag only (not divs/spans/nav)
    By requestExportBtn = By.xpath(
        "//button[normalize-space()='Export My Record'" +
        " or normalize-space()='Request Export'" +
        " or normalize-space()='Generate Export'" +
        " or normalize-space()='Submit Request']"
    );

    By exportQueuedModalHeader = By.xpath("//*[contains(.,'Export queued')]");

    // New: confirmation page buttons
    By returnToProfileBtn = By.xpath("//button[contains(.,'Return to Profile')] | //a[contains(.,'Return to Profile')]");
    By viewExportHistoryBtn = By.xpath("//button[contains(.,'Export History')] | //a[contains(.,'Export History')]");

    public boolean isExportPageLoaded() {
        return waitForVisible(exportPageHeader).isDisplayed();
    }

    public boolean isIdentityModalDisplayed() {
        return waitForVisible(identityPasswordInput).isDisplayed();
    }

    public void enterIdentityPassword(String password) {
        WebElement field = waitForVisible(identityPasswordInput);
        field.clear();
        field.sendKeys(password);
    }

    public void clickConfirmPassword() {
        click(waitForVisible(confirmPasswordBtn));
    }

    public boolean isIdentityVerifiedToastDisplayed() {
        return waitForVisible(identityVerifiedToast).isDisplayed();
    }

    public void selectFullRecordScope() {
        click(waitForVisible(fullRecordScopeCard));
    }

    public void clickRequestExport() {
        click(waitForVisible(requestExportBtn));
    }

    public boolean isExportQueuedModalDisplayed() {
        return waitForVisible(exportQueuedModalHeader).isDisplayed();
    }

    public void clickReturnToProfile() {
        click(waitForVisible(returnToProfileBtn));
    }

    public void clickViewExportHistory() {
        click(waitForVisible(viewExportHistoryBtn));
    }

    public boolean isProfilePageLoaded() {
        // reuse/adjust with your actual Profile page header locator
        return waitForVisible(By.xpath("//*[contains(.,'Profile')]")).isDisplayed();
    }

    private WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}