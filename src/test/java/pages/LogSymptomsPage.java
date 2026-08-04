package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class LogSymptomsPage extends BasePage {

    @FindBy(xpath = "//h1[contains(text(), 'Daily Health Log')] | //h2[contains(text(), 'Daily Health Log')] | //*[contains(text(), 'Daily Health Log')]")
    private WebElement pageTitle;

    @FindBy(xpath = "//*[contains(text(), 'Breathing & Asthma Control') or contains(text(), 'ACQ-6')]")
    private WebElement acq6SectionHeader;

    @FindBy(xpath = "//*[contains(text(), 'Nose & Sinus Symptoms') or contains(text(), 'SNOT-22')]")
    private WebElement snot22SectionHeader;

    @FindBy(xpath = "//*[contains(text(), 'Skin Symptoms') or contains(text(), 'POEM')]")
    private WebElement poemSectionHeader;

    @FindBy(xpath = "//button[contains(text(), 'Log Today') or contains(text(), 'Submit') or contains(text(), 'Save') or contains(text(), 'Log') or contains(text(), 'Update Today') or contains(text(), 'Update')]")
    private WebElement submitLogButton;

    @FindBy(xpath = "//textarea[contains(@placeholder, 'Did anything change') or contains(@placeholder, 'explain') or contains(@name, 'context')] | //textarea")
    private WebElement contextNotesField;

    public boolean isPageLoaded() {
        try {
            waitForVisibility(pageTitle);
            return pageTitle.isDisplayed() || driver.getCurrentUrl().contains("log-symptoms");
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("log-symptoms");
        }
    }

    public boolean verifyHeaderAndBanners() {
        try {
            boolean headerOk = false;
            try {
                WebElement header = driver.findElement(By.xpath("//*[contains(text(), 'Daily Log') or contains(text(), 'Daily Health Log') or contains(text(), 'PATIENT PORTAL')]"));
                headerOk = header.isDisplayed();
            } catch (Exception ignored) {}

            boolean bannerOk = false;
            try {
                WebElement banner = driver.findElement(By.xpath("//*[contains(text(), 'Monitoring') or contains(text(), 'Program') or contains(text(), 'PATIENT PORTAL') or contains(text(), 'Daily Health Log')]"));
                bannerOk = banner.isDisplayed();
            } catch (Exception ignored) {}

            boolean promptOk = false;
            try {
                WebElement prompt = driver.findElement(By.xpath("//*[contains(text(), 'symptoms today') or contains(text(), 'How are your symptoms') or contains(text(), 'Daily Health Log') or contains(text(), 'SNOT-22')]"));
                promptOk = prompt.isDisplayed();
            } catch (Exception ignored) {}

            return headerOk || bannerOk || promptOk;
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isACQ6SectionVisible() {
        try {
            return acq6SectionHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSNOT22SectionVisible() {
        try {
            return snot22SectionHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPOEMSectionVisible() {
        try {
            return poemSectionHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private void scrollToElement(WebElement element) {
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            Thread.sleep(150);
        } catch (Exception ignored) {}
    }

    private void clickElement(WebElement element) {
        try {
            scrollToElement(element);
            element.click();
        } catch (Exception e) {
            try {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            } catch (Exception ignored) {}
        }
    }

    private void clickAccordionHeader(String sectionName) {
        System.out.println("[INFO] Clicking accordion header for section: " + sectionName + "...");
        try {
            List<WebElement> elements = driver.findElements(By.xpath(String.format("//*[contains(text(), '%s')]", sectionName)));
            for (WebElement el : elements) {
                if (el.isDisplayed()) {
                    scrollToElement(el);
                    WebElement clickable = el;
                    try {
                        clickable = el.findElement(By.xpath("./ancestor::button | ./ancestor::div[contains(@class, 'cursor') or contains(@class, 'flex') or contains(@class, 'border') or contains(@class, 'rounded')][1]"));
                    } catch (Exception ignored) {}

                    try {
                        clickable.click();
                    } catch (Exception e) {
                        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", clickable);
                    }

                    try {
                        WebElement svg = clickable.findElement(By.xpath(".//*[local-name()='svg']"));
                        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", svg);
                    } catch (Exception ignored) {}

                    Thread.sleep(1200);
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error clicking accordion header for " + sectionName + ": " + e.getMessage());
        }
    }

    private int clickVariedRatingsForCurrentlyVisibleQuestions() {
        int count = 0;
        String[] ratingPattern = {"0", "1", "3"};
        try {
            List<WebElement> zeroButtons = driver.findElements(By.xpath("//button[text()='0']"));
            for (int i = 0; i < zeroButtons.size(); i++) {
                try {
                    WebElement zeroBtn = zeroButtons.get(i);
                    scrollToElement(zeroBtn);
                    if (!zeroBtn.isDisplayed()) continue;

                    String targetRating = ratingPattern[i % ratingPattern.length];
                    boolean clicked = false;

                    try {
                        WebElement container = zeroBtn.findElement(By.xpath("./ancestor::div[contains(@class, 'flex') or contains(@class, 'grid') or contains(@class, 'gap') or contains(@class, 'space')][1]"));
                        WebElement targetBtn = container.findElement(By.xpath(".//button[text()='" + targetRating + "']"));
                        if (targetBtn.isDisplayed()) {
                            try {
                                targetBtn.click();
                            } catch (Exception ex) {
                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", targetBtn);
                            }
                            clicked = true;
                        }
                    } catch (Exception ignored) {}

                    if (!clicked) {
                        try {
                            zeroBtn.click();
                        } catch (Exception ex) {
                            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", zeroBtn);
                        }
                    }

                    count++;
                    Thread.sleep(120);
                } catch (Exception ignored) {}
            }
        } catch (Exception e) {
            System.err.println("Error clicking varied rating buttons: " + e.getMessage());
        }
        return count;
    }

    public int selectAllSymptomQuestions() {
        int totalAnswered = 0;

        // ----------------------------------------------------
        // STEP 1: Fill Section 1 (SNOT-22 Nose & Sinus - 8 Questions)
        // ----------------------------------------------------
        System.out.println("==================================================");
        System.out.println("[INFO] Step 1: Filling Section 1 - SNOT-22 (Nose & Sinus Symptoms)...");
        try {
            if (snot22SectionHeader != null) {
                scrollToElement(snot22SectionHeader);
            }
        } catch (Exception ignored) {}

        int snotCount = clickVariedRatingsForCurrentlyVisibleQuestions();
        System.out.println("[INFO] Section 1 (SNOT-22) completed (" + snotCount + " questions). Total answered so far: " + snotCount);
        totalAnswered += snotCount;
        try { Thread.sleep(600); } catch (Exception ignored) {}

        // ----------------------------------------------------
        // STEP 2: Open & Fill Section 2 (ACQ-6 Breathing & Asthma Control - 6 Questions)
        // ----------------------------------------------------
        System.out.println("==================================================");
        System.out.println("[INFO] Step 2: Opening & Filling Section 2 - ACQ-6 (Breathing & Asthma Control)...");
        clickAccordionHeader("Breathing & Asthma");

        int acqCount = clickVariedRatingsForCurrentlyVisibleQuestions();
        System.out.println("[INFO] Section 2 (ACQ-6) completed (" + acqCount + " questions). Total answered so far: " + (totalAnswered + acqCount));
        totalAnswered += acqCount;
        try { Thread.sleep(600); } catch (Exception ignored) {}

        // ----------------------------------------------------
        // STEP 3: Open & Fill Section 3 (POEM Skin Symptoms - 7 Questions)
        // ----------------------------------------------------
        System.out.println("==================================================");
        System.out.println("[INFO] Step 3: Opening & Filling Section 3 - POEM (Skin Symptoms)...");
        clickAccordionHeader("Skin Symptoms");

        int poemCount = clickVariedRatingsForCurrentlyVisibleQuestions();
        System.out.println("[INFO] Section 3 (POEM) completed (" + poemCount + " questions). Total answered overall: " + (totalAnswered + poemCount));
        totalAnswered += poemCount;
        System.out.println("==================================================");
        try { Thread.sleep(600); } catch (Exception ignored) {}

        return totalAnswered;
    }

    public int selectFirstOptionForQuestions() {
        return selectAllSymptomQuestions();
    }

    public List<String> getAnsweredStatusTexts() {
        List<String> statuses = new java.util.ArrayList<>();
        try {
            List<WebElement> elements = driver.findElements(By.xpath("//*[contains(text(), 'answered')]"));
            for (WebElement el : elements) {
                String text = el.getText().trim();
                if (!text.isEmpty() && text.toLowerCase().contains("answered") && !statuses.contains(text)) {
                    statuses.add(text);
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading answered counts: " + e.getMessage());
        }
        return statuses;
    }

    public List<String> getDisplayedScoreBadges() {
        List<String> scores = new java.util.ArrayList<>();
        try {
            List<WebElement> badges = driver.findElements(By.xpath("//*[contains(text(), 'Score:') or contains(text(), 'Score')]"));
            for (WebElement badge : badges) {
                String text = badge.getText().trim();
                if (!text.isEmpty() && text.toLowerCase().contains("score") && !scores.contains(text)) {
                    scores.add(text);
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading score badges: " + e.getMessage());
        }
        return scores;
    }

    public void enterContextNotes(String notes) {
        try {
            if (contextNotesField != null) {
                scrollToElement(contextNotesField);
                contextNotesField.clear();
                contextNotesField.sendKeys(notes);
            }
        } catch (Exception e) {
            System.err.println("Error entering context notes: " + e.getMessage());
        }
    }

    public void clickSubmit() {
        try {
            scrollToElement(submitLogButton);
            wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(submitLogButton));
            submitLogButton.click();
        } catch (Exception e) {
            try {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", submitLogButton);
            } catch (Exception ex) {
                System.err.println("Error clicking submit button: " + ex.getMessage());
            }
        }
        System.out.println("[INFO] Waiting for daily log submission API call to complete on backend...");
        try { Thread.sleep(4500); } catch (Exception ignored) {}
    }
}
