package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class MedicationsPage extends BasePage {

    @FindBy(xpath = "//h1[contains(text(), 'Medication')] | //h2[contains(text(), 'Medication')] | //*[contains(text(), 'Active Prescriptions')]")
    private WebElement pageTitle;

    @FindBy(xpath = "//button[contains(text(), 'Log Dose') or contains(text(), 'Take') or contains(text(), 'Log Medication')]")
    private List<WebElement> logDoseButtons;

    @FindBy(xpath = "//div[contains(@class, 'card') or contains(@class, 'medication')]")
    private List<WebElement> medicationCards;

    public boolean isPageLoaded() {
        try {
            waitForVisibility(pageTitle);
            return pageTitle.isDisplayed() || driver.getCurrentUrl().contains("medications");
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("medications");
        }
    }

    public int getMedicationCount() {
        return medicationCards.size();
    }

    public void clickFirstLogDoseButton() {
        if (!logDoseButtons.isEmpty()) {
            click(logDoseButtons.get(0));
        }
    }
}
