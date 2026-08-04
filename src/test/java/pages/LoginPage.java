package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(linkText = "Forgot password?")
    private WebElement forgotPasswordLink;

    @FindBy(css = "[data-sonner-toast], li[data-sonner-toast], div[role='status'], .toast")
    private WebElement toastMessage;

    public void enterEmail(String email) {
        sendKeys(emailInput, email);
    }

    public void enterPassword(String password) {
        sendKeys(passwordInput, password);
    }

    public void clickLogin() {
        click(loginButton);
    }

    public void clickForgotPassword() {
        click(forgotPasswordLink);
    }

    public String getToastErrorMessage() {
        // Check for inline form errors first (e.g. invalid credentials card)
        try {
            java.util.List<org.openqa.selenium.WebElement> inlineErrors = driver.findElements(org.openqa.selenium.By.xpath("//form/div[contains(@class,'text-danger')]"));
            if (!inlineErrors.isEmpty()) {
                return inlineErrors.get(0).getText();
            }
        } catch (Exception ignored) {}

        // Fall back to toast notification message
        try {
            waitForVisibility(toastMessage);
            return getText(toastMessage);
        } catch (Exception e) {
            return "";
        }
    }

    public String getEmailValidationMessage() {
        // Check for browser native validation first
        String nativeMsg = emailInput.getAttribute("validationMessage");
        if (nativeMsg != null && !nativeMsg.isEmpty()) {
            return nativeMsg;
        }
        // Check for custom validation error displayed under the email field
        try {
            java.util.List<org.openqa.selenium.WebElement> errors = driver.findElements(org.openqa.selenium.By.xpath("//label[@for='email']/parent::div//p[contains(@class,'text-danger')]"));
            if (!errors.isEmpty()) {
                return errors.get(0).getText();
            }
        } catch (Exception ignored) {}
        return "";
    }

    public String getPasswordValidationMessage() {
        // Check for browser native validation first
        String nativeMsg = passwordInput.getAttribute("validationMessage");
        if (nativeMsg != null && !nativeMsg.isEmpty()) {
            return nativeMsg;
        }
        // Check for custom validation error displayed under the password field
        try {
            java.util.List<org.openqa.selenium.WebElement> errors = driver.findElements(org.openqa.selenium.By.xpath("//label[@for='password']/parent::div//p[contains(@class,'text-danger')]"));
            if (!errors.isEmpty()) {
                return errors.get(0).getText();
            }
        } catch (Exception ignored) {}
        return "";
    }

    public boolean isLoginPageLoaded() {
        waitForVisibility(emailInput);
        return emailInput.isDisplayed() && passwordInput.isDisplayed();
    }
}
