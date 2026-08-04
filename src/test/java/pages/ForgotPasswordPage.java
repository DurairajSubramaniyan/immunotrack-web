package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ForgotPasswordPage extends BasePage {

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(css = "button[type='submit']")
    private WebElement sendResetCodeButton;

    @FindBy(xpath = "//*[contains(text(), 'Back to Login')]")
    private WebElement backToLoginLink;

    @FindBy(xpath = "//h1[contains(text(), 'Forgot Password')] | //h2[contains(text(), 'Forgot Password')]")
    private WebElement forgotPasswordHeader;

    public void enterEmail(String email) {
        sendKeys(emailInput, email);
    }

    public void clickSendResetCode() {
        click(sendResetCodeButton);
    }

    public void clickBackToLogin() {
        click(backToLoginLink);
    }

    public boolean isPageLoaded() {
        waitForVisibility(forgotPasswordHeader);
        return forgotPasswordHeader.isDisplayed();
    }
}
