package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//input[@id='email' or @name='email' or @type='email' or contains(@placeholder, 'email') or contains(@placeholder, 'Email')]")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@id='password' or @name='password' or @type='password' or contains(@placeholder, 'password') or contains(@placeholder, 'Password')]")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit'], input[type='submit']")
    private WebElement loginButton;

    @FindBy(xpath = "//a[contains(text(), 'Forgot')] | //button[contains(text(), 'Forgot')]")
    private WebElement forgotPasswordLink;

    @FindBy(css = "[data-sonner-toast], li[data-sonner-toast], div[role='status'], .toast")
    private WebElement toastMessage;

    public void enterEmail(String email) {
        ensureOnLoginPage();
        sendKeys(emailInput, email);
    }

    public void enterPassword(String password) {
        ensureOnLoginPage();
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
            java.util.List<org.openqa.selenium.WebElement> inlineErrors = driver.findElements(org.openqa.selenium.By.xpath("//form/div[contains(@class,'text-danger')] | //form//*[contains(@class, 'red') or contains(@class, 'error') or contains(@class, 'danger')]"));
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
        try {
            String nativeMsg = emailInput.getAttribute("validationMessage");
            if (nativeMsg != null && !nativeMsg.isEmpty()) {
                return nativeMsg;
            }
        } catch (Exception ignored) {}

        // Check for custom validation error displayed under the email field
        try {
            java.util.List<org.openqa.selenium.WebElement> errors = driver.findElements(org.openqa.selenium.By.xpath("//label[contains(text(),'Email') or @for='email']/parent::div//p | //input[@type='email']/following-sibling::p"));
            if (!errors.isEmpty()) {
                return errors.get(0).getText();
            }
        } catch (Exception ignored) {}
        return "";
    }

    public String getPasswordValidationMessage() {
        // Check for browser native validation first
        try {
            String nativeMsg = passwordInput.getAttribute("validationMessage");
            if (nativeMsg != null && !nativeMsg.isEmpty()) {
                return nativeMsg;
            }
        } catch (Exception ignored) {}

        // Check for custom validation error displayed under the password field
        try {
            java.util.List<org.openqa.selenium.WebElement> errors = driver.findElements(org.openqa.selenium.By.xpath("//label[contains(text(),'Password') or @for='password']/parent::div//p | //input[@type='password']/following-sibling::p"));
            if (!errors.isEmpty()) {
                return errors.get(0).getText();
            }
        } catch (Exception ignored) {}
        return "";
    }

    public void ensureOnLoginPage() {
        String currentUrl = driver.getCurrentUrl();
        if (driver.findElements(org.openqa.selenium.By.xpath("//input[@type='email' or @id='email' or @name='email']")).isEmpty()) {
            // Check if there are nav buttons to login / sign in / patient portal
            try {
                java.util.List<org.openqa.selenium.WebElement> navLinks = driver.findElements(org.openqa.selenium.By.xpath("//a[contains(text(),'Sign In') or contains(text(),'Log In') or contains(text(),'Patient') or contains(@href, 'login')] | //button[contains(text(),'Sign In') or contains(text(),'Log In')]"));
                if (!navLinks.isEmpty()) {
                    navLinks.get(0).click();
                } else if (!currentUrl.contains("/patient/login")) {
                    driver.get(currentUrl.replaceAll("/+$", "") + "/patient/login");
                }
            } catch (Exception e) {
                if (!currentUrl.contains("/patient/login")) {
                    driver.get(currentUrl.replaceAll("/+$", "") + "/patient/login");
                }
            }
        }
    }

    public boolean isLoginPageLoaded() {
        ensureOnLoginPage();
        try {
            waitForVisibility(emailInput);
            return emailInput.isDisplayed();
        } catch (Exception e) {
            try {
                String baseUrl = utils.ConfigReader.getProperty("url").replaceAll("/+$", "");
                driver.get(baseUrl + "/patient/login");
                waitForVisibility(emailInput);
                return emailInput.isDisplayed();
            } catch (Exception ex) {
                try {
                    String baseUrl = utils.ConfigReader.getProperty("url").replaceAll("/+$", "");
                    driver.get(baseUrl + "/login");
                    waitForVisibility(emailInput);
                    return emailInput.isDisplayed();
                } catch (Exception ignored) {
                    return false;
                }
            }
        }
    }
}
