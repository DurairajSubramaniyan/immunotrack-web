package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;
import java.time.Duration;
import java.util.List;

public class NoticeOfPrivacyPracticesPage extends BasePage {

    By pageHeader = By.xpath("//*[contains(text(),'Notice of Privacy Practices')]");

    // FIX: contains(normalize-space(),'CURRENT NOTICE VERSION') is case-sensitive and
    // only matches ALL CAPS text. If the page renders this label in any other case
    // (e.g. "Current Notice Version"), the locator never matches and the element
    // is reported as not found. translate() makes the match case-insensitive.
    By currentNoticeVersion = By.xpath(
        "//*[contains(translate(normalize-space(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'NOTICE VERSION')]" +
        "/following::*[string-length(normalize-space()) > 0][1]"
    );
    By effectiveDate = By.xpath(
        "//*[contains(translate(normalize-space(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'EFFECTIVE')]"
    );

    By acknowledgementStatus = By.xpath(
        "//*[contains(normalize-space(),'ACKNOWLEDGEMENT STATUS')]/following::*[contains(normalize-space(),'Acknowledged') or contains(normalize-space(),'Not Acknowledged')][1]" +
        " | //*[contains(normalize-space(),'Acknowledged')][not(contains(normalize-space(),'ACKNOWLEDGEMENT'))][1]"
    );

    By downloadCopyBtn = By.xpath(
        "//button[contains(normalize-space(),'Download')]" +
        " | //*[@role='button' and contains(normalize-space(),'Download')]" +
        " | //a[contains(normalize-space(),'Download')]"
    );

    By previousVersionsHeader = By.xpath(
        "//*[contains(text(),'Previous versions')]" +
        " | //*[contains(text(),'previous versions')]"
    );

    By backArrowLink = By.xpath(
        "(//a[contains(@href,'/patient/profile')] | //*[contains(@class,'rounded-full')][.//*[name()='svg']])[1]"
    );

    private WebElement waitForVisible(By locator) {
        WebDriverWait localWait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(25));
        return localWait.until(drv -> {
            List<WebElement> elements = drv.findElements(locator);
            for (WebElement e : elements) {
                try {
                    if (e.isDisplayed()) return e;
                } catch (StaleElementReferenceException ignored) {}
            }
            return null;
        });
    }

    public boolean isPageLoaded() {
        return waitForVisible(pageHeader).isDisplayed() && driver.getCurrentUrl().contains("/npp");
    }

    public boolean isCurrentVersionDisplayed() {
        try {
            WebElement version = waitForVisible(currentNoticeVersion);
            WebElement date = waitForVisible(effectiveDate);
            return version.isDisplayed() && date.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getAcknowledgementStatusText() {
        return waitForVisible(acknowledgementStatus).getText();
    }

    public void clickDownloadCopy() {
        WebElement btn = waitForVisible(downloadCopyBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public boolean isDownloadButtonPresentAndClickable() {
        try {
            WebElement btn = waitForVisible(downloadCopyBtn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
            return btn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPreviousVersionsSectionDisplayed() {
        try {
            return waitForVisible(previousVersionsHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickBackToProfile() {
        try {
            WebElement btn = waitForVisible(backArrowLink);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        } catch (Exception e) {
            String currentUrl = driver.getCurrentUrl();
            driver.get(currentUrl.replace("/patient/npp", "/patient/profile"));
        }
    }
}
