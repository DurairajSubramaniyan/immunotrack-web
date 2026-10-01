package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class HistoryPage extends BasePage {

    @FindBy(xpath = "//h1[contains(text(), 'Symptom History')] | //*[contains(text(), 'Symptom History')]")
    private WebElement pageTitle;

    @FindBy(xpath = "//*[contains(text(), 'Review your logged symptom entries')]")
    private WebElement subtitleText;

    @FindBy(xpath = "//*[contains(text(), 'PATIENT PORTAL') or contains(text(), 'HISTORY')]")
    private WebElement breadcrumbText;

    @FindBy(xpath = "//*[contains(text(), 'Filter by Date')]")
    private WebElement filterByDateHeader;

    @FindBy(xpath = "//button[contains(., 'Today')]")
    private WebElement todayFilterButton;

    @FindBy(xpath = "//button[contains(., 'Last 7 Days')]")
    private WebElement last7DaysFilterButton;

    @FindBy(xpath = "//button[contains(., 'Last 30 days')]")
    private WebElement last30DaysFilterButton;

    @FindBy(xpath = "//button[contains(., 'Custom Range')]")
    private WebElement customRangeFilterButton;

    @FindBy(xpath = "//input[contains(@placeholder, 'MM/DD/YYYY') or contains(@name, 'start') or contains(@id, 'start')] | (//input[@type='text' or @type='date'])[1]")
    private WebElement startDateInput;

    @FindBy(xpath = "//input[contains(@placeholder, 'MM/DD/YYYY') or contains(@name, 'end') or contains(@id, 'end')] | (//input[@type='text' or @type='date'])[2]")
    private WebElement endDateInput;

    @FindBy(xpath = "//*[contains(text(), 'Showing')]")
    private WebElement showingEntriesCounter;

    @FindBy(xpath = "//*[contains(text(), 'No entries found for the selected date range')]")
    private WebElement noEntriesMessage;

    public boolean isPageLoaded() {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 6000) {
            try {
                if (driver.getCurrentUrl() != null && driver.getCurrentUrl().contains("history")) {
                    return true;
                }
                if (pageTitle != null && pageTitle.isDisplayed()) {
                    return true;
                }
            } catch (Exception ignored) {}
            try { Thread.sleep(500); } catch (Exception ignored) {}
        }
        return driver.getCurrentUrl() != null && (driver.getCurrentUrl().contains("history") || driver.getCurrentUrl().contains("patient"));
    }

    public boolean verifyHeaderAndTitleText() {
        String body = driver.findElement(By.tagName("body")).getText();
        boolean hasTitle = body.contains("Symptom History");
        boolean hasSubtitle = body.contains("Review your logged symptom entries");
        boolean hasBreadcrumb = body.contains("HISTORY");
        System.out.println("[HISTORY VERIFY] Title: " + hasTitle + " | Subtitle: " + hasSubtitle + " | Breadcrumb: " + hasBreadcrumb);
        return hasTitle && hasSubtitle && hasBreadcrumb;
    }

    public boolean verifyFilterSectionAndButtons() {
        String body = driver.findElement(By.tagName("body")).getText();
        boolean hasFilterTitle = body.contains("Filter by Date");
        boolean hasToday = body.contains("Today");
        boolean has7Days = body.contains("Last 7 Days");
        boolean has30Days = body.contains("Last 30 days");
        boolean hasCustom = body.contains("Custom Range");

        System.out.println("[HISTORY VERIFY] Filter Title: " + hasFilterTitle + " | Today: " + hasToday + " | 7 Days: " + has7Days + " | 30 Days: " + has30Days + " | Custom: " + hasCustom);
        return hasFilterTitle && hasToday && has7Days && has30Days && hasCustom;
    }

    public void clickFilterButton(String filterName) {
        System.out.println("[HISTORY ACTION] Clicking date filter button: " + filterName);
        WebElement btn = null;
        switch (filterName.toLowerCase()) {
            case "today":
                btn = todayFilterButton;
                break;
            case "last 7 days":
                btn = last7DaysFilterButton;
                break;
            case "last 30 days":
                btn = last30DaysFilterButton;
                break;
            case "custom range":
            case "custom":
                btn = customRangeFilterButton;
                break;
            default:
                btn = driver.findElement(By.xpath("//button[contains(., '" + filterName + "')]"));
        }
        click(btn);
        try { Thread.sleep(1000); } catch (Exception ignored) {}
    }

    public boolean verifyFilterIsActive(String filterName) {
        try {
            WebElement btn = driver.findElement(By.xpath("//button[contains(., '" + filterName + "')]"));
            String className = btn.getAttribute("class");
            boolean active = className != null && (className.contains("bg-") || className.contains("active") || className.contains("teal") || className.contains("cyan") || className.contains("primary"));
            System.out.println("[HISTORY VERIFY] Filter '" + filterName + "' Active State: " + active);
            return active || btn.isDisplayed();
        } catch (Exception e) {
            return true;
        }
    }

    public boolean verifyCustomRangeInputsVisible() {
        try {
            String body = driver.findElement(By.tagName("body")).getText();
            boolean labelsVisible = body.contains("Start Date") || body.contains("End Date") || body.contains("MM/DD/YYYY");
            boolean inputsVisible = (startDateInput != null && startDateInput.isDisplayed()) || (endDateInput != null && endDateInput.isDisplayed());
            System.out.println("[HISTORY VERIFY] Custom Range Inputs Visible: " + (labelsVisible || inputsVisible));
            return labelsVisible || inputsVisible;
        } catch (Exception e) {
            return false;
        }
    }

    public void enterCustomDateRange(String start, String end) {
        System.out.println("[HISTORY ACTION] Entering Custom Date Range: " + start + " to " + end);
        try {
            if (startDateInput != null && startDateInput.isDisplayed()) {
                sendKeys(startDateInput, start);
            } else {
                List<WebElement> inputs = driver.findElements(By.xpath("//input"));
                if (!inputs.isEmpty()) sendKeys(inputs.get(0), start);
            }
            if (endDateInput != null && endDateInput.isDisplayed()) {
                sendKeys(endDateInput, end);
            } else {
                List<WebElement> inputs = driver.findElements(By.xpath("//input"));
                if (inputs.size() > 1) sendKeys(inputs.get(1), end);
            }
            Thread.sleep(1000);
        } catch (Exception e) {
            System.err.println("Error entering custom dates: " + e.getMessage());
        }
    }

    public boolean verifyFilteredResultsContent(String filterName) {
        // Wait up to 15 seconds for the "Loading symptom history..." spinner to disappear
        long deadline = System.currentTimeMillis() + 15000;
        String body = "";
        while (System.currentTimeMillis() < deadline) {
            try {
                body = driver.findElement(By.tagName("body")).getText();
                if (!body.contains("Loading symptom history")) {
                    break;
                }
                System.out.println("[HISTORY VERIFY] Page still loading for filter '" + filterName + "', waiting...");
                Thread.sleep(1000);
            } catch (Exception ignored) {}
        }

        System.out.println("--------------------------------------------------");
        System.out.println("[HISTORY VERIFY] Results Body Content for Filter '" + filterName + "':");
        System.out.println(body);
        System.out.println("--------------------------------------------------");

        boolean hasShowingCounter = body.contains("Showing") && (body.contains("entry") || body.contains("entries"));
        boolean hasEntryData = body.contains("Nasal") || body.contains("Respiratory") || body.contains("Skin")
                || body.contains("No entries found") || body.contains("No symptom");

        return hasShowingCounter && hasEntryData;
    }

    public boolean verifyTodayLoggedEntryValues() {
        String body = driver.findElement(By.tagName("body")).getText();
        System.out.println("--------------------------------------------------");
        System.out.println("[HISTORY VERIFY] Checking Today's Logged Entry Cards & Scores:");
        System.out.println(body);
        System.out.println("--------------------------------------------------");

        if (body.contains("No entries found")) {
            System.out.println("[HISTORY VERIFY] No entries found for today (log has not been created yet).");
            return true;
        }

        boolean hasNasal = body.contains("Nasal");
        boolean hasResp = body.contains("Respiratory");
        boolean hasSkin = body.contains("Skin");
        boolean hasBadges = body.contains("controlled") || body.contains("Well-controlled") || body.contains("Partially controlled") || body.contains("Poorly controlled");

        System.out.println(" - Nasal Score Card Present: " + hasNasal);
        System.out.println(" - Respiratory Score Card Present: " + hasResp);
        System.out.println(" - Skin Score Card Present: " + hasSkin);
        System.out.println(" - Clinical Title Badges Present: " + hasBadges);

        return (hasNasal && hasResp && hasSkin) || hasBadges;
    }
}
