package stepdefinitions.clinician;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.clinician.ClinicianDashboardPage;
import pages.clinician.ClinicianLoginPage;
import utils.ConfigReader;
import utils.DriverManager;

public class ClinicianLoginSteps {

    private final ClinicianLoginPage loginPage = new ClinicianLoginPage();
    private final ClinicianDashboardPage dashboardPage = new ClinicianDashboardPage();
    private final WebDriver driver = DriverManager.getDriver();

    @Given("the user navigates to the clinician login page")
    public void theUserNavigatesToTheClinicianLoginPage() {
        loginPage.navigateToClinicianLogin();
    }

    @When("the clinician enters credentials from config")
    public void theClinicianEntersCredentialsFromConfig() {
        String email = ConfigReader.getProperty("clinician.email");
        String password = ConfigReader.getProperty("clinician.password");
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
    }

    @When("the clinician enters email {string}")
    public void theClinicianEntersEmail(String email) {
        loginPage.enterEmail(email);
    }

    @When("the clinician enters password {string}")
    public void theClinicianEntersPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("the clinician clicks the Log In button")
    public void theClinicianClicksTheLogInButton() {
        loginPage.clickLogin();
    }

    @When("the clinician enters the Google Authenticator 2FA code")
    public void theClinicianEntersGoogleAuthenticatorCodeFromConfig() {
        String secretKey = ConfigReader.getProperty("clinician.mfa_secret");
        if (secretKey != null && !secretKey.trim().isEmpty()) {
            loginPage.enterTwoFactorCodeFromSecret(secretKey);
        } else {
            System.out.println("Warning: clinician.mfa_secret is empty in config.properties");
        }
    }

    @When("the clinician enters Google Authenticator 2FA code with secret {string}")
    public void theClinicianEntersGoogleAuthenticatorCodeWithSecret(String secretKey) {
        loginPage.enterTwoFactorCodeFromSecret(secretKey);
    }

    @When("the clinician enters OTP code {string}")
    public void theClinicianEntersOtpCode(String otpCode) {
        loginPage.enterOtpCode(otpCode);
    }

    @When("the clinician clicks the Verify OTP button")
    public void theClinicianClicksTheVerifyOtpButton() {
        loginPage.clickVerifyOtp();
    }

    @Then("the clinician should see the Google Authenticator 2FA prompt")
    public void theClinicianShouldSeeTheTwoFactorPrompt() {
        Assertions.assertTrue(loginPage.isTwoFactorPromptDisplayed(),
                "Expected Google Authenticator / 2FA prompt to be displayed");
    }

    @Then("the clinician should be successfully logged in to the clinician dashboard")
    public void theClinicianShouldBeSuccessfullyLoggedIn() {
        Assertions.assertTrue(dashboardPage.isDashboardDisplayed(),
                "Clinician dashboard was not displayed. Current URL: " + driver.getCurrentUrl());
    }
}
