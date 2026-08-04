package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class HistoryPage extends BasePage {

    @FindBy(xpath = "//h1[contains(text(), 'History')] | //h2[contains(text(), 'History')] | //*[contains(text(), 'Symptom History')]")
    private WebElement pageTitle;

    @FindBy(xpath = "//div[contains(@class, 'timeline') or contains(@class, 'history') or contains(@class, 'card')]")
    private List<WebElement> historyEntries;

    public boolean isPageLoaded() {
        try {
            waitForVisibility(pageTitle);
            return pageTitle.isDisplayed() || driver.getCurrentUrl().contains("history");
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("history");
        }
    }

    public boolean hasHistoryEntries() {
        return !historyEntries.isEmpty();
    }
}
