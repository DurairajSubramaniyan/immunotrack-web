package pages.clinician;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;
import utils.ConfigReader;
import utils.TotpUtil;

import java.util.List;

public class ClinicianLoginPage extends BasePage {

    @FindBy(xpath = "//input[@id='email' or @name='email' or @type='email' or contains(@placeholder, 'email') or contains(@placeholder, 'Email')]")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@id='password' or @name='password' or @type='password' or contains(@placeholder, 'password') or contains(@placeholder, 'Password')]")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit'], input[type='submit']")
    private WebElement loginButton;

    // 2FA / Google Authenticator OTP input field
    @FindBy(xpath = "//input[@placeholder='000000' or @id='otp' or @name='otp' or @id='code' or @name='code' or contains(@placeholder, 'code') or contains(@placeholder, '000000') or contains(@placeholder, 'OTP')]")
    private WebElement otpInput;

    // Button to submit/verify 2FA code
    @FindBy(xpath = "//button[contains(text(), 'Verify Code') or contains(text(), 'Verify') or @type='submit']")
    private WebElement verifyOtpButton;

    @FindBy(css = "[data-sonner-toast], li[data-sonner-toast], div[role='status'], .toast")
    private WebElement toastMessage;

    public void navigateToClinicianLogin() {
        String url = ConfigReader.getProperty("clinician.url");
        if (url != null && !url.trim().isEmpty()) {
            driver.get(url);
        }
    }

    public void enterEmail(String email) {
        waitForVisibility(emailInput);
        sendKeys(emailInput, email);
    }

    public void enterPassword(String password) {
        waitForVisibility(passwordInput);
        sendKeys(passwordInput, password);
    }

    public void clickLogin() {
        click(loginButton);
    }

    /**
     * Checks if the 2FA / Google Authenticator verification screen is displayed.
     */
    public boolean isTwoFactorPromptDisplayed() {
        try {
            waitForPageReady();
            List<WebElement> prompts = driver.findElements(By.xpath(
                "//*[contains(text(), 'Enter Authenticator Code') or contains(text(), '6-Digit Code') or contains(text(), 'Verify Code')] | //input[@placeholder='000000']"
            ));
            return !prompts.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Generates the 6-digit Google Authenticator code from the secret key and enters it.
     */
    public void enterTwoFactorCodeFromSecret(String secretKey) {
        String currentOtp = TotpUtil.generateCurrentOtp(secretKey);
        enterOtpCode(currentOtp);
    }

    /**
     * Enters a specific 6-digit OTP code into the input field.
     */
    public void enterOtpCode(String otpCode) {
        waitForVisibility(otpInput);
        sendKeys(otpInput, otpCode);
    }

    public void clickVerifyOtp() {
        click(verifyOtpButton);
    }

    public boolean isLoginPageLoaded() {
        try {
            waitForVisibility(emailInput);
            return emailInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
