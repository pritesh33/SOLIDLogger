package com.solidlogger;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class to launch Google, assert its title, and perform a simple search.
 * Extends BaseTest to inherit WebDriver setup and teardown.
 */
public class TestLogin extends TestBase {

    /**
     * Test case: Launch Google.com and assert its title.
     * This test method inherits the WebDriver setup and teardown from BaseTest.
     */
    @Test
    public void testLaunchGoogleAndAssertTitle() {
        logger.info("Executing test: testLaunchGoogleAndAssertTitle");
        String url = "https://www.google.com";
        String expectedTitle = "Google";

        logger.info("Navigating to URL: {}", url);
        driver.get(url);
        logger.debug("Current URL: {}", driver.getCurrentUrl());

        String actualTitle = driver.getTitle();
        logger.info("Actual page title: '{}'", actualTitle);

        try {
            Assert.assertEquals(actualTitle, expectedTitle, "Page title mismatch!");
            logger.info("Assertion Passed: Page title is '{}' as expected.", expectedTitle);
        } catch (AssertionError e) {
            logger.error("Assertion Failed: Expected title '{}' but found '{}'. Error: {}", expectedTitle, actualTitle, e.getMessage());
            throw e; // Re-throw to make TestNG mark the test as failed
        }
    }
}
