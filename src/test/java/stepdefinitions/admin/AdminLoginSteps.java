package stepdefinitions.admin;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.admin.AdminDashboardPage;
import pages.admin.AdminLoginPage;
import utils.ConfigReader;
import utils.DriverManager;

public class AdminLoginSteps {

    private final AdminLoginPage loginPage = new AdminLoginPage();
    private final AdminDashboardPage dashboardPage = new AdminDashboardPage();
    private final WebDriver driver = DriverManager.getDriver();

    @Given("the user navigates to the admin login page")
    public void theUserNavigatesToTheAdminLoginPage() {
        loginPage.navigateToAdminLogin();
    }

    @When("the admin enters credentials from config")
    public void theAdminEntersCredentialsFromConfig() {
        String email = ConfigReader.getProperty("admin.email");
        String password = ConfigReader.getProperty("admin.password");
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
    }

    @When("the admin enters email {string}")
    public void theAdminEntersEmail(String email) {
        loginPage.enterEmail(email);
    }

    @When("the admin enters password {string}")
    public void theAdminEntersPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("the admin clicks the Log In button")
    public void theAdminClicksTheLogInButton() {
        loginPage.clickLogin();
    }

    @When("the admin enters the Google Authenticator 2FA code")
    public void theAdminEntersGoogleAuthenticatorCodeFromConfig() {
        String secretKey = ConfigReader.getProperty("admin.mfa_secret");
        if (secretKey != null && !secretKey.trim().isEmpty()) {
            loginPage.enterTwoFactorCodeFromSecret(secretKey);
        } else {
            System.out.println("Warning: admin.mfa_secret is empty in config.properties");
        }
    }

    @When("the admin enters Google Authenticator 2FA code with secret {string}")
    public void theAdminEntersGoogleAuthenticatorCodeWithSecret(String secretKey) {
        loginPage.enterTwoFactorCodeFromSecret(secretKey);
    }

    @When("the admin enters OTP code {string}")
    public void theAdminEntersOtpCode(String otpCode) {
        loginPage.enterOtpCode(otpCode);
    }

    @When("the admin clicks the Verify OTP button")
    public void theAdminClicksTheVerifyOtpButton() {
        loginPage.clickVerifyOtp();
    }

    @Then("the admin should see the Google Authenticator 2FA prompt")
    public void theAdminShouldSeeTheTwoFactorPrompt() {
        Assertions.assertTrue(loginPage.isTwoFactorPromptDisplayed(),
                "Expected Google Authenticator / 2FA prompt to be displayed");
    }

    @Then("the admin should be successfully logged in to the admin dashboard")
    public void theAdminShouldBeSuccessfullyLoggedIn() {
        Assertions.assertTrue(dashboardPage.isDashboardDisplayed(),
                "Admin dashboard was not displayed. Current URL: " + driver.getCurrentUrl());
    }
}
