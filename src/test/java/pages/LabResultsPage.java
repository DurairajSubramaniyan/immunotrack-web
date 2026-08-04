package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class LabResultsPage extends BasePage {

    @FindBy(xpath = "//h1[contains(text(), 'Lab Results')] | //h2[contains(text(), 'Lab Results')] | //*[contains(text(), 'Biomarkers')]")
    private WebElement pageTitle;

    @FindBy(xpath = "//div[contains(@class, 'card') or contains(text(), 'IgE') or contains(text(), 'Eosinophils')]")
    private List<WebElement> labResultCards;

    public boolean isPageLoaded() {
        try {
            waitForVisibility(pageTitle);
            return pageTitle.isDisplayed() || driver.getCurrentUrl().contains("lab-results");
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("lab-results");
        }
    }

    public boolean hasLabResults() {
        return !labResultCards.isEmpty();
    }
}
