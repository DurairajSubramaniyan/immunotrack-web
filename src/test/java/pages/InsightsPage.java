package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InsightsPage extends BasePage {

    @FindBy(xpath = "//h1[contains(text(), 'Insights')] | //h2[contains(text(), 'Insights')] | //*[contains(text(), 'Health Insights')]")
    private WebElement pageTitle;

    @FindBy(xpath = "//*[contains(text(), 'Risk Factors') or contains(text(), 'Trends') or contains(text(), 'Analytics')]")
    private WebElement insightsWidget;

    public boolean isPageLoaded() {
        try {
            waitForVisibility(pageTitle);
            return pageTitle.isDisplayed() || driver.getCurrentUrl().contains("insights");
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("insights");
        }
    }

    public boolean isInsightsWidgetVisible() {
        try {
            return insightsWidget.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
