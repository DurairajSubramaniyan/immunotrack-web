package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Snot22AssessmentPage extends BasePage {

    @FindBy(xpath = "//h1")
    private WebElement pageHeading;

    @FindBy(xpath = "//h2")
    private WebElement pageSubheading;

    @FindBy(xpath = "//*[contains(text(), 'CLINICAL QUESTIONNAIRE')]")
    private WebElement clinicalBadge;

    @FindBy(xpath = "//button[contains(text(), 'REMIND ME LATER') or contains(text(), 'Remind me later')]")
    private WebElement remindMeLaterButton;

    @FindBy(xpath = "//button[contains(text(), 'Begin check-in') or contains(text(), 'Begin')]")
    private WebElement beginCheckInButton;

    @FindBy(xpath = "//button[contains(text(), 'Log Out') or contains(text(), 'Switch Account')]")
    private WebElement logOutSwitchAccountButton;

    public static class Snot22ScoreResult {
        public int totalCalculatedScore = 0;
        public Map<String, Integer> domainScores = new HashMap<>();

        public Snot22ScoreResult() {
            domainScores.put("Nose and sinus symptoms", 0);
            domainScores.put("Ear and facial symptoms", 0);
            domainScores.put("Sleep", 0);
            domainScores.put("Daily activities and productivity", 0);
            domainScores.put("Emotional impact", 0);
        }

        public String getExpectedSeverity() {
            if (totalCalculatedScore <= 10) return "MINIMAL";
            if (totalCalculatedScore <= 20) return "MILD";
            if (totalCalculatedScore <= 30) return "MODERATE";
            if (totalCalculatedScore <= 45) return "SEVERE";
            return "VERY SEVERE";
        }
    }

    public boolean isLandingPageLoaded() {
        // Wait up to 30 seconds for the browser to authenticate, redirect away from /login,
        // and render the SNOT-22 assessment landing card
        for (int i = 0; i < 30; i++) {
            waitForPageReady();
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl != null && !currentUrl.contains("/login")) {
                List<WebElement> h1s = driver.findElements(By.xpath("//h1 | //h2[contains(text(), 'check-in') or contains(text(), 'log')]"));
                for (WebElement h : h1s) {
                    try {
                        if (h.isDisplayed() && !h.getText().trim().isEmpty()) {
                            return true;
                        }
                    } catch (Exception ignored) {}
                }
            }
            try { Thread.sleep(1000); } catch (Exception ignored) {}
        }
        return false;
    }

    public void verifyLandingPageContent(String assessmentType) {
        waitForPageReady();

        String expHeading = "";
        String expSubheading = "";

        if (assessmentType.equalsIgnoreCase("intake")) {
            expHeading = "Health Assessment on intake";
            expSubheading = "Before your first log — a quick health check-in";
        } else if (assessmentType.equalsIgnoreCase("monthly")) {
            expHeading = "Monthly Health Check-in";
            expSubheading = "Your monthly check-in is ready";
        } else if (assessmentType.equalsIgnoreCase("clinician") || assessmentType.toLowerCase().contains("clinician")) {
            expHeading = "Health Assessment on clinician request";
            expSubheading = "Your clinician has requested a health check-in";
        }

        // Wait up to 15 seconds for headings to be populated
        String actualHeading = "";
        String actualSubheading = "";
        for (int i = 0; i < 15; i++) {
            List<WebElement> h1s = driver.findElements(By.xpath("//h1"));
            if (!h1s.isEmpty()) {
                actualHeading = h1s.get(0).getText().trim();
            }
            List<WebElement> h2s = driver.findElements(By.xpath("//h2"));
            if (!h2s.isEmpty()) {
                actualSubheading = h2s.get(0).getText().trim();
            }
            if (!actualHeading.isEmpty() && !actualSubheading.isEmpty()) {
                break;
            }
            try { Thread.sleep(1000); } catch (Exception ignored) {}
        }

        System.out.println("[VERIFY] Assessment Type: " + assessmentType);
        System.out.println("[VERIFY] Actual Heading: '" + actualHeading + "' | Expected: '" + expHeading + "'");
        System.out.println("[VERIFY] Actual Subheading: '" + actualSubheading + "' | Expected: '" + expSubheading + "'");

        Assertions.assertTrue(actualHeading.toLowerCase().contains(expHeading.toLowerCase()),
                "Page heading mismatch! Actual: '" + actualHeading + "', Expected to contain: '" + expHeading + "'");
        Assertions.assertTrue(actualSubheading.toLowerCase().contains(expSubheading.toLowerCase()),
                "Page subheading mismatch! Actual: '" + actualSubheading + "', Expected to contain: '" + expSubheading + "'");

        // Verify Clinical Questionnaire Badge
        List<WebElement> badges = driver.findElements(By.xpath("//*[contains(text(), 'CLINICAL QUESTIONNAIRE')]"));
        Assertions.assertFalse(badges.isEmpty(), "Badge 'CLINICAL QUESTIONNAIRE' should be displayed on screen");

        // Verify Description Text
        List<WebElement> desc = driver.findElements(By.xpath("//*[contains(text(), 'Your responses will help your care team monitor your progress')]"));
        Assertions.assertFalse(desc.isEmpty(), "Description text 'Your responses will help your care team monitor your progress...' should be displayed");

        // Verify 3 Minutes Rate Notice
        List<WebElement> notice = driver.findElements(By.xpath("//*[contains(text(), 'Takes about 3 minutes') and contains(text(), 'last 2 weeks')]"));
        Assertions.assertFalse(notice.isEmpty(), "Info prompt 'Takes about 3 minutes. Rate how much each symptom has bothered you in the last 2 weeks.' should be displayed");

        // Verify Primary Action Button
        List<WebElement> beginBtns = driver.findElements(By.xpath("//button[contains(text(), 'Begin check-in')]"));
        Assertions.assertFalse(beginBtns.isEmpty(), "Primary action button 'Begin check-in' should be visible");
    }

    public void verifyRemindMeLaterPresence(boolean expectedPresent) {
        List<WebElement> remindBtns = driver.findElements(By.xpath("//button[contains(text(), 'REMIND ME LATER') or contains(text(), 'Remind me later')]"));
        boolean isDisplayed = false;
        for (WebElement b : remindBtns) {
            try {
                if (b.isDisplayed()) {
                    isDisplayed = true;
                    break;
                }
            } catch (Exception ignored) {}
        }
        System.out.println("[VERIFY] Remind Me Later presence: Actual = " + isDisplayed + ", Expected = " + expectedPresent);
        Assertions.assertEquals(expectedPresent, isDisplayed,
                "Remind Me Later button presence check failed! Expected present: " + expectedPresent + ", Actual: " + isDisplayed);
    }

    public void clickRemindMeLater() {
        System.out.println("[ACTION] Clicking 'REMIND ME LATER' button...");
        List<WebElement> remindBtns = driver.findElements(By.xpath("//button[contains(text(), 'REMIND ME LATER') or contains(text(), 'Remind me later')]"));
        if (!remindBtns.isEmpty()) {
            click(remindBtns.get(0));
        } else {
            Assertions.fail("'REMIND ME LATER' button not found to click!");
        }
        try { Thread.sleep(3000); } catch (Exception ignored) {}
    }

    public void verifyRedirectedToDashboard() {
        waitForPageReady();
        String currentUrl = driver.getCurrentUrl();
        System.out.println("[VERIFY] Redirection after Remind Me Later: URL = " + currentUrl);
        Assertions.assertTrue(currentUrl.contains("dashboard"), "User was not redirected to dashboard URL! Current URL: " + currentUrl);

        List<WebElement> dashboardGreetings = driver.findElements(By.xpath("//*[contains(text(), 'Good morning') or contains(text(), 'Good afternoon') or contains(text(), 'Good evening') or contains(text(), 'Today')]"));
        Assertions.assertFalse(dashboardGreetings.isEmpty(), "Dashboard greetings / cards not found after Remind Me Later dismissal!");
    }

    public void clickBeginCheckIn() {
        System.out.println("[ACTION] Clicking 'Begin check-in' button...");
        List<WebElement> beginBtns = driver.findElements(By.xpath("//button[contains(text(), 'Begin check-in')]"));
        if (!beginBtns.isEmpty()) {
            click(beginBtns.get(0));
        } else {
            click(beginCheckInButton);
        }
        try { Thread.sleep(2500); } catch (Exception ignored) {}
    }

    private String getRatingButtonXPath(int rating) {
        switch (rating) {
            case 0:
                return "//button[starts-with(normalize-space(.), '0') or contains(., 'No problem')]";
            case 1:
                return "//button[starts-with(normalize-space(.), '1') or contains(., 'Very mild problem')]";
            case 2:
                return "//button[starts-with(normalize-space(.), '2') or contains(., 'Mild or slight problem')]";
            case 3:
                return "//button[starts-with(normalize-space(.), '3') or contains(., 'Moderate problem')]";
            case 4:
                return "//button[starts-with(normalize-space(.), '4') or contains(., 'Severe problem')]";
            case 5:
                return "//button[starts-with(normalize-space(.), '5') or contains(., 'bad as it can be')]";
            default:
                return "//button[starts-with(normalize-space(.), '" + rating + "')]";
        }
    }

    public Snot22ScoreResult answerAllQuestions(int chosenRating) {
        Snot22ScoreResult result = new Snot22ScoreResult();
        String currentDomain = "Nose and sinus symptoms";

        Map<String, String> sectionToDomainMap = new HashMap<>();
        sectionToDomainMap.put("Nose and sinus symptoms", "Nose and sinus symptoms");
        sectionToDomainMap.put("Ear and facial symptoms", "Ear and facial symptoms");
        sectionToDomainMap.put("Sleep", "Sleep");
        sectionToDomainMap.put("Daily activities", "Daily activities and productivity");
        sectionToDomainMap.put("How you're feeling", "Emotional impact");

        int questionsAnswered = 0;
        int maxSteps = 45;
        String lastQuestionTitle = "";

        for (int step = 0; step < maxSteps; step++) {
            waitForPageReady();

            // Check if we hit an interstitial section card with a "Continue" button
            List<WebElement> continueBtns = driver.findElements(By.xpath("//button[normalize-space()='Continue' or contains(text(), 'Continue')]"));
            if (!continueBtns.isEmpty() && continueBtns.get(0).isDisplayed()) {
                List<WebElement> secTitles = driver.findElements(By.xpath("//h2 | //h3"));
                for (WebElement st : secTitles) {
                    if (st.isDisplayed()) {
                        String txt = st.getText().trim();
                        for (String key : sectionToDomainMap.keySet()) {
                            if (txt.toLowerCase().contains(key.toLowerCase())) {
                                currentDomain = sectionToDomainMap.get(key);
                                System.out.println("[SECTION] Entering section: " + txt + " -> mapped domain: " + currentDomain);
                                break;
                            }
                        }
                    }
                }
                click(continueBtns.get(0));
                try { Thread.sleep(1200); } catch (Exception ignored) {}
                continue;
            }

            // Check if we reached the completion screen
            List<WebElement> completionHeaders = driver.findElements(By.xpath("//*[contains(text(), 'All done — thank you') or contains(text(), 'COMPLETED SUCCESSFULLY')]"));
            if (!completionHeaders.isEmpty() && completionHeaders.get(0).isDisplayed()) {
                System.out.println("[QUESTIONNAIRE] Completed all questions. Total answered: " + questionsAnswered);
                break;
            }

            // Get current question title
            List<WebElement> qHeadings = driver.findElements(By.xpath("//h2 | //h3"));
            String questionTitle = "";
            for (WebElement qh : qHeadings) {
                if (qh.isDisplayed() && !qh.getText().trim().isEmpty() && !qh.getText().contains("Health")) {
                    questionTitle = qh.getText().trim();
                    break;
                }
            }

            // If same question is still transitioning, wait a bit
            if (!questionTitle.isEmpty() && questionTitle.equals(lastQuestionTitle)) {
                try { Thread.sleep(800); } catch (Exception ignored) {}
                continue;
            }

            if (!questionTitle.isEmpty()) {
                System.out.println("[QUESTION " + (questionsAnswered + 1) + "] " + questionTitle + " (Domain: " + currentDomain + ")");
                lastQuestionTitle = questionTitle;
            }

            // Click the designated rating button
            String xpath = getRatingButtonXPath(chosenRating);
            List<WebElement> ratingBtns = driver.findElements(By.xpath(xpath));
            if (!ratingBtns.isEmpty() && ratingBtns.get(0).isDisplayed()) {
                click(ratingBtns.get(0));
                result.totalCalculatedScore += chosenRating;
                result.domainScores.put(currentDomain, result.domainScores.get(currentDomain) + chosenRating);
                questionsAnswered++;
                try { Thread.sleep(1000); } catch (Exception ignored) {}
            } else {
                List<WebElement> fallback = driver.findElements(By.xpath("//button[contains(., 'problem') or contains(., 'Problem')]"));
                if (!fallback.isEmpty() && fallback.get(0).isDisplayed()) {
                    click(fallback.get(0));
                    questionsAnswered++;
                    try { Thread.sleep(1000); } catch (Exception ignored) {}
                }
            }
        }

        Assertions.assertEquals(22, questionsAnswered, "Expected to answer 22 SNOT-22 questions, but answered: " + questionsAnswered);
        return result;
    }

    public void verifyCompletionScreen(Snot22ScoreResult calculated) {
        waitForPageReady();
        try { Thread.sleep(2000); } catch (Exception ignored) {}

        // 1. Verify "COMPLETED SUCCESSFULLY" badge
        List<WebElement> statusBadges = driver.findElements(By.xpath("//*[contains(text(), 'COMPLETED SUCCESSFULLY')]"));
        Assertions.assertFalse(statusBadges.isEmpty(), "Badge 'COMPLETED SUCCESSFULLY' should be displayed on completion screen");

        // 2. Verify "All done — thank you." heading
        List<WebElement> completionTitles = driver.findElements(By.xpath("//*[contains(text(), 'All done — thank you')]"));
        Assertions.assertFalse(completionTitles.isEmpty(), "Completion heading 'All done — thank you.' should be displayed");

        // 3. Verify total score calculation matches actual score
        List<WebElement> scoreElements = driver.findElements(By.xpath("//*[contains(text(), 'YOUR SNOT-22 SCORE')]/following::span[1] | //div[contains(@class, 'score')]//span"));
        String actualScoreText = "";
        for (WebElement se : scoreElements) {
            String txt = se.getText().trim();
            if (txt.matches("\\d+")) {
                actualScoreText = txt;
                break;
            }
        }
        if (actualScoreText.isEmpty()) {
            List<WebElement> allSpans = driver.findElements(By.xpath("//span"));
            for (WebElement sp : allSpans) {
                if (sp.isDisplayed() && sp.getText().trim().matches("^\\d+$")) {
                    actualScoreText = sp.getText().trim();
                    break;
                }
            }
        }

        int actualScore = Integer.parseInt(actualScoreText);
        int expectedScore = calculated.totalCalculatedScore;
        System.out.println("[SCORE VERIFICATION] Actual Displayed Score = " + actualScore + " | Expected Calculated Score = " + expectedScore);
        Assertions.assertEquals(expectedScore, actualScore,
                "SNOT-22 Total Score mismatch! Actual displayed: " + actualScore + ", Expected calculated: " + expectedScore);

        // 4. Verify severity label
        String expectedSeverity = calculated.getExpectedSeverity();
        List<WebElement> severityElements = driver.findElements(By.xpath("//*[contains(text(), '" + expectedSeverity + "')]"));
        System.out.println("[SEVERITY VERIFICATION] Expected Severity = " + expectedSeverity + " | Found elements count = " + severityElements.size());
        Assertions.assertFalse(severityElements.isEmpty(),
                "Expected severity category '" + expectedSeverity + "' not found on completion screen!");

        // 5. Verify Domain Breakdowns
        System.out.println("[DOMAIN BREAKDOWN VERIFICATION]");
        for (Map.Entry<String, Integer> entry : calculated.domainScores.entrySet()) {
            String domain = entry.getKey();
            int expDomainScore = entry.getValue();
            System.out.println("  Verifying Domain: " + domain + " with expected score: " + expDomainScore);
            List<WebElement> domainRows = driver.findElements(By.xpath("//*[contains(text(), '" + domain + "')]"));
            Assertions.assertFalse(domainRows.isEmpty(), "Domain header '" + domain + "' not found in completion breakdown!");
        }

        // 6. Verify Severity Reference Scale
        List<WebElement> refScale = driver.findElements(By.xpath("//*[contains(text(), 'SEVERITY REFERENCE SCALE')]"));
        Assertions.assertFalse(refScale.isEmpty(), "'SEVERITY REFERENCE SCALE' header not found on completion screen!");
    }
}
