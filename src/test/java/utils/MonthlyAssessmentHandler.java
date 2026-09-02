package utils;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class MonthlyAssessmentHandler {

    private MonthlyAssessmentHandler() {
        // Utility class
    }

    public static boolean isMonthlyAssessmentPresent(WebDriver driver) {
        try {
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl != null && currentUrl.contains("snot22")) {
                return true;
            }
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(300));
            List<WebElement> headers = driver.findElements(By.xpath("//*[contains(text(), 'Monthly Health Check-in') or contains(text(), 'Your monthly check-in is ready') or contains(text(), 'Begin check-in')]"));
            boolean found = false;
            for (WebElement h : headers) {
                if (h.isDisplayed()) {
                    found = true;
                    break;
                }
            }
            int timeout = Integer.parseInt(ConfigReader.getProperty("timeout"));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
            return found;
        } catch (Exception ignored) {
            try {
                int timeout = Integer.parseInt(ConfigReader.getProperty("timeout"));
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
            } catch (Exception ignored2) {}
        }
        return false;
    }

    public static void handleMonthlyAssessmentIfPresent(WebDriver driver) {
        int timeout = Integer.parseInt(ConfigReader.getProperty("timeout"));

        if (!isMonthlyAssessmentPresent(driver)) {
            return;
        }

        System.out.println("==================================================");
        System.out.println("[MONTHLY ASSESSMENT] Monthly Health Check-in (SNOT-22) detected!");
        System.out.println("[MONTHLY ASSESSMENT] Starting automated completion of monthly health check-in...");
        System.out.println("==================================================");

        Random random = new Random();
        long lastActionTime = System.currentTimeMillis();

        try {
            // Fast scanning with 300ms implicit wait
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(300));

            for (int step = 1; step <= 40; step++) {
                String currentUrl = driver.getCurrentUrl();
                if (currentUrl != null && !currentUrl.contains("snot22") && (currentUrl.contains("dashboard") || currentUrl.contains("symptoms") || currentUrl.contains("patient"))) {
                    List<WebElement> snotHeaders = driver.findElements(By.xpath("//*[contains(text(), 'Monthly Health Check-in') or contains(text(), 'Your monthly check-in is ready')]"));
                    boolean stillPresent = false;
                    for (WebElement h : snotHeaders) {
                        if (h.isDisplayed()) {
                            stillPresent = true;
                            break;
                        }
                    }
                    if (!stillPresent) {
                        System.out.println("[MONTHLY ASSESSMENT] Monthly assessment finished! Redirected to: " + currentUrl);
                        if (currentUrl.contains("login")) {
                            System.out.println("[MONTHLY ASSESSMENT] App redirected to login page after monthly assessment. Re-logging in...");
                            reLoginIfOnLoginPage(driver);
                        }
                        System.out.println("==================================================");
                        return;
                    }
                }

                boolean actionTaken = false;

                // 1. Click "Begin check-in" button if present
                List<WebElement> beginBtns = driver.findElements(By.xpath("//button[contains(text(),'Begin check-in') or contains(text(),'Begin Check-in') or contains(text(),'Begin Check In') or contains(text(),'Begin')]"));
                for (WebElement btn : beginBtns) {
                    if (btn.isDisplayed()) {
                        System.out.println("[MONTHLY ASSESSMENT] Step " + step + ": Clicking 'Begin check-in' button...");
                        clickElement(driver, btn);
                        actionTaken = true;
                        lastActionTime = System.currentTimeMillis();
                        Thread.sleep(1000);
                        break;
                    }
                }
                if (actionTaken) continue;

                // 2. Select rating (random 1, 2, or 3) for current question
                int rating = 1 + random.nextInt(3);
                String[] labelKeywords = {"Very mild", "Mild or slight", "Moderate"};
                String targetKeyword = labelKeywords[rating - 1];

                // Strategy A: Find text element matching target keyword
                List<WebElement> textElements = driver.findElements(By.xpath(String.format(
                        "//*[text()='%s' or contains(text(), '%s')]", targetKeyword, targetKeyword
                )));

                for (WebElement txt : textElements) {
                    if (txt.isDisplayed()) {
                        System.out.println("[MONTHLY ASSESSMENT] Step " + step + ": Clicking option containing '" + targetKeyword + "' (Rating " + rating + ")...");
                        clickElement(driver, txt);
                        try {
                            WebElement card = txt.findElement(By.xpath(
                                    "./ancestor::button | ./ancestor::div[contains(@class, 'border') or contains(@class, 'rounded') or contains(@class, 'p-') or contains(@class, 'cursor')][1]"
                            ));
                            if (card.isDisplayed()) {
                                clickElement(driver, card);
                            }
                        } catch (Exception ignored) {}

                        actionTaken = true;
                        lastActionTime = System.currentTimeMillis();
                        Thread.sleep(600);
                        break;
                    }
                }

                // Strategy B: Find option cards by index
                if (!actionTaken) {
                    List<WebElement> optionCards = driver.findElements(By.xpath(
                            "//div[contains(@class, 'space-y') or contains(@class, 'gap')]/div[contains(@class, 'border') or contains(@class, 'rounded') or contains(@class, 'card')] | //button[text()='0' or text()='1' or text()='2' or text()='3' or text()='4' or text()='5']"
                    ));

                    List<WebElement> visibleCards = new java.util.ArrayList<>();
                    for (WebElement c : optionCards) {
                        if (c.isDisplayed()) {
                            visibleCards.add(c);
                        }
                    }

                    if (!visibleCards.isEmpty()) {
                        int cardIdx = Math.min(rating, visibleCards.size() - 1);
                        System.out.println("[MONTHLY ASSESSMENT] Step " + step + ": Selecting option card at index " + cardIdx + " (Rating " + rating + ")...");
                        clickElement(driver, visibleCards.get(cardIdx));
                        actionTaken = true;
                        lastActionTime = System.currentTimeMillis();
                        Thread.sleep(600);
                    }
                }

                // 3. Click "Continue >", "Next", "Submit", "Complete" or "Finish" buttons if visible
                List<WebElement> actionBtns = driver.findElements(By.xpath(
                        "//button[contains(text(),'Continue') or contains(text(),'Next') or contains(text(),'Submit') or contains(text(),'Complete') or contains(text(),'Finish')]"
                ));

                for (WebElement btn : actionBtns) {
                    if (btn.isDisplayed() && !btn.getText().toLowerCase().contains("log out") && !btn.getText().toLowerCase().contains("switch")) {
                        System.out.println("[MONTHLY ASSESSMENT] Step " + step + ": Clicking '" + btn.getText().trim() + "' button...");
                        clickElement(driver, btn);
                        actionTaken = true;
                        lastActionTime = System.currentTimeMillis();
                        Thread.sleep(1000);
                        break;
                    }
                }

                // Enforce 10 seconds timeout rule: fail test if stuck or element not found/interacted with after 10 seconds
                if (!actionTaken && (System.currentTimeMillis() - lastActionTime > 10000)) {
                    System.err.println("[ERROR] Monthly assessment failed: No clickable assessment element found within 10 seconds limit!");
                    Assertions.fail("Monthly assessment stuck: Element not found or interactable after 10 seconds.");
                }

                Thread.sleep(300);
            }
        } catch (AssertionError e) {
            throw e;
        } catch (Exception e) {
            System.out.println("[MONTHLY ASSESSMENT] Notice: " + e.getMessage());
        } finally {
            try {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
            } catch (Exception ignored) {}
        }
        System.out.println("==================================================");
    }

    public static void reLoginIfOnLoginPage(WebDriver driver) {
        try {
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl != null && currentUrl.contains("login")) {
                pages.LoginPage loginPage = new pages.LoginPage();
                String email = stepdefinitions.LoginSteps.getLastUsedEmail();
                String password = stepdefinitions.LoginSteps.getLastUsedPassword();
                if (email == null || email.isEmpty()) {
                    email = ConfigReader.getProperty("patient.email");
                }
                if (password == null || password.isEmpty()) {
                    password = ConfigReader.getProperty("patient.password");
                }

                if (email != null && !email.isEmpty()) {
                    System.out.println("[INFO] Re-logging in as " + email + " after monthly assessment redirect...");
                    loginPage.enterEmail(email);
                    loginPage.enterPassword(password != null ? password : "Testing@123");
                    loginPage.clickLogin();
                    try { Thread.sleep(2000); } catch (Exception ignored) {}
                }
            }
        } catch (Exception e) {
            System.out.println("[MONTHLY ASSESSMENT] Notice during re-login: " + e.getMessage());
        }
    }

    private static void clickElement(WebDriver driver, WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            Thread.sleep(50);
        } catch (Exception ignored) {}

        try {
            element.click();
        } catch (Exception e) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            } catch (Exception ignored) {}
        }
    }
}
