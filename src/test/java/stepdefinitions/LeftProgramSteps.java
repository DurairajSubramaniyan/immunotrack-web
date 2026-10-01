package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.LeftProgramPage;
import utils.DriverManager;

public class LeftProgramSteps {

    private final LeftProgramPage leftProgramPage = new LeftProgramPage();
    private final WebDriver driver = DriverManager.getDriver();

    // =========================================================================
    // HOME / DASHBOARD PAGE VERIFICATIONS
    // =========================================================================

    @Then("the user should see the Monitoring Suspended banner on the home page")
    public void theUserShouldSeeTheMonitoringSuspendedBannerOnTheHomePage() {
        String expectedBadge = "MONITORING SUSPENDED";
        String actualBadge = leftProgramPage.getMonitoringSuspendedBadgeText();
        System.out.println("[ASSERTION] Banner Badge - Expected: '" + expectedBadge + "', Actual: '" + actualBadge + "'");
        Assertions.assertEquals(expectedBadge, actualBadge.toUpperCase(), "Monitoring Suspended badge mismatch!");

        String expectedHeadline = "You've left your monitoring.";
        String actualHeadline = leftProgramPage.getSuspendedBannerHeadline().replace("’", "'");
        System.out.println("[ASSERTION] Banner Headline - Expected: '" + expectedHeadline + "', Actual: '" + actualHeadline + "'");
        Assertions.assertEquals(expectedHeadline, actualHeadline, "Banner headline mismatch!");

        String expectedDesc = "Your daily symptom data is no longer shared with your care team. Re-join the monitoring to resume remote tracking and keep your clinician informed.";
        String actualDesc = leftProgramPage.getSuspendedBannerDescription();
        System.out.println("[ASSERTION] Banner Description - Expected: '" + expectedDesc + "', Actual: '" + actualDesc + "'");
        Assertions.assertEquals(expectedDesc, actualDesc, "Banner description mismatch!");

        String expectedBtn = "Re-join Monitoring";
        String actualBtn = leftProgramPage.getRejoinBannerButtonText();
        System.out.println("[ASSERTION] Re-join Banner Button - Expected: '" + expectedBtn + "', Actual: '" + actualBtn + "'");
        Assertions.assertEquals(expectedBtn, actualBtn, "Re-join banner button text mismatch!");
    }

    @Then("the 16 days progress bar should not be displayed on the home page")
    public void the16DaysProgressBarShouldNotBeDisplayedOnTheHomePage() {
        boolean isBarDisplayed = leftProgramPage.is16DaysProgressBarDisplayed();
        System.out.println("[ASSERTION] 16 Days Progress Bar On Home - Expected: false, Actual: " + isBarDisplayed);
        Assertions.assertFalse(isBarDisplayed,
                "The 16 days monitoring progress bar should NOT be displayed for a user who left the monitoring program.");
    }

    @When("the user clicks on Re-join Monitoring button on the dashboard banner")
    public void theUserClicksOnRejoinMonitoringButtonOnTheDashboardBanner() {
        leftProgramPage.clickRejoinMonitoringOnBanner();
    }

    // =========================================================================
    // LOG SYMPTOMS PAGE VERIFICATIONS
    // =========================================================================

    @Then("the 16 days progress bar should not be displayed on the Daily Health Log page")
    public void the16DaysProgressBarShouldNotBeDisplayedOnTheDailyHealthLogPage() {
        boolean isBarDisplayed = leftProgramPage.is16DaysProgressBarDisplayed();
        System.out.println("[ASSERTION] 16 Days Progress Bar On Daily Log - Expected: false, Actual: " + isBarDisplayed);
        Assertions.assertFalse(isBarDisplayed,
                "The 16 days monitoring progress bar should NOT be displayed on the Daily Health Log page for a user who left the program.");
    }

    // =========================================================================
    // PROFILE PAGE - SETTINGS & PRIVACY VERIFICATIONS
    // =========================================================================

    @Then("the user should see the Settings and Privacy section with all configuration options")
    public void theUserShouldSeeTheSettingsAndPrivacySectionWithAllConfigurationOptions() {
        String expectedHeader = "Settings & Privacy";
        String actualHeader = leftProgramPage.getSettingsAndPrivacyHeaderText();
        System.out.println("[ASSERTION] Settings & Privacy Header - Expected: '" + expectedHeader + "', Actual: '" + actualHeader + "'");
        Assertions.assertEquals(expectedHeader, actualHeader, "Settings & Privacy header mismatch on Profile page!");

        String[] expectedOptions = {
            "Medication Reminders",
            "Push Notifications",
            "Change Password",
            "Remote Monitoring",
            "Notice of Privacy Practices",
            "Export My Record",
            "Privacy & Security",
            "Cookie Policy"
        };

        for (String expectedOption : expectedOptions) {
            String actualOption = leftProgramPage.getSettingsAndPrivacyOptionText(expectedOption);
            System.out.println("[ASSERTION] Settings Option - Expected: '" + expectedOption + "', Actual: '" + actualOption + "'");
            Assertions.assertEquals(expectedOption, actualOption, "Settings & Privacy option '" + expectedOption + "' mismatch!");
        }
    }

    @Then("the Remote Monitoring item should display {string} badge")
    public void theRemoteMonitoringItemShouldDisplayBadge(String expectedBadgeText) {
        String actualBadge = leftProgramPage.getRemoteMonitoringBadgeInProfile();
        System.out.println("[ASSERTION] Remote Monitoring Badge - Expected: '" + expectedBadgeText + "', Actual: '" + actualBadge + "'");
        Assertions.assertEquals(expectedBadgeText, actualBadge,
                "Remote Monitoring badge mismatch in Profile!");
    }

    @When("the user clicks on Remote Monitoring in Settings and Privacy")
    public void theUserClicksOnRemoteMonitoringInSettingsAndPrivacy() {
        leftProgramPage.clickRemoteMonitoringInProfile();
    }

    // =========================================================================
    // REMOTE MONITORING OVERVIEW SCREEN (/patient/remote-monitoring)
    // =========================================================================

    @Then("the user should be redirected to the Remote Monitoring overview page")
    public void theUserShouldBeRedirectedToTheRemoteMonitoringOverviewPage() {
        Assertions.assertTrue(leftProgramPage.isRemoteMonitoringPageLoaded() || driver.getCurrentUrl().contains("remote-monitoring"),
                "User should be on the Remote Monitoring overview page (/patient/remote-monitoring).");
    }

    @Then("the user should see the Remote Monitoring title, subtitle, and breadcrumb")
    public void theUserShouldSeeTheRemoteMonitoringTitleSubtitleAndBreadcrumb() {
        String expectedTitle = "Remote Monitoring";
        String actualTitle = leftProgramPage.getRemoteMonitoringPageTitle();
        System.out.println("[ASSERTION] Page Title - Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
        Assertions.assertEquals(expectedTitle, actualTitle, "Remote Monitoring page title mismatch!");

        String expectedSubtitle = "Remote Therapeutic Monitoring program";
        String actualSubtitle = leftProgramPage.getRemoteMonitoringSubtitle();
        System.out.println("[ASSERTION] Page Subtitle - Expected: '" + expectedSubtitle + "', Actual: '" + actualSubtitle + "'");
        Assertions.assertEquals(expectedSubtitle, actualSubtitle, "Remote Monitoring page subtitle mismatch!");

        String expectedBreadcrumb = "PATIENT PORTAL / OVERVIEW";
        String actualBreadcrumb = leftProgramPage.getBreadcrumbText();
        System.out.println("[ASSERTION] Breadcrumb - Expected: '" + expectedBreadcrumb + "', Actual: '" + actualBreadcrumb + "'");
        Assertions.assertEquals(expectedBreadcrumb, actualBreadcrumb, "Breadcrumb text mismatch!");
    }

    @Then("the user should see the Left Monitoring status card with description and Re-join button")
    public void theUserShouldSeeTheLeftMonitoringStatusCardWithDescriptionAndRejoinButton() {
        String expectedTitle = "Left Monitoring";
        String actualTitle = leftProgramPage.getLeftMonitoringCardTitle();
        System.out.println("[ASSERTION] Left Monitoring Title - Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
        Assertions.assertEquals(expectedTitle, actualTitle, "Left Monitoring card title mismatch!");

        String expectedDesc = "You left the monitoring. Your daily symptom data is no longer shared with your care team. You can re-join the monitoring at any time to share your logs again.";
        String actualDesc = leftProgramPage.getLeftMonitoringCardDescription();
        System.out.println("[ASSERTION] Left Monitoring Description - Expected: '" + expectedDesc + "', Actual: '" + actualDesc + "'");
        Assertions.assertEquals(expectedDesc, actualDesc, "Left Monitoring description mismatch!");

        String expectedBtn = "Re-join monitoring";
        String actualBtn = leftProgramPage.getRejoinMonitoringButtonText();
        System.out.println("[ASSERTION] Re-join Button - Expected: '" + expectedBtn + "', Actual: '" + actualBtn + "'");
        Assertions.assertEquals(expectedBtn, actualBtn, "Re-join monitoring button text mismatch!");
    }

    @Then("the user should see the ABOUT RTM section with all three informational cards")
    public void theUserShouldSeeTheAboutRtmSectionWithAllThreeInformationalCards() {
        String expectedHeader = "ABOUT RTM";
        String actualHeader = leftProgramPage.getAboutRtmHeaderText().toUpperCase();
        System.out.println("[ASSERTION] ABOUT RTM Header - Expected: '" + expectedHeader + "', Actual: '" + actualHeader + "'");
        Assertions.assertEquals(expectedHeader, actualHeader, "ABOUT RTM header mismatch!");

        String expectedHowItWorksTitle = "How does it work?";
        String actualHowItWorksTitle = leftProgramPage.getHowItWorksTitle();
        System.out.println("[ASSERTION] 'How does it work?' Title - Expected: '" + expectedHowItWorksTitle + "', Actual: '" + actualHowItWorksTitle + "'");
        Assertions.assertEquals(expectedHowItWorksTitle, actualHowItWorksTitle, "'How does it work?' title mismatch!");

        String expectedHowItWorksContent = "By simply logging your nasal, breathing, or skin scores daily, your clinician receives detailed clinical tracking to make informed care plan changes.";
        String actualHowItWorksContent = leftProgramPage.getHowItWorksContent();
        System.out.println("[ASSERTION] 'How does it work?' Content - Expected: '" + expectedHowItWorksContent + "', Actual: '" + actualHowItWorksContent + "'");
        Assertions.assertEquals(expectedHowItWorksContent, actualHowItWorksContent, "'How does it work?' content mismatch!");

        String expectedNoHardwareTitle = "No Extra Hardware";
        String actualNoHardwareTitle = leftProgramPage.getNoExtraHardwareTitle();
        System.out.println("[ASSERTION] 'No Extra Hardware' Title - Expected: '" + expectedNoHardwareTitle + "', Actual: '" + actualNoHardwareTitle + "'");
        Assertions.assertEquals(expectedNoHardwareTitle, actualNoHardwareTitle, "'No Extra Hardware' title mismatch!");

        String expectedNoHardwareContent = "No smartwatch or tracker required. RTM relies entirely on the self-reported logs you already record inside ImmunoTrack.";
        String actualNoHardwareContent = leftProgramPage.getNoExtraHardwareContent();
        System.out.println("[ASSERTION] 'No Extra Hardware' Content - Expected: '" + expectedNoHardwareContent + "', Actual: '" + actualNoHardwareContent + "'");
        Assertions.assertEquals(expectedNoHardwareContent, actualNoHardwareContent, "'No Extra Hardware' content mismatch!");

        String expectedCostTitle = "Cost and Coverage";
        String actualCostTitle = leftProgramPage.getCostAndCoverageTitle();
        System.out.println("[ASSERTION] 'Cost and Coverage' Title - Expected: '" + expectedCostTitle + "', Actual: '" + actualCostTitle + "'");
        Assertions.assertEquals(expectedCostTitle, actualCostTitle, "'Cost and Coverage' title mismatch!");

        String expectedCostContent = "Most insurance plans cover RTM under remote care guidelines, subject to regular copays and deductibles.";
        String actualCostContent = leftProgramPage.getCostAndCoverageContent();
        System.out.println("[ASSERTION] 'Cost and Coverage' Content - Expected: '" + expectedCostContent + "', Actual: '" + actualCostContent + "'");
        Assertions.assertEquals(expectedCostContent, actualCostContent, "'Cost and Coverage' content mismatch!");
    }

    @When("the user clicks the back arrow from Remote Monitoring overview")
    public void theUserClicksTheBackArrowFromRemoteMonitoringOverview() {
        leftProgramPage.clickBackArrow();
    }
}
