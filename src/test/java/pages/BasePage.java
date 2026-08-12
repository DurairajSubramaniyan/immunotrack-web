package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.DriverManager;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        int timeout = Integer.parseInt(ConfigReader.getProperty("timeout"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        PageFactory.initElements(driver, this);
    }

    protected void click(WebElement element) {
        try {
            java.util.List<WebElement> cookieBtns = driver.findElements(By.xpath("//*[contains(@class, 'cc-') or contains(@class, 'cookie')]//button | //button[contains(text(), 'Accept') or contains(text(), 'Got it') or contains(text(), 'Dismiss') or contains(text(), 'Allow')]"));
            if (!cookieBtns.isEmpty() && cookieBtns.get(0).isDisplayed()) {
                try { cookieBtns.get(0).click(); } catch(Exception ignored) {}
            }
        } catch(Exception ignored) {}

        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            Thread.sleep(150);
        } catch(Exception ignored) {}

        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (Exception e) {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected void sendKeys(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.COMMAND, "a"));
        element.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"));
        element.sendKeys(org.openqa.selenium.Keys.BACK_SPACE);
        if (text != null && !text.isEmpty()) {
            element.sendKeys(text);
        }
    }

    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected boolean waitForTitleToContain(String fraction) {
        try {
            return wait.until(ExpectedConditions.titleContains(fraction));
        } catch (Exception e) {
            return false;
        }
    }
}
