package pages.admin;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class AdminDashboardPage extends BasePage {

    @FindBy(xpath = "//h1 | //h2 | //*[contains(@class, 'dashboard') or contains(@class, 'header')]")
    private WebElement dashboardHeading;

    @FindBy(xpath = "//button[contains(text(), 'Logout') or contains(text(), 'Sign Out')] | //a[contains(text(), 'Logout') or contains(text(), 'Sign Out')]")
    private WebElement logoutButton;

    public boolean isDashboardDisplayed() {
        try {
            waitForPageReady();
            return driver.getCurrentUrl().contains("admin") && 
                   !driver.getCurrentUrl().contains("login");
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogout() {
        click(logoutButton);
    }
}
