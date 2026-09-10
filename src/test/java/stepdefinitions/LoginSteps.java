package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.ForgotPasswordPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverManager;

import java.time.Duration;

public class LoginSteps {
    private final LoginPage loginPage = new LoginPage();
    private final ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage();
    private final WebDriver driver = DriverManager.getDriver();

    private static String lastUsedEmail = "";
    private static String lastUsedPassword = "";

    public static String getLastUsedEmail() {
        return lastUsedEmail;
    }

    public static String getLastUsedPassword() {
        return lastUsedPassword;
    }

    @Given("the user navigates to the login page")
    public void theUserNavigatesToTheLoginPage() {
        String baseUrl = ConfigReader.getProperty("url");
        driver.get(baseUrl);
        if (!loginPage.isLoginPageLoaded()) {
            if (!baseUrl.contains("login")) {
                driver.get(baseUrl.replaceAll("/+$", "") + "/patient/login");
            }
        }
        Assertions.assertTrue(loginPage.isLoginPageLoaded(), "Login page failed to load at URL: " + driver.getCurrentUrl());
    }

    @When("the user enters a valid email {string}")
    public void theUserEntersAValidEmail(String email) {
        lastUsedEmail = email;
        loginPage.enterEmail(email);
    }

    @When("the user enters a valid password {string}")
    public void theUserEntersAValidPassword(String password) {
        lastUsedPassword = password;
        loginPage.enterPassword(password);
    }

    @When("the user clicks the Log In button")
    public void theUserClicksTheLogInButton() {
        loginPage.clickLogin();
    }

    @Then("the user should see the dashboard page or a login error if credentials are mock")
    public void theUserShouldSeeTheDashboardPageOrALoginErrorIfCredentialsAreMock() {
        // Render free/hobby-tier backends cold-start after idle time and can take
        // well over 6 seconds to respond to the first request. A short wait here
        // misreads a slow-but-successful login as a failure, then wastes the
        // fallback-password attempt on a server that hasn't finished waking up.
        final Duration REDIRECT_WAIT = Duration.ofSeconds(45);

        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, REDIRECT_WAIT)
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.or(
                            org.openqa.selenium.support.ui.ExpectedConditions.urlContains("dashboard"),
                            org.openqa.selenium.support.ui.ExpectedConditions.urlContains("symptoms"),
                            org.openqa.selenium.support.ui.ExpectedConditions.urlContains("snot22")
                    ));
        } catch (Exception ignored) {}

        // Handle Monthly Health Check-in if redirected to /patient/snot22
        utils.MonthlyAssessmentHandler.handleMonthlyAssessmentIfPresent(driver);

        String currentUrl = driver.getCurrentUrl();
        String toastError = loginPage.getToastErrorMessage();

        if (!currentUrl.contains("dashboard") && !currentUrl.contains("symptoms") && !currentUrl.contains("snot22")) {
            // Attempt fallback password automatically if primary failed
            try {
                System.out.println("[INFO] Primary password attempt failed. Retrying login with 'Immunotrack@123'...");
                loginPage.enterPassword("Immunotrack@123");
                loginPage.clickLogin();
                new org.openqa.selenium.support.ui.WebDriverWait(driver, REDIRECT_WAIT)
                        .until(org.openqa.selenium.support.ui.ExpectedConditions.or(
                                org.openqa.selenium.support.ui.ExpectedConditions.urlContains("dashboard"),
                                org.openqa.selenium.support.ui.ExpectedConditions.urlContains("symptoms"),
                                org.openqa.selenium.support.ui.ExpectedConditions.urlContains("snot22")
                        ));
                utils.MonthlyAssessmentHandler.handleMonthlyAssessmentIfPresent(driver);
                currentUrl = driver.getCurrentUrl();
                toastError = loginPage.getToastErrorMessage();
            } catch (Exception ignored) {}
        }

        if (currentUrl.contains("dashboard") || currentUrl.contains("symptoms") || currentUrl.contains("snot22")) {
            System.out.println("Login Successful! Redirection URL: " + currentUrl);
            Assertions.assertTrue(true);
        } else {
            System.out.println("Login did not redirect. Toast Error message: " + toastError);
            // Require actual evidence of a real error message here - being "still
            // on the login page" is not evidence, it's the default state whenever
            // login hasn't succeeded yet (including mid cold-start). Without this,
            // the assertion passes on ANY failure for ANY reason, silently masking
            // real problems (e.g. both credentials rejected, backend down/slow)
            // as if they were the expected "mock credentials" case.
            Assertions.assertTrue(
                    !toastError.isEmpty()
                            && (toastError.toLowerCase().contains("incorrect")
                                || toastError.toLowerCase().contains("credentials")
                                || toastError.toLowerCase().contains("invalid")
                                || toastError.toLowerCase().contains("archived")),
                    "Expected redirect to dashboard or a visible login error message, but current URL is "
                            + currentUrl + " and toast is '" + toastError + "' (empty toast after "
                            + REDIRECT_WAIT.getSeconds() + "s suggests a real failure - e.g. backend "
                            + "unreachable/still cold-starting, or account credentials genuinely changed - "
                            + "not a normal 'mock credentials rejected' case)");
        }
    }

    @When("the user enters email {string} and password {string}")
    public void theUserEntersEmailAndPassword(String email, String password) {
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
    }

    @Then("the user should see an error notification containing {string}")
    public void theUserShouldSeeAnErrorNotificationContaining(String expectedError) {
        // Retrieve HTML5 browser validations
        String emailValidation = loginPage.getEmailValidationMessage();
        String passwordValidation = loginPage.getPasswordValidationMessage();

        // Retrieve page toast error
        String toastError = loginPage.getToastErrorMessage();

        System.out.println("Validation check details - Expected: " + expectedError);
        System.out.println("  Email field validation message: " + emailValidation);
        System.out.println("  Password field validation message: " + passwordValidation);
        System.out.println("  Page toast error: " + toastError);

        boolean matchFound = false;

        // Check if browser native validation contains expected error
        if (emailValidation != null && emailValidation.toLowerCase().contains(expectedError.toLowerCase())) {
            matchFound = true;
        } else if (passwordValidation != null
                && passwordValidation.toLowerCase().contains(expectedError.toLowerCase())) {
            matchFound = true;
        } else if (toastError != null && toastError.toLowerCase().contains(expectedError.toLowerCase())) {
            matchFound = true;
        }

        Assertions.assertTrue(matchFound,
                String.format(
                        "Expected error containing '%s', but got: Email Validation='%s', Password Validation='%s', Toast Error='%s'",
                        expectedError, emailValidation, passwordValidation, toastError));
    }

    @When("the user clicks the Forgot Password link")
    public void theUserClicksTheForgotPasswordLink() {
        loginPage.clickForgotPassword();
    }

    @Then("the user should see the Forgot Password recovery page")
    public void theUserShouldSeeTheForgotPasswordRecoveryPage() {
        Assertions.assertTrue(forgotPasswordPage.isPageLoaded(), "Forgot Password recovery page failed to load.");
        Assertions.assertTrue(driver.getCurrentUrl().contains("forgot-password"),
                "Expected URL to contain 'forgot-password' but got: " + driver.getCurrentUrl());
    }
}