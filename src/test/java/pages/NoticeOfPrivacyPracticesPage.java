package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;
import java.time.Duration;
import java.util.List;

public class NoticeOfPrivacyPracticesPage extends BasePage {

    By pageHeader = By.xpath("//*[contains(text(),'Notice of Privacy Practices')]");



    By effectiveDate = By.xpath(
        "//*[contains(text(),'Effective Date')]"
    );


    By downloadCopyBtn = By.xpath(
        "//button[contains(normalize-space(),'Download a copy')]" +
        " | //button[contains(normalize-space(),'Download')][@class]"
    );

    By readFullNoticeLink = By.xpath(
        "//*[contains(normalize-space(.),'Read full Privacy Notice')" +
        " and not(.//*[contains(normalize-space(.),'Read full Privacy Notice')])]"
    );

    By previousVersionsHeader = By.xpath(
        "//*[contains(text(),'Previous versions')]"
    );

    By backArrowLink = By.xpath(
        "(//a[contains(@href,'/patient/profile')] | //*[contains(@class,'rounded-full')][.//*[name()='svg']])[1]"
    );

    By fullDocumentModalHeader = By.xpath(
        "//*[contains(normalize-space(),'Full Document')]" +
        " | //div[@role='dialog']//*[contains(normalize-space(),'Notice of Privacy Practices')]"
    );
    By fullDocumentDownloadPdfBtn = By.xpath("//button[contains(normalize-space(),'Download PDF')]");
    By fullDocumentCloseBtn = By.xpath("//button[normalize-space()='Close']");

    private WebElement waitForVisible(By locator) {
        WebDriverWait localWait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(25));
        return localWait.until(drv -> {
            List<WebElement> elements = drv.findElements(locator);
            for (WebElement e : elements) {
                try {
                    if (e.isDisplayed()) return e;
                } catch (StaleElementReferenceException ignored) {}
            }
            return null;
        });
    }

    public boolean isPageLoaded() {
        return waitForVisible(pageHeader).isDisplayed() && driver.getCurrentUrl().contains("/npp");
    }

    private boolean isContentPresentNearLabel(String[] candidateLabelPhrases, int timeoutSeconds) {
        WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        int maxLevels = 3;

        try {
            return Boolean.TRUE.equals(localWait.until(drv -> {
                for (String phrase : candidateLabelPhrases) {
                    String upperExpr = "translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')";
                    By labelLocator = By.xpath("//*[contains(" + upperExpr + ",'" + phrase.toUpperCase() + "')]");
                    List<WebElement> labels = drv.findElements(labelLocator);

                    for (WebElement label : labels) {
                        try {
                            String labelOwnText = label.getText() == null ? "" : label.getText().trim();
                            String remainder = labelOwnText.toUpperCase().replace(phrase.toUpperCase(), "").trim();
                           
                            if (remainder.length() > 2) {
                                return Boolean.TRUE;
                            }

                           
                            WebElement container = label;
                            for (int level = 0; level < maxLevels; level++) {
                                container = (WebElement) ((JavascriptExecutor) drv)
                                        .executeScript("return arguments[0].parentElement;", container);
                                if (container == null) break;
                                String containerText = container.getText();
                                if (containerText == null) continue;
                                String leftover = containerText.toUpperCase().replace(phrase.toUpperCase(), "").trim();
                                if (leftover.length() > 2) {
                                    return Boolean.TRUE;
                                }
                            }
                        } catch (StaleElementReferenceException ignored) {}
                    }
                }
                return null; // keep polling
            }));
        } catch (org.openqa.selenium.TimeoutException e) {
            dumpNearbyHtmlForDebug(candidateLabelPhrases);
            return false;
        }
    }

    private void dumpNearbyHtmlForDebug(String[] candidateLabelPhrases) {
        try {
            for (String phrase : candidateLabelPhrases) {
                String upperExpr = "translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')";
                List<WebElement> labels = driver.findElements(By.xpath("//*[contains(" + upperExpr + ",'" + phrase.toUpperCase() + "')]"));
                if (labels.isEmpty()) {
                    System.out.println("[DEBUG] No element containing '" + phrase + "' found on page.");
                    continue;
                }
                WebElement container = labels.get(0);
                for (int i = 0; i < 3 && container != null; i++) {
                    WebElement parent = (WebElement) ((JavascriptExecutor) driver)
                            .executeScript("return arguments[0].parentElement;", container);
                    if (parent == null) break;
                    container = parent;
                }
                String html = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].outerHTML;", container);
                System.out.println("[DEBUG] Found label '" + phrase + "' but no extra value text nearby. HTML around it:\n" + html);
            }
        } catch (Exception ex) {
            System.out.println("[DEBUG] Could not capture debug HTML: " + ex.getMessage());
        }
    }

    public boolean isCurrentVersionDisplayed() {
        boolean versionPresent = isContentPresentNearLabel(
            new String[]{"CURRENT NOTICE VERSION", "Notice Version", "Version"}, 15
        );
        boolean datePresent = isContentPresentNearLabel(
            new String[]{"Effective Date", "Effective"}, 15
        );
        return versionPresent && datePresent;
    }


    private String findStatusNearLabel(String labelContainsText, String[] orderedStatusKeywords, int timeoutSeconds) {
        String upperExpr = "translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')";
        By labelLocator = By.xpath("//*[contains(" + upperExpr + ",'" + labelContainsText.toUpperCase() + "')]");
        int maxLevels = 4;

        WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        try {
            return localWait.until(drv -> {
                List<WebElement> labels = drv.findElements(labelLocator);
                for (WebElement label : labels) {
                    WebElement container = label;
                    for (int level = 0; level <= maxLevels; level++) {
                        try {
                            String containerText = container.getText();
                            if (containerText != null) {
                                for (String keyword : orderedStatusKeywords) {
                                    if (containerText.toLowerCase().contains(keyword.toLowerCase())) {
                                        return keyword;
                                    }
                                }
                            }
                            container = (WebElement) ((JavascriptExecutor) drv)
                                    .executeScript("return arguments[0].parentElement;", container);
                            if (container == null) break;
                        } catch (StaleElementReferenceException ignored) {
                            break;
                        }
                    }
                }
                return null;
            });
        } catch (org.openqa.selenium.TimeoutException e) {
            try {
                List<WebElement> labels = driver.findElements(labelLocator);
                if (labels.isEmpty()) {
                    System.out.println("[DEBUG] No element containing '" + labelContainsText + "' found on page.");
                } else {
                    WebElement container = labels.get(0);
                    for (int i = 0; i < 3 && container != null; i++) {
                        WebElement parent = (WebElement) ((JavascriptExecutor) driver)
                                .executeScript("return arguments[0].parentElement;", container);
                        if (parent == null) break;
                        container = parent;
                    }
                    String html = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].outerHTML;", container);
                    System.out.println("[DEBUG] Found label '" + labelContainsText + "' but no status keyword matched nearby. HTML around it:\n" + html);
                }
            } catch (Exception ignored) {}
            return "Not Found";
        }
    }

    public String getAcknowledgementStatusText() {
        final String[] validStatuses = {"Not Acknowledged", "Acknowledged"};
        String status = findStatusNearLabel(
            "ACKNOWLEDGEMENT STATUS",
            validStatuses,
            20
        );

        if (status == null || status.trim().isEmpty() || "Not Found".equalsIgnoreCase(status)) {
            return "Not Found";
        }

        return status.trim().replaceAll("\\s+", " ");
    }

    public void clickDownloadCopy() {
        WebElement btn = waitForVisible(downloadCopyBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public boolean isDownloadButtonPresentAndClickable() {
        try {
            WebElement btn = waitForVisible(downloadCopyBtn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
            return btn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPreviousVersionsSectionDisplayed() {
        try {
            return waitForVisible(previousVersionsHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickReadFullPrivacyNotice() {
        WebElement link = waitForVisible(readFullNoticeLink);
        try {
            String tag = link.getTagName();
            String html = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].outerHTML;", link);
            System.out.println("[DEBUG] Clicking 'Read full Privacy Notice' element <" + tag + ">: " + html);
        } catch (Exception ignored) {}
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
    }

    public void clickBackToProfile() {
        try {
            WebElement btn = waitForVisible(backArrowLink);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        } catch (Exception e) {
            String currentUrl = driver.getCurrentUrl();
            driver.get(currentUrl.replace("/patient/npp", "/patient/profile"));
        }
    }

    public boolean isFullDocumentModalDisplayed() {
        try {
            WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(20));
            return localWait.until(drv -> {
                List<WebElement> els = drv.findElements(fullDocumentModalHeader);
                for (WebElement e : els) {
                    try {
                        if (e.isDisplayed()) return Boolean.TRUE;
                    } catch (StaleElementReferenceException ignored) {}
                }
                return null;
            });
        } catch (Exception e) {
            System.out.println("[DEBUG] Full document modal never appeared within timeout.");
            try {
                List<WebElement> anyDialogs = driver.findElements(By.xpath("//div[@role='dialog']"));
                if (anyDialogs.isEmpty()) {
                    System.out.println("[DEBUG] No element with role='dialog' exists on the page at all — the click likely did not trigger anything.");
                } else {
                    for (WebElement dialog : anyDialogs) {
                        String html = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].outerHTML;", dialog);
                        System.out.println("[DEBUG] A dialog IS present but didn't match fullDocumentModalHeader locator. Its HTML:\n" + html);
                    }
                }
            } catch (Exception ignored) {}
            return false;
        }
    }

    public boolean isFullDocumentDownloadPdfButtonDisplayed() {
        try {
            return waitForVisible(fullDocumentDownloadPdfBtn) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public void closeFullDocumentModal() {
        try {
            WebElement btn = waitForVisible(fullDocumentCloseBtn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        } catch (Exception ignored) {}
    }
}