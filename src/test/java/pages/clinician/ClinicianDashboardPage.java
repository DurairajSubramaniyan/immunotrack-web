package pages.clinician;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class ClinicianDashboardPage extends BasePage {

    @FindBy(xpath = "//h1 | //h2 | //*[contains(@class, 'dashboard') or contains(@class, 'header')]")
    private WebElement dashboardHeading;

    @FindBy(xpath = "//button[contains(text(), 'Logout') or contains(text(), 'Sign Out')] | //a[contains(text(), 'Logout') or contains(text(), 'Sign Out')]")
    private WebElement logoutButton;

    public boolean isDashboardDisplayed() {
        try {
            waitForPageReady();
            return driver.getCurrentUrl().contains("clinician") && 
                   !driver.getCurrentUrl().contains("login");
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogout() {
        click(logoutButton);
    }
}
