package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {
        // Prevent instantiation
    }

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            String browser = ConfigReader.getProperty("browser");
            if (browser == null) {
                browser = "chrome";
            }
            browser = browser.toLowerCase();

            WebDriver driver;
            switch (browser) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    String headlessProp = ConfigReader.getProperty("headless");
                    if (headlessProp != null && headlessProp.equalsIgnoreCase("true")) {
                        chromeOptions.addArguments("--headless=new");
                    }
                    chromeOptions.addArguments("--window-size=1920,1080");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--disable-autofill");
                    chromeOptions.addArguments("--disable-single-click-autofill");
                    chromeOptions.addArguments("--disable-features=AutofillServerCommunication");
                    chromeOptions.addArguments("--disable-save-password-bubble");

                    // Auto-approve browser notification permission requests.
                    // Without this, Chrome shows a native "Allow notifications?"
                    // popup that Selenium cannot interact with. If the app's
                    // "Push Notifications" toggle depends on the Notification
                    // permission actually being granted (checking
                    // Notification.permission === 'granted' before it will flip
                    // its internal state to true), the toggle click will appear
                    // to do nothing - it's stuck because the permission prompt
                    // was silently left unanswered ("default"), not because the
                    // click itself failed.
                    java.util.Map<String, Object> chromePrefs = new java.util.HashMap<>();
                    chromePrefs.put("profile.default_content_setting_values.notifications", 1); // 1 = allow, 2 = block
                    chromeOptions.setExperimentalOption("prefs", chromePrefs);
                    // NOTE: Do NOT add "--disable-notifications" here (with or
                    // without "=false"). Chromium switches like this are
                    // boolean-by-presence - appending "=false" does not negate
                    // it, it still disables the Notification API entirely,
                    // which is the opposite of what we want and silently makes
                    // any "enable push notifications" toggle impossible to
                    // turn on. The "prefs" map above is the correct way to
                    // pre-grant the permission.

                    driver = new ChromeDriver(chromeOptions);
                    break;
                // case "firefox":
                // FirefoxOptions firefoxOptions = new FirefoxOptions();
                // // firefoxOptions.addArguments("-headless");
                // driver = new FirefoxDriver(firefoxOptions);
                // break;
                // case "safari":
                // driver = new SafariDriver();
                // break;
                default:
                    throw new RuntimeException("Unsupported browser: " + browser);
            }

            driver.manage().window().maximize();
            int timeout = Integer.parseInt(ConfigReader.getProperty("timeout"));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
            driverThreadLocal.set(driver);
        }
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            try {
                driverThreadLocal.get().quit();
            } catch (Exception e) {
                System.err.println("Error quitting driver: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }
}