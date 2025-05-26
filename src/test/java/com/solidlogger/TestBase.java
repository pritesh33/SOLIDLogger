package com.solidlogger;

import com.solidlogger.utils.LoggerUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * BaseTest class to handle common WebDriver setup and teardown for all test classes.
 * It also provides a logger instance for all extending test classes.
 */
public class TestBase {

    protected WebDriver driver;
    protected final Logger logger;

    /**
     * Constructor to initialize the logger for the BaseTest class.
     * The logger instance is obtained from the LoggerUtil Singleton.
     */
    public TestBase() {
        this.logger = LoggerUtil.getInstance().getLogger();
    }

    /**
     * Setup method to initialize WebDriver before each test method.
     * Uses TestNG's @BeforeMethod annotation.
     * Configures ChromeDriver with headless options and uses WebDriverManager for automatic setup.
     */
    @BeforeMethod
    public void setup() {
        logger.info("--- Setting up WebDriver for a new test ---");
        try {
            // Automatically downloads and sets up the ChromeDriver executable
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            // Add common Chrome options for stable execution
            options.addArguments("--headless"); // Run in headless mode (no UI)
            options.addArguments("--disable-gpu"); // Required for headless on some systems
            options.addArguments("--window-size=1920,1080"); // Set a consistent window size
            options.addArguments("--no-sandbox"); // Bypass OS security model (needed for some CI environments)
            options.addArguments("--disable-dev-shm-usage"); // Overcomes limited resource problems in Docker/CI

            driver = new ChromeDriver(options);
            logger.info("Chrome browser launched successfully in headless mode.");
        } catch (Exception e) {
            logger.error("Failed to launch Chrome browser: {}", e.getMessage(), e);
            // Fail the test if browser doesn't launch
            Assert.fail("Browser launch failed: " + e.getMessage());
        }
    }

    /**
     * Teardown method to quit WebDriver after each test method.
     * Uses TestNG's @AfterMethod annotation.
     * Ensures the browser is closed even if a test fails.
     */
    @AfterMethod
    public void teardown() {
        logger.info("--- Tearing down WebDriver after test ---");
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed successfully.");
        }
    }
}
